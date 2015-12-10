package com.utility.cric_grap;

/**
 * Created by ANDROID on 09-12-2015.
 */
public class HistoryGetSet {

    private static String TEAMA;
    private static String TEAMB;
    private static String INNINGS;
    private static String OVER;

    public String getTEAMA() {
        return TEAMA;
    }

    public static void setTEAMA(String TEAMA) {
        HistoryGetSet.TEAMA = TEAMA;
    }

    public String getTEAMB() {
        return TEAMB;
    }

    public static void setTEAMB(String TEAMB) {
        HistoryGetSet.TEAMB = TEAMB;
    }

    public String getINNINGS() {
        return INNINGS;
    }

    public static void setINNINGS(String INNINGS) {
        HistoryGetSet.INNINGS = INNINGS;
    }

    public String getOVER() {
        return OVER;
    }

    public static void setOVER(String OVER) {
        HistoryGetSet.OVER = OVER;
    }
}
