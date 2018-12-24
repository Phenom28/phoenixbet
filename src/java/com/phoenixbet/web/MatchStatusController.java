package com.phoenixbet.web;

import com.phoenixbet.entity.MatchStatus;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.MatchStatusBean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.omnifaces.cdi.ViewScoped;

/**
 * Controller class for Match Status page
 * @author Ogundipe Segun David
 */
@Named("matchStatusController")
@ViewScoped
public class MatchStatusController implements Serializable {

    private static final long serialVersionUID = -3507169375446614995L;
    @EJB
    private MatchStatusBean matchStatusBean;
    private List<MatchStatus> matchStatuses = null;
    private MatchStatus selected;

    public MatchStatusController() {
    }

    public MatchStatus getSelected() {
        return selected;
    }

    public void setSelected(MatchStatus selected) {
        this.selected = selected;
    }

    private MatchStatusBean getMatchStatusBean() {
        return matchStatusBean;
    }

    public MatchStatus prepareCreate() {
        selected = new MatchStatus();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MatchStatusCreated"));
        if (!JsfUtil.isValidationFailed()) {
            matchStatuses = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MatchStatusUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MatchStatusDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            matchStatuses = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<MatchStatus> getMatchStatuses() {
        if (matchStatuses == null) {
            matchStatuses = getMatchStatusBean().findAll();
        }
        return matchStatuses;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getMatchStatusBean().edit(selected);
                } else {
                    getMatchStatusBean().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public MatchStatus getMatchStatus(Integer id) {
        return getMatchStatusBean().find(id);
    }

    public List<MatchStatus> getMatchStatusesAvailableSelectMany() {
        return getMatchStatusBean().findAll();
    }

    public List<MatchStatus> getMatchStatusesAvailableSelectOne() {
        return getMatchStatusBean().findAll();
    }

    @FacesConverter(forClass = MatchStatus.class)
    public static class MatchStatusControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MatchStatusController controller = (MatchStatusController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "matchStatusController");
            return controller.getMatchStatus(getKey(value));
        }

        Integer getKey(String value) {
            Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MatchStatus) {
                MatchStatus o = (MatchStatus) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), MatchStatus.class.getName()});
                return null;
            }
        }

    }

}
