package com.pawerskills.damianlzy.pawerapp.functions;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.pawerskills.damianlzy.pawerapp.R;

public class DialogMenu implements View.OnClickListener {
    private Dialog dialog;
    private OnDialogMenuListener listener;

    public DialogMenu(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_up_categories);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();


        Button btnConfirm =  dialog.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

    }

    public void setListener(OnDialogMenuListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirm:
                listener.onConfirm();
                dialog.dismiss();
                break;
        }
    }

    public interface OnDialogMenuListener {
        void onConfirm();
    }
}