package com.phoenixbet.web;

import com.phoenixbet.entity.Groups;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.GroupsBean;

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
 * Controller class for Groups page
 * @author Ogundipe Segun David
 */
@Named("groupsController")
@ViewScoped
public class GroupsController implements Serializable {

    private static final long serialVersionUID = -7547614208881415688L;
    @EJB
    private GroupsBean groupsBean;
    private List<Groups> groups = null;
    private Groups selected;

    public GroupsController() {
    }

    public Groups getSelected() {
        return selected;
    }

    public void setSelected(Groups selected) {
        this.selected = selected;
    }

    private GroupsBean getGroupsBean() {
        return groupsBean;
    }

    public Groups prepareCreate() {
        selected = new Groups();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("GroupsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            groups = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("GroupsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("GroupsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            groups = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Groups> getGroups() {
        if (groups == null) {
            groups = getGroupsBean().findAll();
        }
        return groups;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getGroupsBean().edit(selected);
                } else {
                    getGroupsBean().remove(selected);
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

    public Groups getGroups(Integer id) {
        return getGroupsBean().find(id);
    }

    public List<Groups> getGroupsAvailableSelectMany() {
        return getGroupsBean().findAll();
    }

    public List<Groups> getGroupsAvailableSelectOne() {
        return getGroupsBean().findAll();
    }

    @FacesConverter(forClass = Groups.class)
    public static class GroupsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GroupsController controller = (GroupsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "groupsController");
            return controller.getGroups(getKey(value));
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
            if (object instanceof Groups) {
                Groups o = (Groups) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), Groups.class.getName()});
                return null;
            }
        }

    }

}
