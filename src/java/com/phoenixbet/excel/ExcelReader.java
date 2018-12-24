package com.phoenixbet.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

/**
 * This class load matches from an excel file
 * @author Ogundipe Segun David
 */
public class ExcelReader implements Serializable {

    private static final long serialVersionUID = -2883555699682403107L;
    private String sheet;
    private int row;
    private int firstRow;
    private int lastRow;
    private int date;
    private int round;
    private int homeTeam, awayTeam;
    private int fthgoal, ftagoal;
    private int hthgoal, htagoal;
    private int hs, as;
    private int hst, ast;
    private int hf, af;
    private int hc, ac;
    private int hy, ay;
    private int hr, ar;
    private int firstColumn, lastColumn;
    private UploadedFile file;
//    private String excelFilePath = "C:/Users/Loretta/Desktop/New folder/all-euro-data-2018-2019.xlsx";

    public ExcelReader() {
    }

    public ExcelReader(UploadedFile file, String sheet, int row, int date, int home, int away, int fhgoal, int fagoal,
            int hhgoal, int hagoal, int hShot, int aShot, int hsTarget, int asTarget,
            int hFoul, int aFoul, int hCorner, int aCorner, int hYellow, int aYellow,
            int hRed, int aRed) {
        this.file = file;
        this.sheet = sheet;
        this.row = row - 1;
        this.date = date;
        this.homeTeam = home;
        this.awayTeam = away;
        this.fthgoal = fhgoal;
        this.ftagoal = fagoal;
        this.hthgoal = hhgoal;
        this.htagoal = hagoal;
        this.hs = hShot;
        this.as = aShot;
        this.hst = hsTarget;
        this.ast = asTarget;
        this.hf = hFoul;
        this.af = aFoul;
        this.hc = hCorner;
        this.ac = aCorner;
        this.hy = hYellow;
        this.ay = aYellow;
        this.hr = hRed;
        this.ar = aRed;
    }

    public ExcelReader(UploadedFile file, String sheet, int firstRow, int lastRow, int date, int home, int away,
            int fhgoal, int fagoal, int hhgoal, int hagoal, int hShot, int aShot,
            int hsTarget, int asTarget, int hFoul, int aFoul, int hCorner, int aCorner,
            int hYellow, int aYellow, int hRed, int aRed) {
        this.file = file;
        this.sheet = sheet;
        this.firstRow = firstRow - 1;
        this.lastRow = lastRow - 1;
        this.date = date;
        this.homeTeam = home;
        this.awayTeam = away;
        this.fthgoal = fhgoal;
        this.ftagoal = fagoal;
        this.hthgoal = hhgoal;
        this.htagoal = hagoal;
        this.hs = hShot;
        this.as = aShot;
        this.hst = hsTarget;
        this.ast = asTarget;
        this.hf = hFoul;
        this.af = aFoul;
        this.hc = hCorner;
        this.ac = aCorner;
        this.hy = hYellow;
        this.ay = aYellow;
        this.hr = hRed;
        this.ar = aRed;
    }

    public ExcelReader(UploadedFile file, String sheet, int row, int fhgoal, int fagoal,
            int hhgoal, int hagoal, int hShot, int aShot, int hsTarget, int asTarget, int hFoul,
            int aFoul, int hCorner, int aCorner, int hYellow, int aYellow, int hRed, int aRed) {
        this.file = file;
        this.sheet = sheet;
        this.row = row - 1;
        this.fthgoal = fhgoal;
        this.ftagoal = fagoal;
        this.hthgoal = hhgoal;
        this.htagoal = hagoal;
        this.hs = hShot;
        this.as = aShot;
        this.hst = hsTarget;
        this.ast = asTarget;
        this.hf = hFoul;
        this.af = aFoul;
        this.hc = hCorner;
        this.ac = aCorner;
        this.hy = hYellow;
        this.ay = aYellow;
        this.hr = hRed;
        this.ar = aRed;
    }

    public ExcelReader(UploadedFile file, String sheet, int firstRow, int lastRow, int firstColumn, int lastColumn) {
        this.file = file;
        this.sheet = sheet;
        this.firstRow = firstRow - 1;
        this.lastRow = lastRow - 1;
        this.firstColumn = firstColumn - 1;
        this.lastColumn = lastColumn - 1;
    }

    public ExcelReader(UploadedFile file, String sheet, int firstRow, int lastRow, int date, int round, int home, int away) {
        this.file = file;
        this.sheet = sheet;
        this.firstRow = firstRow - 1;
        this.lastRow = lastRow - 1;
        this.date = date;
        this.round = round;
        this.homeTeam = home;
        this.awayTeam = away;
    }
    
    public ExcelReader(UploadedFile file, String sheet, int firstRow, int lastRow, int fhgoal,
            int fagoal, int hhgoal, int hagoal, int hShot, int aShot, int hsTarget, int asTarget,
            int hFoul, int aFoul, int hCorner, int aCorner, int hYellow, int aYellow, int hRed,
            int aRed){
        this.file = file;
        this.sheet = sheet;
        this.firstRow = firstRow - 1;
        this.lastRow = lastRow - 1;
        this.fthgoal = fhgoal;
        this.ftagoal = fagoal;
        this.hthgoal = hhgoal;
        this.htagoal = hagoal;
        this.hs = hShot;
        this.as = aShot;
        this.hst = hsTarget;
        this.ast = asTarget;
        this.hf = hFoul;
        this.af = aFoul;
        this.hc = hCorner;
        this.ac = aCorner;
        this.hy = hYellow;
        this.ay = aYellow;
        this.hr = hRed;
        this.ar = aRed;
    }

    public ExcelMatch getMatch() throws IOException {
        ExcelMatch match = new ExcelMatch();
        Workbook excel;

        try (InputStream input = file.getInputstream()) {
            excel = new XSSFWorkbook(input);

            Sheet league = excel.getSheet(sheet);
            Row matchRow = league.getRow(row);

            Cell dateCell = matchRow.getCell(date);
            Cell homeCell = matchRow.getCell(homeTeam);
            Cell awayCell = matchRow.getCell(awayTeam);
            Cell fHomeGoalCell = matchRow.getCell(fthgoal);
            Cell fAwayGoalCell = matchRow.getCell(ftagoal);
            Cell hHomeGoalCell = matchRow.getCell(hthgoal);
            Cell hAwayGoalCell = matchRow.getCell(htagoal);
            Cell hsCell = matchRow.getCell(hs);
            Cell asCell = matchRow.getCell(as);
            Cell hstCell = matchRow.getCell(hst);
            Cell astCell = matchRow.getCell(ast);
            Cell hfCell = matchRow.getCell(hf);
            Cell afCell = matchRow.getCell(af);
            Cell hcCell = matchRow.getCell(hc);
            Cell acCell = matchRow.getCell(ac);
            Cell hyCell = matchRow.getCell(hy);
            Cell ayCell = matchRow.getCell(ay);
            Cell hrCell = matchRow.getCell(hr);
            Cell arCell = matchRow.getCell(ar);

            match.setDate(dateCell.getDateCellValue());
            match.setHomeTeam(homeCell.getRichStringCellValue().getString());
            match.setAwayTeam(awayCell.getRichStringCellValue().getString());
            match.setFullTimeHomeGoal((int) fHomeGoalCell.getNumericCellValue());
            match.setFullTimeAwayGoal((int) fAwayGoalCell.getNumericCellValue());
            match.setHalfTimeHomeGoal((int) hHomeGoalCell.getNumericCellValue());
            match.setHalfTimeAwayGoal((int) hAwayGoalCell.getNumericCellValue());
            match.setHomeShot((int) hsCell.getNumericCellValue());
            match.setAwayShot((int) asCell.getNumericCellValue());
            match.setHomeShotOnTarget((int) hstCell.getNumericCellValue());
            match.setAwayShotOnTarget((int) astCell.getNumericCellValue());
            match.setHomeFoul((int) hfCell.getNumericCellValue());
            match.setAwayFoul((int) afCell.getNumericCellValue());
            match.setHomeCorner((int) hcCell.getNumericCellValue());
            match.setAwayCorner((int) acCell.getNumericCellValue());
            match.setHomeYellow((int) hyCell.getNumericCellValue());
            match.setAwayYellow((int) ayCell.getNumericCellValue());
            match.setHomeRed((int) hrCell.getNumericCellValue());
            match.setAwayRed((int) arCell.getNumericCellValue());

            input.close();
        }
        excel.close();
        return match;
    }

    public List<ExcelMatch> getMatches() throws IOException {
        List<ExcelMatch> matches = new ArrayList();
        ExcelMatch match;
        Workbook excel;

        try (InputStream input = file.getInputstream()) {
            excel = new XSSFWorkbook(input);
            Sheet league = excel.getSheet(sheet);

            for (int i = firstRow; i <= lastRow; i++) {
                match = new ExcelMatch();
                Cell dateCell = league.getRow(i).getCell(date);
                Cell homeCell = league.getRow(i).getCell(homeTeam);
                Cell awayCell = league.getRow(i).getCell(awayTeam);
                Cell fhgCell = league.getRow(i).getCell(fthgoal);
                Cell fagCell = league.getRow(i).getCell(ftagoal);
                Cell hhgCell = league.getRow(i).getCell(hthgoal);
                Cell hagCell = league.getRow(i).getCell(htagoal);
                Cell hsCell = league.getRow(i).getCell(hs);
                Cell asCell = league.getRow(i).getCell(as);
                Cell hstCell = league.getRow(i).getCell(hst);
                Cell astCell = league.getRow(i).getCell(ast);
                Cell hfCell = league.getRow(i).getCell(hf);
                Cell afCell = league.getRow(i).getCell(af);
                Cell hcCell = league.getRow(i).getCell(hc);
                Cell acCell = league.getRow(i).getCell(ac);
                Cell hyCell = league.getRow(i).getCell(hy);
                Cell ayCell = league.getRow(i).getCell(ay);
                Cell hrCell = league.getRow(i).getCell(hr);
                Cell arCell = league.getRow(i).getCell(ar);

                match.setDate(dateCell.getDateCellValue());
                match.setHomeTeam(homeCell.getStringCellValue());
                match.setAwayTeam(awayCell.getStringCellValue());
                match.setFullTimeHomeGoal((int) fhgCell.getNumericCellValue());
                match.setFullTimeAwayGoal((int) fagCell.getNumericCellValue());
                match.setHalfTimeHomeGoal((int) hhgCell.getNumericCellValue());
                match.setHalfTimeAwayGoal((int) hagCell.getNumericCellValue());
                match.setHomeShot((int) hsCell.getNumericCellValue());
                match.setAwayShot((int) asCell.getNumericCellValue());
                match.setHomeShotOnTarget((int) hstCell.getNumericCellValue());
                match.setAwayShotOnTarget((int) astCell.getNumericCellValue());
                match.setHomeFoul((int) hfCell.getNumericCellValue());
                match.setAwayFoul((int) afCell.getNumericCellValue());
                match.setHomeCorner((int) hcCell.getNumericCellValue());
                match.setAwayCorner((int) acCell.getNumericCellValue());
                match.setHomeYellow((int) hyCell.getNumericCellValue());
                match.setAwayYellow((int) ayCell.getNumericCellValue());
                match.setHomeRed((int) hrCell.getNumericCellValue());
                match.setAwayRed((int) arCell.getNumericCellValue());

                matches.add(match);
            }
            input.close();
        }
        excel.close();
        return matches;
    }

    public List<String> getTeamNames() throws IOException {
        List<String> teams = new ArrayList();
        Workbook excel;

        try (InputStream input = file.getInputstream()) {
            excel = new XSSFWorkbook(input);
            Sheet league = excel.getSheet(sheet);

            for (int i = firstRow; i <= lastRow; i++) {
                for (int j = firstColumn; j <= lastColumn; j++) {
                    teams.add(league.getRow(i).getCell(j).getStringCellValue());
                }
            }
            input.close();
        }
        return teams;
    }

    public List<ExcelMatch> getTeams() throws IOException {
        List<ExcelMatch> matches = new ArrayList<>();
        ExcelMatch match;
        Workbook excel;

        try (InputStream input = file.getInputstream()) {
            excel = new XSSFWorkbook(input);
            Sheet league = excel.getSheet(sheet);

            for (int i = firstRow; i <= lastRow; i++) {
                match = new ExcelMatch();

                Cell dateCell = league.getRow(i).getCell(date);
                Cell roundCell = league.getRow(i).getCell(round);
                Cell homeCell = league.getRow(i).getCell(homeTeam);
                Cell awayCell = league.getRow(i).getCell(awayTeam);

                match.setDate(dateCell.getDateCellValue());
                match.setRound((int) roundCell.getNumericCellValue());
                match.setHomeTeam(homeCell.getStringCellValue());
                match.setAwayTeam(awayCell.getStringCellValue());

                matches.add(match);
            }
            input.close();
        }
        return matches;
    }

    public ExcelMatch editMatch() throws IOException {
        ExcelMatch match = new ExcelMatch();
        Workbook excel;

        try (InputStream input = file.getInputstream()) {
            excel = new XSSFWorkbook(input);

            Sheet league = excel.getSheet(sheet);
            Row matchRow = league.getRow(row);

            Cell fHomeGoalCell = matchRow.getCell(fthgoal);
            Cell fAwayGoalCell = matchRow.getCell(ftagoal);
            Cell hHomeGoalCell = matchRow.getCell(hthgoal);
            Cell hAwayGoalCell = matchRow.getCell(htagoal);
            Cell hsCell = matchRow.getCell(hs);
            Cell asCell = matchRow.getCell(as);
            Cell hstCell = matchRow.getCell(hst);
            Cell astCell = matchRow.getCell(ast);
            Cell hfCell = matchRow.getCell(hf);
            Cell afCell = matchRow.getCell(af);
            Cell hcCell = matchRow.getCell(hc);
            Cell acCell = matchRow.getCell(ac);
            Cell hyCell = matchRow.getCell(hy);
            Cell ayCell = matchRow.getCell(ay);
            Cell hrCell = matchRow.getCell(hr);
            Cell arCell = matchRow.getCell(ar);

            match.setFullTimeHomeGoal((int) fHomeGoalCell.getNumericCellValue());
            match.setFullTimeAwayGoal((int) fAwayGoalCell.getNumericCellValue());
            match.setHalfTimeHomeGoal((int) hHomeGoalCell.getNumericCellValue());
            match.setHalfTimeAwayGoal((int) hAwayGoalCell.getNumericCellValue());
            match.setHomeShot((int) hsCell.getNumericCellValue());
            match.setAwayShot((int) asCell.getNumericCellValue());
            match.setHomeShotOnTarget((int) hstCell.getNumericCellValue());
            match.setAwayShotOnTarget((int) astCell.getNumericCellValue());
            match.setHomeFoul((int) hfCell.getNumericCellValue());
            match.setAwayFoul((int) afCell.getNumericCellValue());
            match.setHomeCorner((int) hcCell.getNumericCellValue());
            match.setAwayCorner((int) acCell.getNumericCellValue());
            match.setHomeYellow((int) hyCell.getNumericCellValue());
            match.setAwayYellow((int) ayCell.getNumericCellValue());
            match.setHomeRed((int) hrCell.getNumericCellValue());
            match.setAwayRed((int) arCell.getNumericCellValue());

            input.close();
        }
        return match;
    }

    public List<ExcelMatch> editMatches() throws IOException {
        List<ExcelMatch> matches = new ArrayList();
        ExcelMatch match;
        Workbook excel;

        try (InputStream input = file.getInputstream()) {
            excel = new XSSFWorkbook(input);
            Sheet league = excel.getSheet(sheet);

            for (int i = firstRow; i <= lastRow; i++) {
                match = new ExcelMatch();
                
                Cell fhgCell = league.getRow(i).getCell(fthgoal);
                Cell fagCell = league.getRow(i).getCell(ftagoal);
                Cell hhgCell = league.getRow(i).getCell(hthgoal);
                Cell hagCell = league.getRow(i).getCell(htagoal);
                Cell hsCell = league.getRow(i).getCell(hs);
                Cell asCell = league.getRow(i).getCell(as);
                Cell hstCell = league.getRow(i).getCell(hst);
                Cell astCell = league.getRow(i).getCell(ast);
                Cell hfCell = league.getRow(i).getCell(hf);
                Cell afCell = league.getRow(i).getCell(af);
                Cell hcCell = league.getRow(i).getCell(hc);
                Cell acCell = league.getRow(i).getCell(ac);
                Cell hyCell = league.getRow(i).getCell(hy);
                Cell ayCell = league.getRow(i).getCell(ay);
                Cell hrCell = league.getRow(i).getCell(hr);
                Cell arCell = league.getRow(i).getCell(ar);

                match.setFullTimeHomeGoal((int) fhgCell.getNumericCellValue());
                match.setFullTimeAwayGoal((int) fagCell.getNumericCellValue());
                match.setHalfTimeHomeGoal((int) hhgCell.getNumericCellValue());
                match.setHalfTimeAwayGoal((int) hagCell.getNumericCellValue());
                match.setHomeShot((int) hsCell.getNumericCellValue());
                match.setAwayShot((int) asCell.getNumericCellValue());
                match.setHomeShotOnTarget((int) hstCell.getNumericCellValue());
                match.setAwayShotOnTarget((int) astCell.getNumericCellValue());
                match.setHomeFoul((int) hfCell.getNumericCellValue());
                match.setAwayFoul((int) afCell.getNumericCellValue());
                match.setHomeCorner((int) hcCell.getNumericCellValue());
                match.setAwayCorner((int) acCell.getNumericCellValue());
                match.setHomeYellow((int) hyCell.getNumericCellValue());
                match.setAwayYellow((int) ayCell.getNumericCellValue());
                match.setHomeRed((int) hrCell.getNumericCellValue());
                match.setAwayRed((int) arCell.getNumericCellValue());

                matches.add(match);
            }
            input.close();
        }
        return matches;
    }
}
