package is.hi.hbv601g.podypus.ui.search;

import java.util.List;

import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.SearchItem;

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
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        final SearchItem si = mDataset.get(position);
        holder.textView.setText(si.collectionName);
        holder.imageView.setImageBitmap(si.image);
        holder.subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.INFO, "Button", "Subscribe Button clicked " + si.collectionName);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
