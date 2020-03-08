package is.hi.hbv601g.podypus.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Represents the podypus app user. The assumption is that there will only ever be one user using
// the application.
// TODO: Switch to Oauth2 and log in with google credentials

@Entity
public class User {
    @NonNull @PrimaryKey
    public String username;
    public String password;
}
