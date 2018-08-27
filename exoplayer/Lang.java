package com.example.exoplayer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Lang extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);
        Button button=(Button)findViewById(R.id.button4);
        Button button1=(Button)findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Lang.this,PlayerActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Lang.this,PlayerActivityHindi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
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
