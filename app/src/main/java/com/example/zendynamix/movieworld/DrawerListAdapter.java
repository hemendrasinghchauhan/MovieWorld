package com.example.zendynamix.movieworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zendynamix on 6/16/2016.
 */
public class DrawerListAdapter extends BaseAdapter {
    private static final String LOG_TAG= DrawerListAdapter.class.getSimpleName();

    Context context;
    ArrayList<DrawerItemData> drawerItemData;

    public DrawerListAdapter(Context context, ArrayList<DrawerItemData> drawerItemData) {
        this.context=context;
        this.drawerItemData = drawerItemData;
    }

        @Override
        public int getCount() {

            return drawerItemData.size();
        }

        @Override
        public Object getItem(int position) {

            return drawerItemData.get(position);

        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.drawer_item,null);
            }
            else {
                view = convertView;
            }

            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
            ImageView iconView = (ImageView) view.findViewById(R.id.icon);

            titleView.setText( drawerItemData.get(position).title );
            subtitleView.setText( drawerItemData.get(position).subtitle);
            iconView.setImageResource(drawerItemData.get(position).icon);



            return view;
        }
    }