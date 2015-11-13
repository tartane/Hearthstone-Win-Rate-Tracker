package com.hswinratetracker.dialogfragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hswinratetracker.adapters.PickClassAdapter;
import com.hswinratetracker.models.Deck;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hswinratetracker.com.hswinratetracker.R;

public class ClassPickerDialog extends DialogFragment implements AdapterView.OnItemClickListener {


    private ResultListener mOnResultListener;

    @Bind(R.id.listViewPickClass)
    ListView listViewPickClass;

    private PickClassAdapter adapter;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_pickclass, null, false);
        ButterKnife.bind(this, view);
        ArrayList heroClasses = new ArrayList<Deck.HeroClasses>();
        heroClasses.add(Deck.HeroClasses.DRUID);
        heroClasses.add(Deck.HeroClasses.HUNTER);
        heroClasses.add(Deck.HeroClasses.MAGE);
        heroClasses.add(Deck.HeroClasses.PALADIN);
        heroClasses.add(Deck.HeroClasses.PRIEST);
        heroClasses.add(Deck.HeroClasses.ROGUE);
        heroClasses.add(Deck.HeroClasses.SHAMAN);
        heroClasses.add(Deck.HeroClasses.WARLOCK);
        heroClasses.add(Deck.HeroClasses.WARRIOR);
        adapter = new PickClassAdapter(getActivity(), heroClasses);
        listViewPickClass.setAdapter(adapter);
        listViewPickClass.setOnItemClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view);

        return builder.create();
    }

    public void setOnResultListener(ResultListener resultListener) {
        mOnResultListener = resultListener;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        mOnResultListener.heroSelected(adapter.getItem(position));
    }

    public interface ResultListener {
        void heroSelected(Deck.HeroClasses heroClass);
    }
}