package com.phoenixbet.web;

import com.phoenixbet.ejb.BttsBean;
import com.phoenixbet.ejb.DoubleChanceBean;
import com.phoenixbet.ejb.FirstHalfBean;
import com.phoenixbet.ejb.FirstHalfGoalsBean;
import com.phoenixbet.ejb.LeagueBean;
import com.phoenixbet.ejb.MatchStatusBean;
import com.phoenixbet.entity.Matches;
import com.phoenixbet.web.util.JsfUtil;
import com.phoenixbet.web.util.JsfUtil.PersistAction;
import com.phoenixbet.ejb.MatchesBean;
import com.phoenixbet.ejb.OverUnderBean;
import com.phoenixbet.ejb.SeasonBean;
import com.phoenixbet.ejb.SecondHalfBean;
import com.phoenixbet.ejb.SecondHalfGoalsBean;
import com.phoenixbet.ejb.TeamBean;
import com.phoenixbet.entity.Btts;
import com.phoenixbet.entity.DoubleChance;
import com.phoenixbet.entity.FirstHalf;
import com.phoenixbet.entity.FirstHalfGoals;
import com.phoenixbet.entity.League;
import com.phoenixbet.entity.OverUnder;
import com.phoenixbet.entity.SecondHalf;
import com.phoenixbet.entity.SecondHalfGoals;
import com.phoenixbet.excel.ExcelMatch;
import com.phoenixbet.excel.ExcelReader;
import com.phoenixbet.poissondist.PoissonModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Controller class for Match page used by the Admin
 * @author Ogundipe Segun David
 */
@Named("matchesController")
@SessionScoped
public class MatchesController implements Serializable {

    private static final long serialVersionUID = -7326230594246545719L;
    @EJB
    private MatchesBean matchBean;
    @EJB
    private FirstHalfBean firstHalfBean;
    @EJB
    private FirstHalfGoalsBean firstHalfGoalsBean;
    @EJB
    private SecondHalfBean secondHalfBean;
    @EJB
    private SecondHalfGoalsBean secondHalfGoalsBean;
    @EJB
    private BttsBean bttsBean;
    @EJB
    private OverUnderBean overUnderBean;
    @EJB
    private DoubleChanceBean doubleChanceBean;
    @EJB
    private MatchStatusBean matchStatusBean;
    @EJB
    private SeasonBean seasonBean;
    @EJB
    private TeamBean teamBean;
    @EJB
    private LeagueBean leagueBean;
    private List<Matches> matches = null;
    private List<Matches> notStartedMatches = null;
    private List<Matches> finishedMatches = null;
    private List<SelectItem> leagues;
    private Matches selected;
    private ExcelReader reader;
    private String sheet;
    private Integer row;
    private Integer firstRow;
    private Integer lastRow;
    private Integer date;
    private Integer homeTeam;
    private Integer awayTeam;
    private Integer fullTimeHomeGoal;
    private Integer fullTimeAwayGoal;
    private Integer halfTimeHomeGoal;
    private Integer halfTimeAwayGoal;
    private Integer homeShot;
    private Integer awayShot;
    private Integer homeShotOnTarget;
    private Integer awayShotOnTarget;
    private Integer homeFoul;
    private Integer awayFoul;
    private Integer homeCorner;
    private Integer awayCorner;
    private Integer homeYellow;
    private Integer awayYellow;
    private Integer homeRed;
    private Integer awayRed;
    private boolean renderThreeWaysFirstHalf;
    private boolean renderThreeWaysSecondHalf;
    private boolean renderGoalsFirstHalf;
    private boolean renderGoalsSecondHalf;
    private boolean renderGoals = true;
    private boolean renderBtts;
    private boolean renderDoubleChance;
    private boolean predictHome;
    private boolean predictAway;
    private Integer status;
    private Integer league;
    private Integer season;
    private Integer round;
    private UploadedFile file;
    private League leag;

    public MatchesController() {
    }

    @PostConstruct
    public void init() {
        SelectItemGroup g1 = new SelectItemGroup("England");
        g1.setSelectItems(new SelectItem[]{
            new SelectItem(1, "Premier League"),
            new SelectItem(2, "Championship"),
            new SelectItem(3, "League One"),
            new SelectItem(4, "League Two")
        });

        SelectItemGroup g2 = new SelectItemGroup("Spain");
        g2.setSelectItems(new SelectItem[]{
            new SelectItem(11, "Primera Division"),
            new SelectItem(12, "Segunda Division")
        });

        SelectItemGroup g3 = new SelectItemGroup("Italy");
        g3.setSelectItems(new SelectItem[]{
            new SelectItem(13, "Serie A"),
            new SelectItem(14, "Serie B")
        });

        SelectItemGroup g4 = new SelectItemGroup("France");
        g4.setSelectItems(new SelectItem[]{
            new SelectItem(15, "Ligue 1"),
            new SelectItem(16, "Ligue 2")
        });

        SelectItemGroup g5 = new SelectItemGroup("Germany");
        g5.setSelectItems(new SelectItem[]{
            new SelectItem(9, "Bundesliga"),
            new SelectItem(10, "Bundesliga 2")
        });

        SelectItemGroup g6 = new SelectItemGroup("Scotland");
        g6.setSelectItems(new SelectItem[]{
            new SelectItem(5, "Premier League"),
            new SelectItem(6, "Championship"),
            new SelectItem(7, "League One"),
            new SelectItem(8, "League Two")
        });

        SelectItemGroup g7 = new SelectItemGroup("Belgium");
        g7.setSelectItems(new SelectItem[]{
            new SelectItem(17, "Jupiler League")
        });

        SelectItemGroup g8 = new SelectItemGroup("Netherlands");
        g8.setSelectItems(new SelectItem[]{
            new SelectItem(18, "Ere Divisie")
        });

        SelectItemGroup g9 = new SelectItemGroup("Portugal");
        g9.setSelectItems(new SelectItem[]{
            new SelectItem(19, "La Liga Primeira")
        });

        SelectItemGroup g10 = new SelectItemGroup("Turkey");
        g10.setSelectItems(new SelectItem[]{
            new SelectItem(20, "Super Lig")
        });

        SelectItemGroup g11 = new SelectItemGroup("Greece");
        g11.setSelectItems(new SelectItem[]{
            new SelectItem(21, "Super League")
        });

        leagues = new ArrayList<>();
        leagues.add(g1);
        leagues.add(g2);
        leagues.add(g3);
        leagues.add(g4);
        leagues.add(g5);
        leagues.add(g6);
        leagues.add(g7);
        leagues.add(g8);
        leagues.add(g9);
        leagues.add(g10);
        leagues.add(g11);
    }

    public Matches prepareCreate() {
        selected = new Matches();
        return selected;
    }

    public void prepareExcel() {
        selected.setMatchStatus(getMatchStatusBean().find(status));
        selected.setLeague(leag);
        selected.setSeason(getSeasonBean().find(season));
        selected.setMatchRound(round);
    }

    public void prepareExcel2() {
        selected.setMatchStatus(getMatchStatusBean().find(status));
        selected.setLeague(leag);
        selected.setSeason(getSeasonBean().find(season));
    }

    public void setMatch() throws IOException {
        reader = new ExcelReader(file, sheet, row, date, homeTeam, awayTeam, fullTimeHomeGoal, fullTimeAwayGoal,
                halfTimeHomeGoal, halfTimeAwayGoal, homeShot, awayShot, homeShotOnTarget, awayShotOnTarget,
                homeFoul, awayFoul, homeCorner, awayCorner, homeYellow, awayYellow, homeRed, awayRed);
        ExcelMatch excelMatch = reader.getMatch();

        selected.setMatchDate(excelMatch.getDate());
        selected.setHomeTeam(getTeamBean().findByName(excelMatch.getHomeTeam()));
        selected.setAwayTeam(getTeamBean().findByName(excelMatch.getAwayTeam()));
        selected.setFullTimeHomeGoal(excelMatch.getFullTimeHomeGoal());
        selected.setFullTimeAwayGoal(excelMatch.getFullTimeAwayGoal());
        selected.setHalfTimeHomeGoal(excelMatch.getHalfTimeHomeGoal());
        selected.setHalfTimeAwayGoal(excelMatch.getHalfTimeAwayGoal());
        selected.setHomeShot(excelMatch.getHomeShot());
        selected.setAwayShot(excelMatch.getAwayShot());
        selected.setHomeShotOnTarget(excelMatch.getHomeShotOnTarget());
        selected.setAwayShotOnTarget(excelMatch.getAwayShotOnTarget());
        selected.setHomeFoul(excelMatch.getHomeFoul());
        selected.setAwayFoul(excelMatch.getAwayFoul());
        selected.setHomeCorner(excelMatch.getHomeCorner());
        selected.setAwayCorner(excelMatch.getAwayCorner());
        selected.setHomeYellow(excelMatch.getHomeYellow());
        selected.setAwayYellow(excelMatch.getAwayYellow());
        selected.setHomeRed(excelMatch.getHomeRed());
        selected.setAwayRed(excelMatch.getAwayRed());

    }

    public List<Matches> setMatches() {
        reader = new ExcelReader(file, sheet, firstRow, lastRow, date, homeTeam, awayTeam, fullTimeHomeGoal, fullTimeAwayGoal,
                halfTimeHomeGoal, halfTimeAwayGoal, homeShot, awayShot, homeShotOnTarget, awayShotOnTarget,
                homeFoul, awayFoul, homeCorner, awayCorner, homeYellow, awayYellow, homeRed, awayRed);
        List<ExcelMatch> excelMatches;
        List<Matches> matche = new ArrayList<>();
        Matches match;

        try {
            excelMatches = reader.getMatches();
            for (ExcelMatch excelMatche : excelMatches) {
                match = new Matches();
                match.setMatchDate(excelMatche.getDate());
                match.setHomeTeam(getTeamBean().findByName(excelMatche.getHomeTeam()));
                match.setAwayTeam(getTeamBean().findByName(excelMatche.getAwayTeam()));
                match.setFullTimeHomeGoal(excelMatche.getFullTimeHomeGoal());
                match.setFullTimeAwayGoal(excelMatche.getFullTimeAwayGoal());
                match.setHalfTimeHomeGoal(excelMatche.getHalfTimeHomeGoal());
                match.setHalfTimeAwayGoal(excelMatche.getHalfTimeAwayGoal());
                match.setHomeShot(excelMatche.getHomeShot());
                match.setAwayShot(excelMatche.getAwayShot());
                match.setHomeShotOnTarget(excelMatche.getHomeShotOnTarget());
                match.setAwayShotOnTarget(excelMatche.getAwayShotOnTarget());
                match.setHomeFoul(excelMatche.getHomeFoul());
                match.setAwayFoul(excelMatche.getAwayFoul());
                match.setHomeCorner(excelMatche.getHomeCorner());
                match.setAwayCorner(excelMatche.getAwayCorner());
                match.setHomeYellow(excelMatche.getHomeYellow());
                match.setAwayYellow(excelMatche.getAwayYellow());
                match.setHomeRed(excelMatche.getHomeRed());
                match.setAwayRed(excelMatche.getAwayRed());

                matche.add(match);
                JsfUtil.addSuccessMessage(match.getHomeTeam().getName() + " : "
                        + match.getAwayTeam().getName() + " has been added");
                Logger.getLogger(MatchesController.class.getName()).log(Level.INFO, "{0} : {1} has been added", new Object[]{
                    match.getHomeTeam().getName(), match.getAwayTeam().getName()});
            }
        } catch (IOException ioe) {
            Logger.getLogger(MatchesController.class.getName()).log(Level.SEVERE, null, ioe);
            JsfUtil.addErrorMessage(ioe, "Unable to get file");
        }

        return matche;
    }

    public List<Matches> setTeams() {
        reader = new ExcelReader(file, sheet, firstRow, lastRow, date, round, homeTeam, awayTeam);
        List<ExcelMatch> excelMatches;
        List<Matches> matche = new ArrayList<>();
        Matches match;

        try {
            excelMatches = reader.getTeams();

            for (ExcelMatch mat : excelMatches) {
                match = new Matches();
                match.setMatchDate(mat.getDate());
                match.setMatchRound(mat.getRound());
                match.setHomeTeam(getTeamBean().findByName(mat.getHomeTeam()));
                match.setAwayTeam(getTeamBean().findByName(mat.getAwayTeam()));

                matche.add(match);
                JsfUtil.addSuccessMessage(match.getHomeTeam().getName() + " : "
                        + match.getAwayTeam().getName() + " has been added");
                Logger.getLogger(MatchesController.class.getName()).log(Level.INFO, "{0} : {1} has been added", new Object[]{
                    match.getHomeTeam().getName(), match.getAwayTeam().getName()});
            }
        } catch (IOException e) {
            Logger.getLogger(MatchesController.class.getName()).log(Level.SEVERE, null, e);
            JsfUtil.addErrorMessage(e, "Unable to get file");
        }
        return matche;
    }

    public void setEdit() throws IOException {
        reader = new ExcelReader(file, sheet, row, fullTimeHomeGoal, fullTimeAwayGoal, halfTimeHomeGoal,
                halfTimeAwayGoal, homeShot, awayShot, homeShotOnTarget, awayShotOnTarget, homeFoul, awayFoul,
                homeCorner, awayCorner, homeYellow, awayYellow, homeRed, awayRed);
        ExcelMatch excelMatch = reader.editMatch();

        selected.setFullTimeHomeGoal(excelMatch.getFullTimeHomeGoal());
        selected.setFullTimeAwayGoal(excelMatch.getFullTimeAwayGoal());
        selected.setHalfTimeHomeGoal(excelMatch.getHalfTimeHomeGoal());
        selected.setHalfTimeAwayGoal(excelMatch.getHalfTimeAwayGoal());
        selected.setHomeShot(excelMatch.getHomeShot());
        selected.setAwayShot(excelMatch.getAwayShot());
        selected.setHomeShotOnTarget(excelMatch.getHomeShotOnTarget());
        selected.setAwayShotOnTarget(excelMatch.getAwayShotOnTarget());
        selected.setHomeFoul(excelMatch.getHomeFoul());
        selected.setAwayFoul(excelMatch.getAwayFoul());
        selected.setHomeCorner(excelMatch.getHomeCorner());
        selected.setAwayCorner(excelMatch.getAwayCorner());
        selected.setHomeYellow(excelMatch.getHomeYellow());
        selected.setAwayYellow(excelMatch.getAwayYellow());
        selected.setHomeRed(excelMatch.getHomeRed());
        selected.setAwayRed(excelMatch.getAwayRed());
    }

    public void create() {
        matchPrediction();
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MatchesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            matches = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createNoPredict() {
        selected.setBtts(bttsBean.find(215));
        selected.setDoubleChance(doubleChanceBean.find(215));
        selected.setSecondHalf(secondHalfBean.find(215));
        selected.setSecondHalfGoals(secondHalfGoalsBean.find(215));
        selected.setFirstHalf(firstHalfBean.find(215));
        selected.setFirstHalfGoals(firstHalfGoalsBean.find(215));
        selected.setOverUnder(overUnderBean.find(215));
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MatchesCreated"));
        if (!JsfUtil.isValidationFailed()) {
            matches = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void createMatchesNoPredict() {
        for (Matches match : setMatches()) {
            selected.setMatchDate(match.getMatchDate());
            selected.setHomeTeam(match.getHomeTeam());
            selected.setAwayTeam(match.getAwayTeam());
            selected.setFullTimeHomeGoal(match.getFullTimeHomeGoal());
            selected.setFullTimeAwayGoal(match.getFullTimeAwayGoal());
            selected.setHalfTimeHomeGoal(match.getHalfTimeHomeGoal());
            selected.setHalfTimeAwayGoal(match.getHalfTimeAwayGoal());
            selected.setHomeShot(match.getHomeShot());
            selected.setAwayShot(match.getAwayShot());
            selected.setHomeShotOnTarget(match.getHomeShotOnTarget());
            selected.setAwayShotOnTarget(match.getAwayShotOnTarget());
            selected.setHomeFoul(match.getHomeFoul());
            selected.setAwayFoul(match.getAwayFoul());
            selected.setHomeCorner(match.getHomeCorner());
            selected.setAwayCorner(match.getAwayCorner());
            selected.setHomeYellow(match.getHomeYellow());
            selected.setAwayYellow(match.getAwayYellow());
            selected.setHomeRed(match.getHomeRed());
            selected.setAwayRed(match.getAwayRed());
            selected.setBtts(bttsBean.find(215));
            selected.setDoubleChance(doubleChanceBean.find(215));
            selected.setSecondHalf(secondHalfBean.find(215));
            selected.setSecondHalfGoals(secondHalfGoalsBean.find(215));
            selected.setFirstHalf(firstHalfBean.find(215));
            selected.setFirstHalfGoals(firstHalfGoalsBean.find(215));
            selected.setOverUnder(overUnderBean.find(215));

            prepareExcel();
            persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MatchesCreated"));
            if (!JsfUtil.isValidationFailed()) {
                matches = null;    // Invalidate list of items to trigger re-query.
            }

            prepareCreate();
        }
    }

    public void createMatches() throws IOException {
        for (Matches match : setMatches()) {
            selected.setMatchDate(match.getMatchDate());
            selected.setHomeTeam(match.getHomeTeam());
            selected.setAwayTeam(match.getAwayTeam());
            selected.setFullTimeHomeGoal(match.getFullTimeHomeGoal());
            selected.setFullTimeAwayGoal(match.getFullTimeAwayGoal());
            selected.setHalfTimeHomeGoal(match.getHalfTimeHomeGoal());
            selected.setHalfTimeAwayGoal(match.getHalfTimeAwayGoal());
            selected.setHomeShot(match.getHomeShot());
            selected.setAwayShot(match.getAwayShot());
            selected.setHomeShotOnTarget(match.getHomeShotOnTarget());
            selected.setAwayShotOnTarget(match.getAwayShotOnTarget());
            selected.setHomeFoul(match.getHomeFoul());
            selected.setAwayFoul(match.getAwayFoul());
            selected.setHomeCorner(match.getHomeCorner());
            selected.setAwayCorner(match.getAwayCorner());
            selected.setHomeYellow(match.getHomeYellow());
            selected.setAwayYellow(match.getAwayYellow());
            selected.setHomeRed(match.getHomeRed());
            selected.setAwayRed(match.getAwayRed());

            matchPrediction();
            prepareExcel();
            persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MatchesCreated"));
            if (!JsfUtil.isValidationFailed()) {
                matches = null;    // Invalidate list of items to trigger re-query.
            }

            prepareCreate();
        }
    }

    public void createFromExcel() {
        for (Matches setTeam : setTeams()) {
            selected.setMatchDate(setTeam.getMatchDate());
            selected.setMatchRound(setTeam.getMatchRound());
            selected.setHomeTeam(setTeam.getHomeTeam());
            selected.setAwayTeam(setTeam.getAwayTeam());

            matchPrediction();
            prepareExcel2();
            persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MatchesCreated"));
            if (!JsfUtil.isValidationFailed()) {
                matches = null;    // Invalidate list of items to trigger re-query.
            }
            prepareCreate();
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MatchesUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MatchesDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            matches = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Matches> getMatches() {
        matches = getMatchBean().findAll();
        return matches;
    }

    public List<Matches> getNotStartedMatches() {
        notStartedMatches = getMatchBean().findByStatusLeague(league, 1);
        return notStartedMatches;
    }

    public List<Matches> getFinishedMatches() {
        finishedMatches = getMatchBean().findByStatusLeague(league, 2);
        return finishedMatches;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    getMatchBean().edit(selected);
                } else {
                    Btts btts = selected.getBtts();
                    DoubleChance db = selected.getDoubleChance();
                    FirstHalf fh = selected.getFirstHalf();
                    FirstHalfGoals fhg = selected.getFirstHalfGoals();
                    OverUnder ou = selected.getOverUnder();
                    SecondHalf sh = selected.getSecondHalf();
                    SecondHalfGoals shg = selected.getSecondHalfGoals();

                    getMatchBean().remove(selected);
                    if (btts.getId() != 215) {
                        getBttsBean().remove(btts);
                    }
                    if (db.getId() != 215) {
                        getDoubleChanceBean().remove(db);
                    }
                    if (fh.getId() != 215) {
                        getFirstHalfBean().remove(fh);
                    }
                    if (fhg.getId() != 215) {
                        getFirstHalfGoalsBean().remove(fhg);
                    }
                    if (ou.getId() != 215) {
                        getOverUnderBean().remove(ou);
                    }
                    if (sh.getId() != 215) {
                        getSecondHalfBean().remove(sh);
                    }
                    if (shg.getId() != 215) {
                        getSecondHalfGoalsBean().remove(shg);
                    }
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

    public Matches getMatch(Integer id) {
        return getMatchBean().find(id);
    }

    private List<Matches> lastFourHomeMatches() {
        List<Matches> matchs;
        List<Matches> matche = getMatchBean().lastTenHomeMatches(selected.getHomeTeam(), selected.getMatchDate());
        if (matche.isEmpty() || matche.size() < 4) {
            matchs = new ArrayList<>();
            predictHome = false;
        } else {
            matchs = matche.subList(0, 4);
            predictHome = true;
        }
        return matchs;
    }

    private List<Matches> lastFourAwayMatches() {
        List<Matches> matchs;
        List<Matches> matche = getMatchBean().lastTenAwayMatches(selected.getAwayTeam(), selected.getMatchDate());
        if (matche.isEmpty() || matche.size() < 4) {
            matchs = new ArrayList<>();
            predictAway = false;
        } else {
            matchs = matche.subList(0, 4);
            predictAway = true;
        }
        return matchs;
    }

    public List<Matches> getMatchesAvailableSelectMany() {
        return getMatchBean().findAll();
    }

    public List<Matches> getMatchesAvailableSelectOne() {
        return getMatchBean().findAll();
    }

    private void matchPrediction() {
        Integer fullTimeHomeGoals = 0;
        Integer fullTimeHomeConceded = 0;
        Integer fullTimeAwayGoals = 0;
        Integer fullTimeAwayConceded = 0;
        Integer firstHalfHomeGoals = 0;
        Integer firstHalfHomeConceded = 0;
        Integer firstHalfAwayGoals = 0;
        Integer firstHalfAwayConceded = 0;
        Integer secondHalfHomeGoals;
        Integer secondHalfHomeConceded;
        Integer secondHalfAwayGoals;
        Integer secondHalfAwayConceded;

        for (Matches homeMatch : lastFourHomeMatches()) {
            fullTimeHomeGoals += homeMatch.getFullTimeHomeGoal() == null ? 0 : homeMatch.getFullTimeHomeGoal();
            fullTimeHomeConceded += homeMatch.getFullTimeAwayGoal() == null ? 0 : homeMatch.getFullTimeAwayGoal();
            firstHalfHomeGoals += homeMatch.getHalfTimeHomeGoal() == null ? 0 : homeMatch.getHalfTimeHomeGoal();
            firstHalfHomeConceded += homeMatch.getHalfTimeAwayGoal() == null ? 0 : homeMatch.getHalfTimeAwayGoal();

        }

        for (Matches awayMatch : lastFourAwayMatches()) {
            fullTimeAwayGoals += awayMatch.getFullTimeAwayGoal() == null ? 0 : awayMatch.getFullTimeAwayGoal();
            fullTimeAwayConceded += awayMatch.getFullTimeHomeGoal() == null ? 0 : awayMatch.getFullTimeHomeGoal();
            firstHalfAwayGoals += awayMatch.getHalfTimeAwayGoal() == null ? 0 : awayMatch.getHalfTimeAwayGoal();
            firstHalfAwayConceded += awayMatch.getHalfTimeHomeGoal() == null ? 0 : awayMatch.getHalfTimeHomeGoal();
        }

        if (predictHome && predictAway) {

            secondHalfHomeGoals = fullTimeHomeGoals - firstHalfHomeGoals;
            secondHalfHomeConceded = fullTimeHomeConceded - firstHalfHomeConceded;
            secondHalfAwayGoals = fullTimeAwayGoals - firstHalfAwayGoals;
            secondHalfAwayConceded = fullTimeAwayConceded - firstHalfAwayConceded;

            double fullTimeHomeAverage = (fullTimeHomeGoals.doubleValue() / 4) * (fullTimeAwayConceded.doubleValue() / 4);
            double fullTimeAwayAverage = (fullTimeAwayGoals.doubleValue() / 4) * (fullTimeHomeConceded.doubleValue() / 4);

            double secondHalfHomeAverage = (secondHalfHomeGoals.doubleValue() / 4) * (secondHalfAwayConceded.doubleValue() / 4);
            double secondHalfAwayAverage = (secondHalfAwayGoals.doubleValue() / 4) * (secondHalfHomeConceded.doubleValue() / 4);

            double firstHalfHomeAverage = (firstHalfHomeGoals.doubleValue() / 4) * (firstHalfAwayConceded.doubleValue() / 4);
            double firstHalfAwayAverage = (firstHalfAwayGoals.doubleValue() / 4) * (firstHalfHomeConceded.doubleValue() / 4);

            PoissonModel fullTime = new PoissonModel("full", fullTimeHomeAverage, fullTimeAwayAverage);
            double bttsYes = (double) fullTime.btts().get(0);
            double bttsNo = (double) fullTime.btts().get(1);
            String bttsTip = (String) fullTime.btts().get(2);

            Btts btts = new Btts();
            btts.setYes(bttsYes);
            btts.setNo(bttsNo);
            btts.setTip(bttsTip);
            getBttsBean().create(btts);

            double oneX = (double) fullTime.doubleChance().get(0);
            double oneTwo = (double) fullTime.doubleChance().get(1);
            double xTwo = (double) fullTime.doubleChance().get(2);
            String chanceTip = (String) fullTime.doubleChance().get(3);

            DoubleChance doubleChance = new DoubleChance();
            doubleChance.setOneX(oneX);
            doubleChance.setOneTwo(oneTwo);
            doubleChance.setXTwo(xTwo);
            doubleChance.setTip(chanceTip);
            getDoubleChanceBean().create(doubleChance);

            PoissonModel secondHalfModel = new PoissonModel(secondHalfHomeAverage, secondHalfAwayAverage);
            double secondHome = (double) secondHalfModel.getThreeWays().get(0);
            double secondDraw = (double) secondHalfModel.getThreeWays().get(1);
            double secondAway = (double) secondHalfModel.getThreeWays().get(2);
            String secondThreeWaysTip = (String) secondHalfModel.getThreeWays().get(3);

            SecondHalf secondHalf = new SecondHalf();
            secondHalf.setHome(secondHome);
            secondHalf.setDraw(secondDraw);
            secondHalf.setAway(secondAway);
            secondHalf.setTip(secondThreeWaysTip);
            getSecondHalfBean().create(secondHalf);

            double overSecond = (double) secondHalfModel.overUnderOne().get(0);
            double underSecond = (double) secondHalfModel.overUnderOne().get(1);
            String overSecondTip = (String) secondHalfModel.overUnderOne().get(2);

            SecondHalfGoals secondHalfGoals = new SecondHalfGoals();
            secondHalfGoals.setOver(overSecond);
            secondHalfGoals.setUnder(underSecond);
            secondHalfGoals.setTip(overSecondTip);
            getSecondHalfGoalsBean().create(secondHalfGoals);

            PoissonModel firstHalfModel = new PoissonModel(firstHalfHomeAverage, firstHalfAwayAverage);
            double firstThreeWaysHome = (double) firstHalfModel.getThreeWays().get(0);
            double firstThreeWaysDraw = (double) firstHalfModel.getThreeWays().get(1);
            double firstThreeWaysAway = (double) firstHalfModel.getThreeWays().get(2);
            String firstThreeWaysTip = (String) firstHalfModel.getThreeWays().get(3);

            FirstHalf firstHalf = new FirstHalf();
            firstHalf.setHome(firstThreeWaysHome);
            firstHalf.setDraw(firstThreeWaysDraw);
            firstHalf.setAway(firstThreeWaysAway);
            firstHalf.setTip(firstThreeWaysTip);
            getFirstHalfBean().create(firstHalf);

            double firstOver = (double) firstHalfModel.overUnderOne().get(0);
            double firstUnder = (double) firstHalfModel.overUnderOne().get(1);
            String firstTip = (String) firstHalfModel.overUnderOne().get(2);

            FirstHalfGoals firstHalfGoals = new FirstHalfGoals();
            firstHalfGoals.setOver(firstOver);
            firstHalfGoals.setUnder(firstUnder);
            firstHalfGoals.setTip(firstTip);
            getFirstHalfGoalsBean().create(firstHalfGoals);

            double over = (firstOver + overSecond) / 2;
            double under = (firstUnder + underSecond) / 2;

            OverUnder overUnder = new OverUnder();
            overUnder.setOver(over);
            overUnder.setUnder(under);
            overUnder.setTip(over > under ? "Ov" : "Und");
            getOverUnderBean().create(overUnder);

            selected.setBtts(btts);
            selected.setDoubleChance(doubleChance);
            selected.setSecondHalf(secondHalf);
            selected.setSecondHalfGoals(secondHalfGoals);
            selected.setFirstHalf(firstHalf);
            selected.setFirstHalfGoals(firstHalfGoals);
            selected.setOverUnder(overUnder);
        } else {
            selected.setBtts(bttsBean.find(215));
            selected.setDoubleChance(doubleChanceBean.find(215));
            selected.setSecondHalf(secondHalfBean.find(215));
            selected.setSecondHalfGoals(secondHalfGoalsBean.find(215));
            selected.setFirstHalf(firstHalfBean.find(215));
            selected.setFirstHalfGoals(firstHalfGoalsBean.find(215));
            selected.setOverUnder(overUnderBean.find(215));
        }
    }

    public List<SelectItem> getLeagues() {
        return leagues;
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
    }

    public Matches getSelected() {
        return selected;
    }

    public void setSelected(Matches selected) {
        this.selected = selected;
    }

    private MatchesBean getMatchBean() {
        return matchBean;
    }

    public FirstHalfBean getFirstHalfBean() {
        return firstHalfBean;
    }

    public FirstHalfGoalsBean getFirstHalfGoalsBean() {
        return firstHalfGoalsBean;
    }

    public SecondHalfBean getSecondHalfBean() {
        return secondHalfBean;
    }

    public SecondHalfGoalsBean getSecondHalfGoalsBean() {
        return secondHalfGoalsBean;
    }

    public BttsBean getBttsBean() {
        return bttsBean;
    }

    public OverUnderBean getOverUnderBean() {
        return overUnderBean;
    }

    public DoubleChanceBean getDoubleChanceBean() {
        return doubleChanceBean;
    }

    public MatchStatusBean getMatchStatusBean() {
        return matchStatusBean;
    }

    public SeasonBean getSeasonBean() {
        return seasonBean;
    }

    public TeamBean getTeamBean() {
        return teamBean;
    }

    public LeagueBean getLeagueBean() {
        return leagueBean;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
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

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Integer homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Integer awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getFullTimeHomeGoal() {
        return fullTimeHomeGoal;
    }

    public void setFullTimeHomeGoal(Integer fullTimeHomeGoal) {
        this.fullTimeHomeGoal = fullTimeHomeGoal;
    }

    public Integer getFullTimeAwayGoal() {
        return fullTimeAwayGoal;
    }

    public void setFullTimeAwayGoal(Integer fullTimeAwayGoal) {
        this.fullTimeAwayGoal = fullTimeAwayGoal;
    }

    public Integer getHalfTimeHomeGoal() {
        return halfTimeHomeGoal;
    }

    public void setHalfTimeHomeGoal(Integer halfTimeHomeGoal) {
        this.halfTimeHomeGoal = halfTimeHomeGoal;
    }

    public Integer getHalfTimeAwayGoal() {
        return halfTimeAwayGoal;
    }

    public void setHalfTimeAwayGoal(Integer halfTimeAwayGoal) {
        this.halfTimeAwayGoal = halfTimeAwayGoal;
    }

    public Integer getHomeShot() {
        return homeShot;
    }

    public void setHomeShot(Integer homeShot) {
        this.homeShot = homeShot;
    }

    public Integer getAwayShot() {
        return awayShot;
    }

    public void setAwayShot(Integer awayShot) {
        this.awayShot = awayShot;
    }

    public Integer getHomeShotOnTarget() {
        return homeShotOnTarget;
    }

    public void setHomeShotOnTarget(Integer homeShotOnTarget) {
        this.homeShotOnTarget = homeShotOnTarget;
    }

    public Integer getAwayShotOnTarget() {
        return awayShotOnTarget;
    }

    public void setAwayShotOnTarget(Integer awayShotOnTarget) {
        this.awayShotOnTarget = awayShotOnTarget;
    }

    public Integer getHomeFoul() {
        return homeFoul;
    }

    public void setHomeFoul(Integer homeFoul) {
        this.homeFoul = homeFoul;
    }

    public Integer getAwayFoul() {
        return awayFoul;
    }

    public void setAwayFoul(Integer awayFoul) {
        this.awayFoul = awayFoul;
    }

    public Integer getHomeCorner() {
        return homeCorner;
    }

    public void setHomeCorner(Integer homeCorner) {
        this.homeCorner = homeCorner;
    }

    public Integer getAwayCorner() {
        return awayCorner;
    }

    public void setAwayCorner(Integer awayCorner) {
        this.awayCorner = awayCorner;
    }

    public Integer getHomeYellow() {
        return homeYellow;
    }

    public void setHomeYellow(Integer homeYellow) {
        this.homeYellow = homeYellow;
    }

    public Integer getAwayYellow() {
        return awayYellow;
    }

    public void setAwayYellow(Integer awayYellow) {
        this.awayYellow = awayYellow;
    }

    public Integer getHomeRed() {
        return homeRed;
    }

    public void setHomeRed(Integer homeRed) {
        this.homeRed = homeRed;
    }

    public Integer getAwayRed() {
        return awayRed;
    }

    public void setAwayRed(Integer awayRed) {
        this.awayRed = awayRed;
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

    public boolean isRenderGoalsFirstHalf() {
        return renderGoalsFirstHalf;
    }

    public void setRenderGoalsFirstHalf(boolean renderGoalsFirstHalf) {
        this.renderGoalsFirstHalf = renderGoalsFirstHalf;
    }

    public boolean isRenderGoalsSecondHalf() {
        return renderGoalsSecondHalf;
    }

    public void setRenderGoalsSecondHalf(boolean renderGoalsSecondHalf) {
        this.renderGoalsSecondHalf = renderGoalsSecondHalf;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLeague() {
        return league;
    }

    public void setLeague(Integer league) {
        this.league = league;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public League getLeag() {
        return leag;
    }

    public void setLeag(League leag) {
        this.leag = leag;
    }

    @FacesConverter(forClass = Matches.class)
    public static class MatchesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MatchesController controller = (MatchesController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "matchesController");
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
