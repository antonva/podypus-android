package is.hi.hbv601g.podypus.ui.search;

import java.io.IOException;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.SearchItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SearchViewHolder> {

    private List<SearchItem> mDataset;

    public GridAdapter(List<SearchItem> searchResultData) {
        this.mDataset = searchResultData;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        static Button subscribeButton;

        public SearchViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.tv);
            imageView = v.findViewById(R.id.iv);
            subscribeButton = v.findViewById(R.id.subscribeButton);
            /*subscribeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.println(Log.INFO, "Button", "Subscribe Button clicked " + getAdapterPosition());
                }
            });*/
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        SearchViewHolder svh = new SearchViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {
        final SearchItem si = mDataset.get(position);
        holder.textView.setText(si.collectionName);
        holder.imageView.setImageBitmap(si.image);
        holder.subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.INFO, "Button", "Subscribe Button clicked " + si.feedUrl);
                try {
                    postSubscription(si);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    //function that posts user's new subscriptions to server
    private void postSubscription(SearchItem i) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String url = "https://podypus.punk.is/subscribe";
        OkHttpClient client = new OkHttpClient();

        JSONObject jo = new JSONObject();
        final String reqBody = jo.toString();

        try {
            jo.put("username", "demouser");
            jo.put("url", i.feedUrl);
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(reqBody, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String mMessage = response.body().string();
                Log.println(Log.INFO, "post", mMessage);
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                String mMessage = e.getMessage().toString();
                Log.println(Log.ERROR, "failure Response", mMessage);
            }
        });
    }
}
