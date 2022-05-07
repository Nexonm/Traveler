package we.itschool.project.traveler.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.ActivityMainBinding;
import we.itschool.project.traveler.presentation.fragment.card_list.CardListFragment;
import we.itschool.project.traveler.presentation.fragment.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    //keys for SharedPreferences
    public static final String KEY_PREF_USER_EMAIL = "UserEmail";
    public static final String KEY_PREF_USER_PASSWORD = "UserPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (!userLogged())
//        startLogInFragmentOrCardList();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setDisplayData();
    }

    /**
     * Check if user registered on this gadget. If no user needs to log in or register.
     * All app has just one SharedPreferences file with data and keys are stored as fields.
     * @return true if user logged and data saved
     */
    private boolean userLogged() {
        //make object of SharedPreferences, in case we have just one file we call
        //getPreferences() passing with context of app/activity
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        //check if there is needed data
        return pref.contains(MainActivity.KEY_PREF_USER_EMAIL) && pref.contains(MainActivity.KEY_PREF_USER_PASSWORD);
    }

    private void startLogInFragmentOrCardList() {
        if (userLogged()) {
            //in case we have user data we just login him
            Fragment fragment = CardListFragment.newInstance();

            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("null")
                    .replace(R.id.nav_host_fragment_content_main, fragment, null)
                    .commit();
        }else{
            //in case user is new to app we suggest login or register
            Fragment fragment = LoginFragment.newInstance();

            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("null")
                    .replace(R.id.nav_host_fragment_content_main, fragment, null)
                    .commit();
        }
    }

    //method saves display size to AppStart fields
    private void setDisplayData(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        AppStart.getInstance().setDisplayHeight(size.y);//height is up and down, it's y
        AppStart.getInstance().setDisplayWidth(size.x);//width is right and left, it's x
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}