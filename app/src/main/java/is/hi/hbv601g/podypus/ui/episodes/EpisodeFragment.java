package is.hi.hbv601g.podypus.ui.episodes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601g.podypus.MainActivityViewModel;
import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.Channel;
import is.hi.hbv601g.podypus.entities.Episode;
import is.hi.hbv601g.podypus.ui.podcasts.PodcastsFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EpisodeFragment extends Fragment implements EpisodeAdapter.OnEpisodeListener {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MainActivityViewModel model;
    private List<Episode> myDataset;
    long channelId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        recyclerView = (RecyclerView) root.findViewById(R.id.podcastChannelRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myDataset = new ArrayList<>();
        mAdapter = new EpisodeAdapter(myDataset, this);
        recyclerView.setAdapter(mAdapter);
        channelId = model.getChannelId().getValue();

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user = sp.getString("username", "demouser");
        try {
            getSubscribedEpisodes(user, channelId, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.println(Log.ERROR, "Episodes", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // Authentication success
                    if (response.code() == 200) {
                        Gson gson = new Gson();
                        Episode[] subscribedEpisodes = gson.fromJson(response.body().string(), Episode[].class);
                        for (Episode e: subscribedEpisodes) {
                            myDataset.add(e);
                        }
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.println(Log.INFO, "Episodes", String.valueOf(response.code()));
                    Log.println(Log.INFO, "Episodes", String.valueOf(myDataset.size()));
                    response.close();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    private Call getSubscribedEpisodes(String user, long channelId, Callback callback) throws JSONException, IOException {
        String url = "https://podypus.punk.is/episodes";

        OkHttpClient client = new OkHttpClient();

        JSONObject lf = new JSONObject();
        lf.put("channel_id",channelId);
        lf.put("username",user);
        final String reqBody = lf.toString();
        RequestBody body = RequestBody.create(reqBody,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    @Override
    public void onEpisodeClick(View view, int position) {
        final Episode e = myDataset.get(position);
        model.setCurrentEpisode(e);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_EpisodeFragment_to_PlayerFragment);
    }
}
