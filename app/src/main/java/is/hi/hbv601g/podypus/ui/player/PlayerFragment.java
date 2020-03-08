package is.hi.hbv601g.podypus.ui.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import is.hi.hbv601g.podypus.R;

public class PlayerFragment extends Fragment {

    private PlayerViewModel playerViewModel;
    private PlayerObject player = PlayerObject.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        System.out.print("In the on create in player");
        playerViewModel =
                ViewModelProviders.of(this).get(PlayerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        playerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //setup Player
        player.loadAudio(root.getContext());

        /*Play button interaction*/
        final Button playStop = (Button)root.findViewById(R.id.buttonPlayStop);
        playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "PLaying or stopping dunno", Toast.LENGTH_SHORT).show();
                player.stopStartFunction();
            }
        });

        /*Quit button interaction*/
        final Button stop = (Button)root.findViewById(R.id.buttonQuit);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Stopping the audio and quiting", Toast.LENGTH_SHORT).show();
                player.quitPlayback();
            }
        });

        return root;
    }


}