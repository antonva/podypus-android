package is.hi.hbv601g.podypus.ui.player;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlayerViewModel() {
        /*Fragment generated text sets predefined string value to screen as a string*/
        mText = new MutableLiveData<>();
        mText.setValue("This is the player");
    }

    public LiveData<String> getText() {
        return mText;
    }
}