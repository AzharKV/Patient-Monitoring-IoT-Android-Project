package com.delaroystudios.alarmreminder;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ShowProgress extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);




        return dialog;



    }
}
