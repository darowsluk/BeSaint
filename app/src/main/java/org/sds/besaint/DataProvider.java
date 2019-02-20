package org.sds.besaint;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;
import android.widget.Toast;

import java.io.IOException;

// CUSTOM data provider (abstraction) for the application
public class DataProvider {

    // Constants
    private static final String DB_TABLE_BESAINT = "besaint_data";

    /////////////////////////////
    // SHARED PREFERENCES DATA //
    /////////////////////////////

    public void setSharedProgressDone(Context context, boolean taskDone) {
        int totalDays, finishedDays;
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        totalDays = sharedPreferences.getInt(DataConstants.KEY_PROGRESS_TOTALDAYS, DataConstants.DEFAULT_INT);
        finishedDays = sharedPreferences.getInt(DataConstants.KEY_PROGRESS_FINISHEDDAYS, DataConstants.DEFAULT_INT);
        if (taskDone) {
            finishedDays++;
        }
        totalDays++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DataConstants.KEY_PROGRESS_TOTALDAYS, totalDays);
        editor.putInt(DataConstants.KEY_PROGRESS_FINISHEDDAYS, finishedDays);
        editor.commit();
    }

    public void setSharedUserName(Context context, String userName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DataConstants.KEY_USERNAME, userName);
        editor.commit();
    }

    public void setSharedCurrentDay(Context context, int currentDay) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DataConstants.KEY_CURRENT_DAY, currentDay);
        editor.commit();
    }

    public void setSharedCurrentJourneyUID(Context context, int currentJourneyUID) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DataConstants.KEY_CURRENT_JOURNEY_UID, currentJourneyUID);
        editor.commit();
    }

    public int getSharedProgressTotalDays(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(DataConstants.KEY_PROGRESS_TOTALDAYS, DataConstants.DEFAULT_INT);
    }

    public int getSharedProgressFinishedDays(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(DataConstants.KEY_PROGRESS_FINISHEDDAYS, DataConstants.DEFAULT_INT);
    }

    public String getSharedUserName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(DataConstants.KEY_USERNAME, DataConstants.DEFAULT_STRING);
    }

    public int getSharedCurrentDay(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(DataConstants.KEY_CURRENT_DAY, DataConstants.DEFAULT_INT);
    }

    public int getSharedCurrentJourneyUID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DataConstants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(DataConstants.KEY_CURRENT_JOURNEY_UID, DataConstants.DEFAULT_INT);
    }

    ////////////////////
    // SQLITE DB DATA //
    ////////////////////


    public DataDay getDataDay(Context context, int journeyUID, int currentDay) {
        Cursor cursor;
        DataDay dataDay = new DataDay();

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            cursor = dB.query ("day",
                    new String[] {"_ID", "journey_UID", "title", "day", "inspiration"},
                    "journey_UID = ? AND day = ?",
                    new String[] {Integer.toString(journeyUID), Integer.toString(currentDay)},
                    null, null, null);
            if (!cursor.moveToFirst()) {
                // No entry found - provide default
                // TODO: THROW EXCEPTION
                dataDay = null;
            }
            else {
                dataDay.setId(cursor.getInt(0));
                dataDay.setJourneyId(cursor.getInt(1));
                dataDay.setTitle(cursor.getString(2));
                dataDay.setDay(cursor.getString(3));
                dataDay.setInspiration(cursor.getString(4));
            }
        }
        return dataDay;
    }

    public DataJourney getDataJourney(Context context, int journeyUID) {
        Cursor c;
        DataJourney journey = new DataJourney();

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            c = dB.query ("journey",
                    new String[] {"_ID", "journey_UID", "saint_ID", "title", "level", "author", "days", "description", "image"},
                    "journey_UID = ?",
                    new String[] {Integer.toString(journeyUID)},
                    null, null, null);
            if (!c.moveToFirst()) {
                // No entry found - provide default
                // TODO: THROW EXCEPTION
                journey = null;
            }
            else {
                journey.setId(c.getInt(0));
                journey.setJourneyUID(c.getInt(1));
                journey.setSaintId(c.getInt(2));
                journey.setTitle(c.getString(3));
                journey.setLevel(c.getInt(4));
                journey.setAuthor(c.getString(5));
                journey.setDays(c.getInt(6));
                journey.setDescription(c.getString(7));
                journey.setImage(c.getBlob(8));
            }
        }
        return journey;
    }

    public DataSaint getDataSaint(Context context, int saint_ID) {
        Cursor c;
        DataSaint saint = new DataSaint();

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            c = dB.query ("saint",
                    new String[] {"name", "fullname", "title", "img100r", "img200r"},
                    "_ID = ?",
                    new String[] {Integer.toString(saint_ID)},
                    null, null, null);
            if (!c.moveToFirst()) {
                // No entry found - provide default
                // TODO: THROW EXCEPTION
                saint = null;
            }
            else {
                saint.setId(saint_ID);
                saint.setName(c.getString(0));
                saint.setFullName(c.getString(1));
                saint.setTitle(c.getString(2));
                saint.setImage100(c.getBlob(3));
                saint.setImage200(c.getBlob(4));
            }
        }
        return saint;
    }

    public SparseArray<DataJourney> getDataJourneys(Context context, int saintId) {
        Cursor c;
        int i;

        SparseArray<DataJourney> journeys = new SparseArray<DataJourney>();
        DataJourney journey;

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            //q = "SELECT * FROM journey WHERE saint_id = " + Integer.toString(saintId);
            //c = dB.rawQuery(q, null);
            c = dB.query ("journey",
                    new String[] {"_ID", "journey_UID", "saint_ID", "title", "level", "author", "days", "description", "image"},
                    "saint_ID = ?",
                    new String[] {Integer.toString(saintId)},
                    null, null, null);
            if (!c.moveToFirst()) {
                // No entry found - provide default
                journey = new DataJourney();
                journey.setId(0);
                journey.setTitle("");
                journey.setAuthor("");
                journey.setLevel(0);
                journey.setDays(0);
                journey.setId(0);
                journey.setSaintId(saintId);
                journey.setJourneyUID(0); // TODO: check for this
                journey.setDescription("");
                journey.setImage(null);
                journeys.append(0, journey);
            }
            else {
                i=0;
                do {
                    journey = new DataJourney();
                    journey.setId(c.getInt(0));
                    journey.setJourneyUID(c.getInt(1));
                    journey.setSaintId(c.getInt(2));
                    journey.setTitle(c.getString(3));
                    journey.setLevel(c.getInt(4));
                    journey.setAuthor(c.getString(5));
                    journey.setDays(c.getInt(6));
                    journey.setDescription(c.getString(7));
                    journey.setImage(c.getBlob(8));
                    journeys.append(i++, journey);
                } while (c.moveToNext());

            }
        }
        return journeys;
    }

    public SparseArray<DataSaint> getDataSaints(Context context) {
        Cursor c;
        int i;

        SparseArray<DataSaint> saints = new SparseArray<DataSaint>();
        DataSaint saint;

        // Load data from DB
        SQLiteDatabase dB = getDB(context);
        if (dB.getVersion() >= 1) {
            c = dB.query ("saint",
                    new String[] {"_ID", "name", "fullname", "title", "img100r", "img200r"},
                    null, null, null, null, null);
            if (!c.moveToFirst()) {
                // No entry found - provide default
                saint = new DataSaint();
                saint.setId(0);
                saint.setName("");
                saint.setFullName("");
                saint.setTitle("");
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