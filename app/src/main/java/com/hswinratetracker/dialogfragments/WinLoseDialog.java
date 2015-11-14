package com.hswinratetracker.dialogfragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.hswinratetracker.models.Deck;
import butterknife.Bind;
import butterknife.ButterKnife;
import hswinratetracker.com.hswinratetracker.R;

public class WinLoseDialog extends DialogFragment {


    private ResultListener mOnResultListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_winlose, null, false);
        ButterKnife.bind(this, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setCancelable(true);

        return builder.create();
    }

    public void setOnResultListener(ResultListener resultListener) {
        mOnResultListener = resultListener;
    }


    public interface ResultListener {
        void ResultSelected(Deck deck);
    }


}