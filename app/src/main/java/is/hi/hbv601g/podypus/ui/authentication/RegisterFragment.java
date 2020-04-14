package is.hi.hbv601g.podypus.ui.authentication;

import android.app.Activity;
import android.content.Context;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import is.hi.hbv601g.podypus.MainActivityViewModel;
import is.hi.hbv601g.podypus.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private MainActivityViewModel model;
    private EditText passwordField;
    private EditText usernameField;
    private EditText emailField;
    private Button registerButton;
    private NavController navController;
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        model = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        usernameField = (EditText) view.findViewById(R.id.registerUsername);
        passwordField = (EditText) view.findViewById(R.id.registerPassword);
        emailField = (EditText) view.findViewById(R.id.registerEmail);
        registerButton = (Button) view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        navController = NavHostFragment.findNavController(RegisterFragment.this);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_RegisterFragment_to_LoginFragment);
            }
        });
    }


    public Call register(String user, String password, String email, Callback callback) throws JSONException, IOException{
        String url = "https://podypus.punk.is/register";
        JSONObject lf = new JSONObject();
        lf.put("username",user);
        lf.put("password",password);
        lf.put("email",email);
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
            final String email = emailField.getText().toString();
            register(user, password, email, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.println(Log.ERROR, "Register", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // Authentication success
                    Log.println(Log.INFO, "Register", user + " " + password);
                    if (response.code() == 200) {
                        SharedPreferences.Editor spe = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                navController.navigate(R.id.action_RegisterFragment_to_LoginFragment);
                            }
                        });
                    } else {
                        //TODO: TOAST!?
                    }
                    Log.println(Log.INFO, "Register", String.valueOf(response.code()));
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
