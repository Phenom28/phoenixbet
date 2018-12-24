package com.phoenixbet.web;

import com.phoenixbet.entity.League;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.LeagueBean;

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
 * Controller class for League page
 * @author Ogundipe Segun David
 */
@Named("leagueController")
@ViewScoped
public class LeagueController implements Serializable {

    private static final long serialVersionUID = 8218781002462485068L;
    @EJB
    private LeagueBean leagueBean;
    private List<League> leagues = null;
    private League selected;

    public LeagueController() {
    }

    public League getSelected() {
        return selected;
    }

    public void setSelected(League selected) {
        this.selected = selected;
    }

    private LeagueBean getLeagueBean() {
        return leagueBean;
    }

    public League prepareCreate() {
        selected = new League();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LeagueCreated"));
        if (!JsfUtil.isValidationFailed()) {
            leagues = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LeagueUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LeagueDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            leagues = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<League> getLeagues() {
        if (leagues == null) {
            leagues = getLeagueBean().findAll();
        }
        return leagues;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getLeagueBean().edit(selected);
                } else {
                    getLeagueBean().remove(selected);
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

    public League getLeague(Integer id) {
        return getLeagueBean().find(id);
    }

    public List<League> getLeaguesAvailableSelectMany() {
        return getLeagueBean().findAll();
    }

    public List<League> getLeaguesAvailableSelectOne() {
        return getLeagueBean().findAll();
    }

    @FacesConverter(forClass = League.class)
    public static class LeagueControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LeagueController controller = (LeagueController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "leagueController");
            return controller.getLeague(getKey(value));
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
            if (object instanceof League) {
                League o = (League) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), League.class.getName()});
                return null;
            }
        }

    }

}
