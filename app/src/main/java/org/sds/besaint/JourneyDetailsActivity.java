package org.sds.besaint;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class JourneyDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_JOURNEY_UID = "journey_UID";
    //public static final String EXTRA_SAINT_NAME = "saint_NAME";

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_details);

        Intent intent = getIntent();
        // String saintName = intent.getStringExtra(EXTRA_SAINT_NAME);
        int journeyUID = intent.getIntExtra(EXTRA_JOURNEY_UID, 0);


        DataProvider dataProvider = new DataProvider();
        DataJourney mJourney = dataProvider.getDataJourney(this, journeyUID);
        byte[] image = mJourney.getImage();
        if (image != null) {
            Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(image, 0, image.length));
            int w = (int) (drawable.getIntrinsicWidth() * getResources().getDisplayMetrics().density);
            int h = (int) (drawable.getIntrinsicHeight() * getResources().getDisplayMetrics().density);
            drawable.setBounds(0, 0, w, h);
            ImageView imgView = findViewById(R.id.id_imgSaint);
            imgView.setImageDrawable(drawable);
        }

        TextView textView = findViewById(R.id.id_txtViewTitle);
        textView.setText(mJourney.getTitle());
        TextView txtViewDescription = findViewById(R.id.id_txtViewDescription);
        txtViewDescription.setText(mJourney.getDescription());
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(intent);
    }
}
