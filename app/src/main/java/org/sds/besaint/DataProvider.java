package org.sds.besaint;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;
import android.widget.Toast;

import java.io.IOException;

// CUSTOM data provider (abstraction) for the application
public class DataProvider {

    // Methods
    public DataJourney getDataJourney(Context context, int journeyUID) {
        Cursor c;
        String q;
        int i=0;
        DataJourney journey = new DataJourney();

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            //q = "SELECT * FROM journey WHERE saint_id = " + Integer.toString(saintId);
            //c = dB.rawQuery(q, null);
            c = dB.query ("journey",
                    new String[] {"journey_UID", "saint_ID", "title", "level", "author", "days", "description", "image"},
                    "journey_UID = ?",
                    new String[] {Integer.toString(journeyUID)},
                    null, null, null);
            if (!c.moveToFirst()) {
                // No entry found - provide default
                // TODO: THROW EXCEPTION
                journey = null;
            }
            else {
                journey.setJourneyUID(c.getInt(0));
                journey.setId(c.getInt(1));
                journey.setTitle(c.getString(2));
                journey.setLevel(c.getInt(3));
                journey.setAuthor(c.getString(4));
                journey.setDays(c.getInt(5));
                journey.setDescription(c.getString(6));
                journey.setImage(c.getBlob(7));
            }
        }
        return journey;
    }

    public SparseArray<DataJourney> getDataJourneys(Context context, int saintId) {
        Cursor c;
        String q;
        int i;

        SparseArray<DataJourney> journeys = new SparseArray<DataJourney>();
        DataJourney journey;

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            //q = "SELECT * FROM journey WHERE saint_id = " + Integer.toString(saintId);
            //c = dB.rawQuery(q, null);
            c = dB.query ("journey",
                    new String[] {"journey_UID", "saint_ID", "title", "level", "author", "days", "description", "image"},
                    "saint_ID = ?",
                    new String[] {Integer.toString(saintId)},
                    null, null, null);
            if (!c.moveToFirst()) {
                // No entry found - provide default
                journey = new DataJourney();
                journey.setTitle("Unavailable journey");
                journey.setAuthor("Unknown");
                journey.setLevel(1);
                journey.setDays(3);
                journey.setId(0);
                journey.setSaintId(saintId);
                journey.setJourneyUID(0); // TODO: check for this
                journey.setDescription("Unknown");
                journey.setImage(null);
                journeys.append(0, journey);
            }
            else {
                i=0;
                do {
                    journey = new DataJourney();
                    journey.setJourneyUID(c.getInt(0));
                    journey.setId(c.getInt(1));
                    journey.setTitle(c.getString(2));
                    journey.setLevel(c.getInt(3));
                    journey.setAuthor(c.getString(4));
                    journey.setDays(c.getInt(5));
                    journey.setDescription(c.getString(6));
                    journey.setImage(c.getBlob(7));
                    journeys.append(i++, journey);
                } while (c.moveToNext());

            }
        }
        return journeys;
    }

    public SparseArray<DataSaint> getDataSaints(Context context) {
        Cursor c;
        String q;
        int i;

        SparseArray<DataSaint> saints = new SparseArray<DataSaint>();
        DataSaint saint;

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            //q = "SELECT * FROM journey WHERE saint_id = " + Integer.toString(saintId);
            //c = dB.rawQuery(q, null);
            c = dB.query ("saint",
                    new String[] {"_ID", "name", "fullname", "title", "img100r", "img200r"},
                    null, null, null, null, null);
            if (!c.moveToFirst()) {
                // No entry found - provide default
                saint = new DataSaint();
                saint.setId(0);
                saint.setName("Unknown");
                saint.setFullName("Unknown");
                saint.setTitle("");
                saint.setId(0);
                saint.setImage100(null);
                saint.setImage200(null);
            }
            else {
                i=0;
                do {
                    saint = new DataSaint();
                    saint.setId(c.getInt(0));
                    saint.setName(c.getString(1));
                    saint.setFullName(c.getString(2));
                    saint.setTitle(c.getString(3));
                    saint.setImage100(c.getBlob(4));
                    saint.setImage200(c.getBlob(5));
                    saints.append(i++, saint);
                } while (c.moveToNext());

            }
        }
        return saints;
    }

    // Helper DB methods
    public SQLiteDatabase getDB(Context context) {
        SQLiteDatabase mDb;
        int mVersion;

        DatabaseHelper mDBHelper = DatabaseHelper.getInstance(context);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            // TODO: get error string from resources
            Toast toast = Toast.makeText(context, "Database cannot be updated", Toast.LENGTH_SHORT);
            toast.show();
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException e) {
            // TODO: get error string from resources
            Toast toast = Toast.makeText(context, "Writable Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }
        return mDb;
    }
}