package org.sds.besaint;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class JourneyVPFragment extends Fragment {
    // MY CODE:
    private int mCardPosition;
    private int[] mImageIds = new int[] {R.drawable.res_img_jordan200r, R.drawable.res_img_jpii200r, R.drawable.res_img_teresa200r, R.drawable.res_img_kalkuta200r, R.drawable.res_img_pio200r};


    public static Fragment newInstance(Activity context, int position, float scale) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putFloat("scale", scale);
        return Fragment.instantiate(context, JourneyVPFragment.class.getName(), bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout linearLayout = (LinearLayout)
                inflater.inflate(R.layout.carousel_item, container, false);

        int position = this.getArguments().getInt("position");

        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.item_content);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mImageIds[position]);

        CustomLinearLayout root = (CustomLinearLayout) linearLayout.findViewById(R.id.item_root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        return linearLayout;
    }

    public void updateCardPosition(int position) {
        mCardPosition = position;
        updateCardFragmentData(position);
    }

    private void updateCardFragmentData(int position) {
        // Populate CardView data
        SparseArray<DataJourney> mJourneys;
        DataProvider dataProvider = new DataProvider();
        mJourneys = dataProvider.getDataJourneys(getActivity(), position);
        int arraySize = mJourneys.size();
        String[] titles = new String[arraySize];
        String[] levels = new String[arraySize];
        String[] authors = new String[arraySize];
        String[] days = new String[arraySize];
        byte[][] imageIds = new byte[arraySize][];
        for(int i = 0; i < arraySize; i++) {
            DataJourney journey = mJourneys.get(mJourneys.keyAt(i));
            imageIds[i] = journey.getImage();
            titles[i] = journey.getTitle();
            levels[i] = "Level " + Integer.toString(journey.getLevel());
            authors[i] = journey.getAuthor();
            days[i] = Integer.toString(journey.getDays()) + " days";
        }
        //mAdapter.updateData(imageIds, titles, levels, authors, days);
        //mAdapter.notifyDataSetChanged();
    }
}
