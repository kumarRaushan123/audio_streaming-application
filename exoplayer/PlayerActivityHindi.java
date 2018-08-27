/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
* limitations under the License.
 */
package com.example.exoplayer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A fullscreen activity to play audio or video streams.
 */
public class PlayerActivityHindi extends AppCompatActivity  {
    boolean doubleBackToExitPressedOnce = false;

    private Button button;

    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private String s;
    private int i;
    private int z=0;
    long currentpostion=0;
    long lastposition=0;
    public static final class CustomLoadControl implements LoadControl {

        /**
         * The default minimum duration of media that the player will attempt to ensure is buffered at all
         * times, in milliseconds.
         */
        public static final int DEFAULT_MIN_BUFFER_MS = 15;

        /**
         * The default maximum duration of media that the player will attempt to buffer, in milliseconds.
         */
        public static final int DEFAULT_MAX_BUFFER_MS = 30;

        /**
         * The default duration of media that must be buffered for playback to start or resume following a
         * user action such as a seek, in milliseconds.
         */
        public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 25;

        /**
         * The default duration of media that must be buffered for playback to resume after a rebuffer,
         * in milliseconds. A rebuffer is defined to be caused by buffer depletion rather than a user
         * action.
         */
        public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS  = 50;

        /**
         * To increase buffer time and size.
         * Added by Sri
         */
        public static int VIDEO_BUFFER_SCALE_UP_FACTOR = 4;

        /**
         * Priority for media loading.
         */
        public static final int LOADING_PRIORITY = 0;


        private com.example.exoplayer.CustomLoadControl.EventListener bufferedDurationListener;
        private Handler eventHandler;

        private static final int ABOVE_HIGH_WATERMARK = 0;
        private static final int BETWEEN_WATERMARKS = 1;
        private static final int BELOW_LOW_WATERMARK = 2;

        private final DefaultAllocator allocator;

        private final long minBufferUs;
        private final long maxBufferUs;
        private final long bufferForPlaybackUs;
        private final long bufferForPlaybackAfterRebufferUs;
        private final PriorityTaskManager priorityTaskManager;

        private int targetBufferSize;
        private boolean isBuffering;

        /**
         * Constructs a new instance, using the {@code DEFAULT_*} constants defined in this class.
         */
        public CustomLoadControl() {
            this(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE));
        }

        /**
         * Constructs a new instance, using the {@code DEFAULT_*} constants defined in this class.
         */
        public CustomLoadControl(com.example.exoplayer.CustomLoadControl.EventListener listener, Handler handler) {
            this(new DefaultAllocator(true, C.DEFAULT_BUFFER_SEGMENT_SIZE));
            bufferedDurationListener = listener;
            eventHandler = handler;
        }

        /**
         * Constructs a new instance, using the {@code DEFAULT_*} constants defined in this class.
         *
         * @param allocator The {@link DefaultAllocator} used by the loader.
         */
        public CustomLoadControl(DefaultAllocator allocator) {
            this(allocator, DEFAULT_MIN_BUFFER_MS, DEFAULT_MAX_BUFFER_MS, DEFAULT_BUFFER_FOR_PLAYBACK_MS,
                    DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
        }

        /**
         * Constructs a new instance.
         *
         * @param allocator The {@link DefaultAllocator} used by the loader.
         * @param minBufferMs The minimum duration of media that the player will attempt to ensure is
         *     buffered at all times, in milliseconds.
         * @param maxBufferMs The maximum duration of media that the player will attempt buffer, in
         *     milliseconds.
         * @param bufferForPlaybackMs The duration of media that must be buffered for playback to start or
         *     resume following a user action such as a seek, in milliseconds.
         * @param bufferForPlaybackAfterRebufferMs The default duration of media that must be buffered for
         *     playback to resume after a rebuffer, in milliseconds. A rebuffer is defined to be caused by
         *     buffer depletion rather than a user action.
         */
        public CustomLoadControl(DefaultAllocator allocator, int minBufferMs, int maxBufferMs,
                                 long bufferForPlaybackMs, long bufferForPlaybackAfterRebufferMs) {
            this(allocator, minBufferMs, maxBufferMs, bufferForPlaybackMs, bufferForPlaybackAfterRebufferMs,
                    null);
        }

        /**
         * Constructs a new instance.
         *
         * @param allocator The {@link DefaultAllocator} used by the loader.
         * @param minBufferMs The minimum duration of media that the player will attempt to ensure is
         *     buffered at all times, in milliseconds.
         * @param maxBufferMs The maximum duration of media that the player will attempt buffer, in
         *     milliseconds.
         * @param bufferForPlaybackMs The duration of media that must be buffered for playback to start or
         *     resume following a user action such as a seek, in milliseconds.
         * @param bufferForPlaybackAfterRebufferMs The default duration of media that must be buffered for
         *     playback to resume after a rebuffer, in milliseconds. A rebuffer is defined to be caused by
         *     buffer depletion rather than a user action.
         * @param priorityTaskManager If not null, registers itself as a task with priority
         *     {@link #LOADING_PRIORITY} during loading periods, and unregisters itself during draining
         *     periods.
         */
        public CustomLoadControl(DefaultAllocator allocator, int minBufferMs, int maxBufferMs,
                                 long bufferForPlaybackMs, long bufferForPlaybackAfterRebufferMs,
                                 PriorityTaskManager priorityTaskManager) {
            this.allocator = allocator;
            minBufferUs = VIDEO_BUFFER_SCALE_UP_FACTOR/*Added by Sri to control buffer size */ * minBufferMs * 1000L;
            maxBufferUs = VIDEO_BUFFER_SCALE_UP_FACTOR/*Added by Sri to control buffer size */ * maxBufferMs * 1000L;
            bufferForPlaybackUs = bufferForPlaybackMs * 1000L;
            bufferForPlaybackAfterRebufferUs = bufferForPlaybackAfterRebufferMs * 1000L;
            this.priorityTaskManager = priorityTaskManager;
        }

        @Override
        public void onPrepared() {
            reset(false);
        }

        @Override
        public void onTracksSelected(Renderer[] renderers, TrackGroupArray trackGroups,
                                     TrackSelectionArray trackSelections) {
            targetBufferSize = 0;
            for (int i = 0; i < renderers.length; i++) {
                if (trackSelections.get(i) != null) {
                    targetBufferSize += Util.getDefaultBufferSize(renderers[i].getTrackType());
                    if(renderers[i].getTrackType() == C.TRACK_TYPE_VIDEO)
                        targetBufferSize *= VIDEO_BUFFER_SCALE_UP_FACTOR; /*Added by Sri to control buffer size */
                }
            }
            allocator.setTargetBufferSize(targetBufferSize);
        }

        @Override
        public void onStopped() {
            reset(true);
        }

        @Override
        public void onReleased() {
            reset(true);
        }

        @Override
        public Allocator getAllocator() {
            return allocator;
        }

        @Override
        public boolean shouldStartPlayback(long bufferedDurationUs, boolean rebuffering) {
            long minBufferDurationUs = rebuffering ? bufferForPlaybackAfterRebufferUs : bufferForPlaybackUs;
            return minBufferDurationUs <= 0 || bufferedDurationUs >= minBufferDurationUs;
        }

        @Override
        public boolean shouldContinueLoading(final long bufferedDurationUs) {
            int bufferTimeState = getBufferTimeState(bufferedDurationUs);
            boolean targetBufferSizeReached = allocator.getTotalBytesAllocated() >= targetBufferSize;
            boolean wasBuffering = isBuffering;
            isBuffering = bufferTimeState == BELOW_LOW_WATERMARK
                    || (bufferTimeState == BETWEEN_WATERMARKS
            /*
             * commented below line to achieve drip-feeding method for better caching. once you are below maxBufferUs, do fetch immediately.
             * Added by Sri
             */
            /* && isBuffering */
                    && !targetBufferSizeReached);
            if (priorityTaskManager != null && isBuffering != wasBuffering) {
                if (isBuffering) {
                    priorityTaskManager.add(LOADING_PRIORITY);
                } else {
                    priorityTaskManager.remove(LOADING_PRIORITY);
                }
            }
            if (null != bufferedDurationListener && null != eventHandler)
                eventHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        bufferedDurationListener.onBufferedDurationSample(bufferedDurationUs);
                    }
                });
//    Log.e("DLC","current buff Dur: "+bufferedDurationUs+",max buff:" + maxBufferUs +" shouldContinueLoading: "+isBuffering);
            return isBuffering;
        }

        private int getBufferTimeState(long bufferedDurationUs) {
            return bufferedDurationUs > maxBufferUs ? ABOVE_HIGH_WATERMARK
                    : (bufferedDurationUs < minBufferUs ? BELOW_LOW_WATERMARK : BETWEEN_WATERMARKS);
        }

        private void reset(boolean resetAllocator) {
            targetBufferSize = 0;
            if (priorityTaskManager != null && isBuffering) {
                priorityTaskManager.remove(LOADING_PRIORITY);
            }
            isBuffering = false;
            if (resetAllocator) {
                allocator.reset();
            }
        }

        public interface EventListener {
            void onBufferedDurationSample(long bufferedDurationUs);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mWifi.isConnected()) {
            alert(PlayerActivityHindi.this);
        }
        boolean doubleBackToExitPressedOnce = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_hindi);
        ImageView demoImage = (ImageView) findViewById(R.id.imageView5);
        int imagesToShow[] = { R.drawable.indian, R.drawable.slogan,R.drawable.indian_railways };
        animate(demoImage, imagesToShow, 0,true);
        playerView = findViewById(R.id.video_view);
        button = (Button) findViewById(R.id.button);

        s = button.getText().toString();
        i = 0;
        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 1) {
                    player.setVolume(0);
                    button.setText("घोषणा");
                    i = 0;

                } else if (i == 0) {
                    player.setVolume(1);
                    button.setText("रुकें");
                    i = 1;



                }

            }
        });


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

    public void alert(PlayerActivityHindi view){
        AlertDialog.Builder mbulider=new AlertDialog.Builder(PlayerActivityHindi.this);
        View mview = getLayoutInflater().inflate(R.layout.alert_dailog_hindi,null);
        final TextView textView=(TextView)mview.findViewById(R.id.text_dialog);
        final TextView textView1=(TextView)mview.findViewById(R.id.set);
        final TextView textView2=(TextView)mview.findViewById(R.id.textView2);
        final TextView textView3=(TextView)mview.findViewById(R.id.set3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlayerActivityHindi.this,MainAcHindi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
                // moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        mbulider.setView(mview);
        final AlertDialog dialog=mbulider.create();
        dialog.show();
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23){
            try {
                initializePlayer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            try {
                initializePlayer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    private void initializePlayer() throws Exception {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new CustomLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_mp3)));
        player.prepare(mediaSource, true, false);
        //mediaSource.maybeThrowSourceInfoRefreshError();
        player.setVolume(0);
        new CountDownTimer(20000,1000){

            @Override
            public void  onTick(long millisUntilFinished) {


            }

            @Override
            public void onFinish() {
                currentpostion=player.getCurrentPosition();
                if(currentpostion==lastposition)
                {
                    server(PlayerActivityHindi.this);
                }
            }
        }.start();


    }
    public void server(PlayerActivityHindi view){
        AlertDialog.Builder mbulider=new AlertDialog.Builder(PlayerActivityHindi.this);
        View mview = getLayoutInflater().inflate(R.layout.server_not_hindi,null);
        final TextView textView1=(TextView)mview.findViewById(R.id.set1);
        final TextView textView2=(TextView)mview.findViewById(R.id.textView2);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlayerActivityHindi.this,MainAcHindi.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);

            }
        });
        mbulider.setView(mview);
        final AlertDialog dialog=mbulider.create();
        dialog.show();
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                .createMediaSource(uri);
    }

    public  void change(View view){

        Intent intent=new Intent(PlayerActivityHindi.this,MainAcHindi.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
        // moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        //System.exit(1);
    }
    public  void change1(View view){

        Intent intent=new Intent(PlayerActivityHindi.this,MainActivityHindi.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
        // moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        //System.exit(1);
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
