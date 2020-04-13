package is.hi.hbv601g.podypus.ui.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import is.hi.hbv601g.podypus.MainActivity;
import is.hi.hbv601g.podypus.MainActivityViewModel;
import is.hi.hbv601g.podypus.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private MainActivityViewModel model;
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    Intent intent;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        usernameField = (EditText) view.findViewById(R.id.loginUsername);
        passwordField = (EditText) view.findViewById(R.id.loginPassword);
        loginButton = (Button) view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        // Create the intent of returning to the main activity with a successful login
        intent = new Intent(getActivity(), MainActivity.class);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_RegisterFragment);
            }
        });
    }

    private Call login(final String user, final String password, Callback callback) throws JSONException, IOException {
        String url = "https://podypus.punk.is/login";
        JSONObject lf = new JSONObject();
        lf.put("username",user);
        lf.put("password",password);
        final String reqBody = lf.toString();

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(reqBody,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    @Override
    public void onClick(View v) {
        try {
            final String user = usernameField.getText().toString();
            final String password = passwordField.getText().toString();
            login(user, password, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                     Log.println(Log.ERROR, "Login", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // Authentication success
                    Log.println(Log.INFO, "Login", user + " " + password);
                    if (response.code() == 200) {
                        SharedPreferences.Editor spe = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                        spe.putString("username", user);
                        spe.putString("password", password);
                        Log.println(Log.INFO, "Login", user + " " + password);
                        spe.commit();
                        model.authenticated.postValue(true);
                        //startActivity(intent);
                    }
                    Log.println(Log.INFO, "Login", String.valueOf(response.code()));
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
