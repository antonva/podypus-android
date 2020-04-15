package is.hi.hbv601g.podypus;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import is.hi.hbv601g.podypus.entities.SearchResult;
import is.hi.hbv601g.podypus.ui.player.PlayActivity;

public class MainActivityViewModel extends ViewModel {
    //MutanleLiveData variables
    public MutableLiveData<Boolean> authenticated;

    public MutableLiveData<SearchResult> searchResult;

    public MutableLiveData<String> username = new MutableLiveData<>();

    public MutableLiveData<String> episodeUrl = new MutableLiveData<>();
    public MutableLiveData<Long> channelId = new MutableLiveData<>();

    public MutableLiveData<Integer> playerTime = new MutableLiveData<Integer>();

    private PlayActivity player = PlayActivity.getInstance();

    //episodeUrl operations
    public void setEpisodeUrl(String url){ episodeUrl.postValue(url); }

    public String getEpisodeUrl(){
        return episodeUrl.getValue();
    }

    //channelId operations
    public void setChannelId(long id){ channelId.postValue(id); }

    public MutableLiveData<Long> getChannelId(){ return channelId; }

    //Authentication operations
    public LiveData<Boolean> isAuthenticated() {
        if (authenticated == null) {
            authenticated = new MutableLiveData<Boolean>();
            authenticated.setValue(false);
        }
        return authenticated;
    }

    public void setAuthenticated(boolean b) {
        this.authenticated.setValue(b);
    }

    //PlayerTimeKeeping operations
    public void setPlayerTime(int time){
        this.playerTime.setValue(time);
    }

    public Integer getPlayerTime(){
        return playerTime.getValue();
    }

    //Username operarions
    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) {
        this.username.postValue(username);
    }

    //PLayer operations
    public void loadAudioUrl(Context ctx, String url){
        try {
            player.loadAudioURL(ctx, url);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopStartFunction(Button b){
        player.stopStartFunction(b);
    }

    public void quitPlayback(){
        player.quitPlayback();
    }

    public int getDuration(){
        return player.getDuration();
    }

    public int getCurrentPos(){
        return player.getCurrentPos();
    }

    public void seek(int time){
        player.seek(time);
    }

    public void stopFunction(Button btn){
        player.stopStartFunction(btn);
    }

    public void startFunction(Button btn){
        player.startFunction(btn);
    }

    public Boolean isPlaying(){
        return player.isPlaying();
    }
}

