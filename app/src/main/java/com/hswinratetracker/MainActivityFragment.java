package com.hswinratetracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hswinratetracker.adapters.DeckAdapter;
import com.hswinratetracker.decorations.DividerItemDecoration;
import com.hswinratetracker.dialogfragments.AddDeckDialog;
import com.hswinratetracker.dialogfragments.ClassPickerDialog;
import com.hswinratetracker.dialogfragments.WinLoseDialog;
import com.hswinratetracker.models.Deck;
import com.hswinratetracker.sqlite.SQLiteHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hswinratetracker.com.hswinratetracker.R;


public class MainActivityFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.layAddDeck)
    RelativeLayout layAddDeck;

    @Bind(R.id.txtNoDeck)
    TextView txtNoDeck;

    final private String WIN_LOSE_TAG = "WINLOSE_FRAGMENT";
    final private String ADDDECK_TAG = "ADDDECK_FRAGMENT";

    private DeckAdapter deckAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        layAddDeck.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        SQLiteHelper db = new SQLiteHelper(getActivity());

        //Get all saved decks
        ArrayList<Deck> decks = db.getDecks();
        db.close();

        if(decks.size() < 1) {
            recyclerView.setVisibility(View.GONE);
            txtNoDeck.setVisibility(View.VISIBLE);
        }
        else
            txtNoDeck.setVisibility(View.GONE);

        deckAdapter = new DeckAdapter(getActivity(), decks);
        deckAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerView.setAdapter(deckAdapter);

    }

    private DeckAdapter.OnItemClickListener mOnItemClickListener = new DeckAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(final View view, final Deck deck, final int position) {
            WinLoseDialog dialogFragment = WinLoseDialog.newInstance(deck);
            dialogFragment.setOnResultListener(new WinLoseDialog.ResultListener() {
                @Override
                public void ResultSelected(Deck deck, boolean win) {
                    SQLiteHelper db = new SQLiteHelper(getActivity());
                    if(win)
                        // yay
                        db.addWin(deck.getDeckId());
                    else
                        // :(
                        db.addLose(deck.getDeckId());

                    Deck updatedDeck = db.getDeck(deck.getDeckId());
                    
                    if(updatedDeck != null)
                        deckAdapter.updateItem(updatedDeck);
                    else
                        //lolwhat
                        Toast.makeText(getActivity(), getString(R.string.error_updating_deck), Toast.LENGTH_LONG).show();
                }
            });
            dialogFragment.show(getFragmentManager(), WIN_LOSE_TAG);

        }
    };

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.layAddDeck:
                AddDeckDialog dialogFragment = new AddDeckDialog();
                dialogFragment.setOnResultListener(new AddDeckDialog.ResultListener() {

                    @Override
                    public void DeckAdded(Deck deck) {
                        deckAdapter.addItem(deck);

                        recyclerView.setVisibility(View.VISIBLE);
                        txtNoDeck.setVisibility(View.GONE);
                    }
                });
                dialogFragment.show(getFragmentManager(), ADDDECK_TAG);
            break;
        }
    }
}
