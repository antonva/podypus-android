package is.hi.hbv601g.podypus.ui.episodes;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.Episode;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {
    private List<Episode> mDataset;
    private OnEpisodeListener mOnEpisodeListener;

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleView;
        public TextView timeView;
        public ImageView imageView;
        OnEpisodeListener onEpisodeListener;

        public EpisodeViewHolder(View v, OnEpisodeListener onEpisodeListener) {
            super(v);
            titleView = (TextView) v.findViewById(R.id.textview_name);
            timeView = (TextView) v.findViewById(R.id.episode_current_timestamp);
            this.onEpisodeListener = onEpisodeListener;

            titleView.setOnClickListener(this);
            timeView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEpisodeListener.onEpisodeClick(v, getAdapterPosition());
        }
    }
    public EpisodeAdapter(List<Episode> p0, OnEpisodeListener onEpisodeListener) {
        this.mDataset = p0;
        this.mOnEpisodeListener = onEpisodeListener;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_title_view, parent, false);
        EpisodeViewHolder evh = new EpisodeViewHolder(v, mOnEpisodeListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode e = mDataset.get(position);
        if(e.played) {
            holder.titleView.setTextColor(Color.parseColor("#C0C0C0"));
            holder.timeView.setTextColor(Color.parseColor("#C0C0C0"));
        }
        holder.titleView.setText(e.title);
        String t = LocalTime.ofSecondOfDay((long) e.playbackPos).toString();
        holder.timeView.setText(t);
    }

    @Override
    public int getItemCount() {
        return this.mDataset.size();
    }

    public interface OnEpisodeListener {
        void onEpisodeClick(View view, int position);
    }
}
