package com.learn.locationmanagement.ui.common.dialog;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.learn.locationmanagement.R;
import com.learn.locationmanagement.model.location.common.Message;

public class DialogNavigator {
    private Context context;

    public DialogNavigator(Context context) {
        this.context = context;
    }

    public void showErrorDialog(Message message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(message.getTitle());
        alertDialog.setIcon(R.drawable.ic_error_24);
        alertDialog.setMessage(message.getBody());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Đã hiểu",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
