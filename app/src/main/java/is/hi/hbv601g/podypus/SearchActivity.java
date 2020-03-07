package is.hi.hbv601g.podypus;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends ListActivity {

    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchChannel(query);
        }
        ListView list = (ListView) findViewById(R.id.searchList);
        Log.d(TAG, "onCreate: Started.");

        //TODO: Have a list of suggestions below search box in SearchActivity
        ArrayList<String> suggested = new ArrayList<>();
        suggested.add("Podcast 1");
        suggested.add("Podcast 2");
        suggested.add("Podcast 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggested);
        list.setAdapter(adapter);
    }

    //TODO: implement method that conducts search for podcast
    private void searchChannel(String query) {

    }

}
