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
import android.preference.PreferenceManager;
import android.view.Display;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import traveler.module.data.travelerapi.errors.UserNetAnswers;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    float v = 0;
    int delayed = 500;

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

        //binding
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // setting 2 tabs in tab layout
        tabLayout.addTab(tabLayout.newTab().setText("Log in"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(loginAdapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        tabLayout.setTranslationY(300);

        tabLayout.setAlpha(v);

        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        //set display params for future usage
        setDisplayData();

        checkConnection();
    }

    public void checkConnection() {
        if (hasConnection()) {
            mainJob();
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
        }
    }

    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void mainJob() {
        //it's supposed that inet connection was provided
        if (userLogged()) {
            logUserIn(userDataFromSPEmail(), userDataFromSPPass());
            AppStart.cUploadUC.upload();
            delayed = 1500;
        }

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            //Do something after handler work

            if (userLogged()) {
                //already sent data and wait for data to come in
                while (defaultFlag.equals(flag)) ;//wait while request is going
                //in case login is successful we start real app
                if (UserNetAnswers.userSuccessLogin.equals(flag)) {
                    startMainActivity();
                } else {
                    if (UserNetAnswers.userOtherError.equals(flag)) {
                        Toast.makeText(getBaseContext(), R.string.login_some_error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getBaseContext(), flag, Toast.LENGTH_LONG).show();
                    }
                }
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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        return pref.getString(KEY_PREF_USER_EMAIL, "null");
    }

    //TODO make this method in data module

    /**
     * Gets user's password from SharedPreferences
     *
     * @return user password
     */
    private String userDataFromSPPass() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
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

    //method saves display size to AppStart fields
    private void setDisplayData() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        AppStart.getInstance().setDisplayHeight(size.y); //height is up and down, it's y
        AppStart.getInstance().setDisplayWidth(size.x); //width is right and left, it's x
    }

}
