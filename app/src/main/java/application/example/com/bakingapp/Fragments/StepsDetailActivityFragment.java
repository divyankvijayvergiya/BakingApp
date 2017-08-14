package application.example.com.bakingapp.Fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import application.example.com.bakingapp.R;
import application.example.com.bakingapp.StepsDetailActivity;

import static application.example.com.bakingapp.Fragments.StepsIngredientsFragment.stepsArrayList;
import static application.example.com.bakingapp.MainActivity.isTablet;

/**
 * Created by Dell on 09-08-2017.
 */

public class StepsDetailActivityFragment extends Fragment implements ExoPlayer.EventListener {
    public static final String TAG = StepsDetailActivity.class.getSimpleName();
    private SimpleExoPlayer mSimpleExoPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private TextView longDescription;
    private Button prev, next;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    protected static int index = 0;
    private long currentPosition=0;
    boolean isPreparing;
    ExoPlayer.EventListener listener;


    public StepsDetailActivityFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail_fragment, container, false);
        longDescription = (TextView) rootView.findViewById(R.id.long_desrciption);
        prev = (Button) rootView.findViewById(R.id.prev_button);
        next = (Button) rootView.findViewById(R.id.next_button);


        mSimpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);
        initializeMediaSession();
        initializePlayer(Uri.parse(stepsArrayList.get(index).getVideoUrl()));

        if(savedInstanceState!=null){

            currentPosition=savedInstanceState.getLong("item");

            mSimpleExoPlayer.seekTo(currentPosition);


        }



        if (!isTablet) {
            index = getActivity().getIntent().getExtras().getInt("item");

        }
        getActivity().setTitle(stepsArrayList.get(index).getShortDescription());
        longDescription.setText(stepsArrayList.get(index).getDescription());


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    index--;
                    getActivity().setTitle(stepsArrayList.get(index).getShortDescription());
                    longDescription.setText(stepsArrayList.get(index).getDescription());
                    mSimpleExoPlayer.seekTo(0);
                    mSimpleExoPlayer.setPlayWhenReady(false);
                    initializePlayer(Uri.parse(stepsArrayList.get(index).getVideoUrl()));

                }


            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < stepsArrayList.size() - 1) {
                    index++;
                    getActivity().setTitle(stepsArrayList.get(index).getShortDescription());
                    longDescription.setText(stepsArrayList.get(index).getDescription());
                    mSimpleExoPlayer.seekTo(0);
                    mSimpleExoPlayer.setPlayWhenReady(false);
                    initializePlayer(Uri.parse(stepsArrayList.get(index).getVideoUrl()));


                }

            }
        });
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            fullScreenVideo();
            mSimpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            longDescription.setVisibility(View.GONE);
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        }


        return rootView;
    }

    private void fullScreenVideo() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY |
                        PlaybackStateCompat.ACTION_PAUSE |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);
        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallBack());
        mMediaSession.setActive(true);


    }

    private void initializePlayer(Uri mediaUri) {
        if (mSimpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "StepsDetailActivityFragment");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);

        } else {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mSimpleExoPlayer);
            mSimpleExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), "StepsDetailActivityFragment");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);

        }


    }

    private void releasePlayer() {
        mSimpleExoPlayer.stop();
        mSimpleExoPlayer.release();
        mSimpleExoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSimpleExoPlayer.setPlayWhenReady(false);
        currentPosition=mSimpleExoPlayer.getCurrentPosition();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMediaSession.setActive(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mMediaSession.setActive(false);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mSimpleExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mSimpleExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    private class MySessionCallBack extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mSimpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mSimpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {

            mSimpleExoPlayer.seekTo(0);
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("item",currentPosition);

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }


    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
