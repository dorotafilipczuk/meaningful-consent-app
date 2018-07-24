package com.meaningful.ata1g11.meaningfulconsent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Wait extends AppCompatActivity {
    Helper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0150R.layout.activity_wait);
        this.helper = new Helper(this);
        ((TextView) findViewById(C0150R.id.txtManuel)).setText("''" + this.helper.getTreatment() + "''");
    }

    public void onBackPressed() {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0150R.menu.menu_wait, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == C0150R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void letBegin(View view) {
        Intent next = new Intent(this, Settings.class);
        finish();
        startActivity(next);
    }
}
