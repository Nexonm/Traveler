package we.itschool.project.traveler.presentation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.presentation.fragment.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {


    //keys for SharedPreferences
    public static final String KEY_PREF_USER_EMAIL = "UserEmail";
    public static final String KEY_PREF_USER_PASSWORD = "UserPassword";

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((ImageView) findViewById(R.id.iv_login_icon)).setImageDrawable(getResources().getDrawable(R.drawable.icon_app_house));

        //set display params for future usage
        setDisplayData();
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            //Do something after 2000ms
            ((ProgressBar) findViewById(R.id.progressBar)).setVisibility(View.INVISIBLE);
            if (userLogged()) {
                AppStart.uLoginUC.login(userDataFromSPPass(), userDataFromSPEmail());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //close activity in case we don't need it more
                closeActivity();
            } else {
                startLogInFragment();
            }
        }, ((int)(Math.random()*2001)+1500));
    }

    private void closeActivity() {
        this.finish();
    }

    /**
     * Check if user registered on this gadget. If no user needs to log in or register.
     * All app has just one SharedPreferences file with data and keys are stored as fields.
     *
     * @return true if user logged and data saved
     */
    private boolean userLogged() {
        //make object of SharedPreferences, in case we have just one file we call
        //getPreferences() passing with context of app/activity
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        //check if there is needed data
        return pref.contains(KEY_PREF_USER_EMAIL) && pref.contains(KEY_PREF_USER_PASSWORD);
    }

    /**
     * Gets user's email from SharedPreferences
     * @return user email
     */
    private String userDataFromSPEmail() {
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        return pref.getString(KEY_PREF_USER_EMAIL, "null");
    }

    /**
     * Gets user's password from SharedPreferences
     * @return user password
     */
    private String userDataFromSPPass() {
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        return pref.getString(KEY_PREF_USER_PASSWORD, "null");
    }

    //start main login fragment and then start working
    private void startLogInFragment() {
        //in case user is new to app we suggest login or register
        Fragment fragment = LoginFragment.newInstance();

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("null")
                .replace(R.id.fcv_login, fragment, null)
                .commit();
    }

    //method saves display size to AppStart fields
    private void setDisplayData() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        AppStart.getInstance().setDisplayHeight(size.y);//height is up and down, it's y
        AppStart.getInstance().setDisplayWidth(size.x);//width is right and left, it's x
    }


}
