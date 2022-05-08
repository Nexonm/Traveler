package we.itschool.project.traveler.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.presentation.fragment.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {


    //keys for SharedPreferences
    public static final String KEY_PREF_USER_EMAIL = "UserEmail";
    public static final String KEY_PREF_USER_PASSWORD = "UserPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setDisplayData();

        if (userLogged()) {
            AppStart.loginUC.login(userDataFromSPEmail(), userDataFromSPPass());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            startLogInFragment();
        }

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

    private String userDataFromSPEmail() {
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        return pref.getString(KEY_PREF_USER_EMAIL, "null");
    }

    private String userDataFromSPPass() {
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        return pref.getString(KEY_PREF_USER_PASSWORD, "null");
    }

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
