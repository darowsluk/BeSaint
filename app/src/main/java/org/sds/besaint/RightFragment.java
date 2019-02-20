package org.sds.besaint;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {

    private DataProvider mDataProvider;

    public RightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataProvider = new DataProvider();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_right, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        int totalDays, finishedDays, intPerc;
        String textHeader, textPercentage;
        Context context = getContext();
        View view = getView();

        if (view != null) {
            // Get data from DB
            totalDays = mDataProvider.getSharedProgressTotalDays(context);
            finishedDays = mDataProvider.getSharedProgressFinishedDays(context);

            if (totalDays > 0) {
                textHeader = getResources().getString(R.string.res_txtProgressDays)
                        + " " + Integer.toString(finishedDays) + "/"
                        + Integer.toString(totalDays);
                float perc = ((float)finishedDays / (float)totalDays) * 100;
                intPerc = (int) perc;
                textPercentage = Integer.toString(intPerc) + "%";
            }
            else {
                textHeader = "0"; // No days finished yet
                textPercentage = "0%";
                intPerc = 100;
            }

            // Update text views
            TextView headerView = (TextView) view.findViewById(R.id.id_txtProgressDays);
            headerView.setText(textHeader);
            TextView percentageView = (TextView) view.findViewById(R.id.id_txtProgressPercentage);
            percentageView.setText(textPercentage);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.id_progressRatingBar);
            progressBar.setProgress(intPerc);
        }
    }
}
