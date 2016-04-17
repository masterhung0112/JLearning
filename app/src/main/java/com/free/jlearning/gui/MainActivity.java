package com.free.jlearning.gui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.widget.SlidingPaneLayout;
import com.free.jlearning.R;

public class MainActivity extends JLContainerActivity {
    public final static String TAG = "JL/MainActivity";

    protected DrawerLayout      mDrawerLayout;
    protected ProgressBar       mInfoProgress;
    protected TextView          mInfoText;
    protected RelativeLayout    mInfoLayout;
    protected NavigationView    mNavigationView;
    protected Toolbar               mToolbar;
    protected ActionBar             mActionBar;
    protected SlidingPaneLayout     mSlidingPane;
    protected View                  mAudioPlayerFilling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*** Start initializing the UI ***/
        setContentView(R.layout.main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.root_container);
        mInfoLayout = (RelativeLayout) findViewById(R.id.info_layout);
        mInfoProgress = (ProgressBar) findViewById(R.id.info_progress);
        mInfoText = (TextView) findViewById(R.id.info_text);

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mSlidingPane = (SlidingPaneLayout)findViewById(R.id.pane);
        mAudioPlayerFilling = findViewById(R.id.audio_player_filling);


    }


}
