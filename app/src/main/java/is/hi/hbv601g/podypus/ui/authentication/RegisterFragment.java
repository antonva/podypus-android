package is.hi.hbv601g.podypus.ui.authentication;

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
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONException;

import java.io.IOException;

import is.hi.hbv601g.podypus.MainActivityViewModel;
import is.hi.hbv601g.podypus.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private MainActivityViewModel model;
    private EditText passwordField;
    private EditText usernameField;
    private EditText emailField;
    private Button registerButton;

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
        registerButton = (Button) view.findViewById(R.id.loginButton);
        registerButton.setOnClickListener(this);

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_RegisterFragment_to_LoginFragment);
            }
        });
    }


    public void register(String user, String password, Callback callback) throws JSONException, IOException{

    }

    @Override
    public void onClick(View v) {
        try {
            final String user = usernameField.getText().toString();
            final String password = passwordField.getText().toString();
            register(user, password, new Callback() {
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
                    }
                    Log.println(Log.INFO, "Login", String.valueOf(response.code()));
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
