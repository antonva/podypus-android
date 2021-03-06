package is.hi.hbv601g.podypus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import is.hi.hbv601g.podypus.entities.Episode;
import is.hi.hbv601g.podypus.entities.SearchResult;

public class MainActivityViewModel extends ViewModel {
    //MutableLiveData variables
    public MutableLiveData<Boolean> authenticated;
    public MutableLiveData<SearchResult> searchResult = new MutableLiveData<>();
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<Episode> currentEpisode = new MutableLiveData<>();
    public MutableLiveData<Long> channelId = new MutableLiveData<>();
    public MutableLiveData<Integer> playerTime = new MutableLiveData<Integer>();

    //Variable operations
    //episodeUrl operations
    public void setCurrentEpisode(Episode episode){ this.currentEpisode.postValue(episode); }

    public LiveData<Episode> getCurrentEpisode(){
        return currentEpisode;
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

    public void updatePlaybackPos(Double pos) {
        Episode tmpep = this.currentEpisode.getValue();
        tmpep.playbackPos = pos;
        this.currentEpisode.postValue(tmpep);
    }

    public SearchResult getSearchResult() {
        return searchResult.getValue();
    }

    public void setSearchResult(SearchResult result) {
        this.searchResult.postValue(result);
    }

}

