package is.hi.hbv601g.podypus.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    public static List<SearchResult> searchResults = new ArrayList<>();

    public long id;
    public String title;
    public String name;
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

    public Bitmap image;
}
