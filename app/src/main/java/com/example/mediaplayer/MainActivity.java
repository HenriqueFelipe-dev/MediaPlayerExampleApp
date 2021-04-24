package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Runnable runnable;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBarVolume);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                seekBar.setMax(mediaPlayer.getDuration());
                progressoSeekBar();

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                findViewById(R.id.button).setBackgroundResource(R.drawable.play);
                seekBar.setProgress(0);

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if (fromUser){

                   mediaPlayer.seekTo(progress);
                   seekBar.setProgress(progress);

               }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    public void progressoSeekBar(){

        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()){

            runnable = new Runnable() {
                @Override
                public void run() {
                    progressoSeekBar();
                }
            };

            handler.postDelayed(runnable, 1);

        }

    }

    public void start (View view){
        if(mediaPlayer != null){

            if(mediaPlayer.isPlaying()){

                findViewById(R.id.button).setBackgroundResource(R.drawable.play);
                mediaPlayer.pause();

            }else{

                findViewById(R.id.button).setBackgroundResource(R.drawable.pause);
                mediaPlayer.start();
                progressoSeekBar();


            }

        }
    }
}