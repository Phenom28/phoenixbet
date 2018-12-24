package com.phoenixbet.web;

import com.phoenixbet.ejb.LeagueBean;
import com.phoenixbet.ejb.MatchesBean;
import com.phoenixbet.entity.League;
import com.phoenixbet.entity.Matches;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 * Controller class for Match page
 * 
 * @author Ogundipe Segun David
 */
@Named("matchController")
@ViewScoped
public class MatchController implements Serializable {

    private static final long serialVersionUID = -531824725085778928L;
    @EJB
    private MatchesBean matchBean;
    @EJB
    private LeagueBean leagueBean;
    private List<Matches> notStarted = null;
    private List<Matches> finished = null;
    private List<Matches> leagueMatches = null;
    private List<Matches> leagueNotStarted = null;
    private List<Matches> leagueFinished = null;
    private List<Matches> finishedRound = null;
    private League league;
    private Matches selected;
    private boolean renderThreeWaysFirstHalf;
    private boolean renderThreeWaysSecondHalf;
    private boolean renderGoals = true;
    private boolean renderBtts;
    private boolean renderDoubleChance;
    private Integer leagueId;
    private Integer round = 0;

    public MatchController() {
    }

    public List<Matches> getNotStarted() {
        if (notStarted == null) {
            notStarted = matchBean.findByStatus(1);
        }
        return notStarted;
    }

    public List<Matches> getFinishedMatches() {
        if (finished == null) {
            finished = matchBean.findByStatus(2);
        }
        return finished;
    }

    public List<Matches> getLeagueMatches() {
        if (leagueMatches == null) {
            leagueMatches = matchBean.findByLeague(leagueId);
        } else {
            leagueMatches.clear();
            leagueMatches = matchBean.findByLeague(leagueId);
        }
        return leagueMatches;
    }

    public List<Matches> getLeagueNotStarted() {
        if (leagueNotStarted == null) {
            leagueNotStarted = matchBean.findByStatusLeague(leagueId, 1);
        }
        return leagueNotStarted;
    }

    public List<Matches> getLeagueFinished() {
        if (!getFinishedRound().isEmpty()) {
            round = getFinishedRound().get(0).getMatchRound();
        }
        if (leagueFinished == null) {
            leagueFinished = matchBean.findByStatusRound(leagueId, 2, round);
        }
        return leagueFinished;
    }

    public List<Matches> getFinishedRound() {
        if (finishedRound == null) {
            finishedRound = matchBean.findFinishedByRound(leagueId);
        }
        return finishedRound;
    }

    public Matches getMatch(Integer id) {
        return matchBean.find(id);
    }

    public League getLeague() {
        league = leagueBean.find(leagueId);
        return league;
    }

    public Matches getSelected() {
        return selected;
    }

    public void setSelected(Matches selected) {
        this.selected = selected;
    }

    public boolean isRenderThreeWaysFirstHalf() {
        return renderThreeWaysFirstHalf;
    }

    public void setRenderThreeWaysFirstHalf(boolean renderThreeWaysFirstHalf) {
        this.renderThreeWaysFirstHalf = renderThreeWaysFirstHalf;
    }

    public boolean isRenderThreeWaysSecondHalf() {
        return renderThreeWaysSecondHalf;
    }

    public void setRenderThreeWaysSecondHalf(boolean renderThreeWaysSecondHalf) {
        this.renderThreeWaysSecondHalf = renderThreeWaysSecondHalf;
    }

    public boolean isRenderGoals() {
        return renderGoals;
    }

    public void setRenderGoals(boolean renderGoals) {
        this.renderGoals = renderGoals;
    }

    public boolean isRenderBtts() {
        return renderBtts;
    }

    public void setRenderBtts(boolean renderBtts) {
        this.renderBtts = renderBtts;
    }

    public boolean isRenderDoubleChance() {
        return renderDoubleChance;
    }

    public void setRenderDoubleChance(boolean renderDoubleChance) {
        this.renderDoubleChance = renderDoubleChance;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        if (leagueId != null) {
            this.leagueId = leagueId;
        }
    }

    @FacesConverter(forClass = Matches.class)
    public static class MatchControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MatchController controller = (MatchController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "matchController");
            return controller.getMatch(getKey(value));
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
            if (object instanceof Matches) {
                Matches o = (Matches) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), Matches.class.getName()});
                return null;
            }
        }
    }
}
