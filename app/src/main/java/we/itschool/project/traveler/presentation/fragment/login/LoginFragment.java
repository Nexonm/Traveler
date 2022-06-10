package we.itschool.project.traveler.presentation.fragment.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import traveler.module.data.travelerapi.errors.UserNetAnswers;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.presentation.activity.LoginActivity;
import we.itschool.project.traveler.presentation.activity.MainActivity;
import we.itschool.project.traveler.presentation.fragment.registration.RegistrationFragment;


public class LoginFragment extends Fragment {

    public static final String KEY_PREF_USER_EMAIL = "UserEmail";
    public static final String KEY_PREF_USER_PASSWORD = "UserPassword";
    EditText et_email;
    EditText et_password;
    Button bt_login;
    Button bt_to_reg;

    private static String defaultFlag = "waiting";
    private String flag = defaultFlag;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        et_email = view.findViewById(R.id.et_login_field_email);
        et_password = view.findViewById(R.id.et_login_field_password);
        et_password = view.findViewById(R.id.et_login_field_password);
        bt_login = view.findViewById(R.id.bt_login_sign_in);
        bt_login.setOnClickListener(v -> {
            if (checkLoginData()) {
                //TODO make circle progress bar while send data
                //try same trick with handler
                //send data
                logUserIn();
                while (defaultFlag.equals(flag));
                //all went successfully and user logged in
                if (UserNetAnswers.userSuccessLogin.equals(flag)){
                    savePrefs();
                    startMainActivity();
                }else{
                    //TODO find what is the mistake and somehow fix it
                    //make errors to text Views
                    Toast.makeText(this.getContext(), "some error in login", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this.getContext(), flag, Toast.LENGTH_LONG).show();
                }

            }
        });
        bt_to_reg = view.findViewById(R.id.bt_login_register);
        bt_to_reg.setOnClickListener(v -> startRegistrationFragment());
    }

    private void logUserIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("UserLOGIN", flag = AppStart.uLoginUC.login(
                        et_password.getText().toString(), et_email.getText().toString())
                );
            }
        }).start();
    }

    private boolean checkLoginData() {
        boolean check = true;
        if (et_email.getText().length() <= 0) {
            check = false;
            et_email.setHintTextColor(Color.RED);
            et_email.setHint(R.string.login_email_ev);
        }
        if (et_password.getText().length() <= 0) {
            check = false;
            et_password.setHintTextColor(Color.RED);
            et_password.setHint(R.string.login_password_ev);
        }
        return check;
    }

    private void savePrefs() {
        //make object of SharedPreferences, in case we have just one file we call
        //getPreferences() passing with context of app/activity
        SharedPreferences pref = this.requireActivity().getPreferences(Context.MODE_PRIVATE);
        //make object of SharedPreferences.Editor which provides methods to edit data
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LoginActivity.KEY_PREF_USER_PASSWORD, et_password.getText().toString());
        editor.putString(LoginActivity.KEY_PREF_USER_EMAIL, et_email.getText().toString());
        //use either apply() or commit()
        //apply() changes the in-memory SharedPreferences object immediately but writes the updates to disk asynchronously
        editor.apply();
        //commit() is synchronous, you should avoid calling it from your main thread because it could pause your UI rendering
        //editor.commit();
    }

    private void startRegistrationFragment() {
        Fragment fragment = RegistrationFragment.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager
                .beginTransaction()
                .addToBackStack("null")
                .replace(R.id.fcv_login, fragment, null)
                .commit();
    }

    private void startMainActivity(){
        Intent intent = new Intent(this.requireActivity().getBaseContext(), MainActivity.class);
        startActivity(intent);
        closeActivity();
    }

    private boolean userLogged() {
        //make object of SharedPreferences, in case we have just one file we call
        //getPreferences() passing with context of app/activity
        SharedPreferences pref = this.requireActivity().getPreferences(Context.MODE_PRIVATE);
        //check if there is needed data
        return pref.contains(KEY_PREF_USER_EMAIL) && pref.contains(KEY_PREF_USER_PASSWORD);
    }

    private void closeActivity() {
        this.requireActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}