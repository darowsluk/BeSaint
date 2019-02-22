package org.sds.besaint;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;


public class RunningActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DataProvider mDataProvider;
    private DataJourney mJourney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        // This line initializes Stetho for viewing sqlite database in chrome browser (http://facebook.github.io/stetho/)
        Stetho.initializeWithDefaults(this);

        ViewPager viewPager = findViewById(R.id.id_ViewPager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.id_TabLayout);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.id_mainToolbar);
        setSupportActionBar(toolbar);

        mDataProvider = new DataProvider();

        // Add drawer toggle (Head First Android Development, p. 607)
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // Update welcome header with user name
                String welcomeText = getString(R.string.res_txtWelcomeDefault) + " " + mDataProvider.getSharedUserName(getApplicationContext());
                TextView txtDrawerWelcome = findViewById(R.id.id_txtDrawerWelcome);
                txtDrawerWelcome.setText(welcomeText);

                // Update current journey details
                Context context = getApplicationContext();
                String days, level;
                int currentJourneyUID;
                byte[] headImage;

                // Get views handles
                TextView navTextTitle = findViewById(R.id.id_navTextTitle);
                TextView navTextAuthor = findViewById(R.id.id_navTextAuthor);
                TextView navTextLevel = findViewById(R.id.id_navTextLevel);
                TextView navTextDays = findViewById(R.id.id_navTextDays);
                ImageView imgView = findViewById(R.id.id_navImageView);

                currentJourneyUID = mDataProvider.getSharedCurrentJourneyUID(context);
                DataJourney dataJourney = mDataProvider.getDataJourney(context, currentJourneyUID);

                if (dataJourney != null) {
                    navTextTitle.setText(dataJourney.getTitle());
                    navTextAuthor.setText(dataJourney.getAuthor());
                    level = getString(R.string.res_txtLevelLabel) + " " + dataJourney.getLevelString();
                    navTextLevel.setText(level);
                    days = getString(R.string.res_txtDayLabel) + " " + mDataProvider.getSharedCurrentDay(context) + "/" + dataJourney.getDays();
                    navTextDays.setText(days);
                    headImage = mJourney.getImage();
                    Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(headImage, 0, headImage.length));
                    int w = (int) (drawable.getIntrinsicWidth() * getResources().getDisplayMetrics().density);
                    int h = (int) (drawable.getIntrinsicHeight() * getResources().getDisplayMetrics().density);
                    drawable.setBounds(0, 0, w, h);
                    imgView.setImageDrawable(drawable);
                }
                else {
                    // Current journey was cleared
                    navTextTitle.setText(DataConstants.EMPTY_STRING);
                    navTextAuthor.setText(getResources().getString(R.string.res_txtEmptyJourney));
                    navTextLevel.setText(DataConstants.EMPTY_STRING);
                    navTextDays.setText(DataConstants.EMPTY_STRING);
                    imgView.setImageResource(R.drawable.res_img_intro80);
                }

            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Register the activity as a listener on the navigation view (p. 608)
        NavigationView navigationView= findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Update image and title from the current journey in DB
        String txtDisplay, saintName, journeyTitle, currentDayText;

        int currentJourneyUID, currentDay;
        currentJourneyUID = mDataProvider.getSharedCurrentJourneyUID(this);
        currentDay = mDataProvider.getSharedCurrentDay(this);


        mJourney = mDataProvider.getDataJourney(this, currentJourneyUID);
        DataDay mDay = mDataProvider.getDataDay(this, currentJourneyUID, currentDay);


        if (mJourney == null || mDay == null) {
            journeyTitle = DataConstants.DEFAULT_JOURNEY_TITLE;
            saintName = DataConstants.DEFAULT_SAINT_NAME;
            currentDayText = DataConstants.DEFAULT_DAY;
        }
        else {
            DataSaint mSaint = mDataProvider.getDataSaint(this, mJourney.getSaintId());
            if (mSaint == null) {
                // TODO: Throw exception
                journeyTitle = DataConstants.DEFAULT_JOURNEY_TITLE;
                saintName = DataConstants.DEFAULT_SAINT_NAME;
                currentDayText = DataConstants.DEFAULT_DAY;
            }
            else {
                journeyTitle = mJourney.getTitle();
                currentDayText = mDay.getDay();
                saintName = mSaint.getName();
                byte[] mImage = mSaint.getImage200();
                if (mImage != null) {
                    Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(mImage, 0, mImage.length));
                    int w = (int) (drawable.getIntrinsicWidth() * getResources().getDisplayMetrics().density);
                    int h = (int) (drawable.getIntrinsicHeight() * getResources().getDisplayMetrics().density);
                    drawable.setBounds(0, 0, w, h);
                    ImageView imgView = findViewById(R.id.id_runningHeaderImage);
                    imgView.setImageDrawable(drawable);
                }
            }
        }
        txtDisplay = getString(R.string.res_txtDayLabel) + " " + currentDayText;
        TextView saintView = findViewById(R.id.id_runningHeaderSaint);
        saintView.setText(saintName);
        TextView titleView = findViewById(R.id.id_runningHeaderTitle);
        titleView.setText(journeyTitle);
        TextView dayView = findViewById(R.id.id_runningHeaderCurrentDay);
        dayView.setText(txtDisplay);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.id_collapsingToolbarLayout);
        collapsingToolbarLayout.setTitleEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        //toolbar.setTitle("");
        //toolbar.setSubtitle(journeyTitle);
        //toolbar.setLogo(R.drawable.res_img_jpii48round);

    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, JourneyActivity.class);
        startActivity(intent);
    }


    // This method is called whenever an item in the drawer is clicked
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // (p. 609)
        int id = menuItem.getItemId();
        Intent intent;
        switch(id) {
            case R.id.nav_journey:
                intent = new Intent(this, JourneyActivity.class);
                break;
            case R.id.nav_history:
                intent = new Intent(this, HistoryActivity.class);
                break;
            case R.id.nav_profile:
                intent = new Intent(this, SignActivity.class);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.nav_about:
                intent = new Intent(this, AboutActivity.class);
                break;
            case R.id.nav_help:
                // TODO: open new activity with help
                intent = new Intent(this, AboutActivity.class);
                break;
            default:
                intent = new Intent(this, AboutActivity.class);
        }
        startActivity(intent);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // return boolean indicates whether the item in the drawer should be highlighted
        return true;
    }

    // This method is called whenever the Back button is pressed
    @Override
    public void onBackPressed() {
        // (p. 614)
        // Behavior dependent on whether drawer is open or closed
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            // Close drawer if it is currently open
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            // Call normal onBackPressed method
            super.onBackPressed();
        }
    }
}
