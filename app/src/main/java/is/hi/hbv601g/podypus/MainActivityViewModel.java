package is.hi.hbv601g.podypus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import is.hi.hbv601g.podypus.entities.SearchResult;

public class MainActivityViewModel extends ViewModel {
    public MutableLiveData<Boolean> authenticated;

    public MutableLiveData<SearchResult> searchResult;

    public MutableLiveData<String> username = new MutableLiveData<>();

    public MutableLiveData<String> episodeUrl = new MutableLiveData<>();
    public MutableLiveData<Long> channelId = new MutableLiveData<>();

    //episodeUrl operations
    public void setEpisodeUrl(String url){ episodeUrl.setValue(url); }

    public MutableLiveData<String> getEpisodeUrl(){
        return episodeUrl;
    }

    //channelId operations
    public void setChannelId(long id){ channelId.setValue(id); }

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

    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) {
        this.username.postValue(username);
    }
}

