package org.suinot.migraine;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by remi on 07/10/16.
  Première activity: animation (splashscreen)
 */

public class splashscreen extends Activity {
    /**
     * Called when the activity is first created.
     */

    ProgressBar mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.splashscreen);
        Animation anim1 = AnimationUtils.loadAnimation (this, R.anim.anim_down);
        ImageView img = (ImageView) findViewById (R.id.imageView);
        img.setAnimation (anim1);

        mprogressBar = (ProgressBar) findViewById (R.id.progressBar);
        mprogressBar.setPadding (10,0,10,0);
        ObjectAnimator anim = ObjectAnimator.ofInt (mprogressBar, "progress", 0, 100);
        anim.setDuration (4000);
        anim.setInterpolator (new DecelerateInterpolator ());
        anim.start ();

        Handler handler = new Handler ();

        handler.postDelayed (new Runnable () {
            @Override
            public void run() {

                startActivity (new Intent (splashscreen.this, Point_Entree.class));
                finish ();

            }
        }, 4000);
    }
}

