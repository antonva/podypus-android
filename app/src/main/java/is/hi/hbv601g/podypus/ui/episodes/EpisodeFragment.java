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
import is.hi.hbv601g.podypus.entities.Episode;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EpisodeFragment extends Fragment {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Episode> myDataset;

    MainActivityViewModel model;
    long channelId = ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_podcasts, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.podcastChannelRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        myDataset = new ArrayList<>();
        mAdapter = new EpisodeAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        Bundle args = getArguments();
        channelId = args.getLong("channelId", 0 );

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user = sp.getString("username", "demouser");
        try {
            getSubscribedChannels(user, channelId, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.println(Log.ERROR, "Episodes", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // Authentication success
                    if (response.code() == 200) {
                        Gson gson = new Gson();
                        Episode[] subscribedChannels = gson.fromJson(response.body().string(), Episode[].class);
                        for (Episode c: subscribedChannels) {
                            URL imageUrl = new URL(c.image);
                            c.bitmapImage = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                            myDataset.add(c);
                            Log.println(Log.INFO, "Episodes", String.valueOf(c.title));
                            Log.println(Log.INFO, "Episodes", String.valueOf(c.image));
                        }
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    Log.println(Log.INFO, "Episodes", String.valueOf(response.code()));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    private Call getSubscribedChannels(String user, long channelId, Callback callback) throws JSONException, IOException {
        String url = "https://podypus.punk.is/subscriptions";

        OkHttpClient client = new OkHttpClient();

        JSONObject lf = new JSONObject();
        lf.put("channel_id",channelId);
        lf.put("username",user);
        final String reqBody = lf.toString();
        RequestBody body = RequestBody.create(user,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

}
