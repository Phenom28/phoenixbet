package com.phoenixbet.web;

import com.phoenixbet.entity.Season;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.SeasonBean;

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
 * Controller class for Season page
 * @author Ogundipe Segun David
 */
@Named("seasonController")
@ViewScoped
public class SeasonController implements Serializable {

    private static final long serialVersionUID = 3730938578140018922L;
    @EJB
    private SeasonBean seasonBean;
    private List<Season> seasons = null;
    private Season selected;

    public SeasonController() {
    }

    public Season getSelected() {
        return selected;
    }

    public void setSelected(Season selected) {
        this.selected = selected;
    }

    private SeasonBean getSeasonBean() {
        return seasonBean;
    }

    public Season prepareCreate() {
        selected = new Season();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SeasonCreated"));
        if (!JsfUtil.isValidationFailed()) {
            seasons = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SeasonUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SeasonDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            seasons = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Season> getSeasons() {
        if (seasons == null) {
            seasons = getSeasonBean().findAll();
        }
        return seasons;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getSeasonBean().edit(selected);
                } else {
                    getSeasonBean().remove(selected);
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

    public Season getSeason(Integer id) {
        return getSeasonBean().find(id);
    }

    public List<Season> getSeasonsAvailableSelectMany() {
        return getSeasonBean().findAll();
    }

    public List<Season> getSeasonsAvailableSelectOne() {
        return getSeasonBean().findAll();
    }

    @FacesConverter(forClass = Season.class)
    public static class SeasonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SeasonController controller = (SeasonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "seasonController");
            return controller.getSeason(getKey(value));
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
            if (object instanceof Season) {
                Season o = (Season) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), Season.class.getName()});
                return null;
            }
        }

    }

}
