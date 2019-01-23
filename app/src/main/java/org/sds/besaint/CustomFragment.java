package org.sds.besaint;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomFragment extends Fragment {
    // MY CODE:
    private int[] mImageIds = new int[] {R.drawable.res_img_jordan200r, R.drawable.res_img_jpii200r, R.drawable.res_img_teresa200r, R.drawable.res_img_kalkuta200r, R.drawable.res_img_pio200r};


    public static Fragment newInstance(Activity context, int position, float scale) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putFloat("scale", scale);
        return Fragment.instantiate(context, CustomFragment.class.getName(), bundle);
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
}
