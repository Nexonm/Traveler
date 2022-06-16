package we.itschool.project.traveler.presentation.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import traveler.module.data.travelerapi.errors.UserNetAnswers;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.presentation.fragment.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {


    //keys for SharedPreferences
    public static final String KEY_PREF_USER_EMAIL = "UserEmail";
    public static final String KEY_PREF_USER_PASSWORD = "UserPassword";

    private static final String defaultFlag = "waiting";
    private String flag = defaultFlag;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //set display params for future usage
        setDisplayData();

        checkConnection();
    }

    public boolean checkConnection() {
        Log.e("checkConnection", hasConnection() + "");
        if (hasConnection()) {
            mainJob();
            return true;
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

            builder.setCancelable(false)
                    .setMessage(R.string.login_inet_request)
                    .setNegativeButton("Выйти", (dialog, id) -> {
                        //exit from app, but it doesn't finish it running
                        finishAffinity();
                    })
                    .setPositiveButton("Включить интернет", (dialog, id) -> {
                        startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ignored) {
                        }
                        checkConnection();
                    });

            final AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
    }

    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void mainJob() {
        //it's supposed that inet connection was provided
        int delayed = 500;
        if (userLogged()) {
            Log.v("OkHttpClient", "Send request to UC from LoginActivity");
            logUserIn(userDataFromSPEmail(), userDataFromSPPass());
            AppStart.cUploadUC.upload();
            delayed = 1500;
        }

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            //Do something after handler work

            if (userLogged()) {
                Log.v("OkHttpClient", "User is logged in phone LoginActivity");
                Log.v("OkHttpClient", "Waiting till answer comes LoginActivity");
                //already sent data and wait for data to come in
                while (defaultFlag.equals(flag)) ;//wait while request is going

                Log.v("OkHttpClient", "Flag is:" + flag + ", LoginActivity");
                //in case login is successful we start real app
                if (UserNetAnswers.userSuccessLogin.equals(flag)) {
                    startMainActivity();
                } else {
                    //TODO make string resources for this
                    if (UserNetAnswers.userOtherError.equals(flag)) {
                        Toast.makeText(getBaseContext(), "some error in login, please try again", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getBaseContext(), flag, Toast.LENGTH_LONG).show();
                    }
                    startLogInFragment();
                }
            } else {
                Log.v("OkHttpClient", "somehow user is not logged LoginActivity");
                startLogInFragment();
            }
        }, (int) (Math.random() * 1001) + delayed);
    }

    /**
     * Method works with internet, so new Thread is made and started.
     *
     * @param pass  user password
     * @param email user login=email
     */
    private void logUserIn(String email, String pass) {
        new Thread(() -> flag = AppStart.uLoginUC.login(email, pass)).start();
    }

    //TODO make this method in data module

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

    //TODO make this method in data module

    /**
     * Gets user's email from SharedPreferences
     *
     * @return user email
     */
    private String userDataFromSPEmail() {
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        return pref.getString(KEY_PREF_USER_EMAIL, "null");
    }

    //TODO make this method in data module

    /**
     * Gets user's password from SharedPreferences
     *
     * @return user password
     */
    private String userDataFromSPPass() {
        SharedPreferences pref = this.getPreferences(Context.MODE_PRIVATE);
        return pref.getString(KEY_PREF_USER_PASSWORD, "null");
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //close activity in case we don't need it more
        closeActivity();
    }

    private void closeActivity() {
        this.finish();
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
