package is.hi.hbv601g.podypus;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


//TODO: hook this up to PodcastsFragment, EpisodeFragment and PlayerFragment
//TODO: Use to pass values around.
public class MainViewModel extends ViewModel {
    MutableLiveData<String> episodeUrl = new MutableLiveData<>();
    MutableLiveData<Long> channelId = new MutableLiveData<>();
}
