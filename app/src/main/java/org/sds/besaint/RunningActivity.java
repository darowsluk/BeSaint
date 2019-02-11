package org.sds.besaint;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import us.feras.mdv.MarkdownView;


public class RunningActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DataProvider mDataProvider;
    private DataJourney mJourney;
    private byte[] mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        // This line initializes Stetho for viewing sqlite database in chrome browser (http://facebook.github.io/stetho/)
        Stetho.initializeWithDefaults(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.id_ViewPager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_TabLayout);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_mainToolbar);
        setSupportActionBar(toolbar);

        // Add drawer toggle (Head First Android Development, p. 607)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Register the activity as a listener on the navigation view (p. 608)
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Update image and title for the current journey in DB
        String journeyTitle;
        mDataProvider = new DataProvider();
        mJourney = mDataProvider.getCurrentJourney(this);
        if (mJourney == null) {
            journeyTitle = "";
        }
        else {
            mJourney = mDataProvider.getCurrentJourney(this);
            if (mJourney == null) {
                // Day not implemented in DB, so revert to default
                journeyTitle = "";
            }
            else {
                journeyTitle = mJourney.getTitle();
                mImage = mJourney.getImage();
                if (mImage != null) {
                    Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(mImage, 0, mImage.length));
                    int w = (int) (drawable.getIntrinsicWidth() * getResources().getDisplayMetrics().density);
                    int h = (int) (drawable.getIntrinsicHeight() * getResources().getDisplayMetrics().density);
                    drawable.setBounds(0, 0, w, h);
                    ImageView imgView = findViewById(R.id.id_runningHeaderImage);
//                    imgView.setImageBitmap(BitmapFactory.decodeByteArray(mImage, 0, mImage.length));
                    imgView.setImageDrawable(drawable);
                }
            }
        }
        TextView titleView = (TextView) findViewById(R.id.id_runningHeaderTitle);
        titleView.setText(journeyTitle);
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
        Intent intent = null;
        switch(id) {
            case R.id.nav_history:
                intent = new Intent(this, HistoryActivity.class);
                break;
            case R.id.nav_profile:
                intent = new Intent(this, ProfileActivity.class);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // return boolean indicates whether the item in the drawer should be highlighted
        return true;
    }

    // This method is called whenever the Back button is pressed
    @Override
    public void onBackPressed() {
        // (p. 614)
        // Behavior dependent on whether drawer is open or closed
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
