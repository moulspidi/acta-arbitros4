package com.tonkar.volleyballreferee.ui.scoresheet;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tonkar.volleyballreferee.R;
import com.tonkar.volleyballreferee.engine.scoresheet.ScoreSheetBuilder;

public final class StaffNamesHelper {

    private static final String K_HOME_COACH2   = "staff.home.coach2";
    private static final String K_HOME_STAFF1   = "staff.home.staff1";
    private static final String K_HOME_STAFF2   = "staff.home.staff2";
    private static final String K_GUEST_COACH2  = "staff.guest.coach2";
    private static final String K_GUEST_STAFF1  = "staff.guest.staff1";
    private static final String K_GUEST_STAFF2  = "staff.guest.staff2";

    private StaffNamesHelper() {}

    /** Llama esto desde tu Activity del acta una vez tengas mWebView y ScoreSheetBuilder listos. */
    public static void attach(Activity activity, WebView webView, ScoreSheetBuilder builder) {
        // Prefija los valores del builder desde preferencias
        applyFromPrefs(activity, builder);

        FloatingActionButton fab = activity.findViewById(R.id.fab_edit_staff);
        if (fab != null) {
            fab.setOnClickListener(v -> showDialog(activity, webView, builder));
        }
    }

    private static void applyFromPrefs(Context ctx, ScoreSheetBuilder b) {
        var prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        b.setHomeCoach2Name(prefs.getString(K_HOME_COACH2, null));
        b.setHomeStaff1Name(prefs.getString(K_HOME_STAFF1, null));
        b.setHomeStaff2Name(prefs.getString(K_HOME_STAFF2, null));
        b.setGuestCoach2Name(prefs.getString(K_GUEST_COACH2, null));
        b.setGuestStaff1Name(prefs.getString(K_GUEST_STAFF1, null));
        b.setGuestStaff2Name(prefs.getString(K_GUEST_STAFF2, null));
    }

    private static void showDialog(Activity act, WebView web, ScoreSheetBuilder b) {
        View dialog = LayoutInflater.from(act).inflate(R.layout.dialog_staff_names, null, false);
        EditText homeCoach2   = dialog.findViewById(R.id.input_home_coach2);
        EditText homeStaff1   = dialog.findViewById(R.id.input_home_staff1);
        EditText homeStaff2   = dialog.findViewById(R.id.input_home_staff2);
        EditText guestCoach2  = dialog.findViewById(R.id.input_guest_coach2);
        EditText guestStaff1  = dialog.findViewById(R.id.input_guest_staff1);
        EditText guestStaff2  = dialog.findViewById(R.id.input_guest_staff2);

        var prefs = PreferenceManager.getDefaultSharedPreferences(act);
        homeCoach2.setText(prefs.getString(K_HOME_COACH2, ""));
        homeStaff1.setText(prefs.getString(K_HOME_STAFF1, ""));
        homeStaff2.setText(prefs.getString(K_HOME_STAFF2, ""));
        guestCoach2.setText(prefs.getString(K_GUEST_COACH2, ""));
        guestStaff1.setText(prefs.getString(K_GUEST_STAFF1, ""));
        guestStaff2.setText(prefs.getString(K_GUEST_STAFF2, ""));

        new MaterialAlertDialogBuilder(act)
                .setTitle(R.string.staff_edit_title)
                .setView(dialog)
                // Usamos strings del sistema para evitar duplicados (OK/Cancel)
                .setPositiveButton(android.R.string.ok, (d, w) -> {
                    // Guardar
                    prefs.edit()
                            .putString(K_HOME_COACH2,  getOrNull(homeCoach2))
                            .putString(K_HOME_STAFF1,  getOrNull(homeStaff1))
                            .putString(K_HOME_STAFF2,  getOrNull(homeStaff2))
                            .putString(K_GUEST_COACH2, getOrNull(guestCoach2))
                            .putString(K_GUEST_STAFF1, getOrNull(guestStaff1))
                            .putString(K_GUEST_STAFF2, getOrNull(guestStaff2))
                            .apply();

                    // Aplicar al builder
                    b.setHomeCoach2Name(getOrNull(homeCoach2));
                    b.setHomeStaff1Name(getOrNull(homeStaff1));
                    b.setHomeStaff2Name(getOrNull(homeStaff2));
                    b.setGuestCoach2Name(getOrNull(guestCoach2));
                    b.setGuestStaff1Name(getOrNull(guestStaff1));
                    b.setGuestStaff2Name(getOrNull(guestStaff2));

                    // Regenerar el acta en el WebView
                    var sheet = b.createScoreSheet();
                    web.loadDataWithBaseURL(null, sheet.content(), "text/html", "UTF-8", null);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Nullable
    private static String getOrNull(EditText et) {
        String s = et.getText() == null ? "" : et.getText().toString().trim();
        return s.isEmpty() ? null : s;
    }
}
