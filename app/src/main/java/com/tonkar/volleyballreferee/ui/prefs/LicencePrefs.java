package com.tonkar.volleyballreferee.ui.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class LicencePrefs {
    private static final String FILE = "score_sheet_licences";
    private final SharedPreferences sp;

    public LicencePrefs(Context ctx) {
        sp = ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE);
    }

    private String k(String gameId, String who) { return gameId + "_" + who; }

    public void setRef1(String gameId, String v){ sp.edit().putString(k(gameId,"ref1"), v == null ? "" : v).apply(); }
    public void setRef2(String gameId, String v){ sp.edit().putString(k(gameId,"ref2"), v == null ? "" : v).apply(); }
    public void setScorer(String gameId, String v){ sp.edit().putString(k(gameId,"scorer"), v == null ? "" : v).apply(); }

    public String getRef1(String gameId){ return sp.getString(k(gameId,"ref1"), ""); }
    public String getRef2(String gameId){ return sp.getString(k(gameId,"ref2"), ""); }
    public String getScorer(String gameId){ return sp.getString(k(gameId,"scorer"), ""); }

    public void setHomeCoach(String gameId, String v){ sp.edit().putString(k(gameId,"home_coach"), v == null ? "" : v).apply(); }
    public void setGuestCoach(String gameId, String v){ sp.edit().putString(k(gameId,"guest_coach"), v == null ? "" : v).apply(); }
    public void setHomeStaff(String gameId, String v){ sp.edit().putString(k(gameId,"home_staff"), v == null ? "" : v).apply(); }
    public void setGuestStaff(String gameId, String v){ sp.edit().putString(k(gameId,"guest_staff"), v == null ? "" : v).apply(); }

    public String getHomeCoach(String gameId){ return sp.getString(k(gameId,"home_coach"), ""); }
    public String getGuestCoach(String gameId){ return sp.getString(k(gameId,"guest_coach"), ""); }
    public String getHomeStaff(String gameId){ return sp.getString(k(gameId,"home_staff"), ""); }
    public String getGuestStaff(String gameId){ return sp.getString(k(gameId,"guest_staff"), ""); }
}
