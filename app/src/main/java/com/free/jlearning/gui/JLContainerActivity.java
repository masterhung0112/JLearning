package com.free.jlearning.gui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.free.jlearning.R;

public class JLContainerActivity extends AppCompatActivity {

    public static final String TAG = "JL/JLContainerActivity";

    protected SharedPreferences     mSettings;
    //protected AudioPlayer           mAudioPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Get the setting
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        // Theme must be applied before super.onCreate
        applyTheme();

        super.onCreate(savedInstanceState);
    }

    private void applyTheme() {
        boolean enableBlackTheme = mSettings.getBoolean("enable_black_theme", false);
        if (enableBlackTheme) {
            setTheme(R.style.Theme_JLearning_Black);
        }
    }
}
