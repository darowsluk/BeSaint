package org.sds.besaint;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.TextView;

public class JourneyActivity extends AppCompatActivity {

    public final static int PAGES = 5;
    public final static int VISIBLE_PAGES = 3; // always uneven (1,3,5,...)
    public final static int FIRST_PAGE = 1;

    public JourneyVPAdapter mAdapter;
    public ViewPager mViewPager;

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        // START RecyclerView code
        // TODO: consider using getFrameManager() - a native Android class (p. 362)
        JourneyRVFragment frag = (JourneyRVFragment) getSupportFragmentManager().findFragmentById(R.id.id_journeyFragment);
        frag.updatePosition(FIRST_PAGE+1);

        // START Carousel code
        mViewPager = findViewById(R.id.viewPager);
        mAdapter = new JourneyVPAdapter(this, this.getSupportFragmentManager());
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

                switch (position) {
                    case 0:
                        textView.setText("Ojciec Franciszek Jordan");
                        break;
                    case 1:
                        textView.setText("Jan Paweł II");
                       break;
                    case 2:
                        textView.setText("Tereska");
                        break;
                    case 3:
                        textView.setText("Matka Teresa z Kalkuty");
                        break;
                    case 4:
                        textView.setText("Ojciec Pio");
                        break;
                    default:
                        textView.setText("Nieznany święty");
                }

                // Update JourneyRVFragment
                // TODO: consider using getFrameManager() - a native Android class (p. 362)
                JourneyRVFragment frag = (JourneyRVFragment) getSupportFragmentManager().findFragmentById(R.id.id_journeyFragment);
                frag.updatePosition(position+1);

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
}
