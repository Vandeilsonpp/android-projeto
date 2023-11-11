package com.example.ultimavez.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.example.ultimavez.R;

import java.util.ArrayList;
import java.util.List;

public class ErrorInflator {

    public static void showErrors(List<String> notifications, Context context) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.sign_up_error, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        ListView errorMessages = dialogView.findViewById(R.id.errorMessages);
        Button okButton = dialogView.findViewById(R.id.okButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, notifications);
        errorMessages.setAdapter(adapter);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        okButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    public static void showErrors(String error, Context context) {
        List<String> errors = new ArrayList<>();
        errors.add(error);
        showErrors(errors, context);
    }

}
