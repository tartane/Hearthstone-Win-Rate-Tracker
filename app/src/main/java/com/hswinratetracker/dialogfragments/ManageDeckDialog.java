package com.hswinratetracker.dialogfragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hswinratetracker.models.Deck;
import com.hswinratetracker.sqlite.SQLiteHelper;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import hswinratetracker.com.hswinratetracker.R;

public class ManageDeckDialog extends DialogFragment {


    private ResultListener mOnResultListener;
    private Mode mMode;
    private Deck.HeroClasses chosenHero;
    private Deck deckToEdit; //null if in add mode.
    public static final String EXTRA_MODE = "extra_mode";
    public static final String EXTRA_DECKEDIT = "extra_deckedit";
    public enum Mode {
        ADD, EDIT
    }
    @Bind(R.id.btnSave)
    Button btnSave;

    @Bind(R.id.btnDelete)
    Button btnDelete;

    @Bind(R.id.btnCancel)
    Button btnCancel;

    @Bind(R.id.numberPickerWins)
    NumberPicker nbpWins;

    @Bind(R.id.numberPickerLoses)
    NumberPicker nbpLoses;

    @Bind(R.id.edtDeckName)
    EditText edtDeckName;

    @Bind(R.id.imgChooseClass)
    ImageView imgChooseClass;

    @Bind(R.id.layChooseClass)
    RelativeLayout layChooseClass;

    @Bind(R.id.overlay)
    View viewOverlay;

    @Bind(R.id.txtChooseHero)
    TextView txtChooseHero;

    public static ManageDeckDialog newInstance(Mode mode, Deck deckToEdit) {
        ManageDeckDialog dialogFragment = new ManageDeckDialog();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MODE, mode);
        args.putParcelable(EXTRA_DECKEDIT, deckToEdit);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mMode = (Mode) getArguments().getSerializable(EXTRA_MODE);
        deckToEdit = getArguments().getParcelable(EXTRA_DECKEDIT);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_managedeck, null, false);
        ButterKnife.bind(this, view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(getString(R.string.add_deck));
        builder.setCancelable(true);

        nbpWins.setMinValue(0);
        nbpLoses.setMinValue(0);

        nbpWins.setMaxValue(9999);
        nbpLoses.setMaxValue(9999);

        nbpWins.setWrapSelectorWheel(false);
        nbpLoses.setWrapSelectorWheel(false);

        if(mMode == Mode.EDIT)
        {
            btnDelete.setVisibility(View.VISIBLE);
            viewOverlay.setVisibility(View.GONE);
            txtChooseHero.setVisibility(View.GONE);

            chosenHero = deckToEdit.getHeroClass();
            edtDeckName.setText(deckToEdit.getName());
            imgChooseClass.setImageResource(chosenHero.getIconId());
            nbpWins.setValue(deckToEdit.getWins());
            nbpLoses.setValue(deckToEdit.getLoses());
        }
        else
        {
            Random rn = new Random();
            //random 1 - 9 inclusively
            int random = rn.nextInt(9 - 1 + 1) + 1;
            Deck.HeroClasses randomHeroClass = Deck.HeroClasses.values()[random - 1];
            //random default image
            imgChooseClass.setImageResource(randomHeroClass.getIconId());
        }


        layChooseClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassPickerDialog dialogFragment = new ClassPickerDialog();
                dialogFragment.setOnResultListener(new ClassPickerDialog.ResultListener() {
                    @Override
                    public void heroSelected(Deck.HeroClasses heroClass) {
                        imgChooseClass.setImageResource(heroClass.getIconId());
                        viewOverlay.setVisibility(View.GONE);
                        txtChooseHero.setVisibility(View.GONE);
                        chosenHero = heroClass;
                    }
                });
                dialogFragment.show(getFragmentManager(), "pref_fragment");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtDeckName.getText().toString().trim().equals(""))
                {
                    edtDeckName.setError(getString(R.string.deck_name_required));
                    return;
                }

                if(chosenHero == null)
                {
                    Toast.makeText(getContext(), getString(R.string.must_choose_hero), Toast.LENGTH_LONG).show();
                    return;
                }

                SQLiteHelper db = new SQLiteHelper(getActivity());
                if(mMode == Mode.ADD) {
                    Deck addedDeck = db.addDeck(edtDeckName.getText().toString(), chosenHero, nbpWins.getValue(), nbpLoses.getValue());

                    mOnResultListener.DeckAdded(addedDeck);
                }
                else if(mMode == Mode.EDIT)
                {
                    deckToEdit.setHeroClass(chosenHero);
                    deckToEdit.setName(edtDeckName.getText().toString());
                    deckToEdit.setWins(nbpWins.getValue());
                    deckToEdit.setLoses(nbpLoses.getValue());
                    db.updateDeck(deckToEdit);
                    mOnResultListener.DeckUpdated(deckToEdit);
                }

                db.close();
                getDialog().dismiss();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                .setMessage(getString(R.string.are_you_sure))
                .setPositiveButton(getString(R.string.yes), confirmDialogClickListener)
                .setNegativeButton(getString(R.string.no), confirmDialogClickListener)
                        .show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return builder.create();
    }

    DialogInterface.OnClickListener confirmDialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    SQLiteHelper db = new SQLiteHelper(getActivity());
                    db.removeDeck(deckToEdit.getDeckId());
                    db.close();
                    mOnResultListener.DeckDeleted(deckToEdit);
                    getDialog().dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
            dialog.dismiss();
        }
    };

    public void setOnResultListener(ResultListener resultListener) {
        mOnResultListener = resultListener;
    }


    public interface ResultListener {
        void DeckAdded(Deck deck);
        void DeckUpdated(Deck deck);
        void DeckDeleted(Deck deck);
    }


}