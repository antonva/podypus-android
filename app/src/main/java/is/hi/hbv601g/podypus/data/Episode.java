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

    // Channel id as foreign key relation
    @ForeignKey(entity = Channel.class,
                parentColumns = "cid",
                childColumns = "channel",
                onDelete = CASCADE)
    public int channel;

    // Meta
    public int playbackPos;
    public boolean played;


    // Episode enclosure data
    public String enclosure_type;
    public String enclosure_length;
    public String enclosure_url; // Podcast file


    // Episode data
    public String guid;
    public String image;
    public String title;
    public String pubDate;
    public boolean guidIsPermanent;
    public String link;
    public String description;
    public String contentEncoded;
    public String duration;
    public String explicit;
    public String keywords;
    public String subtitle;
    public String summary;
    public String episode; // Episode number
    public String episodeType;

}