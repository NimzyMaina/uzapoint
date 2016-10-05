package com.clemcreativity.uzapoint.helper;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.clemcreativity.uzapoint.R;

/**
 * Created by user on 5/7/2016.
 */
public class Utils {
    public static void displayMessage(View mView,String string, String key){
        Snackbar snackbar = Snackbar.make(mView.findViewById(android.support.design.R.id.snackbar_text), string, Snackbar.LENGTH_LONG)
                .setAction("RETRY",  new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //makeJsonArrayRequest();
                    }
                });

        switch (key){
            case "error":
                ColoredSnackBar.error(snackbar);
                break;
            case "success":
                ColoredSnackBar.confirm(snackbar);
                break;
            default:
                ColoredSnackBar.warning(snackbar);
        }


// Changing message text color
        snackbar.setActionTextColor(Color.RED);

// Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
