package com.meaningful.ata1g11.meaningfulconsent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Final extends AppCompatActivity {
    Helper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0150R.layout.activity_final);
        this.helper = new Helper(this);
        Integer finalPoint = this.helper.getTotalPoints();
        ((TextView) findViewById(C0150R.id.txtFinalPoints)).setText(Integer.toString(finalPoint.intValue()) + " = " + "£" + (((double) Math.round(((double) finalPoint.intValue()) * 1.0d)) / 100.0d));
        ((TextView) findViewById(C0150R.id.txtID)).setText("ID-" + this.helper.getUserNumber());
        new ServerJobFinal(this).execute(new String[]{this.helper.getUserID(), Integer.toString(finalPoint.intValue()), money + "", this.helper.getUserNumber()});
    }

    public void onBackPressed() {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0150R.menu.menu_final, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == C0150R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
