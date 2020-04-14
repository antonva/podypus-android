package is.hi.hbv601g.podypus.ui.player;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import is.hi.hbv601g.podypus.MainActivityViewModel;
import is.hi.hbv601g.podypus.R;

public class PlayerFragment extends Fragment {

    private PlayerViewModel playerViewModel;
    private PlayActivity player = PlayActivity.getInstance();
    private Handler handler;
    private MainActivityViewModel model;

    //Fragment view opener.
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_player, container, false);
        final TextView textView = root.findViewById(R.id.text_player);
        playerViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //setup Player(Local mp3 only) - Replace LoadAudio R.id.queen to url for stream
        //Currently only local
        try {
            player.loadAudioURL(root.getContext(), model.getEpisodeUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Image placeholder(Currently only implemented for local)
        ImageView artWork = (ImageView)root.findViewById(R.id.artcover);

        //Play button interaction
        final Button playStop = (Button)root.findViewById(R.id.buttonPlayStop);
        playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "PLaying or stopping dunno", Toast.LENGTH_SHORT).show();
                player.stopStartFunction(playStop);
            }
        });

        //Quit button interaction
        final Button stop = (Button)root.findViewById(R.id.buttonQuit);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Stopping the audio and quiting", Toast.LENGTH_SHORT).show();
                player.quitPlayback();
            }
        });

        //Seekbar, playback user interaction
        final SeekBar timeBar= (SeekBar)root.findViewById(R.id.timeelapsed);
        timeBar.setMax(player.getDuration());
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    player.seek(progress);
                    timeBar.setProgress(progress);
                }
            }
            //Purposely empty. SeekBar class requires it to be stated.
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            //Purposely empty, SeekBar class requires it to be stated.
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Handler for updating the seekbar on runtime
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                int currentPosition = msg.what;
                //Update pos on timebar
                timeBar.setProgress(currentPosition);

            }
        };

        //Thread to continously update the position of playback(timebar)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(player != null){
                    try{
                        Message msg = new Message();
                        msg.what = player.getCurrentPos();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e){

                    }
                }
            }
        }).start();

        return root;
    }
}