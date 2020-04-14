package is.hi.hbv601g.podypus.ui.podcasts;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PodcastViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PodcastViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}