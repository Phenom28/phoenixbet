package com.phoenixbet.excel;

import java.util.Date;

/**
 * Holder for match data uploaded from excel
 * @author Ogundipe Segun David
 */
public class ExcelMatch {
    
    private Date date;
    private Integer round;
    private String homeTeam;
    private String awayTeam;
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
    
    public ExcelMatch(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
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
    
}
