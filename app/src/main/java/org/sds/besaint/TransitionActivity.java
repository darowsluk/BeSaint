package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        // return to the caller
        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(intent);
    }
}
