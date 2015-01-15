package com.example.hellomoon.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.example.hellomoon.R;


    public class VideoFragment extends Fragment {

       private MediaPlayer   mPlayer;
       private SurfaceHolder mSurfaceHolder;
       private SurfaceView   mSurfaceView;
       
       
       @Override
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setRetainInstance(true);  // fragment not destroyed with activity (rotation) keeps instance vars
       }
       
       @Override
       public void onDestroy()
       {
          super.onDestroy();
          mPlayer.release();
       }
       

       @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
       {
          View v = inflater.inflate(R.layout.fragment_video_container, container, false);

          mSurfaceView = (SurfaceView) v.findViewById(R.id.videoSurface);
          mSurfaceHolder = mSurfaceView.getHolder();
          mSurfaceHolder.addCallback(new SurfaceHolderCallback());

          return v;
       }

       class SurfaceHolderCallback implements SurfaceHolder.Callback {
          @Override
          public void surfaceCreated(SurfaceHolder surfaceHolder)
          
          {
             if (mPlayer == null)  {
                 mPlayer = MediaPlayer.create(getActivity(), R.raw.apollo_17_stroll);
                 mPlayer.setOnCompletionListener(new VideoCompletionListener());
                 mPlayer.setOnPreparedListener(new VideoPreparedListener());
             } 
             mPlayer.setDisplay(mSurfaceHolder);
          }

          @Override public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) { }
          @Override public void surfaceDestroyed(SurfaceHolder surfaceHolder) { }
       }

       class VideoCompletionListener implements MediaPlayer.OnCompletionListener {

          @Override
          public void onCompletion(MediaPlayer mediaPlayer)
          {
             mediaPlayer.release();
             mediaPlayer = null;
             getActivity().finish();
             
             
          }
       }

       class VideoPreparedListener implements MediaPlayer.OnPreparedListener {
          @Override
          public void onPrepared(MediaPlayer mediaPlayer)
          {
             mPlayer.start();
          }
       }
    }
