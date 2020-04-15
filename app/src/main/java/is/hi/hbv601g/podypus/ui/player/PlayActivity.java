package is.hi.hbv601g.podypus.ui.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Button;

import java.io.IOException;

import is.hi.hbv601g.podypus.R;

public class PlayActivity {
    private static PlayActivity instance = null;
    private static MediaPlayer mp;

    //Constructor functions
    //Constructor for the singleton to insure only one instance of the player control object
    private PlayActivity(){
        this.mp = new MediaPlayer();
    }

    //Initialize audio locally
    public void loadAudioLocal(Context context){
        mp = MediaPlayer.create(context, R.raw.queen); // To be URL-ed
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.7f, 0.7f);
    }

    //Function to load media files over URL
    public void loadAudioURL(Context context, String url) throws Exception {
        mp.reset();
        mp.setDataSource(url);
        mp.prepare();
        mp.start();
    }

    //Pause start handler
    public void stopStartFunction(Button button){
        if(!mp.isPlaying()) {
            mp.start();
            button.setBackgroundResource(R.drawable.stop);
        } else {
            mp.pause();
            button.setBackgroundResource(R.drawable.play);
        }
    }

    //Stop the player
    public void stopFunction(Button btn){
        mp.pause();
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
        return mp.getDuration();
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
    public static PlayActivity getInstance(){
        if(instance == null) {
            instance = new PlayActivity();
        }
        return instance;
    }

}
