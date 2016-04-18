package com.free.jlearning.gui;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.widget.SlidingPaneLayout;
import com.free.jlearning.R;
import com.free.jlearning.gui.audio.AudioPlayer;
import com.free.jlearning.interfaces.IRefreshable;
import com.free.jlearning.utils.WeakHandler;

public class JLContainerActivity extends AppCompatActivity {

    public static final String TAG = "JL/JLContainerActivity";

    protected AudioPlayer           mAudioPlayer;
    protected SharedPreferences     mSettings;
    //protected AudioPlayer           mAudioPlayer;
    protected View                  mAudioPlayerFilling;
    protected SlidingPaneLayout     mSlidingPane;

    protected boolean mPreventRescan = false;

    protected Handler mActivityHandler = new StorageHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Get the setting
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        // Theme must be applied before super.onCreate
        //applyTheme();

        mAudioPlayer = new AudioPlayer();

        super.onCreate(savedInstanceState);
    }

    protected void initAudioPlayerComponents() {

        mSlidingPane = (SlidingPaneLayout)findViewById(R.id.pane);
        mSlidingPane.setPanelSlideListener(mSlidingPaneListener);
        mAudioPlayerFilling = findViewById(R.id.audio_player_filling);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.audio_player, mAudioPlayer)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Handle external storage state
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPreventRescan = true;
    }

    public void updateLib() {
        if (mPreventRescan) {
            mPreventRescan = false;
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment current = fm.findFragmentById(R.id.fragment_placeholder);
        if (current != null && current instanceof IRefreshable) {
            ( (IRefreshable) current ).refresh();
        }
    }

    public void showAudioPlayer() {
        mActivityHandler.post(new Runnable() {
            @Override
            public void run() {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.collapseActionView();
                }
                // Open the pane only if is entirely opened.
                if (mSlidingPane.getState() == mSlidingPane.STATE_OPENED_ENTIRELY)
                    mSlidingPane.openPane();
                // Display audio filling
                mAudioPlayerFilling.setVisibility(View.VISIBLE);
            }
        });
    }

    public int getSlidingPaneState() { return mSlidingPane.getState(); }

    public boolean slideDownAudioPlayer() {
        if (mSlidingPane.getState() == mSlidingPane.STATE_CLOSED) {
            mSlidingPane.openPane();
            return true;
        }
        return false;
    }

    /**
     * Slide up and down the audio player depending on its current state.
     */
    public void slideUpOrDownAudioPlayer() {
        if (mSlidingPane.getState() == mSlidingPane.STATE_CLOSED){
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mSlidingPane.openPane();
        } else if (mSlidingPane.getState() == mSlidingPane.STATE_OPENED){
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mSlidingPane.closePane();
        }
    }

    public void hideAudioPlayer() {
        mActivityHandler.post(new Runnable() {
            @Override
            public void run() {
                mSlidingPane.openPaneEntirely();
                mAudioPlayerFilling.setVisibility(View.GONE);
            }
        });
    }

    private void applyTheme() {
        boolean enableBlackTheme = mSettings.getBoolean("enable_black_theme", false);
        if (enableBlackTheme) {
            setTheme(R.style.Theme_JLearning_Black);
        }
    }

    private static final int ACTION_MEDIA_MOUNTED = 1337;
    private static final int ACTION_MEDIA_UNMOUNTED = 1338;

    private static class StorageHandler extends WeakHandler<JLContainerActivity> {

        public StorageHandler(JLContainerActivity owner) {
            super(owner);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what) {
                case ACTION_MEDIA_MOUNTED:
                    removeMessages(ACTION_MEDIA_UNMOUNTED);
                    getOwner().updateLib();
                    break;
                case ACTION_MEDIA_UNMOUNTED:
                    getOwner().updateLib();
                    break;
            }
        }
    }

    private final SlidingPaneLayout.PanelSlideListener mSlidingPaneListener = new SlidingPaneLayout.PanelSlideListener() {
        float previousOffset = 1.0f;

        @Override
        public void onPanelSlide(float slideOffset) {
            ActionBar actionBar = getSupportActionBar();

            if (slideOffset >= 0.1 && slideOffset > previousOffset && actionBar != null && !actionBar.isShowing())
                actionBar.show();
            else if (slideOffset <= 0.1 && slideOffset < previousOffset && actionBar != null && actionBar.isShowing())
                actionBar.hide();
            previousOffset = slideOffset;
        }

        @Override
        public void onPanelOpened() {

        }

        @Override
        public void onPanelOpenedEntirely() {
            mSlidingPane.setShadowDrawable(null);
        }

        @Override
        public void onPanelClosed() {

        }
    };
}
