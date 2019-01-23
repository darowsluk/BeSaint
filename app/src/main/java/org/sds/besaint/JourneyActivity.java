package org.sds.besaint;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class JourneyActivity extends AppCompatActivity {

    public final static int PAGES = 5;
    public final static int VISIBLE_PAGES = 3; // always uneven (1,3,5,...)
    public final static int FIRST_PAGE = 1  ;

    public CustomPagerAdapter mAdapter;
    public ViewPager mViewPager;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        createData();

//        ExpandableListView listView = findViewById(R.id.id_listView);
//        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
//                groups);
//        listView.setAdapter(adapter);

        // START Carousel code
        mViewPager = findViewById(R.id.viewPager);
        mAdapter = new CustomPagerAdapter(this, this.getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(false, mAdapter);

        // Set current carousel_item to the middle page so we can fling to both
        // directions left and right
        mViewPager.setCurrentItem(FIRST_PAGE);

        // Necessary or the mViewPager will only have one extra page to show
        // make this at least however many pages you can see
        mViewPager.setOffscreenPageLimit(VISIBLE_PAGES);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        mViewPager.setPageMargin(-650); // TODO How to calculate depending on the screen?

        // Set Listener to make the active image on top
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                // Update title below the carousel and journey titles
                TextView textView = (TextView)findViewById(R.id.res_txtTitle);
                String[] mJourneys;
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.res_journeyLinearLayout);
                if(linearLayout.getChildCount() > 0)
                    linearLayout.removeAllViews();


                switch (position) {
                    case 0:
                        textView.setText("Ojciec Franciszek Jordan");
                        mJourneys = new String[]{"Dziennnik Duchowny", "Zachęty i upomnienia", "Konstytucje"};
                        break;
                    case 1:
                        textView.setText("Jan Paweł II");
                        mJourneys = new String[]{"Wiara i rozum", "Miłość i odpowiedzialność"};
                        break;
                    case 2:
                        textView.setText("Tereska");
                        mJourneys = new String[]{"Miłość", "Mała droga"};
                        break;
                    case 3:
                        textView.setText("Matka Teresa z Kalkuty");
                        mJourneys = new String[]{"Do kapłanów"};
                        break;
                    case 4:
                        textView.setText("Ojciec Pio");
                        mJourneys = new String[]{"O spowiedzi", "Pokora", "Walki duchowe"};
                        break;
                    default:
                        textView.setText("Nieznany święty");
                        mJourneys = new String[]{""};
                }

                for (String s: mJourneys) {
                    TextView journeyTitle = new TextView(JourneyActivity.this);
                    journeyTitle.setText(s);
                    journeyTitle.setTextColor(Color.BLACK);
                    journeyTitle.setTextSize(20);
                    linearLayout.addView(journeyTitle);
                }
                // Animate list of journeys below the saint name
                expand(linearLayout);

                // Make the active image on top
                int count = 0;  // counter for loop
                if (position == 0) {
                    count = 0; // show only the first image
                }
                else if (position >= PAGES) {
                    // Throw exception: out of bounds
                }
                else {
                    count = position - (VISIBLE_PAGES/2);
                }

                do {
                    Fragment fragment = (Fragment) mAdapter.instantiateItem(mViewPager, count);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                            && fragment.getView() != null) {

                        //If it's current center position, increase view's elevation. Otherwise, we reduce elevation
                        if (count == position) {
                            fragment.getView().setElevation(8.0f);
                        }
                        else {
                            fragment.getView().setElevation(0.0f);
                        }
                    }
                    count++;
                } while ((count <= (position + (VISIBLE_PAGES/2))) && (count < PAGES));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        // END Carousel code
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
    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
