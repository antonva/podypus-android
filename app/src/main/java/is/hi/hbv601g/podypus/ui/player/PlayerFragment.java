package is.hi.hbv601g.podypus.ui.player;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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

public class PlayerFragment extends Fragment {

    //Player views and operation crucial variables
    private PlayerViewModel playerViewModel;
    private PlayActivity player;
    private Handler handler;
    private MainActivityViewModel model;
    private TextView mTitle;
    private ImageView mArtwork;
    private Handler preparedHandler;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    //Fragment view opener.
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Fragment variables
        playerViewModel = new ViewModelProvider(requireActivity()).get(PlayerViewModel.class);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_player, container, false);
        mTitle = root.findViewById(R.id.player_title);
        mArtwork = root.findViewById(R.id.artcover);
        Executor mExecutor = Executors.newSingleThreadExecutor();

        //Seekbar, playback user interaction
        final SeekBar timeBar= (SeekBar)root.findViewById(R.id.timeelapsed);

        // Current playback position and total duration of episode
        final TextView currTime = (TextView)root.findViewById(R.id.currentTime);
        final TextView totalTime = (TextView)root.findViewById(R.id.totalTime);

        preparedHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int duration = msg.arg1;
                int currpos = player.getCurrentPos();
                //playback finished
                if (msg.arg2 == 1) {
                    try {
                        updatePlaybackPosition(true, model.getUsername(), model.currentEpisode.getValue(), currpos, new okhttp3.Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            }
                        });
                    } catch (JSONException e) {
                    } catch (IOException e) {
                    }
                }
                // Function requires seconds but duration is in ms.
                String ltdur = LocalTime.ofSecondOfDay(duration/1000).toString();
                String ltcur = LocalTime.ofSecondOfDay(currpos/1000).toString();
                totalTime.setText(ltdur);
                currTime.setText(ltcur);
                timeBar.setMax(duration);

                //Listener that updates the seekbar every iteration
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
            }
        };

        //Player instance
        player = PlayActivity.getInstance(preparedHandler);

        //Episode update observer, updates episodes when they change in the player
        model.currentEpisode.observe(getViewLifecycleOwner(), new Observer<Episode>() {
            @Override
            public void onChanged(Episode episode) {
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL iu = new URL(episode.image);
                            Episode e = model.getCurrentEpisode().getValue();
                            long p = (long) (e.playbackPos * 1000);
                            playerViewModel.setArtwork(BitmapFactory.decodeStream(iu.openConnection().getInputStream()));
                            playerViewModel.setTitle(episode.title);
                            player.loadAudioURL(root.getContext(), episode.enclosure_url, e.playbackPos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                        }
                    }
                });

            }
        });

        playerViewModel.getTitle().observe(getViewLifecycleOwner(), title -> {
            mTitle.setText(title);
        });

        playerViewModel.getArtwork().observe(getViewLifecycleOwner(), bitmap -> {
            mArtwork.setImageBitmap(bitmap);
        });

        //Play button interaction
        final Button playStop = (Button)root.findViewById(R.id.buttonPlayStop);
        playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stopStartFunction(playStop);
            }
        });

        //Quit button interaction
        final Button stop = (Button)root.findViewById(R.id.buttonForward);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.quitPlayback();
            }
        });

        //Forward button
        final Button forward = (Button)root.findViewById(R.id.buttonForward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stopStartFunction(playStop);
                player.seek(player.getCurrentPos() + 10000);
                player.stopStartFunction(playStop);
            }
        });

        //Backward button
        final Button backward = (Button)root.findViewById(R.id.buttonReplay);
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stopStartFunction(playStop);
                player.seek(player.getCurrentPos() - 10000);
                player.stopStartFunction(playStop);
            }
        });

        //Handler for updating the seekbar on runtime
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                int currentPosition = msg.what;
                //Update pos on timebar
                timeBar.setProgress(currentPosition);
                //API expects seconds
                model.setPlayerTime(currentPosition/1000);
                try {
                    updatePlaybackPosition(false, model.getUsername(), model.currentEpisode.getValue(), model.getPlayerTime(), new okhttp3.Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            //TODO: Maybe do something here
                            Log.println(Log.INFO, "PLAYER", "Updated playback position");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String ltc = LocalTime.ofSecondOfDay(currentPosition/1000).toString();
                currTime.setText(ltc);

            }
        };

        //Thread to continously update the position of playback(timebar)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(player != null){
                    if(player.isPlaying()) {
                        try {
                            Message msg = new Message();
                            msg.what = player.getCurrentPos();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        //Seekbar update listener
        int duration = player.getDuration();
        String ltd = LocalTime.ofSecondOfDay(duration/1000).toString();
        totalTime.setText(ltd);
        timeBar.setMax(duration);
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

        return root;
    }

    Call updatePlaybackPosition(boolean ended, String user, Episode ep, int time, okhttp3.Callback callback) throws JSONException, IOException {
        String url = "https://podypus.punk.is/update-playback-pos";

        OkHttpClient client = new OkHttpClient();
        JSONObject js = new JSONObject();
        js.put("username", user);
        js.put("id", ep.id);
        js.put("pos", time);
        js.put("ended", ended);
        String reqBody = js.toString();
        RequestBody body = RequestBody.create(reqBody,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}