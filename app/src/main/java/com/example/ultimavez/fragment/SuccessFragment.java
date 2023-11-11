package com.example.ultimavez.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.example.ultimavez.activity.AuthActivity;

public class SuccessFragment extends DialogFragment {

    private final String text;
    private final Class aClass;

    public SuccessFragment(String text, Class aClass) {
        this.text = text;
        this.aClass = aClass;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(text)
                .setPositiveButton("OK", (dialog, id) -> {
                    Intent intent = new Intent(getActivity(), aClass);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    getActivity().finish();
                });

        return builder.create();
    }
}

