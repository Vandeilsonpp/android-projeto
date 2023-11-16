package com.example.ultimavez.helper;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

public class Notifications {

    public static void notificarPagamento(Context context, String titulo, String conteudo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo)
                .setMessage(conteudo)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
