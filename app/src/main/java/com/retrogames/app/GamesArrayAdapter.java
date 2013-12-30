package com.retrogames.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tomasz on 30.12.13.
 */
public class GamesArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public GamesArrayAdapter(Context context, int textViewResourceId,
                             List<String> objects) {
        super(context, textViewResourceId, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = super.getView(position, convertView, parent);



        return listItem;
    }
}
