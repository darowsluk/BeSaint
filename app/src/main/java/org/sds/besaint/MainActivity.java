package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

