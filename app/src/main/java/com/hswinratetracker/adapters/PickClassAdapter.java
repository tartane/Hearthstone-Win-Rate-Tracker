package com.hswinratetracker.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hswinratetracker.CropImageView;
import com.hswinratetracker.models.Deck;

import java.util.ArrayList;

import hswinratetracker.com.hswinratetracker.R;

public class PickClassAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<Deck.HeroClasses> values;
    private ViewHolder holder;

    public PickClassAdapter(Context context, ArrayList<Deck.HeroClasses> values) {
        this.context = context;
        this.values = values;
    }

    public void update() {
        notifyDataSetChanged();
    }

    public void add(Deck.HeroClasses heroClass) {
        values.add(heroClass);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vi = convertView;
        Deck.HeroClasses heroClass = values.get(position);
        if (convertView == null) {
            vi = inflater.inflate(R.layout.row_pickclass, null);
            holder = new ViewHolder();
            holder.txtClassName = (TextView) vi.findViewById(R.id.txtClassName);
            holder.imgClassBanner = (CropImageView) vi.findViewById(R.id.imgClassBanner);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        holder.txtClassName.setText(heroClass.toString());
        holder.imgClassBanner.setOffset(1, 0); //right crop

        switch(heroClass)
        {
            case DRUID:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_druid_banner);
                break;
            case HUNTER:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_hunter_banner);
                break;
            case MAGE:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_mage_banner);
                break;
            case PALADIN:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_paladin_banner);
                break;
            case PRIEST:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_priest_banner);
                break;
            case ROGUE:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_rogue_banner);
                break;
            case SHAMAN:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_shaman_banner);
                break;
            case WARLOCK:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_warlock_banner);
                break;
            case WARRIOR:
                holder.imgClassBanner.setImageResource(R.drawable.ic_class_warrior_banner);
                break;
        }

        return vi;
    }

    static class ViewHolder {
        TextView txtClassName;
        CropImageView imgClassBanner;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Deck.HeroClasses getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}