package com.hswinratetracker.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hswinratetracker.App;
import com.hswinratetracker.CropImageView;
import com.hswinratetracker.models.Deck;
import com.hswinratetracker.widgets.FontText;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hswinratetracker.com.hswinratetracker.R;

public class DeckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private DeckAdapter.OnItemClickListener mItemClickListener;
    private ArrayList<Deck> mItems = new ArrayList<>();

    public interface OnItemClickListener {
        public void onItemClick(View v, Deck item, int position);
    }

    public DeckAdapter(Context context, ArrayList<Deck> items) {
        setItems(items);
    }

    public void setOnItemClickListener(DeckAdapter.OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_deck, parent, false);
        return new DeckAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ViewHolder deckViewHolder = (ViewHolder) viewHolder;
        final Deck deck = getItem(position);

        deckViewHolder.imgClassBanner.setOffset(1, 0); //right crop
        deckViewHolder.imgClassBanner.setImageResource(deck.getHeroClass().getBannerId());
        deckViewHolder.viewClassColor.setBackgroundColor(deck.getHeroClass().getClassColor());

        deckViewHolder.txtDeckName.setText(deck.getName());
        deckViewHolder.txtWins.setText(String.valueOf(deck.getWins()));
        deckViewHolder.txtLoses.setText(String.valueOf(deck.getLoses()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Deck getItem(int position) {
        if (position < 0 || mItems.size() <= position) return null;
        return mItems.get(position);
    }

    public void setItems(ArrayList<Deck> items) {
        // Add new items, if available
        if (items != null)
            mItems.addAll(items);

        notifyDataSetChanged();
    }

    public void addItem(Deck deck)
    {
        if(deck != null)
            mItems.add(deck);

        notifyDataSetChanged();
    }

    public void clearItems() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View itemView;

        @Bind(R.id.imgClassBanner)
        CropImageView imgClassBanner;

        @Bind(R.id.txtDeckName)
        FontText txtDeckName;

        @Bind(R.id.txtWins)
        TextView txtWins;

        @Bind(R.id.txtLoses)
        TextView txtLoses;

        @Bind(R.id.viewClassColor)
        View viewClassColor;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(ViewHolder.this, itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                int position = getPosition();
                Deck item = getItem(position);
                mItemClickListener.onItemClick(view, item, position);
            }
        }

    }
}
