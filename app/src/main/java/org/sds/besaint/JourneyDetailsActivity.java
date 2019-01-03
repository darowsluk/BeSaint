package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JourneyDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_details);

        Intent intent = getIntent();
        int groupPosition = intent.getIntExtra(MyExpandableListAdapter.PARAM1, -1);
        int childPosition = intent.getIntExtra(MyExpandableListAdapter.PARAM2, -1);
        String title = intent.getStringExtra(MyExpandableListAdapter.PARAM3);
        TextView textView = findViewById(R.id.id_txtViewTitle);
        textView.setText(title);
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(intent);
    }
}
