package is.hi.hbv601g.podypus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = this.getApplicationContext();
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);

        model = new ViewModelProvider(this).get(MainActivityViewModel.class);

        setContentView(R.layout.activity_main);

        //Show navigate to different views from nav bar
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_player)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        model.isAuthenticated().observe(this, authenticated -> {
            if(authenticated.booleanValue()) {
                navController.navigate(R.id.action_LoginFragment_to_PodcastFragment);
            } else {
                navController.navigate(R.id.action_PodcastFragment_to_LoginFragment);
            }
        });
    }
}
