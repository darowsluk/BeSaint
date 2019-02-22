package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private DataProvider mDataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mDataProvider = new DataProvider();
    }

    public void onResetClick(View view) {
        // Clear data from the Shared Data
        mDataProvider.clearSharedData(getBaseContext());
        Toast.makeText(getBaseContext(), getResources().getString(R.string.res_txtToastResetData),
                Toast.LENGTH_LONG).show();
        // Go back to running activity
        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(intent);
    }
}
