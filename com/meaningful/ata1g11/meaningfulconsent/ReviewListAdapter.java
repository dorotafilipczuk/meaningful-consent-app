package com.meaningful.ata1g11.meaningfulconsent;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewListAdapter extends BaseExpandableListAdapter {
    private static Integer imgNumber = Integer.valueOf(1);
    public static String screen = "Review";
    private Activity activity;
    private Context context;
    private ArrayList<Group> groups;
    Helper helper = new Helper(this.context);

    public ReviewListAdapter(Context context, Activity activity, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
        this.activity = activity;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ((Group) this.groups.get(groupPosition)).getItems().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) childPosition;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Group g;
        LayoutInflater infalInflater;
        TextView tv;
        TextView tv2;
        Child child = (Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            g = (Group) getGroup(groupPosition);
            Context context = this.context;
            Context context2 = this.context;
            infalInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            if (g.getName().equals("Photos")) {
                convertView = infalInflater.inflate(C0150R.layout.child_photo_item, null);
                ((ImageView) convertView.findViewById(C0150R.id.flag)).setImageBitmap(loadImage());
            } else {
                convertView = infalInflater.inflate(C0150R.layout.child_item, null);
                tv = (TextView) convertView.findViewById(C0150R.id.txtTitle);
                tv2 = (TextView) convertView.findViewById(C0150R.id.txtDetails);
                ImageView iv = (ImageView) convertView.findViewById(C0150R.id.flag);
                if (child.getName().toString().equals("No data")) {
                    tv.setText("There was not any new data.");
                    tv2.setText("");
                } else {
                    if (child.getName().length() > 70) {
                        tv.setText(child.getName().toString().substring(0, 70));
                    } else {
                        tv.setText(child.getName().toString());
                    }
                    tv2.setText(child.getDetails().toString());
                }
                iv.setImageResource(child.getImage());
            }
        }
        if (convertView == null) {
            return convertView;
        }
        g = (Group) getGroup(groupPosition);
        context = this.context;
        context2 = this.context;
        infalInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        if (g.getName().equals("Photos")) {
            convertView = infalInflater.inflate(C0150R.layout.child_photo_item, null);
            ((ImageView) convertView.findViewById(C0150R.id.flag)).setImageBitmap(loadImage());
            return convertView;
        }
        convertView = infalInflater.inflate(C0150R.layout.child_item, null);
        tv = (TextView) convertView.findViewById(C0150R.id.txtTitle);
        tv2 = (TextView) convertView.findViewById(C0150R.id.txtDetails);
        iv = (ImageView) convertView.findViewById(C0150R.id.flag);
        if (child.getName().toString().equals("No data")) {
            tv.setText("There was not any new data.");
            tv2.setText("");
        } else {
            if (child.getName().length() > 70) {
                tv.setText(child.getName().toString().substring(0, 70));
            } else {
                tv.setText(child.getName().toString());
            }
            tv2.setText(child.getDetails().toString());
        }
        iv.setImageResource(child.getImage());
        return convertView;
    }

    private String getMinusPoints(String name) {
        String scenario = "";
        Context context = this.context;
        SharedPreferences sharedPrefs = this.context.getSharedPreferences("Settings", 0);
        if (name.equals("Contacts")) {
            scenario = scenario + "F";
        } else if (!sharedPrefs.getBoolean("sContacts", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aContacts", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (name.equals("Messages")) {
            scenario = scenario + "F";
        } else if (!sharedPrefs.getBoolean("sMessages", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aMessages", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (name.equals("Location")) {
            scenario = scenario + "F";
        } else if (!sharedPrefs.getBoolean("sLocation", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aLocation", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (name.equals("Photos")) {
            scenario = scenario + "F";
        } else if (!sharedPrefs.getBoolean("sPhotos", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aPhotos", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (name.equals("Browser")) {
            scenario = scenario + "F";
        } else if (!sharedPrefs.getBoolean("sBrowser", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aBrowser", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        return Integer.toString(Integer.valueOf(Integer.valueOf(checkScenarios(scenario, "day" + Integer.toString(Integer.valueOf(sharedPrefs.getInt("Day", 0)).intValue()) + ".txt").intValue() - calculateNewPoints().intValue()).intValue() - 10).intValue());
    }

    public int getChildrenCount(int groupPosition) {
        return ((Group) this.groups.get(groupPosition)).getItems().size();
    }

    public Object getGroup(int groupPosition) {
        return this.groups.get(groupPosition);
    }

    public int getGroupCount() {
        return this.groups.size();
    }

    public long getGroupId(int groupPosition) {
        return (long) groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Button btn1;
        Button btn2;
        Group group = (Group) getGroup(groupPosition);
        Child child = (Child) group.getItems().get(0);
        final TextView txtMe = (TextView) this.activity.findViewById(this.context.getResources().getIdentifier("txt" + group.getName(), "id", this.context.getPackageName()));
        if (convertView == null) {
            Context context = this.context;
            Context context2 = this.context;
            convertView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C0150R.layout.group_item, null);
            final Button cancelButton = (Button) convertView.findViewById(C0150R.id.btnCancel);
            final Button confirmButton = (Button) convertView.findViewById(C0150R.id.btnConfirm);
            cancelButton.setFocusable(false);
            btn1 = (Button) convertView.findViewById(C0150R.id.btnConfirm);
            btn2 = (Button) convertView.findViewById(C0150R.id.btnCancel);
            final int i = groupPosition;
            cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Group g = (Group) ReviewListAdapter.this.getGroup(i);
                    TextView txtSet = (TextView) ReviewListAdapter.this.activity.findViewById(C0150R.id.txtSetAll);
                    txtSet.setText(txtSet.getText() + "T");
                    cancelButton.setEnabled(false);
                    confirmButton.setEnabled(false);
                    ReviewListAdapter.this.checkAllButtons();
                    txtMe.setText("A");
                    ReviewListAdapter.this.helper.setTotalShared(Integer.valueOf(ReviewListAdapter.this.helper.getTotalShared().intValue() - 1));
                    ReviewListAdapter.this.helper.Logger(ReviewListAdapter.this.helper.getTreatment(), ReviewListAdapter.this.helper.getStudyDay().toString(), ReviewListAdapter.screen, "Regret", ReviewListAdapter.this.helper.getTotalPoints().toString(), g.getName(), "-", "-", "-", "-", "-");
                    ((ExpandableListView) ReviewListAdapter.this.activity.findViewById(C0150R.id.exp_list)).collapseGroup(i);
                }
            });
            confirmButton.setFocusable(false);
            i = groupPosition;
            confirmButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Group g = (Group) ReviewListAdapter.this.getGroup(i);
                    ReviewListAdapter.this.context;
                    Editor prefEditor = ReviewListAdapter.this.context.getSharedPreferences("Settings", 0).edit();
                    prefEditor.putBoolean("s" + g.getName(), true);
                    prefEditor.putBoolean("a" + g.getName(), true);
                    prefEditor.commit();
                    cancelButton.setEnabled(false);
                    confirmButton.setEnabled(false);
                    ReviewListAdapter.this.helper.Logger(ReviewListAdapter.this.helper.getTreatment(), ReviewListAdapter.this.helper.getStudyDay().toString(), ReviewListAdapter.screen, "Happy", ReviewListAdapter.this.helper.getTotalPoints().toString(), g.getName(), "-", "-", "-", "-", "-");
                    TextView txtSet = (TextView) ReviewListAdapter.this.activity.findViewById(C0150R.id.txtSetAll);
                    txtSet.setText(txtSet.getText() + "T");
                    ReviewListAdapter.this.checkAllButtons();
                    txtMe.setText("A");
                    ((ExpandableListView) ReviewListAdapter.this.activity.findViewById(C0150R.id.exp_list)).collapseGroup(i);
                }
            });
            TextView txtSet2 = (TextView) this.activity.findViewById(C0150R.id.txtSetAll);
            if (child.getName().equals("No data")) {
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn2.setText("Cancel");
            } else {
                Button button = (Button) convertView.findViewById(C0150R.id.btnCancel);
            }
            if (txtMe.getText().equals("A")) {
                btn1.setEnabled(false);
                btn2.setEnabled(false);
            }
        }
        if (convertView != null) {
            context = this.context;
            context2 = this.context;
            convertView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C0150R.layout.group_item, null);
            cancelButton = (Button) convertView.findViewById(C0150R.id.btnCancel);
            confirmButton = (Button) convertView.findViewById(C0150R.id.btnConfirm);
            cancelButton.setFocusable(false);
            i = groupPosition;
            cancelButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Group g = (Group) ReviewListAdapter.this.getGroup(i);
                    TextView txtSet = (TextView) ReviewListAdapter.this.activity.findViewById(C0150R.id.txtSetAll);
                    txtSet.setText(txtSet.getText() + "T");
                    cancelButton.setEnabled(false);
                    confirmButton.setEnabled(false);
                    ReviewListAdapter.this.checkAllButtons();
                    txtMe.setText("A");
                    ReviewListAdapter.this.helper.setTotalShared(Integer.valueOf(ReviewListAdapter.this.helper.getTotalShared().intValue() - 1));
                    ReviewListAdapter.this.helper.Logger(ReviewListAdapter.this.helper.getTreatment(), ReviewListAdapter.this.helper.getStudyDay().toString(), ReviewListAdapter.screen, "Regret", ReviewListAdapter.this.helper.getTotalPoints().toString(), g.getName(), "-", "-", "-", "-", "-");
                    ((ExpandableListView) ReviewListAdapter.this.activity.findViewById(C0150R.id.exp_list)).collapseGroup(i);
                }
            });
            confirmButton.setFocusable(false);
            i = groupPosition;
            confirmButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Group g = (Group) ReviewListAdapter.this.getGroup(i);
                    ReviewListAdapter.this.context;
                    Editor prefEditor = ReviewListAdapter.this.context.getSharedPreferences("Settings", 0).edit();
                    prefEditor.putBoolean("s" + g.getName(), true);
                    prefEditor.putBoolean("a" + g.getName(), true);
                    prefEditor.commit();
                    cancelButton.setEnabled(false);
                    confirmButton.setEnabled(false);
                    Integer pending = Integer.valueOf(0);
                    ReviewListAdapter.this.helper.Logger(ReviewListAdapter.this.helper.getTreatment(), ReviewListAdapter.this.helper.getStudyDay().toString(), ReviewListAdapter.screen, "Happy", ReviewListAdapter.this.helper.getTotalPoints().toString(), g.getName(), "-", "-", "-", "-", "-");
                    TextView txtSet = (TextView) ReviewListAdapter.this.activity.findViewById(C0150R.id.txtSetAll);
                    txtSet.setText(txtSet.getText() + "T");
                    ReviewListAdapter.this.checkAllButtons();
                    txtMe.setText("A");
                    ((ExpandableListView) ReviewListAdapter.this.activity.findViewById(C0150R.id.exp_list)).collapseGroup(i);
                }
            });
            btn1 = (Button) convertView.findViewById(C0150R.id.btnConfirm);
            btn2 = (Button) convertView.findViewById(C0150R.id.btnCancel);
            txtSet2 = (TextView) this.activity.findViewById(C0150R.id.txtSetAll);
            if (child.getName().equals("No data")) {
                btn1.setEnabled(false);
                btn2.setEnabled(false);
            } else {
                button = (Button) convertView.findViewById(C0150R.id.btnCancel);
            }
            if (txtMe.getText().equals("A")) {
                btn1.setEnabled(false);
                btn2.setEnabled(false);
            }
        }
        ((TextView) convertView.findViewById(C0150R.id.group_name)).setText(group.getName());
        return convertView;
    }

    private void checkAllButtons() {
        Integer tru = Integer.valueOf(((TextView) this.activity.findViewById(C0150R.id.txtSetAll)).getText().length());
        Context context = this.context;
        if (tru.equals(Integer.valueOf(this.context.getSharedPreferences("Settings", 0).getInt("NumData", 0)))) {
            ((Button) this.activity.findViewById(C0150R.id.btnNextScenario)).setEnabled(true);
        }
    }

    private Integer calculateNewPoints() {
        String scenario = "";
        Context context = this.context;
        SharedPreferences sharedPrefs = this.context.getSharedPreferences("Settings", 0);
        if (!sharedPrefs.getBoolean("sContacts", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aContacts", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (!sharedPrefs.getBoolean("sMessages", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aMessages", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (!sharedPrefs.getBoolean("sLocation", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aLocation", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (!sharedPrefs.getBoolean("sPhotos", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aPhotos", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        if (!sharedPrefs.getBoolean("sBrowser", false)) {
            scenario = scenario + "F";
        } else if (sharedPrefs.getBoolean("aBrowser", false)) {
            scenario = scenario + "T";
        } else {
            scenario = scenario + "F";
        }
        return checkScenarios(scenario, "day" + Integer.toString(Integer.valueOf(sharedPrefs.getInt("Day", 0)).intValue()) + ".txt");
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Bitmap loadImage() {
        Bitmap b = null;
        Helper helper = new Helper(this.context);
        Context context = this.context;
        SharedPreferences sharedPrefs = this.context.getSharedPreferences("Settings", 0);
        Editor prefEditor = sharedPrefs.edit();
        try {
            b = BitmapFactory.decodeStream(new FileInputStream(new File(sharedPrefs.getString("ImgPath", null), "pendingImage" + helper.getStudyDay() + imgNumber + ".jpg")));
            if (imgNumber.equals(Integer.valueOf(3))) {
                imgNumber = Integer.valueOf(1);
            } else {
                imgNumber = Integer.valueOf(imgNumber.intValue() + 1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

    private Integer checkScenarios(String scenario, String day) {
        Map scenarios = new HashMap();
        try {
            InputStream inputStream = this.context.openFileInput(day);
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
}
