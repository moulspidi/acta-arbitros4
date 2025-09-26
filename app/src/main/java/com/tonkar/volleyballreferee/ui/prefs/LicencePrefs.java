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
}
