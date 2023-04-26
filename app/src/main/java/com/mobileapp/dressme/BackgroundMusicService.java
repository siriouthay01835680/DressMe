package com.mobileapp.dressme;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BackgroundMusicService extends Service {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startid)
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.moonshine); //or kyoto
        mediaPlayer.start();
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
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
