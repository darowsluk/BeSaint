package org.sds.besaint;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

// A Singleton class for storing state and data
public class AppSingleton {
    private static AppSingleton ourInstance = new AppSingleton();

    // State
    private boolean mFirstRun = true;

    // Constructor is private, so it cannot be instantiated from outside
    private AppSingleton() {
        // Load data state from external storage
    }

    public static AppSingleton getInstance() {
        return ourInstance;
    }

    public boolean getFirstRun() {
        return mFirstRun;
    }
    public void setFirstRun(boolean run) {
        mFirstRun = run;
    }
}
