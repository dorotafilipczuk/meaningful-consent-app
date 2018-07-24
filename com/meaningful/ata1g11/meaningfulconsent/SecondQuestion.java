package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SecondQuestion extends AppCompatActivity {
    public static String age;
    public static String answer1;
    public static String gender;
    public static String job;
    public static String nationality;
    public static String user_id;
    Helper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0150R.layout.activity_second_question);
        this.helper = new Helper(this);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        job = intent.getStringExtra("job");
        nationality = intent.getStringExtra("nationality");
        answer1 = intent.getStringExtra("answer1");
        setTitle("2nd Question");
    }

    public void onBackPressed() {
    }

    public void nextQuestion(View view) {
        TextView warn = (TextView) findViewById(C0150R.id.txtWarn2);
        boolean flag = true;
        RadioGroup rg = (RadioGroup) findViewById(C0150R.id.rgAnswer2);
        if (rg.getCheckedRadioButtonId() == -1) {
            flag = false;
            warn.setText("Please select an answer.");
        }
        if (flag) {
            new ServerJobAppList().execute(this.helper.getInstalledApps());
            new ServerJobCountData(this).execute(new String[]{user_id});
            RadioButton rb = (RadioButton) findViewById(Integer.valueOf(rg.getCheckedRadioButtonId()).intValue());
            Intent next = new Intent(this, ThirdQuestion.class);
            next.putExtra("user_id", user_id);
            next.putExtra("age", age);
            next.putExtra("gender", gender);
            next.putExtra("job", job);
            next.putExtra("nationality", nationality);
            next.putExtra("answer1", answer1);
            next.putExtra("answer2", rb.getText().toString());
            this.helper.setTotalPoints(Integer.valueOf(500));
            this.helper.setStudyDay(Integer.valueOf(1));
            finish();
            startActivity(next);
        }
    }
}
