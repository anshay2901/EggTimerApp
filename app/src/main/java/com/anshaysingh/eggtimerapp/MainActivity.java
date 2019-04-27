package com.anshaysingh.eggtimerapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekbar;
    TextView timerTextView;
    ImageView unbroken;
    ImageView broken;

    Button controllerButton;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;
    Boolean counterFinished = false;

    public void resetTimer() {

        if(counterFinished) {
            new CountDownTimer(6000,1000) {
                @Override
                public void onTick(long millisUntilFinished){
                     }
                public  void onFinish(){
                    broken.animate().alpha(0.0f).setDuration(500);
                    unbroken.animate().alpha(1.0f).setDuration(1000);
                    unbroken.animate().scaleY(1f).setDuration(2000);
                    counterFinished = false;

                }
            }.start();

            timerTextView.setText("0:30");
            timerSeekbar.setProgress(30);
            countDownTimer.cancel();
            controllerButton.setText("GO!");
            counterIsActive = false;
            timerSeekbar.setEnabled(!counterIsActive);
        }

    }

    public void updateTimer(int secsLeft) {
        int mins =(int) secsLeft/60;
        int secs = (int) secsLeft - (mins*60);
        String secString = Integer.toString(secs);
        if(secs <= 9) {
            secString = "0"+secs;
        }
        timerTextView.setText(Integer.toString(mins)+":"+secString);
    }

    public void controlTimer(View view) {
        if(counterIsActive == false) {
            counterIsActive = true;
            timerSeekbar.setEnabled(!counterIsActive);

            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                    controllerButton.setText("CANT STOP");

                }

                @Override
                public void onFinish() {
                    counterFinished = true;
                    unbroken.animate().scaleY(50.0f).setDuration(1000);
                    unbroken.animate().alpha(0.0f).setDuration(2000);
                    broken.animate().alpha(1.0f).setDuration(2000);
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    resetTimer();

                }

            }.start();
        } else {
           resetTimer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekbar = (SeekBar) findViewById(R.id.seekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        unbroken = (ImageView) findViewById(R.id.imageView);
        broken = (ImageView) findViewById(R.id.imageView2);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);
        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
