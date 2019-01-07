package org.sds.besaint;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Group {

    public String string;
    public Drawable resImage;
    public final List<String> children = new ArrayList<String>();
    public Group(String string, Drawable resImage) {
        this.string = string;
        this.resImage = resImage;
    }
}
