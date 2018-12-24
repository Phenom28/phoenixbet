package com.phoenixbet.web;

import com.phoenixbet.entity.Administrator;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.AdministratorBean;

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
 * Controller class for administrator page
 * @author Ogundipe Segun David
 */
@Named("administratorController")
@ViewScoped
public class AdministratorController implements Serializable {

    private static final long serialVersionUID = 4117191361024023124L;
    @EJB
    private AdministratorBean adminBean;
    private List<Administrator> admins = null;
    private Administrator selected;

    public AdministratorController() {
    }

    public Administrator getSelected() {
        return selected;
    }

    public void setSelected(Administrator selected) {
        this.selected = selected;
    }

    private AdministratorBean getAdminBean() {
        return adminBean;
    }

    public Administrator prepareCreate() {
        selected = new Administrator();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AdministratorCreated"));
        if (!JsfUtil.isValidationFailed()) {
            admins = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AdministratorUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AdministratorDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            admins = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Administrator> getAdmins() {
        if (admins == null) {
            admins = getAdminBean().findAll();
        }
        return admins;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getAdminBean().edit(selected);
                } else {
                    getAdminBean().remove(selected);
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

    public Administrator getAdministrator(Integer id) {
        return getAdminBean().find(id);
    }

    public List<Administrator> getAdminsAvailableSelectMany() {
        return getAdminBean().findAll();
    }

    public List<Administrator> getAdminsAvailableSelectOne() {
        return getAdminBean().findAll();
    }

    @FacesConverter(forClass = Administrator.class)
    public static class AdministratorControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AdministratorController controller = (AdministratorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "administratorController");
            return controller.getAdministrator(getKey(value));
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
            if (object instanceof Administrator) {
                Administrator o = (Administrator) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), Administrator.class.getName()});
                return null;
            }
        }

    }

}
