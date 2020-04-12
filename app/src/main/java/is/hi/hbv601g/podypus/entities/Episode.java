package is.hi.hbv601g.podypus.entities;

import android.graphics.Bitmap;

public class Episode {
    public long id;

    public long channel_id;
    public double playbackPos;
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

    public Bitmap bitmapImage;
}
