package is.hi.hbv601g.podypus.ui.search;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import is.hi.hbv601g.podypus.MainActivityViewModel;
import is.hi.hbv601g.podypus.entities.SearchItem;
import is.hi.hbv601g.podypus.entities.SearchResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601g.podypus.R;

public class SearchFragment extends Fragment implements View.OnClickListener {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private MainActivityViewModel model;
    private SearchView searchView = null;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<SearchItem> searchResultData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        Log.println(Log.INFO,"BOOP", "search should have inflated");
        recyclerView = (RecyclerView) root.findViewById(R.id.searchChannelRecycler);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        searchResultData = new ArrayList<>();
        mAdapter = new GridAdapter(searchResultData, model.getUsername());
        recyclerView.setAdapter(mAdapter);

        searchView = root.findViewById(R.id.searchView);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchResultData.clear();
                onClick(searchView);
                searchView.setQuery("", false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    return root;
    }

    private Call search(final String term, Callback callback) throws JSONException, IOException {
        Log.println(Log.INFO,"BOOP", "search: " + term);
        String url = "https://podypus.punk.is/search";
        JSONObject jo = new JSONObject();
        jo.put("term", term);
        final String reqBody = jo.toString();

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(reqBody,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    @Override
    public void onClick(View v) {
        try {
            final String term = searchView.getQuery().toString();
            search(term, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.println(Log.ERROR, "Search", e.toString());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        SearchResult sr = gson.fromJson(response.body().string(), SearchResult.class);
                        for (SearchItem i: sr.results) {
                            URL imageUrl = new URL(i.artworkUrl100);
                            i.image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                            searchResultData.add(i);
                        }
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.println(Log.INFO, "Search", String.valueOf(response.code()));
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}