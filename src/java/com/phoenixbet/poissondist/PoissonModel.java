package com.phoenixbet.poissondist;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.distribution.PoissonDistribution;

/**
 * Calculates the possible outcome of a match based on 
 * The Poisson Distribution.
 * @author Ogundipe Segun David
 */
public class PoissonModel {
    
    private double homeAverage;
    private double awayAverage;
    private static final double EDGE = 0.4;
    private static final double FULLEDGE = 0.1;

    public PoissonModel() {
    }
    
    public PoissonModel(double homeAverage, double awayAverage) {
        this.homeAverage = homeAverage + EDGE;
        this.awayAverage = awayAverage + EDGE;
    }
    
    public PoissonModel( String full, double homeAverage, double awayAverage) {
        this.homeAverage = homeAverage + FULLEDGE;
        this.awayAverage = awayAverage + FULLEDGE;
    }
    
    private List<Double> setHomeGoals() {
        List<Double> homeGoals = new ArrayList<>();
        PoissonDistribution dist = new PoissonDistribution(homeAverage);

        for (int i = 0; i <= 10; i++) {
            homeGoals.add(dist.probability(i));
        }
        return homeGoals;
    }
    
    private List<Double> setAwayGoals() {
        List<Double> awayGoals = new ArrayList<>();
        PoissonDistribution dist = new PoissonDistribution(awayAverage);

        for (int i = 0; i <= 10; i++) {
            awayGoals.add(dist.probability(i));
        }
        return awayGoals;
    }
    
    private List<Double> possibleMatchScores() {
        List<Double> goalList = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                goalList.add(setHomeGoals().get(i) * setAwayGoals().get(j));
            }
        }
        return goalList;
    }
    
    public double getPossibleGoal() {
        double goal = 0.0;
        for (Double score : possibleMatchScores()) {
            if (score > goal) {
                goal = score;
            }
        }
        return goal;
    }
    
    public List getThreeWays() {
        List threeWays = new ArrayList<>();
        double home = 0.0;
        double draw = 0.0;
        double away = 0.0;
        String tip = " ";
        

        for (int i = 0; i <= 120; i++) {
            if (i >= 0 & i < 11 || i > 11 & i < 22 || i > 23 & i < 33 || i > 35 & i < 44) {
                continue;
            }
            if (i > 47 & i < 55 || i > 59 & i < 66 || i > 71 & i < 77) {
                continue;
            }
            if (i > 83 & i < 88 || i > 95 & i < 99 || i > 107 & i < 110 || i > 119) {
                continue;
            }
            home += possibleMatchScores().get(i);
        }

        for (int j = 0; j <= 120; j++) {
            if (j != 0 & j != 12 & j != 24 & j != 36 & j != 48 & j != 60
                    & j != 72 & j != 84 & j != 96 & j != 108 & j != 120) {
                continue;
            }
            draw += possibleMatchScores().get(j);
        }

        for (int k = 0; k <= 120; k++) {
            if (k == 0 || k > 10 & k < 13 || k > 21 & k < 25 || k > 32 & k < 37) {
                continue;
            }
            if (k > 43 & k < 49 || k > 54 & k < 61 || k > 65 & k < 73 || k > 76 & k < 85) {
                continue;
            }
            if (k > 87 & k < 97 || k > 98 & k < 109 || k > 109) {
                continue;
            }
            away += possibleMatchScores().get(k);
        }
        
        if (home > draw && home > away) {
            tip = "1";
        } else if (draw > home && draw > away){
            tip = "X";
        } else if (away > home && away > draw){
            tip = "2";
        }

        threeWays.add(home);
        threeWays.add(draw);
        threeWays.add(away);
        threeWays.add(tip);
        return threeWays;
    }
    
    public List overUnderOne() {
        List overUnder = new ArrayList<>();
        double over = 0.0;
        double under = 0.0;
        String tip;

        for (int i = 0; i <= 120; i++) {
            if (i == 0 || i == 1 || i == 11) {
                continue;
            }
            over += possibleMatchScores().get(i);
        }

        for (int k = 0; k <= 11; k++) {
            if (k == 0 || k == 1 || k == 11) {
                under += possibleMatchScores().get(k);
            }
        }
        
        if(over > under){
            tip = "Ov";
        } else {
            tip = "Und";
        }

        overUnder.add(over);
        overUnder.add(under);
        overUnder.add(tip);
        return overUnder;
    }
    
    public List btts() {
        List btts = new ArrayList<>();
        double gg = 0.0;
        double ng = 0.0;
        String tip;

        for (int i = 0; i <= 120; i++) {
            if (i < 12 || i == 22 || i == 33 || i == 44 || i == 55
                    || i == 66 || i == 77 || i == 88 || i == 99 || i == 110) {
                continue;
            }
            gg += possibleMatchScores().get(i);
        }

        for (int k = 0; k <= 120; k++) {
            if (k < 12 || k == 22 || k == 33 || k == 44 || k == 55
                    || k == 66 || k == 77 || k == 88 || k == 99 || k == 110) {
                ng += possibleMatchScores().get(k);
            }
        }
        
        if(gg > ng){
            tip = "Yes";
        } else {
            tip = "No";
        }

        btts.add(gg);
        btts.add(ng);
        btts.add(tip);
        return btts;
    }
    
    public List doubleChance() {
        List doubleChance = new ArrayList<>();
        double oneX = 0.0;
        double oneTwo = 0.0;
        double xTwo = 0.0;
        String tip = " ";

        for (int i = 0; i <= 120; i++) {
            if (i >= 1 & i < 11 || i > 12 & i < 22 || i > 24 & i < 33 || i > 36 & i < 44) {
                continue;
            }
            if (i > 48 & i < 55 || i > 60 & i < 66 || i > 72 & i < 77) {
                continue;
            }
            if (i > 84 & i < 88 || i > 96 & i < 99 || i > 108 & i < 110) {
                continue;
            }
            oneX += possibleMatchScores().get(i);
        }

        for (int j = 0; j <= 120; j++) {
            if (j == 0 || j == 12 || j == 24 || j == 36 || j == 48 || j == 60
                    || j == 72 || j == 84 || j == 96 || j == 108 || j == 120) {
                continue;
            }
            oneTwo += possibleMatchScores().get(j);
        }

        for (int k = 0; k <= 120; k++) {
            if (k > 10 & k < 12 || k > 21 & k < 24 || k > 32 & k < 36) {
                continue;
            }
            if (k > 43 & k < 48 || k > 54 & k < 60 || k > 65 & k < 72 || k > 76 & k < 84) {
                continue;
            }
            if (k > 87 & k < 96 || k > 98 & k < 108 || k > 109 & k < 120) {
                continue;
            }
            xTwo += possibleMatchScores().get(k);
        }
        
        if (oneX > oneTwo && oneX > xTwo) {
            tip = "1X";
        } else if (oneTwo > oneX && oneTwo > xTwo){
            tip = "12";
        } else if (xTwo > oneX && xTwo > oneTwo){
            tip = "X2";
        }

        doubleChance.add(oneX/2);
        doubleChance.add(oneTwo/2);
        doubleChance.add(xTwo/2);
        doubleChance.add(tip);
        return doubleChance;
    }
}
