package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }

    // TODO: Check parent - if selected from options menu return to running activity
    public void onOKClick(View view) {
        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(intent);
    }
}
