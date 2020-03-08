package is.hi.hbv601g.podypus.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Channel {

    @PrimaryKey
    public int cid;

    public String name;
}
