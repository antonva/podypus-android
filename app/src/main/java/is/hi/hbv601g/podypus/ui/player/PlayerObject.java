package is.hi.hbv601g.podypus.ui.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Button;

import is.hi.hbv601g.podypus.R;

import static androidx.core.content.ContextCompat.startActivity;

public class PlayerObject {
    private static PlayerObject instance = null;
    private static MediaPlayer mp;
    //Constructor functions
    //Constructor for the singleton to insure only one instance of the player control object
    private PlayerObject(){
        this.mp = new MediaPlayer();
    }

    public void loadAudio(Context context){
        mp = MediaPlayer.create(context, R.raw.queen);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.7f, 0.7f);
    }

    public void stopStartFunction(Button button){
        if(!mp.isPlaying()) {
            mp.start();
            button.setBackgroundResource(R.drawable.pause);
        } else {
            mp.pause();
            button.setBackgroundResource(R.drawable.play);
        }
    }

    public void quitPlayback(){
        mp.stop();

    }

    public int getDuration(){
        return mp.getDuration();
    }

    public void seek(int time){
        mp.seekTo(time);
    }

    public int getCurrentPos(){
        return mp.getCurrentPosition();
    }

    //Static functions
    //Get the player object
    public static PlayerObject getInstance(){
        if(instance == null) {
            instance = new PlayerObject();
        }
        return instance;
    }

}
