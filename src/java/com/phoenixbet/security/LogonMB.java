package com.phoenixbet.security;

import static com.phoenixbet.web.util.JsfUtil.addSuccessMessage;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.util.Messages;
/**
 * 
 * @author Ogundipe Segun David
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    private static final long serialVersionUID = -4418019128361436215L;
    @Inject
    private AdminConfig adminConfig;
    private String currentUser;
    private String password;
    private String email;
    private boolean remember;

    public void login() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.login(email, password);

            currentUser = request.getUserPrincipal().getName();
            addSuccessMessage("Logged in successfully as <b>" + currentUser + "</b>");
            Faces.getExternalContext().getFlash().setKeepMessages(true);
            Faces.redirect(adminConfig.getIndexPage());
            
        } catch (ServletException se) {
            Logger.getLogger(LogonMB.class.getName()).log(Level.SEVERE, null, se);
            Messages.addError(null, "Login Failed");
        }

    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

}
