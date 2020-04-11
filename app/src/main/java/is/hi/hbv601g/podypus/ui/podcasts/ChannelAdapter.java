package is.hi.hbv601g.podypus.ui.podcasts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.Channel;

class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    private List<Channel> mDataset;

    public static class ChannelViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public ChannelViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.textview_name);
            imageView = (ImageView) v.findViewById(R.id.imageview_profile);
        }
    }

    public ChannelAdapter(List<Channel> p0) {
        mDataset = p0;
    }

    @NonNull
    @Override
    public ChannelAdapter.ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_title_view, parent, false);
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
