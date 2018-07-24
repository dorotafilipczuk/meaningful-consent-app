package com.meaningful.ata1g11.meaningfulconsent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
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

public class Review extends AppCompatActivity {
    public static String screen = "Review";
    private ReviewListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;
    Helper helper;
    public Integer numberOfData = Integer.valueOf(0);
    private ProgressDialog pd = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0150R.layout.activity_review);
        this.pd = ProgressDialog.show(this, "Data is being shared...", "Please wait.", true, false);
        final Handler handler = new Handler();
        new Thread(new Runnable() {

            class C01511 implements Runnable {
                C01511() {
                }

                public void run() {
                    Review.this.pd.cancel();
                }
            }

            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                }
                handler.post(new C01511());
            }
        }).start();
        this.helper = new Helper(this);
        Boolean setPrefs = this.helper.getSetPrefs();
        String reviewStatus = this.helper.getReviewStatus();
        String dataStored = this.helper.getDataStored();
        if (setPrefs.booleanValue()) {
            if (reviewStatus.equals("NoShare")) {
                setContentView((int) C0150R.layout.activity_review);
                setPoints();
                ((TextView) findViewById(C0150R.id.txtPendingTitle)).setText("All data sharing was declined.");
                ((Button) findViewById(C0150R.id.btnNextScenario)).setEnabled(true);
            }
            if (!reviewStatus.equals("DataShare")) {
                return;
            }
            if (dataStored.equals("NotYet")) {
                setPoints();
                ((TextView) findViewById(C0150R.id.txtPendingTitle)).setText("Data sharing is in progress.");
                return;
            }
            setPoints();
            this.ExpandList = (ExpandableListView) findViewById(C0150R.id.exp_list);
            this.ExpListItems = SetStandardGroups();
            this.ExpAdapter = new ReviewListAdapter(this, this, this.ExpListItems);
            this.ExpandList.setAdapter(this.ExpAdapter);
            Button btn = (Button) findViewById(C0150R.id.btnNextScenario);
            if (this.ExpAdapter.getGroupCount() > 0) {
                this.ExpandList.expandGroup(0);
                this.numberOfData = this.helper.getNumData();
                if (this.numberOfData.equals(Integer.valueOf(0))) {
                    btn.setEnabled(true);
                }
            }
        }
    }

    public void onBackPressed() {
    }

    public ArrayList<Group> SetStandardGroups() {
        String[] preferences = new String[]{"sContacts", "sMessages", "sApps", "sPhotos", "sBrowser"};
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        Boolean state = Boolean.valueOf(sharedPrefs.getBoolean("SetPrefs", false));
        List<String> shared = new ArrayList();
        ArrayList<String> titles = new ArrayList();
        ArrayList<String> details = new ArrayList();
        ArrayList<Integer> images = new ArrayList();
        if (state.booleanValue()) {
            for (String s : preferences) {
                if (sharedPrefs.getBoolean(s, false)) {
                    shared.add(s.substring(1));
                    for (int i = 1; i < 4; i++) {
                        if (s.equals("sPhotos")) {
                            titles.add("Na");
                            details.add("Na");
                            images.add(Integer.valueOf(-1));
                        } else {
                            ArrayList<String> datas = new ArrayList();
                            datas = getDetails(s.substring(1), i);
                            titles.add(datas.get(0));
                            details.add(datas.get(1));
                            images.add(Integer.valueOf(getResources().getIdentifier(s.substring(1).toLowerCase(), "drawable", getPackageName())));
                        }
                    }
                }
            }
        }
        ArrayList<Group> list = new ArrayList();
        int index = 0;
        for (String info : shared) {
            Group gru = new Group();
            gru.setName(info);
            ArrayList<Child> ch_list = new ArrayList();
            for (int j = 0; j < 3; j++) {
                Child ch;
                if (info.equals("Photos")) {
                    ch = new Child();
                    ch.setName("Na");
                    ch.setDetails("Na");
                    ch_list.add(ch);
                } else {
                    ch = new Child();
                    ch.setName((String) titles.get(index));
                    ch.setDetails((String) details.get(index));
                    ch.setImage(((Integer) images.get(index)).intValue());
                    ch_list.add(ch);
                }
                gru.setItems(ch_list);
                index++;
            }
            list.add(gru);
        }
        return list;
    }

    public ArrayList<String> getDetails(String filename, int sira) {
        ArrayList<String> ret = new ArrayList();
        try {
            InputStream inputStream = openFileInput((filename + this.helper.getStudyDay() + sira) + ".txt");
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    receiveString = bufferedReader.readLine();
                    if (receiveString == null) {
                        break;
                    }
                    ret.add(receiveString);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            ret.add("!");
            ret.add("File had a problem.");
        } catch (IOException e2) {
            Log.e("login activity", "Can not read file: " + e2.toString());
            ret.add("!");
            ret.add("File had a problem.");
        }
        return ret;
    }

    private void setPoints() {
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        Integer total = Integer.valueOf(sharedPrefs.getInt("TotalPoints", 0));
        Integer pending = calculateNewPoints();
        EditText etTotal = (EditText) findViewById(C0150R.id.etTotal);
        etTotal.setEnabled(false);
        EditText etPending = (EditText) findViewById(C0150R.id.etPending);
        prefEditor.commit();
        etPending.setText(Integer.toString(pending.intValue()));
        etTotal.setText(Integer.toString(total.intValue()));
    }

    private Integer calculateNewPoints() {
        String scenario = "";
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        int count = 0;
        if (sharedPrefs.getBoolean("sContacts", false)) {
            if (sharedPrefs.getBoolean("aContacts", false)) {
                scenario = scenario + "T";
                count = 0 + 1;
            } else {
                scenario = scenario + "F";
            }
        } else {
            scenario = scenario + "F";
        }
        if (sharedPrefs.getBoolean("sMessages", false)) {
            if (sharedPrefs.getBoolean("aMessages", false)) {
                scenario = scenario + "T";
                count++;
            } else {
                scenario = scenario + "F";
            }
        } else {
            scenario = scenario + "F";
        }
        if (sharedPrefs.getBoolean("sApps", false)) {
            if (sharedPrefs.getBoolean("aApps", false)) {
                scenario = scenario + "T";
                count++;
            } else {
                scenario = scenario + "F";
            }
        } else {
            scenario = scenario + "F";
        }
        if (sharedPrefs.getBoolean("sPhotos", false)) {
            if (sharedPrefs.getBoolean("aPhotos", false)) {
                scenario = scenario + "T";
                count++;
            } else {
                scenario = scenario + "F";
            }
        } else {
            scenario = scenario + "F";
        }
        if (sharedPrefs.getBoolean("sBrowser", false)) {
            if (sharedPrefs.getBoolean("aBrowser", false)) {
                scenario = scenario + "T";
                count++;
            } else {
                scenario = scenario + "F";
            }
        } else {
            scenario = scenario + "F";
        }
        Integer dPoints = checkScenarios(scenario, "day" + Integer.toString(Integer.valueOf(sharedPrefs.getInt("Day", 0)).intValue()) + ".txt");
        prefEditor.putInt("NumData", count);
        prefEditor.commit();
        Boolean[] prefs = this.helper.convertToPrefs(scenario);
        this.helper.Logger(this.helper.getTreatment(), this.helper.getStudyDay().toString(), screen, "Access", this.helper.getTotalPoints().toString(), dPoints.toString(), prefs[0].toString(), prefs[1].toString(), prefs[2].toString(), prefs[3].toString(), prefs[4].toString());
        return dPoints;
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
            Log.e("reading scenarios", "File not found: " + e.toString());
        } catch (IOException e2) {
            Log.e("reading scenarios", "Can not read file: " + e2.toString());
        }
        return Integer.valueOf(Integer.parseInt(scenarios.get(scenario).toString()));
    }

    public void bringNextScenario(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        Integer total = Integer.valueOf(sharedPrefs.getInt("TotalPoints", 0));
        EditText txt = (EditText) findViewById(C0150R.id.etPending);
        this.helper.Logger(this.helper.getTreatment(), this.helper.getStudyDay().toString(), screen, "NextSce", this.helper.getTotalPoints().toString(), txt.getText().toString(), "-", "-", "-", Integer.toString(this.helper.getAgentQuote().intValue()), this.helper.getUserType());
        new ServerJobInteraction(this).execute(this.helper.fillInteractionLogs());
        this.helper.setAgentQuote(Integer.valueOf(0));
        this.helper.updateUserType();
        Editor editor = prefEditor;
        editor.putInt("TotalPoints", Integer.valueOf(total.intValue() + Integer.parseInt(txt.getText().toString())).intValue());
        prefEditor.putInt("PendingPoints", 0);
        prefEditor.putBoolean("SetPrefs", false);
        prefEditor.putString("DataStored", "NotYet");
        Integer dayNum = this.helper.getStudyDay();
        Integer flag = Integer.valueOf(0);
        if (dayNum.intValue() > 7) {
            flag = Integer.valueOf(1);
        } else {
            dayNum = Integer.valueOf(dayNum.intValue() + 1);
        }
        prefEditor.putInt("Day", dayNum.intValue());
        prefEditor.commit();
        if (flag.equals(Integer.valueOf(0))) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            finish();
            startActivity(intent);
            return;
        }
        Intent end = new Intent(getApplicationContext(), Final.class);
        finish();
        startActivity(end);
    }
}
