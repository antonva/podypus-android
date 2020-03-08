package is.hi.hbv601g.podypus.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

// The object representation of a podcast episode
// TODO: Figure out how to store downloaded episodes, in the room db or localstorage, etc.

@Entity
public class Episode {
    @PrimaryKey
    public int eid;

    @ForeignKey(entity = Channel.class,
                parentColumns = "cid",
                childColumns = "channel",
                onDelete = CASCADE)
    public int channel;

    public int playbackPos;
    public boolean played;
}