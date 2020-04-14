package is.hi.hbv601g.podypus.ui.podcasts;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import is.hi.hbv601g.podypus.MainActivityViewModel;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.Channel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PodcastsFragment extends Fragment implements ChannelAdapter.OnChannelListener {


    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private MainActivityViewModel model;
    private PodcastViewModel podcastViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Channel> myDataset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.podcastChannelRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myDataset = new ArrayList<>();
        mAdapter = new ChannelAdapter(myDataset, this);
        recyclerView.setAdapter(mAdapter);



        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user = sp.getString("username", "demouser");
        model.isAuthenticated().observe(getViewLifecycleOwner(), authenticated -> {
            if (authenticated.booleanValue()) {
            try {
                getSubscribedChannels(user, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.println(Log.ERROR, "Podcasts", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // Authentication success
                        if (response.code() == 200) {
                            Gson gson = new Gson();
                            Channel[] subscribedChannels = gson.fromJson(response.body().string(), Channel[].class);
                            for (Channel c: subscribedChannels) {
                                URL imageUrl = new URL(c.imageUrl);
                                c.image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                                myDataset.add(c);
                                Log.println(Log.INFO, "Podcasts", String.valueOf(c.title));
                                Log.println(Log.INFO, "Podcasts", String.valueOf(c.imageUrl));
                            }
                            getActivity().runOnUiThread(new Runnable(){
                                @Override
                                public void run() {
                                                        mAdapter.notifyDataSetChanged();
                                                                                        }
                            });
                        }
                        Log.println(Log.INFO, "Podcasts", String.valueOf(response.code()));
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });

        return root;
    }

    private Call getSubscribedChannels(String user, Callback callback) throws JSONException, IOException {
        String url = "https://podypus.punk.is/subscriptions";

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(user,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    @Override
    public void onChannelClick(View view, int position) {
        final Channel ch = myDataset.get(position);
        model.setChannelId(ch.id);
        NavHostFragment.findNavController(PodcastsFragment.this)
                .navigate(R.id.action_PodcastFragment_to_EpisodeFragment);
    }
}