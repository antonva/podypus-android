package is.hi.hbv601g.podypus.ui.player;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayerViewModel extends ViewModel {
    //Viewmodel for the player

    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<Bitmap> artwork = new MutableLiveData<>();

    //Title of episode
    public LiveData<String> getTitle() {
        if (this.title == null) {
            this.title = new MutableLiveData<>();
            this.title.setValue("Podcast Name");
        }
        return this.title;
    }
    //Getters and setters
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