package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Demogs extends AppCompatActivity {
    public static String age;
    public static String gender;
    public static String job;
    public static String nationality;
    public static String user_id;
    Helper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0150R.layout.activity_demogs);
        this.helper = new Helper(this);
        setTitle("Demographics");
    }

    public void onBackPressed() {
    }

    public boolean onTouchEvent(MotionEvent event) {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void nextQuestion(View view) {
        View sView;
        this.helper.setStudyDay(Integer.valueOf(1));
        EditText etAge = (EditText) findViewById(C0150R.id.etAge);
        EditText etJob = (EditText) findViewById(C0150R.id.etJob);
        EditText etNation = (EditText) findViewById(C0150R.id.etNation);
        TextView warn = (TextView) findViewById(C0150R.id.txtWarn);
        boolean flag = true;
        if (etNation.getText().toString().equals("")) {
            flag = false;
            warn.setText("Please enter your nationality.");
            sView = getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(sView.getWindowToken(), 0);
            }
        }
        if (etJob.getText().toString().equals("")) {
            flag = false;
            warn.setText("Please enter your job.");
            sView = getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(sView.getWindowToken(), 0);
            }
        }
        RadioGroup rg = (RadioGroup) findViewById(C0150R.id.idRG);
        if (rg.getCheckedRadioButtonId() == -1) {
            flag = false;
            warn.setText("Please select your gender.");
            sView = getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(sView.getWindowToken(), 0);
            }
        }
        if (etAge.getText().toString().equals("")) {
            flag = false;
            warn.setText("Please enter your age.");
            sView = getCurrentFocus();
            if (view != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(sView.getWindowToken(), 0);
            }
        }
        if (flag) {
            Helper helper = this.helper;
            user_id = Helper.generateNewUserID();
            this.helper.setUserID(user_id);
            age = etAge.getText().toString();
            gender = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
            job = etJob.getText().toString();
            nationality = etNation.getText().toString();
            Intent next = new Intent(this, FirstQuestion.class);
            next.putExtra("user_id", user_id);
            next.putExtra("age", age);
            next.putExtra("gender", gender);
            next.putExtra("job", job);
            next.putExtra("nationality", nationality);
            finish();
            startActivity(next);
        }
    }
}
