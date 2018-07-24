package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FirstQuestion extends AppCompatActivity {
    public static String age;
    public static String gender;
    public static String job;
    public static String nationality;
    public static Integer numberOfScenarios = Integer.valueOf(8);
    public static String user_id;
    Helper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0150R.layout.activity_first_question);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        job = intent.getStringExtra("job");
        nationality = intent.getStringExtra("nationality");
        setTitle("1st Question");
        this.helper = new Helper(this);
        this.helper.setAllWeights(5, 6, 4, 6, 2, 7, 12, 10, 12, 3, 18, 19, 21, 16, 13);
        this.helper.setPrevConstraints("");
    }

    public void onBackPressed() {
    }

    public void nextQuestion(View view) {
        TextView warn = (TextView) findViewById(C0150R.id.txtWarn1);
        boolean flag = true;
        RadioGroup rg = (RadioGroup) findViewById(C0150R.id.rgAnswer);
        if (rg.getCheckedRadioButtonId() == -1) {
            flag = false;
            warn.setText("Please select an answer.");
        }
        if (flag) {
            RadioButton rb = (RadioButton) findViewById(Integer.valueOf(rg.getCheckedRadioButtonId()).intValue());
            Intent next = new Intent(this, SecondQuestion.class);
            next.putExtra("user_id", user_id);
            next.putExtra("age", age);
            next.putExtra("gender", gender);
            next.putExtra("job", job);
            next.putExtra("nationality", nationality);
            next.putExtra("answer1", rb.getText().toString());
            finish();
            startActivity(next);
        }
    }
}
