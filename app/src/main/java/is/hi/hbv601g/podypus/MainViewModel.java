package is.hi.hbv601g.podypus;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//TODO: hook this up to PodcastsFragment, EpisodeFragment and PlayerFragment
//TODO: Use to pass values around.
public class MainViewModel extends ViewModel {
    public MutableLiveData<String> episodeUrl = new MutableLiveData<>();
    public MutableLiveData<Long> channelId = new MutableLiveData<>();


    //episodeUrl operations
    public void setEpisodeUrl(String url){
        episodeUrl.setValue(url);
    }

    public MutableLiveData<String> getEpisodeUrl(){
        return episodeUrl;
    }
    //channelId operations
    public void setChannelId(long id){
        channelId.setValue(id);
    }

    public MutableLiveData<Long> getChannelId(){
        return channelId;
    }
}
