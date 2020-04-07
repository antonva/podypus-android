package is.hi.hbv601g.podypus.ui.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import is.hi.hbv601g.podypus.R;

public class SearchableActivity extends Activity {

    private String TAG = SearchableActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.println(Log.INFO,"BOOP", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchChannels(query);
        }
    }

    //TODO: implement method that conducts search for podcast
    private void searchChannels(String query) {
        Log.println(Log.INFO,"BOOP", "searchChannel");
        String url = "https://podypus.punk.is/search";

        //Volley json array request object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.println(Log.INFO,"SEARCH", "Response: " + response.toString());
                        //textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Server Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        // Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        /*JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {
                            //looping through json and adding to podcast list
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject podcastObj = response.getJSONObject(i);
                                    String title = podcastObj.getString("title");
                                    String imageURL = podcastObj.getString("imageURL");
                                    //búa til podcast hlut og setja í lista??
                                    //Channel c = new Channel()
                                    //channelList.add();
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
    }
}