package com.meaningful.ata1g11.meaningfulconsent;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.Browser;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import pandora.Box;
import pandora.PandoraProblem;
import permissions.PermissionsSetting;
import permissions.PermissionsSettingBox;
import permissions.PrivacyType;

public class Helper {
    Activity activity;
    Context context;
    Editor prefEditor;
    String setting = "Settings";
    SharedPreferences sharedPrefs;

    public Helper(Context base) {
        this.context = base.getApplicationContext();
        this.activity = (Activity) base;
        this.sharedPrefs = this.context.getSharedPreferences(this.setting, 0);
        this.prefEditor = this.sharedPrefs.edit();
    }

    public Helper(Context base, Boolean flag) {
        if (flag.booleanValue()) {
            this.context = base.getApplicationContext();
            this.sharedPrefs = this.context.getSharedPreferences(this.setting, 0);
            this.prefEditor = this.sharedPrefs.edit();
        }
    }

    public boolean isNewUser() {
        if (this.sharedPrefs.getString("UserID", null) != null) {
            return false;
        }
        return true;
    }

    public static synchronized String generateNewUserID() {
        String uid;
        synchronized (Helper.class) {
            uid = UUID.randomUUID().toString();
        }
        return uid;
    }

    public String getUserID() {
        return this.sharedPrefs.getString("UserID", "0");
    }

    public void setUserID(String userID) {
        this.prefEditor.putString("UserID", userID);
        this.prefEditor.commit();
    }

    public Integer getAgentQuote() {
        return Integer.valueOf(this.sharedPrefs.getInt("AgentQuote", 0));
    }

    public void setAgentQuote(Integer numberOfQuotes) {
        this.prefEditor.putInt("AgentQuote", numberOfQuotes.intValue());
        this.prefEditor.commit();
    }

    public Integer getTotalShared() {
        return Integer.valueOf(this.sharedPrefs.getInt("TotalShared", 0));
    }

    public void setTotalShared(Integer numberOfShares) {
        this.prefEditor.putInt("TotalShared", numberOfShares.intValue());
        this.prefEditor.commit();
    }

    public Float getTypeRatio() {
        return Float.valueOf(this.sharedPrefs.getFloat("TypeRatio", 0.0f));
    }

    public void setTypeRatio(Float ratio) {
        this.prefEditor.putFloat("TypeRatio", ratio.floatValue());
        this.prefEditor.commit();
    }

    public String getUserNumber() {
        return this.sharedPrefs.getString("UserNum", "0");
    }

    public void setUserNumber(String userNum) {
        this.prefEditor.putString("UserNum", userNum);
        this.prefEditor.commit();
    }

    public Integer getStudyDay() {
        return Integer.valueOf(this.sharedPrefs.getInt("Day", 0));
    }

    public void setStudyDay(Integer day) {
        this.prefEditor.putInt("Day", day.intValue());
        this.prefEditor.commit();
    }

    public Integer getNumData() {
        return Integer.valueOf(this.sharedPrefs.getInt("NumData", 0));
    }

    public void setNumData(Integer numData) {
        this.prefEditor.putInt("NumData", numData.intValue());
        this.prefEditor.commit();
    }

    public Integer getTotalPoints() {
        return Integer.valueOf(this.sharedPrefs.getInt("TotalPoints", 0));
    }

    public void setTotalPoints(Integer total) {
        this.prefEditor.putInt("TotalPoints", total.intValue());
        this.prefEditor.commit();
    }

    public String getTreatment() {
        return this.sharedPrefs.getString("Treatment", "Strict");
    }

    public void setTreatment(String treatment) {
        this.prefEditor.putString("Treatment", treatment);
        this.prefEditor.commit();
    }

    public String getUserType() {
        return this.sharedPrefs.getString("UserType", "Error");
    }

    public void setUserType(String userType) {
        this.prefEditor.putString("UserType", userType);
        this.prefEditor.commit();
    }

    public String getScenarioType() {
        return this.sharedPrefs.getString("ScenarioType", "Error");
    }

    public void setScenarioType(String scenarioType) {
        this.prefEditor.putString("ScenarioType", scenarioType);
        this.prefEditor.commit();
    }

    public Boolean getSetPrefs() {
        return Boolean.valueOf(this.sharedPrefs.getBoolean("SetPrefs", false));
    }

    public void setSetPrefs(Boolean setPrefs) {
        this.prefEditor.putBoolean("SetPrefs", setPrefs.booleanValue());
        this.prefEditor.commit();
    }

    public String getReviewStatus() {
        return this.sharedPrefs.getString("ReviewStatus", "Alper");
    }

    public void setReviewStatus(String reviewStatus) {
        this.prefEditor.putString("ReviewStatus", reviewStatus);
        this.prefEditor.commit();
    }

    public String getDataStored() {
        return this.sharedPrefs.getString("DataStored", "Turan");
    }

    public void setDataStored(String dataStored) {
        this.prefEditor.putString("DataStored", dataStored);
        this.prefEditor.commit();
    }

    public String[] getInstalledApps() {
        String user_id = getUserID();
        List<PackageInfo> PackList = this.context.getPackageManager().getInstalledPackages(0);
        List<String> appList = new ArrayList();
        for (int i = 0; i < PackList.size(); i++) {
            PackageInfo PackInfo = (PackageInfo) PackList.get(i);
            if ((PackInfo.applicationInfo.flags & 1) == 0) {
                appList.add(PackInfo.applicationInfo.loadLabel(this.context.getPackageManager()).toString());
            }
        }
        appList.add(user_id);
        return (String[]) appList.toArray(new String[appList.size()]);
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

    public String convertToScenario(Boolean[] prefs) {
        String sce = "";
        for (int i = 0; i < 5; i++) {
            if (prefs[i].booleanValue()) {
                sce = sce + "T";
            } else {
                sce = sce + "F";
            }
        }
        return sce;
    }

    public void setPrefsWithScenario(String scenario) {
        int count = 0;
        Boolean[] prefs = convertToPrefs(scenario);
        if (prefs[0].booleanValue()) {
            this.prefEditor.putBoolean("sContacts", true);
            count = 0 + 1;
        } else {
            this.prefEditor.putBoolean("sContacts", false);
        }
        if (prefs[1].booleanValue()) {
            this.prefEditor.putBoolean("sMessages", true);
            count++;
        } else {
            this.prefEditor.putBoolean("sMessages", false);
        }
        if (prefs[2].booleanValue()) {
            this.prefEditor.putBoolean("sApps", true);
            count++;
        } else {
            this.prefEditor.putBoolean("sApps", false);
        }
        if (prefs[3].booleanValue()) {
            this.prefEditor.putBoolean("sPhotos", true);
            count++;
        } else {
            this.prefEditor.putBoolean("sPhotos", false);
        }
        if (prefs[4].booleanValue()) {
            this.prefEditor.putBoolean("sBrowser", true);
            count++;
        } else {
            this.prefEditor.putBoolean("sBrowser", false);
        }
        if (scenario.equals("FFFFF")) {
            this.prefEditor.putString("ReviewStatus", "NoShare");
        }
        Integer totalShared = Integer.valueOf(getTotalShared().intValue() + count);
        setTotalShared(totalShared);
        setTypeRatio(Float.valueOf(((float) totalShared.intValue()) / ((float) (getStudyDay().intValue() * 5))));
        this.prefEditor.putBoolean("SetPrefs", true);
        this.prefEditor.putString("DataStored", "Yeap");
        this.prefEditor.commit();
    }

    public String getScenarioFromRadioButtons() {
        String scenario = "";
        if (((RadioButton) this.activity.findViewById(C0150R.id.rbContactsShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) this.activity.findViewById(C0150R.id.rbMessagesShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) this.activity.findViewById(C0150R.id.rbAppsShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) this.activity.findViewById(C0150R.id.rbPhotosShare)).isChecked()) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (((RadioButton) this.activity.findViewById(C0150R.id.rbBrowserShare)).isChecked()) {
            return scenario + "T";
        }
        return scenario + "F";
    }

    public String[] agent(String userType, String scenarioType) {
        Integer day = getStudyDay();
        String[] types = scenarioType.split("");
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
        if (userType.equals("Pragmatists")) {
            privacyType = PrivacyType.PRAGMATIST;
        }
        if (userType.equals("Fundamentalists")) {
            privacyType = PrivacyType.FUNDAMENTALIST;
        }
        PermissionsSetting defaultSetting = new PermissionsSetting(getRandomScenario("day" + Integer.toString(day.intValue()) + ".txt")[0]);
        List<Box> allBoxes = new ArrayList();
        PermissionsSettingBox defaultBox = new PermissionsSettingBox(max, defaultSetting, privacyType, this.context);
        defaultBox.open(max);
        setAgentQuote(Integer.valueOf(getAgentQuote().intValue() - 1));
        allBoxes.add(defaultBox);
        PermissionsSetting allOff = new PermissionsSetting("FFFFF");
        PermissionsSettingBox allOffBox = new PermissionsSettingBox(max, allOff, privacyType, this.context);
        allOffBox.open(max);
        setAgentQuote(Integer.valueOf(getAgentQuote().intValue() - 1));
        allBoxes.add(allOffBox);
        for (PermissionsSetting p : PermissionsSetting.getAllPossiblePermissionSettings()) {
            if (!(p.equals(defaultSetting) || p.equals(allOff))) {
                allBoxes.add(new PermissionsSettingBox(max, p, privacyType, this.context));
            }
        }
        return getSpecScenario("day" + Integer.toString(day.intValue()) + ".txt", ((PermissionsSettingBox) new PandoraProblem(allBoxes, max).applyPandorasRule()).toTFString());
    }

    public String[] getInitialScenario() {
        return getRandomScenario("day" + Integer.toString(getStudyDay().intValue()) + ".txt");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getSpecScenario(java.lang.String r9, java.lang.String r10) {
        /*
        r8 = this;
        r6 = 2;
        r4 = new java.lang.String[r6];
        r6 = r8.context;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r1 = r6.openFileInput(r9);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r1 == 0) goto L_0x0042;
    L_0x000b:
        r2 = new java.io.InputStreamReader;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r0 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r0.<init>(r2);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r3 = 0;
    L_0x0016:
        r3 = r0.readLine();	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r3 == 0) goto L_0x003f;
    L_0x001c:
        r6 = "";
        r6 = r3.equals(r6);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r6 != 0) goto L_0x0016;
    L_0x0024:
        r6 = "\\s+";
        r5 = r3.split(r6);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r6 = 0;
        r6 = r5[r6];	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r6 = r6.equals(r10);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        if (r6 == 0) goto L_0x0016;
    L_0x0033:
        r6 = 0;
        r7 = 0;
        r7 = r5[r7];	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r4[r6] = r7;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r6 = 1;
        r7 = 1;
        r7 = r5[r7];	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
        r4[r6] = r7;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
    L_0x003f:
        r1.close();	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0043 }
    L_0x0042:
        return r4;
    L_0x0043:
        r6 = move-exception;
        goto L_0x0042;
    L_0x0045:
        r6 = move-exception;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meaningful.ata1g11.meaningfulconsent.Helper.getSpecScenario(java.lang.String, java.lang.String):java.lang.String[]");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getGreedyScenario(java.lang.String r9) {
        /*
        r8 = this;
        r6 = 2;
        r4 = new java.lang.String[r6];
        r6 = r8.context;	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r1 = r6.openFileInput(r9);	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        if (r1 == 0) goto L_0x0044;
    L_0x000b:
        r2 = new java.io.InputStreamReader;	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r0 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r0.<init>(r2);	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r3 = 0;
    L_0x0016:
        r3 = r0.readLine();	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        if (r3 == 0) goto L_0x0041;
    L_0x001c:
        r6 = "";
        r6 = r3.equals(r6);	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        if (r6 != 0) goto L_0x0016;
    L_0x0024:
        r6 = "\\s+";
        r5 = r3.split(r6);	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r6 = 0;
        r6 = r5[r6];	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r7 = "TTTTT";
        r6 = r6.equals(r7);	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        if (r6 == 0) goto L_0x0016;
    L_0x0035:
        r6 = 0;
        r7 = 0;
        r7 = r5[r7];	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r4[r6] = r7;	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r6 = 1;
        r7 = 1;
        r7 = r5[r7];	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
        r4[r6] = r7;	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
    L_0x0041:
        r1.close();	 Catch:{ FileNotFoundException -> 0x0047, IOException -> 0x0045 }
    L_0x0044:
        return r4;
    L_0x0045:
        r6 = move-exception;
        goto L_0x0044;
    L_0x0047:
        r6 = move-exception;
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meaningful.ata1g11.meaningfulconsent.Helper.getGreedyScenario(java.lang.String):java.lang.String[]");
    }

    public String[] getRandomScenario(String day) {
        String[] scenario = new String[2];
        try {
            InputStream inputStream = this.context.openFileInput(day);
            if (inputStream != null) {
                String receiveString;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                do {
                    receiveString = bufferedReader.readLine();
                    if (receiveString == null) {
                        break;
                    }
                } while (receiveString.equals(""));
                String[] words = receiveString.split("\\s+");
                scenario[0] = words[0];
                scenario[1] = words[1];
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
        }
        return scenario;
    }

    public void deleteAllIDFiles() {
        try {
            for (String filename : new String[]{"Contactsids", "Messagesids", "Appsids", "Photosids", "Browserids"}) {
                File file = this.activity.getBaseContext().getFileStreamPath(filename + ".txt");
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            Log.e("creating dataidfiles", "Ex: " + e.toString());
        }
    }

    public void storeContact() {
        String[] ids = getDataIDs("Contacts");
        Map<Integer, List<String>> contacts = new HashMap();
        ContentResolver cr = this.context.getContentResolver();
        int i;
        List<String> empty;
        try {
            Uri uri = Contacts.CONTENT_URI;
            String[] projection = new String[]{"_id", "display_name", "has_phone_number"};
            String[] selectionArgs = new String[]{TextUtils.join("', '", ids)};
            selectionArgs[0] = "'" + selectionArgs[0];
            selectionArgs[selectionArgs.length - 1] = selectionArgs[selectionArgs.length - 1] + "'";
            String que = Arrays.toString(selectionArgs).substring(1);
            Cursor cur = cr.query(uri, projection, "_id NOT IN (" + que.substring(0, que.length() - 1) + ")" + " AND " + "has_phone_number" + ">0 ", null, "_id DESC LIMIT 10");
            if (cur.getCount() > 3) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex("_id"));
                    String name = cur.getString(cur.getColumnIndex("display_name"));
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex("has_phone_number"))) > 0) {
                        ContentResolver contentResolver = cr;
                        Cursor pCur = contentResolver.query(Phone.CONTENT_URI, null, "contact_id = ?", new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex("data1"));
                            List<String> values = new ArrayList();
                            values.add(name);
                            values.add(phoneNo);
                            contacts.put(Integer.valueOf(Integer.parseInt(id)), values);
                        }
                        pCur.close();
                    }
                }
                Object[] idValues = contacts.keySet().toArray();
                Collections.shuffle(Arrays.asList(idValues));
                for (i = 1; i < 4; i++) {
                    Integer aydi = idValues[i - 1];
                    writeToFile((List) contacts.get(aydi), i, "Contacts");
                    writeToIDFile(aydi, "Contacts");
                }
                this.prefEditor.putBoolean("aContacts", true);
                this.prefEditor.commit();
                return;
            }
            for (i = 1; i < 4; i++) {
                empty = new ArrayList();
                empty.add("No data");
                empty.add("No data");
                writeToFile(empty, i, "Contacts");
            }
            this.prefEditor.putBoolean("aContacts", false);
            this.prefEditor.commit();
        } catch (Exception ex) {
            String message = ex.getMessage();
            for (i = 1; i < 4; i++) {
                empty = new ArrayList();
                empty.add("No data");
                empty.add("No data");
                writeToFile(empty, i, "Contacts");
            }
            this.prefEditor.putBoolean("aContacts", false);
            this.prefEditor.commit();
        }
    }

    private void writeToIDFile(Integer rid, String filename) {
        try {
            File file = this.activity.getBaseContext().getFileStreamPath(filename + "ids.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.context.openFileOutput(filename + "ids.txt", 32768));
            outputStreamWriter.write(rid + "\n");
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void writeToIDFileStr(String name, String filename) {
        try {
            File file = this.activity.getBaseContext().getFileStreamPath(filename + "ids.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.context.openFileOutput(filename + "ids.txt", 32768));
            outputStreamWriter.write(name + "\n");
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void writeToFile(List<String> data, int sira, String filename) {
        try {
            Integer day = getStudyDay();
            File file = this.activity.getBaseContext().getFileStreamPath((filename + day + sira) + ".txt");
            if (file.exists()) {
                file.delete();
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.context.openFileOutput(filename + day + sira + ".txt", 0));
            outputStreamWriter.write(((String) data.get(0)) + "\n");
            outputStreamWriter.write(((String) data.get(1)) + "\n");
            outputStreamWriter.close();
        } catch (IOException e) {
        }
    }

    private String[] getDataIDs(String filename) {
        ArrayList<String> ret = new ArrayList();
        try {
            InputStream inputStream = this.context.openFileInput(filename + "ids.txt");
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
        } catch (IOException e2) {
            Log.e("login activity", "Can not read file: " + e2.toString());
        }
        String[] ids = (String[]) ret.toArray(new String[ret.size()]);
        ret.clear();
        return ids;
    }

    public void storeMessages() {
        int i;
        List<String> empty;
        String[] ids = getDataIDs("Messages");
        Map<Integer, List<String>> messages = new HashMap();
        Uri smsURI = Uri.parse("content://sms");
        ContentResolver cr = this.context.getContentResolver();
        try {
            String[] selectionArgs = new String[]{TextUtils.join("', '", ids)};
            selectionArgs[0] = "'" + selectionArgs[0];
            selectionArgs[selectionArgs.length - 1] = selectionArgs[selectionArgs.length - 1] + "'";
            String que = Arrays.toString(selectionArgs).substring(1);
            String where = "_id NOT IN (" + que.substring(0, que.length() - 1) + ")";
            Cursor cur = cr.query(smsURI, new String[]{"DISTINCT _id", "address", "date", "body"}, where, null, "_id DESC LIMIT 10");
            if (cur.getCount() > 3) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex("_id"));
                    String person = cur.getString(cur.getColumnIndex("address"));
                    String date = cur.getString(cur.getColumnIndex("date"));
                    String body = cur.getString(cur.getColumnIndex("body"));
                    List<String> values = new ArrayList();
                    values.add(person);
                    String lastVisit = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date(Long.parseLong(date)));
                    values.add("Date: " + lastVisit + " Body: " + body);
                    messages.put(Integer.valueOf(Integer.parseInt(id)), values);
                }
                Object[] idValues = messages.keySet().toArray();
                Collections.shuffle(Arrays.asList(idValues));
                for (i = 1; i < 4; i++) {
                    Integer aydi = idValues[i - 1];
                    writeToFile((List) messages.get(aydi), i, "Messages");
                    writeToIDFile(aydi, "Messages");
                }
                this.prefEditor.putBoolean("aMessages", true);
                this.prefEditor.commit();
                return;
            }
            for (i = 1; i < 4; i++) {
                empty = new ArrayList();
                empty.add("No data");
                empty.add("No data");
                writeToFile(empty, i, "Messages");
            }
            this.prefEditor.putBoolean("aMessages", false);
            this.prefEditor.commit();
        } catch (Exception ex) {
            Log.e("mine", "Error: " + ex.getMessage(), ex);
            for (i = 1; i < 4; i++) {
                empty = new ArrayList();
                empty.add("No data");
                empty.add("No data");
                writeToFile(empty, i, "Messages");
            }
            this.prefEditor.putBoolean("aMessages", false);
            this.prefEditor.commit();
        }
    }

    public void storePhoto() {
        String[] ids = getDataIDs("Photos");
        Map<Integer, List<String>> photos = new HashMap();
        Uri images = Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = this.context.getContentResolver();
        String[] selectionArgs = new String[]{TextUtils.join("', '", ids)};
        selectionArgs[0] = "'" + selectionArgs[0];
        selectionArgs[selectionArgs.length - 1] = selectionArgs[selectionArgs.length - 1] + "'";
        String que = Arrays.toString(selectionArgs).substring(1);
        String where = "_id NOT IN (" + que.substring(0, que.length() - 1) + ")";
        Cursor cur = cr.query(images, new String[]{"_id", "_data"}, where, null, "_id DESC LIMIT 10");
        if (cur.getCount() > 3) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex("_id"));
                String data = cur.getString(cur.getColumnIndex("_data"));
                List<String> values = new ArrayList();
                values.add(data);
                values.add("");
                photos.put(Integer.valueOf(Integer.parseInt(id)), values);
            }
            Object[] idValues = photos.keySet().toArray();
            Collections.shuffle(Arrays.asList(idValues));
            for (int i = 1; i < 4; i++) {
                Integer aydi = idValues[i - 1];
                writeToIDFile(aydi, "Photos");
                saveImage(ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(new File((String) ((List) photos.get(aydi)).get(0)).getAbsolutePath()), 250, 250), Integer.valueOf(i));
            }
            this.prefEditor.putBoolean("aPhotos", true);
            this.prefEditor.commit();
            return;
        }
        this.prefEditor.putBoolean("aPhotos", false);
        this.prefEditor.commit();
    }

    public void saveImage(Bitmap img, Integer sira) {
        FileOutputStream fileOutputStream;
        Exception e;
        File directory = new ContextWrapper(this.context.getApplicationContext()).getDir("imageDir", 0);
        try {
            FileOutputStream fos = new FileOutputStream(new File(directory, "pendingImage" + getStudyDay() + sira + ".jpg"));
            try {
                img.compress(CompressFormat.PNG, 100, fos);
                fos.close();
                this.prefEditor.putString("ImgPath", directory.getAbsolutePath());
                this.prefEditor.commit();
                fileOutputStream = fos;
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = fos;
                e.printStackTrace();
                this.prefEditor.putBoolean("aPhotos", false);
                this.prefEditor.commit();
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            this.prefEditor.putBoolean("aPhotos", false);
            this.prefEditor.commit();
        }
    }

    public void storeBrowser() {
        String[] ids = getDataIDs("Browser");
        Map<Integer, List<String>> histories = new HashMap();
        ContentResolver cr = this.context.getContentResolver();
        String[] selectionArgs = new String[]{TextUtils.join("', '", ids)};
        selectionArgs[0] = "'" + selectionArgs[0];
        selectionArgs[selectionArgs.length - 1] = selectionArgs[selectionArgs.length - 1] + "'";
        String que = Arrays.toString(selectionArgs).substring(1);
        String where = "bookmark=0 AND _id NOT IN (" + que.substring(0, que.length() - 1) + ")";
        Cursor cur = cr.query(getBrowserUri(), new String[]{"_id", "url", "date", "visits"}, where, null, "_id DESC LIMIT 10");
        int i;
        if (cur.getCount() > 3) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex("_id"));
                String url = cur.getString(cur.getColumnIndex("url"));
                String date = cur.getString(cur.getColumnIndex("date"));
                String visits = cur.getString(cur.getColumnIndex("visits"));
                List<String> values = new ArrayList();
                values.add(url);
                String lastVisit = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(new Date(Long.parseLong(date)));
                values.add("Last visited: " + lastVisit + " Number of visits: " + visits);
                histories.put(Integer.valueOf(Integer.parseInt(id)), values);
            }
            Object[] idValues = histories.keySet().toArray();
            Collections.shuffle(Arrays.asList(idValues));
            for (i = 1; i < 4; i++) {
                Integer aydi = idValues[i - 1];
                writeToFile((List) histories.get(aydi), i, "Browser");
                writeToIDFile(aydi, "Browser");
            }
            this.prefEditor.putBoolean("aBrowser", true);
            this.prefEditor.commit();
            return;
        }
        for (i = 1; i < 4; i++) {
            List<String> empty = new ArrayList();
            empty.add("No data");
            empty.add("No data");
            writeToFile(empty, i, "Browser");
        }
        this.prefEditor.putBoolean("aBrowser", false);
        this.prefEditor.commit();
    }

    public void storeApp() {
        String[] ids = getDataIDs("Apps");
        String[] apps = getInstalledApps();
        List<String> newList = new ArrayList();
        for (String s : apps) {
            if (!Arrays.asList(ids).contains(s)) {
                newList.add(s);
            }
        }
        int i;
        if (newList.size() > 3) {
            Collections.shuffle(Arrays.asList(new List[]{newList}));
            for (i = 1; i < 4; i++) {
                List<String> values = new ArrayList();
                values.add(newList.get(i));
                values.add("");
                writeToFile(values, i, "Apps");
                writeToIDFileStr((String) newList.get(i), "Apps");
            }
            this.prefEditor.putBoolean("aApps", true);
            this.prefEditor.commit();
            return;
        }
        for (i = 1; i < 4; i++) {
            List<String> empty = new ArrayList();
            empty.add("No data");
            empty.add("No data");
            writeToFile(empty, i, "Apps");
        }
        this.prefEditor.putBoolean("aApps", false);
        this.prefEditor.commit();
    }

    public void Logger(String treatment, String scenario, String screen, String action, String total, String points, String c, String m, String h, String p, String a) {
        long time = System.currentTimeMillis();
        try {
            Integer day = getStudyDay();
            String user_id = getUserID();
            File file = this.activity.getBaseContext().getFileStreamPath(user_id + ".txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.context.openFileOutput(user_id + "_" + day + ".txt", 32768));
            outputStreamWriter.write((time + " " + treatment + " " + scenario + " " + screen + " " + action + " " + total + " " + points + " " + c + " " + m + " " + h + " " + p + " " + a) + "\n");
            outputStreamWriter.close();
        } catch (IOException e) {
        }
    }

    public String[] fillInteractionLogs() {
        String user_id = "";
        List<String> list = new ArrayList();
        try {
            user_id = getUserID();
            InputStream inputStream = this.context.openFileInput(user_id + "_" + getStudyDay() + ".txt");
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str = "";
                while (true) {
                    str = bufferedReader.readLine();
                    if (str == null) {
                        break;
                    }
                    list.add(user_id + " " + str);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public Uri getBrowserUri() {
        try {
            ContentResolver cr = this.context.getContentResolver();
            Uri uriCustom = Uri.parse("content://com.android.chrome.browser/bookmarks");
            Cursor cur = cr.query(uriCustom, new String[]{"_id", "url", "date", "visits"}, null, null, " _id asc limit 10");
            Cursor curr = this.context.getContentResolver().query(Browser.BOOKMARKS_URI, new String[]{"_id", "url", "date", "visits"}, null, null, " _id asc limit 10");
            if (cur.getCount() > 9) {
                return uriCustom;
            }
            return Browser.BOOKMARKS_URI;
        } catch (Exception e) {
            return Browser.BOOKMARKS_URI;
        }
    }

    public void updateUserType() {
        Float ratio = Float.valueOf(((float) getTotalShared().intValue()) / ((float) (getStudyDay().intValue() * 5)));
        setTypeRatio(ratio);
        if (((double) ratio.floatValue()) <= 0.3333333333333333d) {
            setUserType("Fundamentalists");
        }
        if (((double) ratio.floatValue()) > 0.3333333333333333d && ((double) ratio.floatValue()) <= 0.6666666666666666d) {
            setUserType("Pragmatists");
        }
        if (((double) ratio.floatValue()) > 0.6666666666666666d) {
            setUserType("Unconcerned");
        }
    }

    public Integer getLowContacts() {
        return Integer.valueOf(this.sharedPrefs.getInt("LowContacts", 0));
    }

    public void setLowContacts(Integer contacts) {
        this.prefEditor.putInt("LowContacts", contacts.intValue());
        this.prefEditor.commit();
    }

    public Integer getLowMessages() {
        return Integer.valueOf(this.sharedPrefs.getInt("LowMessages", 0));
    }

    public void setLowMessages(Integer messages) {
        this.prefEditor.putInt("LowMessages", messages.intValue());
        this.prefEditor.commit();
    }

    public Integer getLowApps() {
        return Integer.valueOf(this.sharedPrefs.getInt("LowApps", 0));
    }

    public void setLowApps(Integer apps) {
        this.prefEditor.putInt("LowApps", apps.intValue());
        this.prefEditor.commit();
    }

    public Integer getLowPhotos() {
        return Integer.valueOf(this.sharedPrefs.getInt("LowPhotos", 0));
    }

    public void setLowPhotos(Integer photos) {
        this.prefEditor.putInt("LowPhotos", photos.intValue());
        this.prefEditor.commit();
    }

    public Integer getLowHistory() {
        return Integer.valueOf(this.sharedPrefs.getInt("LowHistory", 0));
    }

    public void setLowHistory(Integer history) {
        this.prefEditor.putInt("LowHistory", history.intValue());
        this.prefEditor.commit();
    }

    public Integer getMediumContacts() {
        return Integer.valueOf(this.sharedPrefs.getInt("MediumContacts", 0));
    }

    public void setMediumContacts(Integer contacts) {
        this.prefEditor.putInt("MediumContacts", contacts.intValue());
        this.prefEditor.commit();
    }

    public Integer getMediumMessages() {
        return Integer.valueOf(this.sharedPrefs.getInt("MediumMessages", 0));
    }

    public void setMediumMessages(Integer messages) {
        this.prefEditor.putInt("MediumMessages", messages.intValue());
        this.prefEditor.commit();
    }

    public Integer getMediumApps() {
        return Integer.valueOf(this.sharedPrefs.getInt("MediumApps", 0));
    }

    public void setMediumApps(Integer apps) {
        this.prefEditor.putInt("MediumApps", apps.intValue());
        this.prefEditor.commit();
    }

    public Integer getMediumPhotos() {
        return Integer.valueOf(this.sharedPrefs.getInt("MediumPhotos", 0));
    }

    public void setMediumPhotos(Integer photos) {
        this.prefEditor.putInt("MediumPhotos", photos.intValue());
        this.prefEditor.commit();
    }

    public Integer getMediumHistory() {
        return Integer.valueOf(this.sharedPrefs.getInt("MediumHistory", 0));
    }

    public void setMediumHistory(Integer history) {
        this.prefEditor.putInt("MediumHistory", history.intValue());
        this.prefEditor.commit();
    }

    public Integer getHighContacts() {
        return Integer.valueOf(this.sharedPrefs.getInt("HighContacts", 0));
    }

    public void setHighContacts(Integer contacts) {
        this.prefEditor.putInt("HighContacts", contacts.intValue());
        this.prefEditor.commit();
    }

    public Integer getHighMessages() {
        return Integer.valueOf(this.sharedPrefs.getInt("HighMessages", 0));
    }

    public void setHighMessages(Integer messages) {
        this.prefEditor.putInt("HighMessages", messages.intValue());
        this.prefEditor.commit();
    }

    public Integer getHighApps() {
        return Integer.valueOf(this.sharedPrefs.getInt("HighApps", 0));
    }

    public void setHighApps(Integer apps) {
        this.prefEditor.putInt("HighApps", apps.intValue());
        this.prefEditor.commit();
    }

    public Integer getHighPhotos() {
        return Integer.valueOf(this.sharedPrefs.getInt("HighPhotos", 0));
    }

    public void setHighPhotos(Integer photos) {
        this.prefEditor.putInt("HighPhotos", photos.intValue());
        this.prefEditor.commit();
    }

    public Integer getHighHistory() {
        return Integer.valueOf(this.sharedPrefs.getInt("HighHistory", 0));
    }

    public void setHighHistory(Integer history) {
        this.prefEditor.putInt("HighHistory", history.intValue());
        this.prefEditor.commit();
    }

    public void setAllWeights(int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14) {
        setLowContacts(Integer.valueOf(i));
        setLowMessages(Integer.valueOf(i1));
        setLowApps(Integer.valueOf(i2));
        setLowPhotos(Integer.valueOf(i3));
        setLowHistory(Integer.valueOf(i4));
        setMediumContacts(Integer.valueOf(i5));
        setMediumMessages(Integer.valueOf(i6));
        setMediumApps(Integer.valueOf(i7));
        setMediumPhotos(Integer.valueOf(i8));
        setMediumHistory(Integer.valueOf(i9));
        setHighContacts(Integer.valueOf(i10));
        setHighMessages(Integer.valueOf(i11));
        setHighApps(Integer.valueOf(i12));
        setHighPhotos(Integer.valueOf(i13));
        setHighHistory(Integer.valueOf(i14));
    }

    public void setLowWeights(int i, int i1, int i2, int i3, int i4) {
        setLowContacts(Integer.valueOf(i));
        setLowMessages(Integer.valueOf(i1));
        setLowApps(Integer.valueOf(i2));
        setLowPhotos(Integer.valueOf(i3));
        setLowHistory(Integer.valueOf(i4));
    }

    public void setMediumWeights(int i5, int i6, int i7, int i8, int i9) {
        setMediumContacts(Integer.valueOf(i5));
        setMediumMessages(Integer.valueOf(i6));
        setMediumApps(Integer.valueOf(i7));
        setMediumPhotos(Integer.valueOf(i8));
        setMediumHistory(Integer.valueOf(i9));
    }

    public void setHighWeights(int i10, int i11, int i12, int i13, int i14) {
        setHighContacts(Integer.valueOf(i10));
        setHighMessages(Integer.valueOf(i11));
        setHighApps(Integer.valueOf(i12));
        setHighPhotos(Integer.valueOf(i13));
        setHighHistory(Integer.valueOf(i14));
    }

    public void setAllWeightsStringList(String[] parameters) {
        setLowContacts(Integer.valueOf(Integer.parseInt(parameters[0])));
        setLowMessages(Integer.valueOf(Integer.parseInt(parameters[1])));
        setLowApps(Integer.valueOf(Integer.parseInt(parameters[2])));
        setLowPhotos(Integer.valueOf(Integer.parseInt(parameters[3])));
        setLowHistory(Integer.valueOf(Integer.parseInt(parameters[4])));
        setMediumContacts(Integer.valueOf(Integer.parseInt(parameters[5])));
        setMediumMessages(Integer.valueOf(Integer.parseInt(parameters[6])));
        setMediumApps(Integer.valueOf(Integer.parseInt(parameters[7])));
        setMediumPhotos(Integer.valueOf(Integer.parseInt(parameters[8])));
        setMediumHistory(Integer.valueOf(Integer.parseInt(parameters[9])));
        setHighContacts(Integer.valueOf(Integer.parseInt(parameters[10])));
        setHighMessages(Integer.valueOf(Integer.parseInt(parameters[11])));
        setHighApps(Integer.valueOf(Integer.parseInt(parameters[12])));
        setHighPhotos(Integer.valueOf(Integer.parseInt(parameters[13])));
        setHighHistory(Integer.valueOf(Integer.parseInt(parameters[14])));
    }

    public void setWeightsStringList(String[] parameters) {
        if (parameters[0].equals("L")) {
            setLowContacts(Integer.valueOf(Integer.parseInt(parameters[1])));
            setLowMessages(Integer.valueOf(Integer.parseInt(parameters[2])));
            setLowApps(Integer.valueOf(Integer.parseInt(parameters[3])));
            setLowPhotos(Integer.valueOf(Integer.parseInt(parameters[4])));
            setLowHistory(Integer.valueOf(Integer.parseInt(parameters[5])));
        }
        if (parameters[0].equals("M")) {
            setMediumContacts(Integer.valueOf(Integer.parseInt(parameters[1])));
            setMediumMessages(Integer.valueOf(Integer.parseInt(parameters[2])));
            setMediumApps(Integer.valueOf(Integer.parseInt(parameters[3])));
            setMediumPhotos(Integer.valueOf(Integer.parseInt(parameters[4])));
            setMediumHistory(Integer.valueOf(Integer.parseInt(parameters[5])));
        }
        if (parameters[0].equals("H")) {
            setHighContacts(Integer.valueOf(Integer.parseInt(parameters[1])));
            setHighMessages(Integer.valueOf(Integer.parseInt(parameters[2])));
            setHighApps(Integer.valueOf(Integer.parseInt(parameters[3])));
            setHighPhotos(Integer.valueOf(Integer.parseInt(parameters[4])));
            setHighHistory(Integer.valueOf(Integer.parseInt(parameters[5])));
        }
    }

    public String printAllWeights() {
        String weights = "";
        return getLowContacts() + "," + getLowMessages() + "," + getLowApps() + "," + getLowPhotos() + "," + getLowHistory() + "," + getMediumContacts() + "," + getMediumMessages() + "," + getMediumApps() + "," + getMediumPhotos() + "," + getMediumHistory() + "," + getHighContacts() + "," + getHighMessages() + "," + getHighApps() + "," + getHighPhotos() + "," + getHighHistory();
    }

    public void setPrevConstraints(String prevConstraints) {
        this.prefEditor.putString("PrevConstraints", prevConstraints);
        this.prefEditor.commit();
    }

    public String getPrevConstraints() {
        return this.sharedPrefs.getString("PrevConstraints", "");
    }
}
