package com.hswinratetracker.dialogfragments;


import android.app.AlertDialog;
import android.app.Dialog;
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

public class AddDeckDialog extends DialogFragment {


    private ResultListener mOnResultListener;
    Deck.HeroClasses chosenHero;
    @Bind(R.id.btnAdd)
    Button btnAdd;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_adddeck, null, false);
        ButterKnife.bind(this, view);

        Random rn = new Random();
        //random 1 - 9 inclusively
        int random = rn.nextInt(9 - 1 + 1) + 1;
        Deck.HeroClasses randomHeroClass = Deck.HeroClasses.values()[random - 1];
        //random default image
        imgChooseClass.setImageResource(randomHeroClass.getIconId());

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
        btnAdd.setOnClickListener(new View.OnClickListener() {
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
                Deck addedDeck = db.addDeck(edtDeckName.getText().toString(), chosenHero, nbpWins.getValue(), nbpLoses.getValue());
                db.close();
                mOnResultListener.DeckAdded(addedDeck);
                getDialog().dismiss();

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

    public void setOnResultListener(ResultListener resultListener) {
        mOnResultListener = resultListener;
    }


    public interface ResultListener {
        void DeckAdded(Deck deck);
    }


}