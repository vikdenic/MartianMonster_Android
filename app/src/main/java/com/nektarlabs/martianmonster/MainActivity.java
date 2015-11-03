package com.nektarlabs.martianmonster;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5 })
    public void onSoundboardButtonClicked(View view) {
        int resid = residForButton(view);

        stopPlaying();
        mMediaPlayer = MediaPlayer.create(this, resid);
        mMediaPlayer.start();
    }

    private void stopPlaying() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private int residForButton(View view) {
        Button button = (Button) view;
        int resid;

        switch(button.getId()) {
            case R.id.button1:
                resid = R.raw.firstastronaut;
                break;
            case R.id.button2:
                resid = R.raw.incrediblespeed;
                break;
            case R.id.button3:
                resid = R.raw.tripisshort;
                break;
            case R.id.button4:
                resid = R.raw.ufo;
                break;
            case R.id.button5:
                resid = R.raw.blastoff;
                break;
            default:
                return R.raw.firstastronaut;
        }
        return resid;
    }
}
