package is.hi.hbv601g.podypus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    public MutableLiveData<Boolean> authenticated;

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
}

