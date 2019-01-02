package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }

    public void onOKClick(View view) {
        Intent intent = new Intent(this, JourneyActivity.class);
        startActivity(intent);
    }
}
