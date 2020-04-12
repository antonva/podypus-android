package is.hi.hbv601g.podypus.ui.search;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.SearchResult;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SearchViewHolder> {

    private List<SearchResult> mDataset;

    public GridAdapter(List<SearchResult> searchResultData) {
        this.mDataset = searchResultData;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public SearchViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.tv);
            imageView = v.findViewById(R.id.iv);
        }
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        SearchViewHolder cvh = new SearchViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchResult c = mDataset.get(position);
        holder.textView.setText(c.title);
        holder.imageView.setImageBitmap(c.image);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
