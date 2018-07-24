package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ThirdQuestion extends AppCompatActivity {
    public static String age;
    public static String answer1;
    public static String answer2;
    public static String answer3;
    public static String gender;
    public static String job;
    public static String nationality;
    public static Integer numberOfScenarios = Integer.valueOf(8);
    public static String user_id;
    Handler mHandler = new Handler();
    private Runnable mLaunchTask = new C01571();

    class C01571 implements Runnable {
        C01571() {
        }

        public void run() {
            ThirdQuestion.this.startActivity(new Intent(ThirdQuestion.this.getApplicationContext(), Wait.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0150R.layout.activity_third_question);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        job = intent.getStringExtra("job");
        nationality = intent.getStringExtra("nationality");
        answer1 = intent.getStringExtra("answer1");
        answer2 = intent.getStringExtra("answer2");
        setTitle("3rd Question");
    }

    public void onBackPressed() {
    }

    public void nextQuestion(View view) {
        TextView warn = (TextView) findViewById(C0150R.id.txtWarn3);
        boolean flag = true;
        RadioGroup rg = (RadioGroup) findViewById(C0150R.id.rgAnswer3);
        if (rg.getCheckedRadioButtonId() == -1) {
            flag = false;
            warn.setText("Please select an answer.");
        }
        if (flag) {
            createDataIDFiles();
            answer3 = ((RadioButton) findViewById(Integer.valueOf(rg.getCheckedRadioButtonId()).intValue())).getText().toString();
            String brand = Build.BRAND;
            String model = Build.MODEL;
            String version = VERSION.CODENAME + "-" + VERSION.RELEASE + "-" + Integer.toString(VERSION.SDK_INT);
            new ServerJobParticipant(this).execute(new String[]{user_id, age, gender, job, nationality, answer1, answer2, answer3, brand, model, version, "44"});
            List<String> list = new ArrayList();
            for (int i = 1; i <= numberOfScenarios.intValue(); i++) {
                list.add(Integer.toString(i));
            }
            new ServerJobScenario(this).execute((String[]) list.toArray(new String[0]));
            this.mHandler.postDelayed(this.mLaunchTask, 2500);
        }
    }

    private void createDataIDFiles() {
        try {
            for (String filename : new String[]{"Contactsids", "Messagesids", "Appsids", "Photosids", "Browserids"}) {
                File file = getBaseContext().getFileStreamPath(filename + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
        } catch (Exception e) {
            Log.e("creating dataidfiles", "Ex: " + e.toString());
        }
    }
}
