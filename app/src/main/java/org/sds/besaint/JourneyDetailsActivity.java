package org.sds.besaint;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class JourneyDetailsActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_details);

        String q, description;
        Cursor c;
        int colImg, w, h;
        byte[] imgBlob;
        Drawable drawable;

        Intent intent = getIntent();
        int groupPosition = intent.getIntExtra(MyExpandableListAdapter.PARAM1, -1) + 1;
        int childPosition = intent.getIntExtra(MyExpandableListAdapter.PARAM2, -1);
        String title = intent.getStringExtra(MyExpandableListAdapter.PARAM3);
        TextView textView = findViewById(R.id.id_txtViewTitle);
        textView.setText(title);

        mDBHelper = DatabaseHelper.getInstance(this);
        int dbVersion;

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        dbVersion = mDb.getVersion();
        if (dbVersion >= 1) {
            // QUERY db
            q = "SELECT * FROM saint WHERE _ID = " + groupPosition;
            c = mDb.rawQuery(q, null);
            c.moveToFirst();
            colImg = c.getColumnIndex("img100r");
            imgBlob = c.getBlob(colImg);
            drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length));
            w = (int)(drawable.getIntrinsicWidth()*getResources().getDisplayMetrics().density );
            h = (int)(drawable.getIntrinsicHeight()*getResources().getDisplayMetrics().density);
            drawable.setBounds(0, 0, w, h);
            ImageView imgView = findViewById(R.id.id_imgSaint);
            imgView.setImageDrawable(drawable);

            q = "SELECT * FROM journey WHERE saint_ID = " + groupPosition;
            c = mDb.rawQuery(q, null);
            c.moveToPosition(childPosition);
            colImg = c.getColumnIndex("description");
            description = c.getString(colImg);
            TextView txtViewDescription = findViewById(R.id.id_txtViewDescription);
            txtViewDescription.setText(description);
        }
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(intent);
    }
}
