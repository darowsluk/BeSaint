package org.sds.besaint;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ExpandableListView;
import java.io.IOException;

public class JourneyActivity extends AppCompatActivity {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        createData();
        ExpandableListView listView = findViewById(R.id.id_listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);
    }

    public void createData() {

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
            Drawable drawable;

            Group group;
            String q1, q2, saintName, journeyTitle;
            int i=0, j=0, w, h, colTitle, colFullName, colID, colImg;
            Cursor c1, c2;
            byte[] imgBlob;
            Drawable image;
            q1 = "SELECT * FROM saint";
            c1 = mDb.rawQuery(q1, null);
            Log.d("DB", "c1 count:" + c1.getCount());
            c1.moveToFirst();
            do {
                colImg = c1.getColumnIndex("img100r");
                imgBlob = c1.getBlob(colImg);
                drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length));
                w = (int)(drawable.getIntrinsicWidth()*getResources().getDisplayMetrics().density);
                h = (int)(drawable.getIntrinsicHeight()*getResources().getDisplayMetrics().density);
                drawable.setBounds(0, 0, w, h);

                colTitle = c1.getColumnIndex("title");
                colFullName = c1.getColumnIndex("fullname");
                saintName = c1.getString(colTitle) + " " + c1.getString(colFullName);
                group = new Group(saintName, drawable);
                colID = c1.getColumnIndex("_ID");
                j = c1.getInt(colID);
                q2 = "SELECT * FROM journey WHERE saint_id = " + Integer.toString(j);
                c2 = mDb.rawQuery(q2, null);
                c2.moveToFirst();
                do {
                    colTitle = c2.getColumnIndex("title");
                    journeyTitle = c2.getString(colTitle);
                    group.children.add(journeyTitle);
                } while (c2.moveToNext());
                groups.append(i++, group);
            } while (c1.moveToNext());
        }
    }
}
