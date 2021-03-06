package com.example.spotlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.prefs.PreferencesManager;
import com.wooplr.spotlight.utils.Utils;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    static {
//        AppCompatDelegate.setCompatVectorFromSourcesEnabled(true);
//    }

    private FloatingActionButton fab;
    private static final String INTRO_CARD = "fab_intro";
    private boolean isRevealEnabled = true;

    @BindView(R.id.switchAnimation)
    TextView switchAnimation;
    @BindView(R.id.reset)
    TextView reset;
    @BindView(R.id.resetAndPlay)
    TextView resetAndPlay;
    @BindView(R.id.changePosAndPlay)
    TextView changePosAndPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Fab Clicked!", Toast.LENGTH_LONG).show();
            }
        });

        switchAnimation.setOnClickListener(this);
        reset.setOnClickListener(this);
        resetAndPlay.setOnClickListener(this);
        changePosAndPlay.setOnClickListener(this);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showIntro(fab, INTRO_CARD);
            }
        }, 2400);
    }

    @Override
    public void onClick(View view) {
        PreferencesManager mPreferencesManager = new PreferencesManager(MainActivity.this);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        switch (view.getId()) {

            case R.id.switchAnimation:
                if (isRevealEnabled) {
                    switchAnimation.setText("Switch to Reveal");
                    isRevealEnabled = false;
                } else {
                    switchAnimation.setText("Switch to Fadein");
                    isRevealEnabled = true;
                }
                mPreferencesManager.resetAll();
                break;

            case R.id.reset:
                mPreferencesManager.resetAll();
                break;
            case R.id.resetAndPlay:
                mPreferencesManager.resetAll();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showIntro(fab, INTRO_CARD);
                    }
                }, 400);
                break;
            case R.id.changePosAndPlay:
                mPreferencesManager.resetAll();
                Random r = new Random();
                int right = r.nextInt((screenWidth - Utils.dpToPx(16)) - 16) + 16;
                int bottom = r.nextInt((screenHeight - Utils.dpToPx(16)) - 16) + 16;
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                params.setMargins(Utils.dpToPx(16), Utils.dpToPx(16), right, bottom);
                fab.setLayoutParams(params);
                break;
        }
    }

    private void showIntro(View view, String usageId) {
        new SpotlightView.Builder(this)
                .introAnimationDuration(400)
                .enableRevalAnimation(isRevealEnabled)
                .performClick(true)
                .fadeinTextDuration(400)
                //.setTypeface(FontUtil.get(this, "RemachineScript_Personal_Use"))
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(32)
                .headingTvText("Love")
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText("Like the picture?\nLet others know.")
                .maskColor(Color.parseColor("#dc000000"))
                .target(view)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .enableDismissAfterShown(true)
                .usageId(usageId) //UNIQUE ID
                .show();
    }
}

