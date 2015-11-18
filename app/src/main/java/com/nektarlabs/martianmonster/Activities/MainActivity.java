package com.nektarlabs.martianmonster.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.nektarlabs.martianmonster.GIF.GifAnimationDrawable;
import com.nektarlabs.martianmonster.R;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements MoPubInterstitial.InterstitialAdListener{

    private MediaPlayer mMediaPlayer1;
    private MediaPlayer mMediaPlayer2;
    private MediaPlayer mMediaPlayer3;
    private MediaPlayer mMediaPlayer4;
    private MediaPlayer mMediaPlayer5;

    private MediaPlayer mMediaPlayerLoop1;
    private MediaPlayer mMediaPlayerLoop2;
    private MediaPlayer mMediaPlayerLoop3;

    private Animation animScale;

    private GifAnimationDrawable gif;

    @Bind(R.id.rootLinearLayout) LinearLayout rootLinearLayout;

    @Bind({ R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5})
    List<Button> soundButtons;

    @Bind(R.id.rocketImageView) ImageView rocketImageView;
    @Bind(R.id.catImageView) ImageView catImageView;
    @Bind(R.id.ghostImageView) ImageView ghostImageView;

    private MoPubInterstitial interstitial;

    //region Activity Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        interstitial = new MoPubInterstitial(this, getString(R.string.mopub_fullscreen_unit_id));
        interstitial.setInterstitialAdListener(this);
        interstitial.load();

        setFontForButtons();
        setGifAsBackground();
        Log.i("ONCREATE: ", "just got called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ONRESUME: ", "just got called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        clearOtherLoops();
        Log.i("ONPAUSE: ", "just got called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ONSTOP: ", "just got called");
    }

    @Override
    protected void onDestroy() {
        interstitial.destroy();
        super.onDestroy();
    }
    //endregion

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


    //region Looped Background Tracks
    @OnClick({R.id.rocketImageView, R.id.catImageView, R.id.ghostImageView})
    public void onLoopedImageViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rocketImageView:
                controlLoopedTracksOnSelection(view, R.mipmap.rocketcircle, R.mipmap.rocketcirclewhite, mMediaPlayerLoop1);
                break;
            case R.id.catImageView:
                controlLoopedTracksOnSelection(view, R.mipmap.catcircle, R.mipmap.catcirclewhite, mMediaPlayerLoop2);
                break;
            case R.id.ghostImageView:
                controlLoopedTracksOnSelection(view, R.mipmap.ghostcircle, R.mipmap.ghostcirclewhite, mMediaPlayerLoop3);
                break;
            default:
                break;
        }
    }

    private void controlLoopedTracksOnSelection(View view, int imageId, int imageWhiteId, MediaPlayer mediaPlayer) {
        ImageView imageView = (ImageView) view;

        imageView.clearAnimation();
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        imageView.setAnimation(animScale);

        if (mediaPlayer == null) {
            stopAllBgSongs();
            imageView.setImageResource(imageWhiteId);
            imageView.startAnimation(animScale);
            setUpMediaPlayerForLoop(view);
        } else if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            imageView.setImageResource(imageId);
            imageView.clearAnimation();
        } else {
            stopAllBgSongs();
            mediaPlayer.start();
            imageView.setImageResource(imageWhiteId);
            imageView.startAnimation(animScale);
        }
    }

    private void stopAllBgSongs() {
        if (mMediaPlayerLoop1 != null) {
            if (mMediaPlayerLoop1.isPlaying()) {
                mMediaPlayerLoop1.pause();
                rocketImageView.setImageResource(R.mipmap.rocketcircle);
                rocketImageView.clearAnimation();
            }
        }
        if (mMediaPlayerLoop2 != null) {
            if (mMediaPlayerLoop2.isPlaying()) {
                mMediaPlayerLoop2.pause();
                catImageView.setImageResource(R.mipmap.catcircle);
                catImageView.clearAnimation();
            }
        }
        if (mMediaPlayerLoop3 != null) {
            if (mMediaPlayerLoop3.isPlaying()) {
                mMediaPlayerLoop3.pause();
                ghostImageView.setImageResource(R.mipmap.ghostcircle);
                ghostImageView.clearAnimation();
            }
        }
    }

    private void setUpMediaPlayerForLoop(View view) {
        switch (view.getId()) {
            case R.id.rocketImageView:
                mMediaPlayerLoop1 = MediaPlayer.create(this, R.raw.martianmonsterloop);
                mMediaPlayerLoop1.setLooping(true);
                mMediaPlayerLoop1.start();
                break;
            case R.id.catImageView:
                mMediaPlayerLoop2 = MediaPlayer.create(this, R.raw.petcatloop);
                mMediaPlayerLoop2.setLooping(true);
                mMediaPlayerLoop2.start();
                break;
            case R.id.ghostImageView:
                mMediaPlayerLoop3 = MediaPlayer.create(this, R.raw.ghostloop);
                mMediaPlayerLoop3.setLooping(true);
                mMediaPlayerLoop3.start();
                break;
            default:
                break;
        }
    }

    private void clearOtherLoops() {
        if (mMediaPlayerLoop1 != null) {
            if (mMediaPlayerLoop1.isPlaying()) {
                mMediaPlayerLoop1.pause();
                rocketImageView.setImageResource(R.mipmap.rocketcircle);
                rocketImageView.clearAnimation();
            }
        }
        if (mMediaPlayerLoop2 != null) {
            if (mMediaPlayerLoop2.isPlaying()) {
                mMediaPlayerLoop2.pause();
                catImageView.setImageResource(R.mipmap.catcircle);
                catImageView.clearAnimation();
            }
        }
        if (mMediaPlayerLoop3 != null) {
            if (mMediaPlayerLoop3.isPlaying()) {
                mMediaPlayerLoop3.pause();
                ghostImageView.setImageResource(R.mipmap.ghostcircle);
                ghostImageView.clearAnimation();
            }
        }
    }
    //endregion

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

    //region Fonts
    private void setFontForButtons() {
        Typeface customTypeFace = Typeface.createFromAsset(getAssets(), getString(R.string.FONT_ORBITRON));
            for (View view : soundButtons)
            {
                if (view instanceof Button)
                {
                    Button button = (Button) view;
                    button.setTypeface(customTypeFace);
                }
            }
    }
    //endregion

    // InterstitialAdListener methods
    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        if (interstitial.isReady()) {
            interstitial.show();
        }
    }

    //region MoPub
    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
        Log.d("MoPub", "Interstitial load failed: " + errorCode);
        interstitial.load();
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {
    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {
    }
    //endregion

}