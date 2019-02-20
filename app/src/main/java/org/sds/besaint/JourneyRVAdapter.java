package org.sds.besaint;

import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class JourneyRVAdapter extends RecyclerView.Adapter<JourneyRVAdapter.ViewHolder> {

    // Variables used in the card view for each journey
    private byte[][] jImageIds;
    private String[] jTitles, jLevels, jAuthors, jDays;

    // Listener interface to respond to user clicks
    private Listener mListener;
    interface Listener {
        void onClick (int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define the view to be used for each data item in the recycler view

        private CardView jCardView; // Our recycler will display CardViews

        // Constructor
        public ViewHolder (CardView v) {
            super(v);
            jCardView = v;
        }
    }

    // Constructor
    public JourneyRVAdapter() {
        jImageIds = null;
        jTitles = null;
        jLevels = null;
        jAuthors = null;
        jDays = null;
    }
    public JourneyRVAdapter(byte[][] imageIds, String[] titles, String[] levels, String[] authors, String[] days) {
        jImageIds = imageIds;
        jTitles = titles;
        jLevels = levels;
        jAuthors = authors;
        jDays = days;
    }

    // Setter
    public void updateData(byte[][] imageIds, String[] titles, String[] levels, String[] authors, String[] days) {
        jImageIds = imageIds;
        jTitles = titles;
        jLevels = levels;
        jAuthors = authors;
        jDays = days;
    }

    @Override
    public int getItemCount() {
        return jTitles.length;  // The length of the jTitles array equals the number of data items in the recycler view
    }

    // Activities and fragments will use this method to register as a mListener
    public void setmListener(Listener mListener) {
        this.mListener = mListener;
    }

    @Override
    // This method gets called when the recycler view needs to create a view holder
    public JourneyRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Turn the layout into a CardView
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_journey, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    // This method gets called when the recycler view wants to use (or reuse) a view holder for a new piece of data
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.jCardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.id_cardImageView);
        if (jImageIds[position] == null) {
            // provide default image
            imageView.setImageResource(R.drawable.res_img_empty80);
        }
        else {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(jImageIds[position], 0, jImageIds[position].length));
        }
        imageView.setContentDescription(jTitles[position]);
        TextView textTitle = (TextView) cardView.findViewById(R.id.id_cardJourneyTitle);
        textTitle.setText(jTitles[position]);
        TextView textLevel = (TextView) cardView.findViewById(R.id.id_cardJourneyLevel);
        textLevel.setText(jLevels[position]);
        TextView textAuthor = (TextView) cardView.findViewById(R.id.id_cardJourneyAuthor);
        textAuthor.setText(jAuthors[position]);
        TextView textDays = (TextView) cardView.findViewById(R.id.id_cardJourneyDays);
        textDays.setText(jDays[position]);

        // Add mListener to the CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
            }
        });
    }
}
