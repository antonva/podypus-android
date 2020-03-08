package is.hi.hbv601g.podypus.ui.player;

import android.content.Context;
import android.media.MediaPlayer;

import is.hi.hbv601g.podypus.R;

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
        mp.setVolume(0.5f, 0.5f);
    }

    public void stopStartFunction(){
        if(!mp.isPlaying()) {
            mp.start();
        } else {
            mp.pause();
        }
    }

    public void quitPlayback(){
        mp.stop();
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
