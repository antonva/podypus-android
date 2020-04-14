package is.hi.hbv601g.podypus.ui.episodes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbv601g.podypus.R;
import is.hi.hbv601g.podypus.entities.Episode;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {
    private List<Episode> mDataset;

    public EpisodeAdapter(List<Episode> p0) {
        mDataset = p0;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_title_view, parent, false);
        EpisodeViewHolder evh = new EpisodeViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode e = mDataset.get(position);
        holder.textView.setText(e.title);
        holder.imageView.setImageBitmap(e.bitmapImage);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textview_name);
            imageView = (ImageView) itemView.findViewById(R.id.imageview_profile);
        }
    }
}
