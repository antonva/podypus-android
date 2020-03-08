package is.hi.hbv601g.podypus.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Channel {

    @NonNull @PrimaryKey
    public int cid;

    public String name;
    public String title;
    public String pubDate;
    public String lastBuildDate;
    public String generator;
    public String link;
    public String language;
    public String copyright;
    public String docs;
    public String managingEditor;
    public String description;
    public String summary;
    public String imageUrl;
    public String explicit;
    public String type;
    public String keywords;
    public String category;
}
