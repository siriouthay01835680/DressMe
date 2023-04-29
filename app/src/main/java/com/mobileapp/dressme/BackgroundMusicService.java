package com.mobileapp.dressme;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BackgroundMusicService extends Service {
    //this fragment is created for initializing the media player in order to play music in the app
    MediaPlayer mediaPlayer;
    //initializing media player
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startid)
    {
        //when this function is called, the media player will create the music
        //by getting access to the raw res folder where the song is stored.
        mediaPlayer = MediaPlayer.create(this, R.raw.moonshine); //or kyoto
        mediaPlayer.start();
        //music will start playing and looping until user chooses otherwise
        mediaPlayer.setLooping(true);
        return super.onStartCommand(intent, flags, startid);
    }
    @Override
    public boolean stopService(Intent name)
    {
        return super.stopService(name);
    }

    @Override
    public void onDestroy()
    {
        //destroys and releases the media player, so the music is
        // not overlapping on itself
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        //emptying it out, so music does not overlap
    }
}
