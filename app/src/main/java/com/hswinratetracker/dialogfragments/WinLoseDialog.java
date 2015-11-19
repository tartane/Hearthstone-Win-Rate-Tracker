package com.hswinratetracker.dialogfragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.hswinratetracker.models.Deck;
import butterknife.Bind;
import butterknife.ButterKnife;
import hswinratetracker.com.hswinratetracker.R;

public class WinLoseDialog extends DialogFragment implements View.OnClickListener {


    @Bind(R.id.layWin)
    RelativeLayout layWin;

    @Bind(R.id.layLose)
    RelativeLayout layLose;

    @Bind(R.id.layEdit)
    RelativeLayout layEdit;

    final static private String DECK_KEY = "DECK";
    private Deck deck;
    private ResultListener mOnResultListener;

    public static WinLoseDialog newInstance(Deck deck) {
        WinLoseDialog dialogFragment = new WinLoseDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable(DECK_KEY, deck);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.deck = getArguments().getParcelable(DECK_KEY);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_winlose, null, false);
        ButterKnife.bind(this, view);
        layWin.setOnClickListener(this);
        layLose.setOnClickListener(this);
        layEdit.setOnClickListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(true);

        return builder.create();
    }

    public void setOnResultListener(ResultListener resultListener) {
        mOnResultListener = resultListener;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.layWin:
                mOnResultListener.ResultSelected(deck, true);
                break;
            case R.id.layLose:
                mOnResultListener.ResultSelected(deck, false);
                break;
            case R.id.layEdit:
                mOnResultListener.EditSelected(deck);
                break;
        }

        getDialog().dismiss();
    }


    public interface ResultListener {
        void ResultSelected(Deck deck, boolean win);
        void EditSelected(Deck deck);
    }


}