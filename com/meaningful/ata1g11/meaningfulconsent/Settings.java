package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pandora.Box;
import pandora.PandoraProblem;
import permissions.PermissionsSetting;
import permissions.PermissionsSettingBox;
import permissions.PrivacyType;

public class Settings extends AppCompatActivity {
    public static Context contextOfApp;
    public static String group = "";
    public static Integer quoteIndex = Integer.valueOf(-1);
    public static List<Quote> quotes = new ArrayList();
    public static String screen = "Setting";
    EditText etTotal;
    Helper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.helper = new Helper(this);
        contextOfApp = getApplicationContext();
        if (this.helper.isNewUser()) {
            Intent i = new Intent(this, Demogs.class);
            finish();
            startActivity(i);
        } else {
            String treatment = this.helper.getTreatment();
            Boolean setPrefs = this.helper.getSetPrefs();
            Integer total = this.helper.getTotalPoints();
            if (new String("Flexible").equals(treatment)) {
                setContentView((int) C0150R.layout.activity_settings);
                this.etTotal = (EditText) findViewById(C0150R.id.etTotalFlex);
            } else {
                setContentView((int) C0150R.layout.activity_settings_strict);
                this.etTotal = (EditText) findViewById(C0150R.id.etTotalStrict);
            }
            this.etTotal.setText(Integer.toString(total.intValue()));
            if (!setPrefs.booleanValue()) {
                String[] meters = this.helper.agent(this.helper.getUserType(), this.helper.getScenarioType());
                String scenario = meters[0];
                String points = meters[1];
                Boolean[] prefs = this.helper.convertToPrefs(scenario);
                total = this.helper.getTotalPoints();
                if (new String("Flexible").equals(treatment)) {
                    setContentView((int) C0150R.layout.activity_settings);
                    this.etTotal = (EditText) findViewById(C0150R.id.etTotalFlex);
                } else {
                    setContentView((int) C0150R.layout.activity_settings_strict);
                    this.etTotal = (EditText) findViewById(C0150R.id.etTotalStrict);
                }
                this.etTotal.setText(Integer.toString(total.intValue()));
                this.helper.Logger(treatment, this.helper.getStudyDay().toString(), screen, "Access", total.toString(), points, prefs[0].toString(), prefs[1].toString(), prefs[2].toString(), prefs[3].toString(), prefs[4].toString());
                if (new String("Flexible").equals(treatment)) {
                    setRadioButtons(prefs);
                    quotes.add(new Quote(prefs, Integer.parseInt(points)));
                    quoteIndex = Integer.valueOf(quoteIndex.intValue() + 1);
                    if (quotes.size() > 1) {
                        ((Button) findViewById(C0150R.id.btnPrev)).setEnabled(true);
                    }
                } else {
                    setRadioButtonsForStrict(prefs);
                }
                ((EditText) findViewById(C0150R.id.etPoints)).setText(points);
            } else if (new String("Flexible").equals(treatment)) {
                arrangeButtons();
            } else {
                arrangeButtonsForStrict();
            }
        }
        setTitle("Settings");
    }

    public void onBackPressed() {
    }

    private void arrangeButtons() {
        ((Button) findViewById(C0150R.id.btnQuote)).setText(Html.fromHtml("Quote<br/><small>(-10 pts)</small>"));
        Button btnPrev = (Button) findViewById(C0150R.id.btnPrev);
        Button btnNext = (Button) findViewById(C0150R.id.btnNext);
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(false);
        btnPrev.setEnabled(false);
        btnNext.setEnabled(false);
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        Boolean state = Boolean.valueOf(sharedPrefs.getBoolean("SetPrefs", false));
        Integer studyDay = Integer.valueOf(sharedPrefs.getInt("Day", 0));
        if (state.booleanValue()) {
            RadioButton rb1 = (RadioButton) findViewById(C0150R.id.rbContactsShare);
            RadioButton rb2 = (RadioButton) findViewById(C0150R.id.rbContactsNotShare);
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            if (sharedPrefs.getBoolean("sContacts", false)) {
                rb1.setChecked(true);
                rb2.setChecked(false);
            } else {
                rb1.setChecked(false);
                rb2.setChecked(true);
            }
            rb1 = (RadioButton) findViewById(C0150R.id.rbMessagesShare);
            rb2 = (RadioButton) findViewById(C0150R.id.rbMessagesNotShare);
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            if (sharedPrefs.getBoolean("sMessages", false)) {
                rb1.setChecked(true);
                rb2.setChecked(false);
            } else {
                rb1.setChecked(false);
                rb2.setChecked(true);
            }
            rb1 = (RadioButton) findViewById(C0150R.id.rbAppsShare);
            rb2 = (RadioButton) findViewById(C0150R.id.rbAppsNotShare);
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            if (sharedPrefs.getBoolean("sApps", false)) {
                rb1.setChecked(true);
                rb2.setChecked(false);
            } else {
                rb1.setChecked(false);
                rb2.setChecked(true);
            }
            rb1 = (RadioButton) findViewById(C0150R.id.rbPhotosShare);
            rb2 = (RadioButton) findViewById(C0150R.id.rbPhotosNotShare);
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            if (sharedPrefs.getBoolean("sPhotos", false)) {
                rb1.setChecked(true);
                rb2.setChecked(false);
            } else {
                rb1.setChecked(false);
                rb2.setChecked(true);
            }
            rb1 = (RadioButton) findViewById(C0150R.id.rbBrowserShare);
            rb2 = (RadioButton) findViewById(C0150R.id.rbBrowserNotShare);
            rb1.setEnabled(false);
            rb2.setEnabled(false);
            if (sharedPrefs.getBoolean("sBrowser", false)) {
                rb1.setChecked(true);
                rb2.setChecked(false);
            } else {
                rb1.setChecked(false);
                rb2.setChecked(true);
            }
            ((EditText) findViewById(C0150R.id.etPoints)).setText(Integer.valueOf(sharedPrefs.getInt("PendingPoints", 0)).toString());
            ((TextView) findViewById(C0150R.id.txtInfoSetting)).setText("Please confirm sharing data from Review.");
        }
        if (Integer.valueOf(sharedPrefs.getInt("QuoteIndex", 100)).equals(Integer.valueOf(-1))) {
            Integer quoteIndex = Integer.valueOf(-1);
            quotes.clear();
        }
    }

    private void arrangeButtonsForStrict() {
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        RadioButton rb1 = (RadioButton) findViewById(C0150R.id.rbContactsShare);
        RadioButton rb2 = (RadioButton) findViewById(C0150R.id.rbContactsNotShare);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        if (sharedPrefs.getBoolean("sContacts", false)) {
            rb1.setChecked(true);
            rb2.setChecked(false);
        } else {
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
        rb1 = (RadioButton) findViewById(C0150R.id.rbMessagesShare);
        rb2 = (RadioButton) findViewById(C0150R.id.rbMessagesNotShare);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        if (sharedPrefs.getBoolean("sMessages", false)) {
            rb1.setChecked(true);
            rb2.setChecked(false);
        } else {
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
        rb1 = (RadioButton) findViewById(C0150R.id.rbAppsShare);
        rb2 = (RadioButton) findViewById(C0150R.id.rbAppsNotShare);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        if (sharedPrefs.getBoolean("sApps", false)) {
            rb1.setChecked(true);
            rb2.setChecked(false);
        } else {
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
        rb1 = (RadioButton) findViewById(C0150R.id.rbPhotosShare);
        rb2 = (RadioButton) findViewById(C0150R.id.rbPhotosNotShare);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        if (sharedPrefs.getBoolean("sPhotos", false)) {
            rb1.setChecked(true);
            rb2.setChecked(false);
        } else {
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
        rb1 = (RadioButton) findViewById(C0150R.id.rbBrowserShare);
        rb2 = (RadioButton) findViewById(C0150R.id.rbBrowserNotShare);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        if (sharedPrefs.getBoolean("sBrowser", false)) {
            rb1.setChecked(true);
            rb2.setChecked(false);
        } else {
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
        ((EditText) findViewById(C0150R.id.etPoints)).setText(Integer.valueOf(sharedPrefs.getInt("OfferPoints", 0)).toString());
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(false);
        ((Button) findViewById(C0150R.id.btnDecline)).setEnabled(false);
    }

    private void setRadioButtonsForStrict(Boolean[] pref) {
        if (pref[0].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbContactsShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbContactsNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbContactsShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbContactsNotShare)).setChecked(true);
        }
        if (pref[1].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbMessagesShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbMessagesNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbMessagesShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbMessagesNotShare)).setChecked(true);
        }
        if (pref[2].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbAppsShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbAppsNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbAppsShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbAppsNotShare)).setChecked(true);
        }
        if (pref[3].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbPhotosShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbPhotosNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbPhotosShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbPhotosNotShare)).setChecked(true);
        }
        if (pref[4].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbBrowserShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbBrowserNotShare)).setChecked(false);
            return;
        }
        ((RadioButton) findViewById(C0150R.id.rbBrowserShare)).setChecked(false);
        ((RadioButton) findViewById(C0150R.id.rbBrowserNotShare)).setChecked(true);
    }

    private void setRadioButtons(Boolean[] pref) {
        ((Button) findViewById(C0150R.id.btnQuote)).setText(Html.fromHtml("Quote<br/><small>(-10 pts)</small>"));
        Button btnPrev = (Button) findViewById(C0150R.id.btnPrev);
        Button btnNext = (Button) findViewById(C0150R.id.btnNext);
        if (quoteIndex.intValue() < 1) {
            btnPrev.setEnabled(false);
        }
        if (quoteIndex.equals(Integer.valueOf(quotes.size() - 1))) {
            btnNext.setEnabled(false);
        }
        if (pref[0].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbContactsShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbContactsNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbContactsShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbContactsNotShare)).setChecked(true);
        }
        if (pref[1].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbMessagesShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbMessagesNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbMessagesShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbMessagesNotShare)).setChecked(true);
        }
        if (pref[2].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbAppsShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbAppsNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbAppsShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbAppsNotShare)).setChecked(true);
        }
        if (pref[3].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbPhotosShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbPhotosNotShare)).setChecked(false);
        } else {
            ((RadioButton) findViewById(C0150R.id.rbPhotosShare)).setChecked(false);
            ((RadioButton) findViewById(C0150R.id.rbPhotosNotShare)).setChecked(true);
        }
        if (pref[4].booleanValue()) {
            ((RadioButton) findViewById(C0150R.id.rbBrowserShare)).setChecked(true);
            ((RadioButton) findViewById(C0150R.id.rbBrowserNotShare)).setChecked(false);
            return;
        }
        ((RadioButton) findViewById(C0150R.id.rbBrowserShare)).setChecked(false);
        ((RadioButton) findViewById(C0150R.id.rbBrowserNotShare)).setChecked(true);
    }

    public void savePreferences(View view) {
        int i;
        String scenario = this.helper.getScenarioFromRadioButtons();
        Context context = getApplicationContext();
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        if (scenario.equals("FFFFF")) {
            prefEditor.putString("ReviewStatus", "NoShare");
        } else {
            prefEditor.putString("ReviewStatus", "DataShare");
        }
        this.helper.setPrefsWithScenario(scenario);
        EditText pointsOffer = (EditText) findViewById(C0150R.id.etPoints);
        prefEditor.putInt("PendingPoints", Integer.parseInt(pointsOffer.getText().toString()));
        prefEditor.putInt("OfferPoints", Integer.parseInt(pointsOffer.getText().toString()));
        prefEditor.commit();
        Boolean[] prefs = this.helper.convertToPrefs(scenario);
        this.helper.Logger(this.helper.getTreatment(), this.helper.getStudyDay().toString(), screen, "Accept", this.helper.getTotalPoints().toString(), pointsOffer.getText().toString(), prefs[0].toString(), prefs[1].toString(), prefs[2].toString(), prefs[3].toString(), prefs[4].toString());
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(false);
        pointsOffer.setEnabled(false);
        if (Boolean.valueOf(sharedPrefs.getBoolean("sContacts", false)).booleanValue()) {
            try {
                this.helper.storeContact();
            } catch (Exception e) {
                for (i = 1; i < 4; i++) {
                    List<String> empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Contacts");
                }
                prefEditor.putBoolean("aContacts", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sMessages", false)).booleanValue()) {
            try {
                this.helper.storeMessages();
            } catch (Exception e2) {
                for (i = 1; i < 4; i++) {
                    empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Messages");
                }
                prefEditor.putBoolean("aMessages", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sApps", false)).booleanValue()) {
            try {
                this.helper.storeApp();
            } catch (Exception e3) {
                for (i = 1; i < 4; i++) {
                    empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Apps");
                }
                prefEditor.putBoolean("aApps", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sPhotos", false)).booleanValue()) {
            try {
                this.helper.storePhoto();
            } catch (Exception e4) {
                prefEditor.putBoolean("aPhotos", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sBrowser", false)).booleanValue()) {
            try {
                this.helper.storeBrowser();
            } catch (Exception e5) {
                for (i = 1; i < 4; i++) {
                    empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Browser");
                }
                prefEditor.putBoolean("aBrowser", false);
                prefEditor.commit();
            }
        }
        prefEditor.putString("DataStored", "Yeap");
        prefEditor.putInt("QuoteIndex", -1);
        List<String> AB = calculateAB(quotes, quoteIndex);
        Integer day = this.helper.getStudyDay();
        String[] types = this.helper.getScenarioType().split("");
        String ab = TextUtils.join(";", AB);
        String prevAB = this.helper.getPrevConstraints();
        if (!prevAB.equals("")) {
            ab = prevAB + ";" + ab;
        }
        this.helper.setPrevConstraints(ab);
        new ServerJobWeights(this).execute(new String[]{this.helper.getUserID(), types[day.intValue()], ab});
        quotes.clear();
        quoteIndex = Integer.valueOf(-1);
        prefEditor.commit();
        startActivity(new Intent(getApplicationContext(), Review.class));
    }

    private List<String> calculateAB(List<Quote> quotes, Integer quoteIndex) {
        List<String> list = new ArrayList();
        List<Quote> otherQuotes = new ArrayList();
        Quote accepted = (Quote) quotes.get(quoteIndex.intValue());
        for (int i = 0; i < quotes.size(); i++) {
            if (!quoteIndex.equals(Integer.valueOf(i))) {
                otherQuotes.add(quotes.get(i));
            }
        }
        if (otherQuotes.size() == 0) {
            list.add(getABString(convertPrefsToA(accepted.getPrefs()), accepted.getPending()));
        } else {
            for (Quote quote : otherQuotes) {
                list.add(getABString(calculateA(convertPrefsToA(accepted.getPrefs()), convertPrefsToA(quote.getPrefs())), accepted.getPending() - quote.getPending()));
            }
        }
        return list;
    }

    private Integer[] calculateA(Integer[] accepted, Integer[] quote) {
        Integer[] result = new Integer[5];
        for (int i = 0; i < 5; i++) {
            result[i] = Integer.valueOf(accepted[i].intValue() - quote[i].intValue());
        }
        return result;
    }

    private String getABString(Integer[] a, int pending) {
        String ab = "";
        for (Integer i : a) {
            ab = ab + Integer.toString(i.intValue()) + ",";
        }
        return ab + Integer.toString(pending);
    }

    private Integer[] convertPrefsToA(Boolean[] prefs) {
        Integer[] A = new Integer[5];
        for (int i = 0; i < 5; i++) {
            if (prefs[i].booleanValue()) {
                A[i] = Integer.valueOf(1);
            } else {
                A[i] = Integer.valueOf(0);
            }
        }
        return A;
    }

    public void quotePointsNew(View view) {
        Integer day = this.helper.getStudyDay();
        String[] types = this.helper.getScenarioType().split("");
        int max = 100;
        if (types[day.intValue()].equals("H")) {
            max = 100;
        }
        if (types[day.intValue()].equals("M")) {
            max = 50;
        }
        if (types[day.intValue()].equals("L")) {
            max = 25;
        }
        PrivacyType privacyType = PrivacyType.UNCONCERNED;
        if (this.helper.getUserType().equals("Pragmatists")) {
            privacyType = PrivacyType.PRAGMATIST;
        }
        if (this.helper.getUserType().equals("Fundamentalists")) {
            privacyType = PrivacyType.FUNDAMENTALIST;
        }
        List<Box> allBoxes = new ArrayList();
        List<PermissionsSetting> oldSettings = new ArrayList();
        PermissionsSetting permissionsSetting = new PermissionsSetting("FFFFF");
        PermissionsSettingBox permissionsSettingBox = new PermissionsSettingBox(max, permissionsSetting, privacyType, this);
        oldSettings.add(permissionsSetting);
        for (Quote quote : quotes) {
            oldSettings.add(new PermissionsSetting(convertToScenario(quote.getPrefs())));
        }
        for (PermissionsSetting p : PermissionsSetting.getAllPossiblePermissionSettings()) {
            if (!oldSettings.contains(p)) {
                allBoxes.add(new PermissionsSettingBox(max, p, privacyType, this));
            }
        }
        String[] selectedPrefs = this.helper.getSpecScenario("day" + Integer.toString(day.intValue()) + ".txt", ((PermissionsSettingBox) new PandoraProblem(allBoxes, max).applyPandorasRule()).toTFString());
        String scenario = selectedPrefs[0];
        String points = selectedPrefs[1];
        Boolean[] prefs = this.helper.convertToPrefs(scenario);
        Integer total = Integer.valueOf(this.helper.getTotalPoints().intValue() - 10);
        this.helper.setTotalPoints(total);
        this.etTotal = (EditText) findViewById(C0150R.id.etTotalFlex);
        this.etTotal.setText(Integer.toString(total.intValue()));
        setRadioButtons(prefs);
        quotes.add(new Quote(prefs, Integer.parseInt(points)));
        quoteIndex = Integer.valueOf(quoteIndex.intValue() + 1);
        if (quotes.size() > 1) {
            ((Button) findViewById(C0150R.id.btnPrev)).setEnabled(true);
        }
        ((EditText) findViewById(C0150R.id.etPoints)).setText(points);
        this.helper.Logger(this.helper.getTreatment(), this.helper.getStudyDay().toString(), screen, "QuoteAuto", this.helper.getTotalPoints().toString(), points.toString(), prefs[0].toString(), prefs[1].toString(), prefs[2].toString(), prefs[3].toString(), prefs[4].toString());
    }

    public void quotePoints(View view) {
        Button btnQuote = (Button) findViewById(C0150R.id.btnQuote);
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(true);
        btnQuote.setEnabled(false);
        Integer total = Integer.valueOf(this.helper.getTotalPoints().intValue() - 10);
        this.helper.setTotalPoints(total);
        ((EditText) findViewById(C0150R.id.etTotalFlex)).setText(Integer.toString(total.intValue()));
        calculatePoints();
    }

    public void calculatePoints() {
        String scenario = "";
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        if (((RadioButton) findViewById(C0150R.id.rbContactsShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) findViewById(C0150R.id.rbMessagesShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) findViewById(C0150R.id.rbAppsShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) findViewById(C0150R.id.rbPhotosShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) findViewById(C0150R.id.rbBrowserShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        Integer dPoints = checkScenarios(scenario, "day" + Integer.toString(Integer.valueOf(sharedPrefs.getInt("Day", 0)).intValue()) + ".txt");
        ((EditText) findViewById(C0150R.id.etPoints)).setText(dPoints.toString());
        Boolean[] prefs = new Boolean[5];
        int cnt = 0;
        for (char valueOf : scenario.toCharArray()) {
            if (Character.valueOf(valueOf).toString().equals("T")) {
                prefs[cnt] = Boolean.valueOf(true);
            } else {
                prefs[cnt] = Boolean.valueOf(false);
            }
            cnt++;
        }
        quotes.add(new Quote(prefs, dPoints.intValue()));
        quoteIndex = Integer.valueOf(quoteIndex.intValue() + 1);
        if (quotes.size() > 1) {
            ((Button) findViewById(C0150R.id.btnPrev)).setEnabled(true);
        }
        this.helper.Logger(this.helper.getTreatment(), this.helper.getStudyDay().toString(), screen, "Quote", this.helper.getTotalPoints().toString(), dPoints.toString(), prefs[0].toString(), prefs[1].toString(), prefs[2].toString(), prefs[3].toString(), prefs[4].toString());
    }

    private Integer checkScenarios(String scenario, String day) {
        Map scenarios = new HashMap();
        try {
            InputStream inputStream = openFileInput(day);
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    String receiveString = bufferedReader.readLine();
                    if (receiveString == null) {
                        break;
                    }
                    String[] words = receiveString.split("\\s+");
                    if (!receiveString.equals("")) {
                        scenarios.put(words[0], words[1]);
                    }
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
        }
        return Integer.valueOf(Integer.parseInt(scenarios.get(scenario).toString()));
    }

    public void validateRadioButtons(View view) {
        Boolean res = Boolean.valueOf(false);
        for (int i = 1; i < 6; i++) {
            if (((RadioGroup) findViewById(getResources().getIdentifier("rbGroup" + i, "id", BuildConfig.APPLICATION_ID))).getCheckedRadioButtonId() < 0) {
                res = Boolean.valueOf(false);
            } else {
                res = Boolean.valueOf(true);
            }
        }
        if (res.booleanValue()) {
            ((Button) findViewById(C0150R.id.btnQuote)).setEnabled(true);
            ((EditText) findViewById(C0150R.id.etPoints)).setText("");
        }
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(false);
        ((Button) findViewById(C0150R.id.btnPrev)).setEnabled(true);
        if (this.helper.getScenarioFromRadioButtons().equals("FFFFF")) {
            ((EditText) findViewById(C0150R.id.etPoints)).setText("0");
            ((Button) findViewById(C0150R.id.btnSave)).setEnabled(true);
        }
    }

    public void prevQuote(View view) {
        if (quoteIndex.equals(Integer.valueOf(0))) {
            quoteIndex = quoteIndex;
        } else {
            quoteIndex = Integer.valueOf(quoteIndex.intValue() - 1);
        }
        Quote prevQuote = (Quote) quotes.get(quoteIndex.intValue());
        ((Button) findViewById(C0150R.id.btnNext)).setEnabled(true);
        setQuote(prevQuote, "Prev");
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(true);
    }

    public void nextQuote(View view) {
        quoteIndex = Integer.valueOf(quoteIndex.intValue() + 1);
        Quote nextQuote = (Quote) quotes.get(quoteIndex.intValue());
        ((Button) findViewById(C0150R.id.btnPrev)).setEnabled(true);
        setQuote(nextQuote, "Next");
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(true);
    }

    private void setQuote(Quote quote, String action) {
        Boolean[] pref = quote.getPrefs();
        Integer data = Integer.valueOf(quote.getPending());
        setRadioButtons(pref);
        ((EditText) findViewById(C0150R.id.etPoints)).setText(Integer.toString(data.intValue()));
        String treatment = this.helper.getTreatment();
        this.helper.Logger(treatment, this.helper.getStudyDay().toString(), screen, action, this.helper.getTotalPoints().toString(), data.toString(), pref[0].toString(), pref[1].toString(), pref[2].toString(), pref[3].toString(), pref[4].toString());
    }

    public String convertToScenario(Boolean[] prefs) {
        String scenario = "";
        for (Boolean pre : prefs) {
            if (pre.booleanValue()) {
                scenario = scenario + "T";
            } else {
                scenario = scenario + "F";
            }
        }
        return scenario;
    }

    public Boolean[] convertToPrefs(String scenario) {
        Boolean[] prefs = new Boolean[5];
        for (int i = 0; i < 5; i++) {
            if (Character.toString(scenario.charAt(i)).equals("T")) {
                prefs[i] = Boolean.valueOf(true);
            } else {
                prefs[i] = Boolean.valueOf(false);
            }
        }
        return prefs;
    }

    public void acceptDefault(View view) {
        int i;
        String scenario = this.helper.getScenarioFromRadioButtons();
        Context context = getApplicationContext();
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        if (scenario.equals("FFFFF")) {
            prefEditor.putString("ReviewStatus", "NoShare");
        } else {
            prefEditor.putString("ReviewStatus", "DataShare");
        }
        this.helper.setPrefsWithScenario(scenario);
        EditText pointsOffer = (EditText) findViewById(C0150R.id.etPoints);
        prefEditor.putInt("PendingPoints", Integer.parseInt(pointsOffer.getText().toString()));
        prefEditor.putInt("OfferPoints", Integer.parseInt(pointsOffer.getText().toString()));
        prefEditor.commit();
        Boolean[] prefs = this.helper.convertToPrefs(scenario);
        this.helper.Logger(this.helper.getTreatment(), this.helper.getStudyDay().toString(), screen, "Accept", this.helper.getTotalPoints().toString(), pointsOffer.getText().toString(), prefs[0].toString(), prefs[1].toString(), prefs[2].toString(), prefs[3].toString(), prefs[4].toString());
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(false);
        ((Button) findViewById(C0150R.id.btnDecline)).setEnabled(false);
        pointsOffer.setEnabled(false);
        if (Boolean.valueOf(sharedPrefs.getBoolean("sContacts", false)).booleanValue()) {
            try {
                this.helper.storeContact();
            } catch (Exception e) {
                for (i = 1; i < 4; i++) {
                    List<String> empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Contacts");
                }
                prefEditor.putBoolean("aContacts", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sMessages", false)).booleanValue()) {
            try {
                this.helper.storeMessages();
            } catch (Exception e2) {
                for (i = 1; i < 4; i++) {
                    empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Messages");
                }
                prefEditor.putBoolean("aMessages", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sApps", false)).booleanValue()) {
            try {
                this.helper.storeApp();
            } catch (Exception e3) {
                for (i = 1; i < 4; i++) {
                    empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Apps");
                }
                prefEditor.putBoolean("aApps", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sPhotos", false)).booleanValue()) {
            try {
                this.helper.storePhoto();
            } catch (Exception e4) {
                prefEditor.putBoolean("aPhotos", false);
                prefEditor.commit();
            }
        }
        if (Boolean.valueOf(sharedPrefs.getBoolean("sBrowser", false)).booleanValue()) {
            try {
                this.helper.storeBrowser();
            } catch (Exception e5) {
                for (i = 1; i < 4; i++) {
                    empty = new ArrayList();
                    empty.add("No data");
                    empty.add("No data");
                    this.helper.writeToFile(empty, i, "Browser");
                }
                prefEditor.putBoolean("aBrowser", false);
                prefEditor.commit();
            }
        }
        prefEditor.putString("DataStored", "Yeap");
        prefEditor.commit();
        startActivity(new Intent(getApplicationContext(), Review.class));
    }

    public void declineDefault(View view) {
        String scenario = "FFFFF";
        this.helper.setPrefsWithScenario(scenario);
        EditText pointsOffer = (EditText) findViewById(C0150R.id.etPoints);
        Editor prefEditor = getSharedPreferences("Settings", 0).edit();
        prefEditor.putInt("PendingPoints", 0);
        prefEditor.putInt("OfferPoints", Integer.parseInt(pointsOffer.getText().toString()));
        prefEditor.commit();
        setRadioButtonsForStrict(convertToPrefs(scenario));
        Button btnDec = (Button) findViewById(C0150R.id.btnDecline);
        ((Button) findViewById(C0150R.id.btnSave)).setEnabled(false);
        btnDec.setEnabled(false);
        Boolean[] prefs = this.helper.convertToPrefs(scenario);
        this.helper.Logger(this.helper.getTreatment(), this.helper.getStudyDay().toString(), screen, "Decline", this.helper.getTotalPoints().toString(), pointsOffer.getText().toString(), prefs[0].toString(), prefs[1].toString(), prefs[2].toString(), prefs[3].toString(), prefs[4].toString());
        pointsOffer.setEnabled(false);
        startActivity(new Intent(getApplicationContext(), Review.class));
    }

    public static Context getContextOfApplication() {
        return contextOfApp;
    }
}
