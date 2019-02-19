package org.sds.besaint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JourneyRVFragment.Listener} interface
 * to handle interaction events.
 */
public class JourneyRVFragment extends Fragment {

    private int mPosition;
    private int[] mJourneyUIDs;
    private JourneyRVAdapter mAdapter;

    public JourneyRVFragment() {
        // Required empty public constructor
        mPosition = 1; // TODO: fix this properly
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView jRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_journey, container, false);
        mAdapter = new JourneyRVAdapter();
        updateFragmentData(mPosition);
        jRecycler.setAdapter(mAdapter);

        // Specify which layout to use
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        jRecycler.setLayoutManager(layoutManager);

        // Implement the Listener onClick() method and pass the id of the journey the user chose
        mAdapter.setmListener(new JourneyRVAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent (getActivity(), JourneyDetailsActivity.class);
                //Toast toast = Toast.makeText(getActivity(), "Showing JourneyDetailsActivity", Toast.LENGTH_SHORT);
                //toast.show();
                intent.putExtra(JourneyDetailsActivity.EXTRA_JOURNEY_UID, translatePositionToJourneyUID(position));
                getActivity().startActivity(intent);
            }
        });
        return jRecycler;
    }

    public void updatePosition(int position) {
        mPosition = position;
        updateFragmentData(position);
    }

    public int translatePositionToJourneyUID(int position) {
        return mJourneyUIDs[position];
    }

    private void updateFragmentData(int position) {
        // Populate RecycleView data
        SparseArray<DataJourney> mJourneys;
        DataProvider dataProvider = new DataProvider();
        mJourneys = dataProvider.getDataJourneys(getActivity(), position);
        int arraySize = mJourneys.size();
        String[] titles = new String[arraySize];
        String[] levels = new String[arraySize];
        String[] authors = new String[arraySize];
        String[] days = new String[arraySize];
        mJourneyUIDs = new int[arraySize];
        byte[][] imageIds = new byte[arraySize][];
        for(int i = 0; i < arraySize; i++) {
            DataJourney journey = mJourneys.get(mJourneys.keyAt(i));
            mJourneyUIDs[i] = journey.getJourneyUID();
            imageIds[i] = journey.getImage();
            titles[i] = journey.getTitle();
            levels[i] = "Level " + Integer.toString(journey.getLevel());
            authors[i] = journey.getAuthor();
            days[i] = Integer.toString(journey.getDays()) + " days";
        }
        mAdapter.updateData(imageIds, titles, levels, authors, days);
        mAdapter.notifyDataSetChanged();
    }

    interface Listener {
        void onClick(int position);
    }
}
