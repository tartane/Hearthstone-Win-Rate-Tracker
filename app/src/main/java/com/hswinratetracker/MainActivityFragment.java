package com.hswinratetracker;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.hswinratetracker.adapters.DeckAdapter;
import com.hswinratetracker.dialogfragments.ClassPickerDialog;
import com.hswinratetracker.models.Deck;
import com.hswinratetracker.sqlite.SQLiteHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hswinratetracker.com.hswinratetracker.R;


public class MainActivityFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.fabAdd)
    FloatingActionButton fabAdd;

    @Bind(R.id.txtNoDeck)
    TextView txtNoDeck;

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
        fabAdd.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
            //TODO

        }
    };

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.fabAdd:
                final Dialog dialog = new Dialog(getActivity());
                dialog.setTitle(getString(R.string.add_deck));
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_add_deck);


                Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                final NumberPicker nbpWins = (NumberPicker) dialog.findViewById(R.id.numberPickerWins);
                final NumberPicker nbpLoses = (NumberPicker) dialog.findViewById(R.id.numberPickerLoses);
                final EditText edtDeckName = (EditText) dialog.findViewById(R.id.edtDeckName);
                final ImageView imgChooseClass = (ImageView)dialog.findViewById(R.id.imgChooseClass);


                nbpWins.setMinValue(0);
                nbpLoses.setMinValue(0);

                //TODO What does this do?
                nbpWins.setWrapSelectorWheel(true);
                nbpLoses.setWrapSelectorWheel(true);

                imgChooseClass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClassPickerDialog dialogFragment = new ClassPickerDialog();
                        dialogFragment.setOnResultListener(new ClassPickerDialog.ResultListener() {
                            @Override
                            public void heroSelected(Deck.HeroClasses heroClass) {

                            }
                        });
                        dialogFragment.show(getFragmentManager(), "pref_fragment");
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteHelper db = new SQLiteHelper(getActivity());
                        //Deck addedDeck = db.addDeck(edtDeckName.getText().toString(), (Deck.HeroClasses)imgChooseClass.getTag(), nbpWins.getValue(), nbpLoses.getValue());
                        Deck addedDeck = db.addDeck(edtDeckName.getText().toString(), Deck.HeroClasses.MAGE, nbpWins.getValue(), nbpLoses.getValue());
                        db.close();
                        deckAdapter.addItem(addedDeck);

                        recyclerView.setVisibility(View.VISIBLE);
                        txtNoDeck.setVisibility(View.GONE);
                        dialog.dismiss();

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            break;
        }
    }
}
