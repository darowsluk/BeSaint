package org.sds.besaint;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import us.feras.mdv.MarkdownView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment {

    private DataProvider mDataProvider;
    private DataJourney mJourney;

    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataProvider = new DataProvider();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        String htmlText;
        View view = getView();
        if (view != null) {
            // Get data from DB
            mJourney = mDataProvider.getCurrentJourney(getActivity());
            if (mJourney == null) {
                htmlText = getResources().getString(R.string.res_txtInspirationDefault);
            }
            else {
                DataDay dataDay = mDataProvider.getCurrentDay(getActivity());
                if (dataDay == null) {
                    // Day not implemented in DB, so revert to default
                    htmlText = getResources().getString(R.string.res_txtInspirationDefault);
                }
                else {
                    htmlText = dataDay.getInspiration();
                }
            }

            WebView webview = (WebView) view.findViewById(R.id.id_htmlInspirationView);
            webview.setBackgroundColor(0); // This line makes the background transparent
            webview.loadData(htmlText , "text/html; charset=UTF-8", null);
        }
    }
}
