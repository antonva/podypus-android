package is.hi.hbv601g.podypus.ui.player;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayerViewModel extends ViewModel {

    private MutableLiveData<String> title;
    private MutableLiveData<Bitmap> artwork;

    public PlayerViewModel() {
        /*Fragment generated text sets predefined string value to screen as a string*/
        title = new MutableLiveData<>();
        title.setValue("This is the player");
        artwork = new MutableLiveData<>();
    }

    public LiveData<String> getTitle() {
        if (this.title == null) {
            this.title = new MutableLiveData<>();
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title.postValue(title);
    }

    public LiveData<Bitmap> getArtwork() {
        if (this.artwork == null) {
            this.artwork = new MutableLiveData<>();
        }
        return this.artwork;
    }

    public void setArtwork(Bitmap bitmap) {
        this.artwork.postValue(bitmap);
    }
}