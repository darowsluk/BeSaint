package org.sds.besaint;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment {

    private DataProvider mDataProvider;
    private DataJourney mJourney;
    private int currentDay = 0, maxDays = 0;
    public CenterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDataProvider = new DataProvider();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_center, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        String dayOfMax;
        boolean showWidgets = false;
        View view = getView();
        if (view != null) {
            // Get data from DB
            int currentJourneyUID = mDataProvider.getSharedCurrentJourneyUID(getContext());
            mJourney = mDataProvider.getDataJourney(getActivity(), currentJourneyUID);
            if (mJourney == null || currentJourneyUID == 0) {
                dayOfMax = getResources().getString(R.string.res_txtDayResolutionDefault);
                currentDay = 0;
                maxDays = 0;
            }
            else {
                currentDay = mDataProvider.getSharedCurrentDay(getContext());
                maxDays = mJourney.getDays();
                dayOfMax = getResources().getString(R.string.res_txtDayResolution) +
                        " " + Integer.toString(currentDay) + "/" + mJourney.getDays();
                showWidgets = true;
            }

            TextView textView = (TextView) view.findViewById(R.id.id_txtDayResolution);
            textView.setText(dayOfMax);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.id_checkboxDayResolution);
            Switch s = (Switch) view.findViewById(R.id.id_switchFinishedDay);
            Button button = (Button) view.findViewById(R.id.id_btnFinish);

            if (showWidgets) {
                checkBox.setVisibility(View.VISIBLE);
                s.setVisibility(View.VISIBLE);
                if (s.isChecked()) {
                    button.setVisibility(View.VISIBLE);
                }
                else {
                    button.setVisibility(View.INVISIBLE);
                }
            }
            else {
                checkBox.setVisibility(View.INVISIBLE);
                s.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
            s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // do something, the isChecked will be
                    // true if the switch is in the On position
                    Button mFinishButton;
                    View view = getView();
                    if (view != null) {
                        mFinishButton = (Button) view.findViewById(R.id.id_btnFinish);
                        if (isChecked) {
                            // show the finish button
                            mFinishButton.setVisibility(getView().VISIBLE);
                        } else {
                            mFinishButton.setVisibility(getView().INVISIBLE);
                        }
                    }
                }
            });
            button.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    // Change currentDay in DB to the next day if max day is not reached and update UI
                    Toast.makeText(getActivity(), getResources().getString(R.string.res_txtToastFinishedDay),
                            Toast.LENGTH_LONG).show();

                    // Get the value of the finished checkBox
                    CheckBox checkBox = (CheckBox) getActivity().findViewById(R.id.id_checkboxDayResolution);
                    boolean taskDone = checkBox.isChecked();

                    if (currentDay <= maxDays) {
                        mDataProvider.setSharedCurrentDay(getContext(), currentDay + 1 );
                    }
                    else {
                        // end the current journey
                        mDataProvider.setSharedCurrentDay(getContext(), DataConstants.DEFAULT_INT);
                        mDataProvider.setSharedCurrentJourneyUID(getContext(), DataConstants.DEFAULT_INT);
                    }

                    // Update shared data
                    mDataProvider.setSharedProgressDone(getContext(), taskDone);

                    // Update UI by starting a new activity and returning from it
                    Intent intent = new Intent(getContext(), TransitionActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
