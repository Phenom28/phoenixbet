package com.phoenixbet.web;

import com.phoenixbet.entity.Country;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.CountryBean;

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
 * Controller class for Country page
 * @author Ogundipe Segun David
 */
@Named("countryController")
@ViewScoped
public class CountryController implements Serializable {

    private static final long serialVersionUID = -3390393084983808287L;
    @EJB
    private CountryBean countryBean;
    private List<Country> countries = null;
    private Country selected;

    public CountryController() {
    }

    public Country getSelected() {
        return selected;
    }

    public void setSelected(Country selected) {
        this.selected = selected;
    }

    private CountryBean getCountryBean() {
        return countryBean;
    }

    public Country prepareCreate() {
        selected = new Country();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CountryCreated"));
        if (!JsfUtil.isValidationFailed()) {
            countries = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CountryUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CountryDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            countries = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Country> getCountries() {
        if (countries == null) {
            countries = getCountryBean().findAll();
        }
        return countries;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getCountryBean().edit(selected);
                } else {
                    getCountryBean().remove(selected);
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

    public Country getCountry(Integer id) {
        return getCountryBean().find(id);
    }

    public List<Country> getCountriesAvailableSelectMany() {
        return getCountryBean().findAll();
    }

    public List<Country> getCountriesAvailableSelectOne() {
        return getCountryBean().findAll();
    }

    @FacesConverter(forClass = Country.class)
    public static class CountryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CountryController controller = (CountryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "countryController");
            return controller.getCountry(getKey(value));
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
            if (object instanceof Country) {
                Country o = (Country) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), Country.class.getName()});
                return null;
            }
        }

    }

}
