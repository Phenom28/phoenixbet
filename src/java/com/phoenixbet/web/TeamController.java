package com.phoenixbet.web;

import com.phoenixbet.entity.Team;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.TeamBean;
import com.phoenixbet.entity.League;
import com.phoenixbet.excel.ExcelReader;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Controller class for Team page
 * @author Ogundipe Segun David
 */
@Named("teamController")
@ViewScoped
public class TeamController implements Serializable {

    private static final long serialVersionUID = 1151314984113697316L;
    @EJB
    private TeamBean teamBean;
    private List<Team> teams = null;
    private Team selected;
    private List<League> leagues;
    private League league;
    private ExcelReader reader;
    private UploadedFile file;
    private String sheet;
    private Integer firstRow;
    private Integer lastRow;
    private Integer firstColumn;
    private Integer lastColumn;

    public TeamController() {
    }

    public Team getSelected() {
        return selected;
    }

    public void setSelected(Team selected) {
        this.selected = selected;
    }

    private TeamBean getTeamBean() {
        return teamBean;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Team prepareCreate() {
        selected = new Team();
        return selected;
    }

    public List<String> prepTeams() {
        reader = new ExcelReader(file, sheet, firstRow, lastRow, firstColumn, lastColumn);
        List<String> teamList = new ArrayList<>();

        try {
            teamList = reader.getTeamNames();
        } catch (IOException e) {
            Logger.getLogger(MatchesController.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e, "Unable to get file");
        }
        return teamList;
    }

//    public void create() {
//        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TeamCreated"));
//        if (!JsfUtil.isValidationFailed()) {
//            teams = null;    // Invalidate list of items to trigger re-query.
//        }
//    }
    public void create() {
        if (selected != null) {
            try {
                getTeamBean().create(selected, leagues);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeamCreated"));
                selected = null;
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
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }

        if (!JsfUtil.isValidationFailed()) {
            teams = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createTeams() {
        if (selected != null) {
            for (String team : prepTeams()) {
                selected.setName(team);
                try {
                    getTeamBean().create(selected, league);
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeamCreated"));
                    selected = null;
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
                } catch (Exception e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
                prepareCreate();
            }
        }

        if (!JsfUtil.isValidationFailed()) {
            teams = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TeamUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TeamDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            teams = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Team> getTeams() {
        if (teams == null) {
            teams = getTeamBean().findAll();
        }
        return teams;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getTeamBean().edit(selected);
                } else {
                    getTeamBean().remove(selected);
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
    
    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
    }

    public Team getTeam(Integer id) {
        return getTeamBean().find(id);
    }

    public List<Team> getTeamsAvailableSelectMany() {
        return getTeamBean().findAll();
    }

    public List<Team> getTeamsAvailableSelectOne() {
        return getTeamBean().findAll();
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public Integer getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Integer firstRow) {
        this.firstRow = firstRow;
    }

    public Integer getLastRow() {
        return lastRow;
    }

    public void setLastRow(Integer lastRow) {
        this.lastRow = lastRow;
    }

    public Integer getFirstColumn() {
        return firstColumn;
    }

    public void setFirstColumn(Integer firstColumn) {
        this.firstColumn = firstColumn;
    }

    public Integer getLastColumn() {
        return lastColumn;
    }

    public void setLastColumn(Integer lastColumn) {
        this.lastColumn = lastColumn;
    }

    @FacesConverter(forClass = Team.class)
    public static class TeamControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TeamController controller = (TeamController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "teamController");
            return controller.getTeam(getKey(value));
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
            if (object instanceof Team) {
                Team o = (Team) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), Team.class.getName()});
                return null;
            }
        }

    }

}
