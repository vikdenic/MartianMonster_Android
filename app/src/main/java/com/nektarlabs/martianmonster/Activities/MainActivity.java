package com.nektarlabs.martianmonster.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.nektarlabs.martianmonster.GIF.GifAnimationDrawable;
import com.nektarlabs.martianmonster.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer1;
    private MediaPlayer mMediaPlayer2;
    private MediaPlayer mMediaPlayer3;
    private MediaPlayer mMediaPlayer4;
    private MediaPlayer mMediaPlayer5;

    private MediaPlayer mMediaPlayerLoop1;

    private Animation animScale;

    GifAnimationDrawable gif;

    @Bind(R.id.rootLinearLayout) LinearLayout rootLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setGifAsBackground();
    }

    //region Soundboard
    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    public void onSoundboarsdButtonClicked(View view) {
        playSoundForButton(view);
    }

    private void stopPlaying(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playSoundForButton(View view) {
        Button button = (Button) view;
        int resid;

        switch (button.getId()) {
            case R.id.button1:
                stopPlaying(mMediaPlayer1);
                mMediaPlayer1 = MediaPlayer.create(this, R.raw.firstastronaut);
                mMediaPlayer1.start();
                break;
            case R.id.button2:
                stopPlaying(mMediaPlayer2);
                mMediaPlayer2 = MediaPlayer.create(this, R.raw.incrediblespeed);
                mMediaPlayer2.start();
                break;
            case R.id.button3:
                stopPlaying(mMediaPlayer3);
                mMediaPlayer3 = MediaPlayer.create(this, R.raw.tripisshort);
                mMediaPlayer3.start();
                break;
            case R.id.button4:
                stopPlaying(mMediaPlayer4);
                mMediaPlayer4 = MediaPlayer.create(this, R.raw.ufo);
                mMediaPlayer4.start();
                break;
            case R.id.button5:
                stopPlaying(mMediaPlayer5);
                mMediaPlayer5 = MediaPlayer.create(this, R.raw.blastoff);
                mMediaPlayer5.start();
                break;
            default:
                break;
        }
    }

    //endregion
    @OnClick(R.id.rocketImageView)
    public void onRocketImageViewClicked(View view) {
        ImageView imageView = (ImageView) view;

        imageView.clearAnimation();
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        imageView.setAnimation(animScale);

        if (mMediaPlayerLoop1 == null) {
            imageView.setImageResource(R.mipmap.rocketcirclewhite);
            imageView.startAnimation(animScale);

            mMediaPlayerLoop1 = MediaPlayer.create(this, R.raw.martianmonsterloop);
            mMediaPlayerLoop1.setLooping(true);
            mMediaPlayerLoop1.start();
        } else if (mMediaPlayerLoop1.isPlaying()) {
            mMediaPlayerLoop1.pause();

            imageView.setImageResource(R.mipmap.rocketcircle);
            imageView.clearAnimation();
        } else {
            mMediaPlayerLoop1.start();
            imageView.setImageResource(R.mipmap.rocketcirclewhite);
            imageView.startAnimation(animScale);
        }
    }

    //region Sharing
    @OnClick(R.id.shareImageView)
    public void onShareButtonClicked(View view) {
        shareTo();
    }

    private void shareTo() {
        String message = "♫ My spaceship just blasted off, via the Martian Monster App! http://onelink.to/mmapp";
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(intent, "Share To"));
    }
    //endregion

    //region GIF
    private void setGifAsBackground() {
        try {
            gif = new GifAnimationDrawable(getResources().openRawResource(R.raw.space));
            gif.setOneShot(false);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rootLinearLayout.setBackground(gif);
        gif.setVisible(true, true);
        gif.start();
    }
    //endregion
}