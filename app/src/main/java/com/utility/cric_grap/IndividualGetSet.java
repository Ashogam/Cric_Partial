package com.utility.cric_grap;

/**
 * Created by ANDROID on 10-12-2015.
 */
public class IndividualGetSet {

    private static String PLAYERNAME;
    private static String BALL_NUMBER;
    private static String SCORE;

    public String getPLAYERNAME() {
        return PLAYERNAME;
    }

    public static void setPLAYERNAME(String PLAYERNAME) {
        IndividualGetSet.PLAYERNAME = PLAYERNAME;
    }

    public String getBallNumber() {
        return BALL_NUMBER;
    }

    public static void setBallNumber(String ballNumber) {
        BALL_NUMBER = ballNumber;
    }

    public String getSCORE() {
        return SCORE;
    }

    public static void setSCORE(String SCORE) {
        IndividualGetSet.SCORE = SCORE;
    }
}
