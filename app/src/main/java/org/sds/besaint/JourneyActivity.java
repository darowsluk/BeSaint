package org.sds.besaint;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;

import java.lang.reflect.Field;

public class JourneyActivity extends AppCompatActivity {

    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        createData();
        ExpandableListView listView = findViewById(R.id.id_listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        listView.setAdapter(adapter);
    }

    public void createData() {
        Drawable drawable = getResources().getDrawable(R.drawable.res_img_jordan100round);
        int h = drawable.getIntrinsicHeight();
        int w = drawable.getIntrinsicWidth();
        drawable.setBounds(0,0,w,h);
        Group group = new Group("Venerable Father\nFrancis Jordan", drawable);
        group.children.add("Four pillars of holiness");
        group.children.add("Exhortations and admonitions");
        group.children.add("With the spiritual diary");
        group.children.add("Salvatorian Constitutions");
        groups.append(0, group);

        drawable = getResources().getDrawable(R.drawable.res_img_jpii100round);
        drawable.setBounds(0,0,w,h);
        group = new Group("Saint John Paul II", drawable);
        group.children.add("Love and responsibility");
        group.children.add("Faith and reason");
        groups.append(1, group);

        drawable = getResources().getDrawable(R.drawable.res_img_teresa100round);
        drawable.setBounds(0,0,w,h);
        group = new Group("Saint Therese of Lisieux",drawable);
        group.children.add("The \"little way\" of spiritual childhood");
        group.children.add("Contemplating the face of Jesus");
        groups.append(2, group);

    }
}
