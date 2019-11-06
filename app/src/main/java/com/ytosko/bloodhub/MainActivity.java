package com.ytosko.bloodhub;

import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class MainActivity extends AwesomeSplash {

    @Override
    public void initSplash ( ConfigSplash configSplash ) {

        ActionBar a = getSupportActionBar();
        a.hide();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        configSplash.setBackgroundColor(R.color.strokeColor);
        configSplash.setAnimLogoSplashDuration( 3000 );
        configSplash.setRevealFlagX( Flags.REVEAL_RIGHT );
        configSplash.setRevealFlagX( Flags.REVEAL_TOP );
        configSplash.setLogoSplash( R.drawable.ic_blood_hub_01 );
        configSplash.setAnimLogoSplashDuration( 5000 );

        configSplash.setAnimLogoSplashTechnique( Techniques.Flash );
       Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                MainActivity.class));
    }

    @Override
    public void animationsFinished () {
        startActivity( new Intent( MainActivity.this , MainActivity2.class) );
        finish();

    }
}
