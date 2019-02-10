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


    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
//            MarkdownView markdownView = (MarkdownView) view.findViewById(R.id.id_leftFragment_markdownView);
//            // Find day in the DB
//            String txtDefault = getResources().getString(R.string.res_txtInspirationDefault);
//            markdownView.loadMarkdown(txtDefault);

            WebView webview = (WebView) view.findViewById(R.id.id_htmlInspirationView);
            webview.setBackgroundColor(0); // This line makes the background transparent
            String htmlText = getResources().getString(R.string.res_txtInspirationDefault);;
            webview.loadData(htmlText , "text/html; charset=UTF-8", null);
        }
    }
}
