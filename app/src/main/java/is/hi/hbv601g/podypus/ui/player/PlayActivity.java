package is.hi.hbv601g.podypus.ui.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.google.gson.internal.$Gson$Preconditions;

import is.hi.hbv601g.podypus.R;

public class PlayActivity {
    private static PlayActivity instance = null;
    private static MediaPlayer mp;
    private int duration;
    private String currentUrl;

    //Constructor for the singleton to insure only one instance of the player control object
    private PlayActivity(Handler handler){
        this.mp = new MediaPlayer();
        this.currentUrl = "";
        this.duration = 0;
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
               duration = mp.getDuration();
               Message msg = new Message();
               msg.what = duration;
               handler.sendMessage(msg);
               mp.start();
            }
        });

        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.println(Log.ERROR, "Playerrrrr",String.valueOf(what) + ", " +String.valueOf(extra));
                return false;
            }
        });
    }

    //Initialize audio locally on device
    public void loadAudioLocal(Context context){
        mp = MediaPlayer.create(context, R.raw.queen);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.7f, 0.7f);
    }

    //Function to load media files over URL
    public void loadAudioURL(Context context, String url) throws Exception {
        if (this.currentUrl != url) {
            mp.reset();
            this.currentUrl = url;
        }
        mp.setDataSource(url);
        mp.prepareAsync();
    }

    //Pause start handler
    public void stopStartFunction(Button button){
        if(!mp.isPlaying()) {
            mp.start();
            button.setBackgroundResource(R.drawable.round_pause_black_48);
        } else {
            mp.pause();
            button.setBackgroundResource(R.drawable.round_play_arrow_black_48);
        }
    }

    //Stop the player
    public void stopFunction(Button btn){
        if (mp.isPlaying()) {
            mp.pause();
        }
    }

    //Start the player
    public void startFunction(Button btn){
        mp.start();
    }

    //Check for if playback is currently running
    public Boolean isPlaying(){
        return mp.isPlaying();
    }

    //Quit playback
    public void quitPlayback(){
        mp.stop();
    }

    //Get total length of duration
    public int getDuration(){
        return this.duration;
    }

    //Go to duration x in file
    public void seek(int time){
        mp.seekTo(time);
    }

    //Get current duration
    public int getCurrentPos(){
        return mp.getCurrentPosition();
    }

    //Static functions
    //Get the player object
    public static PlayActivity getInstance(Handler durationHandler){
        if(instance == null) {
            instance = new PlayActivity(durationHandler);
        }
        return instance;
    }

}
