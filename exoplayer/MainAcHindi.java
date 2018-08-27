package com.example.exoplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainAcHindi extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    String[] str;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_hindi);
        ImageView demoImage = (ImageView) findViewById(R.id.imageView);
        int imagesToShow[] = {R.drawable.indian, R.drawable.slogan, R.drawable.indian_railways};
        animate(demoImage, imagesToShow, 0, true);
        final PackageManager pm = getPackageManager();

//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo packageInfo : packages) {
            Log.d("app", packageInfo.packageName);

            try {
                if (packageInfo.toString().matches(".*[F|f][m|M].*")) {
                    s = packageInfo.toString();
                    str = packageInfo.toString().split("[ }]");
                    str[1] = str[1].replace("service", "");

                    Log.d("lfhthhrtrjttjjj", str[1]);
                }
            } catch (Exception e) {
                Toast.makeText(this, "FM not found", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void fmradio(View view) {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(str[1]);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);

        } catch (Exception e) {
            radio_not(MainAcHindi.this);
        }
    }

    public void change(View view) {
        Intent intent = new Intent(MainAcHindi.this, PlayerActivityHindi.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
    }

    private void animate(final ImageView imageView, final int images[], final int imageIndex, final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images, imageIndex + 1, forever); //Calls itself until it gets to the end of the array
                } else {
                    if (forever) {
                        animate(imageView, images, 0, forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void radio_not(MainAcHindi view) {
        AlertDialog.Builder mbulider = new AlertDialog.Builder(MainAcHindi.this);
        View mview = getLayoutInflater().inflate(R.layout.radio_not_hindi, null);
        final TextView textView1 = (TextView) mview.findViewById(R.id.set1);
        final TextView textView2 = (TextView) mview.findViewById(R.id.textView2);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainAcHindi.this, PlayerActivityHindi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);

            }
        });
        mbulider.setView(mview);
        final AlertDialog dialog = mbulider.create();
        dialog.show();
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);

            return;
        }

        this.doubleBackToExitPressedOnce = true;  
        Toast.makeText(this, "press back angain for exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}


