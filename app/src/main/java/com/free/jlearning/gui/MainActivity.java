package com.free.jlearning.gui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
    protected Toolbar           mToolbar;
    protected ActionBar         mActionBar;

    private int                 mCurrentFragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*** Start initializing the UI ***/
        setContentView(R.layout.main);

        initAudioPlayerComponents();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.root_container);
        mInfoLayout = (RelativeLayout) findViewById(R.id.info_layout);
        mInfoProgress = (ProgressBar) findViewById(R.id.info_progress);
        mInfoText = (TextView) findViewById(R.id.info_text);

        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        Button button = (Button) findViewById(R.id.Slide);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSlidingPane.getState() == mSlidingPane.STATE_OPENED){
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.show();
                    }
                    mSlidingPane.openPaneEntirely();
                } else if (mSlidingPane.getState() == mSlidingPane.STATE_OPENED_ENTIRELY){
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.hide();
                    }
                    mSlidingPane.closePane();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        Fragment current = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_placeholder);

        if (current == null) {
            mNavigationView.setCheckedItem(mCurrentFragmentId);
            Fragment ff = getFragment(mCurrentFragmentId);

        }
    }

    private String getTag(int id){
        switch (id){
            case R.id.nav_about:
                return ID_ABOUT;
            case R.id.nav_settings:
                return ID_PREFERENCES;
            case R.id.nav_audio:
                return ID_AUDIO;
            case R.id.nav_directories:
                return ID_DIRECTORIES;
            case R.id.nav_history:
                return ID_HISTORY;
            case R.id.nav_mrl:
                return ID_MRL;
            case R.id.nav_network:
                return ID_NETWORK;
            default:
                return ID_VIDEO;
        }
    }

    private Fragment getFragment(int id)
    {
        Fragment frag = getSupportFragmentManager().findFragmentByTag(getTag(id));
        if (frag != null)
            return frag;
        switch (id) {
        }
    }
}
