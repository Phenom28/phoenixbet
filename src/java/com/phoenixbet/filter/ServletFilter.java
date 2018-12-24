package com.phoenixbet.filter;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminFilter;
import com.github.adminfaces.template.session.AdminSession;
import static com.github.adminfaces.template.util.Assert.has;
import com.github.adminfaces.template.util.Constants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Ogundipe Segun David
 */
@WebFilter(urlPatterns = {"/*"})
public class ServletFilter implements Filter {

    private static final String FACES_RESOURCES = "/javax.faces.resource";
    private static final Logger LOG = Logger.getLogger(AdminFilter.class.getName());

    private String loginPage;
    private String errorPage;
    private String indexPage;

    @Inject
    AdminSession adminSession;

    @Inject
    AdminConfig adminConfig;

    private final List<String> ignoredResources = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            loginPage = filterConfig.getServletContext().getInitParameter(Constants.InitialParams.LOGIN_PAGE);
            if (!has(loginPage)) {
                loginPage = has(adminConfig) ? adminConfig.getLoginPage() : Constants.DEFAULT_LOGIN_PAGE;
            }
            errorPage = filterConfig.getServletContext().getInitParameter(Constants.InitialParams.ERROR_PAGE);
            if (!has(errorPage)) {
                errorPage = Constants.DEFAULT_ERROR_PAGE;
            }
            indexPage = filterConfig.getServletContext().getInitParameter(Constants.InitialParams.INDEX_PAGE);
            if (!has(indexPage)) {
                indexPage = has(adminConfig) ? adminConfig.getIndexPage() : Constants.DEFAULT_INDEX_PAGE;
            }

            //removes leading '/'
            errorPage = errorPage.startsWith("/") ? errorPage.substring(1) : errorPage;
            loginPage = loginPage.startsWith("/") ? loginPage.substring(1) : loginPage;
            indexPage = indexPage.startsWith("/") ? indexPage.substring(1) : indexPage;

            ignoredResources.add("/" + loginPage.substring(0, loginPage.lastIndexOf(".")));//we need leading slash for ignoredResources
            ignoredResources.add("/" + errorPage.substring(0, errorPage.lastIndexOf(".")));
            ignoredResources.add("/" + indexPage.substring(0, indexPage.lastIndexOf(".")));
            ignoredResources.add("/league");

            String configuredResouces = adminConfig.getIgnoredResources();
            if (has(configuredResouces)) {
                this.ignoredResources.addAll(Arrays.asList(configuredResouces.split(",")));
                for (String ignoredResource : ignoredResources) {
                    if (!ignoredResource.startsWith("/")) { //we need leading slash for ignoredResources because getServletPath (in this#skipResource) returns a string with leading slash 
                        ignoredResources.set(ignoredResources.indexOf(ignoredResource), "/" + ignoredResource);
                    }
                }
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "problem initializing admin filter", e);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (request.getRequestURI().equals(request.getContextPath() + "/admin")
                || (adminSession.isLoggedIn() && request.getRequestURI().endsWith(loginPage))) {
            response.sendRedirect(request.getContextPath() + "/admin/administrator/List.jsf");
            return;
        }

        if (skipResource(request, response) || adminSession.isLoggedIn()) {
            if (!adminSession.isUserRedirected() && adminSession.isLoggedIn() && has(request.getHeader("Referer")) && request.getHeader("Referer").contains("?page=")) {
                adminSession.setUserRedirected(true);
                response.sendRedirect(request.getContextPath() + extractPageFromURL(request.getHeader("Referer")));
                return;
            }
            try {
                chain.doFilter(req, resp);
            } catch (FileNotFoundException e) {
                LOG.log(Level.WARNING, "File not found", e);
                response.sendError(404);
            }
        } else { //resource not skipped (e.g a page that is not logon page) AND user not logged in
            redirectToLogon(request, (HttpServletResponse) resp);
        }
    }

    private String extractPageFromURL(String referer) {
        String page = referer.substring(referer.indexOf("page=") + 5);
        try {
            return URLDecoder.decode(page, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.log(Level.WARNING, "Could not extract page from url", e);
            return indexPage;
        }
    }
    
    @Override
    public void destroy() {

    }
    
    /**
     * skips faces-resources, index, error or logon pages
     *
     * @param request
     * @return true if resource must be skipped by the filter false otherwise
     */
    private boolean skipResource(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletPath();
        if(path.contains(".")) {
            path = path.substring(0,path.lastIndexOf("."));
        }
        boolean skip = path.startsWith(FACES_RESOURCES) || shouldIgnoreResource(path) || response.getStatus() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        return skip;
    }
    
    private void redirectToLogon(HttpServletRequest request, HttpServletResponse response) {
        try {
            String referer = request.getHeader("Referer");
            String recoveryUrlParams;
            //get request parameters
            if (has(referer) && referer.contains("?")) {
                recoveryUrlParams = referer.substring(referer.lastIndexOf("?") + 1);
            } else {
                recoveryUrlParams = request.getQueryString();
            }
            //saves page where user were
            String requestedPage = request.getRequestURI();
            StringBuilder recoveryUrl = null;
            if (!loginPage.equals(requestedPage) && requestedPage.contains(".")) {
                if (requestedPage.startsWith(request.getContextPath())) {
                    requestedPage = requestedPage.replaceFirst(request.getContextPath(), "");
                }
                recoveryUrl = new StringBuilder(requestedPage);
                if (has(recoveryUrlParams)) {
                    recoveryUrl.append("?").append(recoveryUrlParams);
                }
            }
            /*
             if saved page is not null and is not index page then send user to logon page and save
            / previous page in url param named 'page'
            */
            String redirectUrl = request.getContextPath() + "/" + loginPage + (has(recoveryUrl) &&
                    isValidRecoveryUrl(recoveryUrl) ? "?page=" + URLEncoder.encode(recoveryUrl.toString(), "UTF-8") : "");
            if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
                //redirect on ajax request: //http://stackoverflow.com/questions/13366936/jsf-filter-not-redirecting-after-initial-redirect
                response.setContentType("text/xml");
                response.getWriter()
                        .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                        .printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", redirectUrl);
            } else {//normal redirect
                response.sendRedirect(redirectUrl);
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE,"Could not redirect to " + loginPage, e);
        }
    }
    
    /**
     * Skip error pages, login and index page as recovery url because it doesn't make sense redirecting user to such pages
     * @param recoveryUrl
     * @return 
     */
    private boolean isValidRecoveryUrl(StringBuilder recoveryUrl) {
        String pageSuffix = adminConfig.getPageSufix();
        return !recoveryUrl.toString().contains(Constants.DEFAULT_INDEX_PAGE.replace("xhtml", pageSuffix)) && !recoveryUrl.toString().contains(Constants.DEFAULT_ACCESS_DENIED_PAGE.replace("xhtml", adminConfig.getPageSufix())) 
                && !recoveryUrl.toString().contains(Constants.DEFAULT_EXPIRED_PAGE.replace("xhtml", pageSuffix)) && !recoveryUrl.toString().contains(Constants.DEFAULT_OPTIMISTIC_PAGE.replace("xhtml", adminConfig.getPageSufix()))
                && !recoveryUrl.toString().contains(Constants.DEFAULT_LOGIN_PAGE.replace("xhtml", adminConfig.getPageSufix()));
    }
    
    /**
     * 
     * @param path
     * @return true if requested path starts with a ignored resource (configured in admin-config.properties)
     */
    private boolean shouldIgnoreResource(String path) {
        for (String ignoredResource : ignoredResources) {
            if(path.startsWith(ignoredResource)) {
                return true;
            }
        }
        return false;
    }
}