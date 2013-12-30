package com.retrogames.app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tomasz on 30.12.13.
 */
public class GamesArrayAdapter extends ArrayAdapter<String> {

    List<String> objects = new ArrayList<String>();

    public GamesArrayAdapter(Context context, List<String> objects) {
        super(context, R.layout.game_item_list, R.id.game_name, objects);
        objects = this.objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);

        TextView textView = (TextView)itemView.findViewById(R.id.game_name);
        //textView.setText(objects.get(position));
        Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), ChooseGameActivity.FONT);
        textView.setTypeface(font);

        return itemView;
    }
}
