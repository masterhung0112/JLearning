package com.free.jlearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.free.jlearning.gui.MainActivity;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        startActivity(new Intent(this, MainActivity.class));
        /*
        if (intent != null) {
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }*/
        finish();
    }
}
