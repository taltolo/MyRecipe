package com.example.myrecipe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ExitDialog extends DialogFragment {


    private OnExitDialogListener mListener;

    public ExitDialog() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) { //  .לבצע את הinflate לנפח את הlayout ולבנות אותו.
        AlertDialog.Builder dlg = new  AlertDialog.Builder(getActivity());


        String title = "Exit";
        dlg.setTitle(title);

        dlg.setMessage("Are you sure you want to exit?");
        dlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onExitpress();
                dismiss();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();            }
        });

        return dlg.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExitDialogListener) {
            mListener = (OnExitDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnExitDialogListener {
        // TODO: Update argument type and name
        void onExitpress();
    }
}
