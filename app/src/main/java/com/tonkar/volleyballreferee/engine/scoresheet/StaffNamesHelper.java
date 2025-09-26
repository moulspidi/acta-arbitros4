package com.tonkar.volleyballreferee.ui.scoresheet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.webkit.WebView;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.tonkar.volleyballreferee.R;
import com.tonkar.volleyballreferee.engine.scoresheet.ScoreSheetBuilder;

public final class StaffNamesHelper {

    public static final String PREF_HOME_COACH2   = "staff_home_coach2";
    public static final String PREF_HOME_STAFF1   = "staff_home_staff1";
    public static final String PREF_HOME_STAFF2   = "staff_home_staff2";
    public static final String PREF_GUEST_COACH2  = "staff_guest_coach2";
    public static final String PREF_GUEST_STAFF1  = "staff_guest_staff1";
    public static final String PREF_GUEST_STAFF2  = "staff_guest_staff2";

    private StaffNamesHelper() {}

    /**
     * Conecta el botón, muestra el diálogo, guarda en SharedPreferences,
     * y regenera el acta en la WebView usando el ScoreSheetBuilder dado.
     */
    public static void attach(Activity activity, WebView webView, ScoreSheetBuilder builder) {
        ExtendedFloatingActionButton fab = activity.findViewById(R.id.score_sheet_staff_button);
        if (fab == null) return;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        fab.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.dialog_staff_names, null, false);

            EditText homeCoach2  = view.findViewById(R.id.input_home_coach2);
            EditText homeStaff1  = view.findViewById(R.id.input_home_staff1);
            EditText homeStaff2  = view.findViewById(R.id.input_home_staff2);
            EditText guestCoach2 = view.findViewById(R.id.input_guest_coach2);
            EditText guestStaff1 = view.findViewById(R.id.input_guest_staff1);
            EditText guestStaff2 = view.findViewById(R.id.input_guest_staff2);

            // Prefill
            homeCoach2.setText(prefs.getString(PREF_HOME_COACH2, ""));
            homeStaff1.setText(prefs.getString(PREF_HOME_STAFF1, ""));
            homeStaff2.setText(prefs.getString(PREF_HOME_STAFF2, ""));
            guestCoach2.setText(prefs.getString(PREF_GUEST_COACH2, ""));
            guestStaff1.setText(prefs.getString(PREF_GUEST_STAFF1, ""));
            guestStaff2.setText(prefs.getString(PREF_GUEST_STAFF2, ""));

            new AlertDialog.Builder(activity)
                .setTitle(R.string.staff_names_title)
                .setView(view)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.save, (d, w) -> {
                    // Guardar
                    prefs.edit()
                         .putString(PREF_HOME_COACH2,  safe(homeCoach2.getText()))
                         .putString(PREF_HOME_STAFF1,  safe(homeStaff1.getText()))
                         .putString(PREF_HOME_STAFF2,  safe(homeStaff2.getText()))
                         .putString(PREF_GUEST_COACH2, safe(guestCoach2.getText()))
                         .putString(PREF_GUEST_STAFF1, safe(guestStaff1.getText()))
                         .putString(PREF_GUEST_STAFF2, safe(guestStaff2.getText()))
                         .apply();

                    // Pasar al builder
                    builder
                        .setHomeCoach2Name(safe(homeCoach2.getText()))
                        .setHomeStaff1Name(safe(homeStaff1.getText()))
                        .setHomeStaff2Name(safe(homeStaff2.getText()))
                        .setGuestCoach2Name(safe(guestCoach2.getText()))
                        .setGuestStaff1Name(safe(guestStaff1.getText()))
                        .setGuestStaff2Name(safe(guestStaff2.getText()));

                    // Regenerar WebView
                    ScoreSheetBuilder.ScoreSheet scoreSheet = builder.createScoreSheet();
                    webView.loadDataWithBaseURL(null, scoreSheet.content(), "text/html", "UTF-8", null);
                })
                .show();
        });
    }

    private static String safe(CharSequence s) {
        return s == null ? "" : s.toString().trim();
    }
}
