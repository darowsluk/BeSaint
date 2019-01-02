package org.sds.besaint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JourneyDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_details);

        Intent intent = getIntent();
        int groupPosition = intent.getIntExtra(MyExpandableListAdapter.PARAM1, -1);
        int childPosition = intent.getIntExtra(MyExpandableListAdapter.PARAM2, -1);
        TextView textView = findViewById(R.id.id_txtViewTitle);
        textView.setText("TODO - use ROOM");
    }
}
