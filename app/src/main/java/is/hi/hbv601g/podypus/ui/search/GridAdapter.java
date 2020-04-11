package is.hi.hbv601g.podypus.ui.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.Channel;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ChannelViewHolder> {

    private List<Channel> mDataset;

    public GridAdapter(List<Channel> channelData) {
        this.mDataset = channelData;
    }

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public ChannelViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.tv);
            imageView = v.findViewById(R.id.iv);
        }
    }

    @NonNull
    @Override
    public GridAdapter.ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        ChannelViewHolder cvh = new ChannelViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        Channel c = mDataset.get(position);
        holder.textView.setText(c.title);
        holder.imageView.setImageBitmap(c.image);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
