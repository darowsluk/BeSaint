package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.id_mainToolbar);
        setSupportActionBar(toolbar);
    }

    public void onStartClick(View view) {
        Intent intent;
        if (AppSingleton.getInstance().getFirstRun()) {
            intent = new Intent(this, SignActivity.class);
            AppSingleton.getInstance().setFirstRun(false);
        }
        else {
            intent = new Intent(this, JourneyActivity.class);
        }
        startActivity(intent);
    }
}

