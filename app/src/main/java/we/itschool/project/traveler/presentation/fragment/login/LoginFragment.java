package we.itschool.project.traveler.presentation.fragment.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import traveler.module.data.travelerapi.errors.UserNetAnswers;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.presentation.activity.LoginActivity;
import we.itschool.project.traveler.presentation.activity.MainActivity;


public class LoginFragment extends Fragment {

    private static final String defaultFlag = "waiting";
    String email, password;
    EditText et_email, et_password;
    Button bt_login;
    ProgressBar pb_login;
    private String flag = defaultFlag;

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
        bt_login = view.findViewById(R.id.bt_login_sign_in);
        bt_login.setOnClickListener(v -> userLogin());

        pb_login = view.findViewById(R.id.pb_login);
    }

    private void userLogin() {
        if (checkLoginData()) {
            //send data
            logUserIn();
            pb_login.setVisibility(View.VISIBLE);
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                while (defaultFlag.equals(flag)) ;
                pb_login.setVisibility(View.INVISIBLE);

                //all went successfully and user logged in
                if (UserNetAnswers.userSuccessLogin.equals(flag)) {
                    savePrefs();
                    startMainActivity();
                } else {
                    if (UserNetAnswers.userIncorrectPasswordException.equals(flag)) {
                        Toast.makeText(this.getContext(), R.string.login_incorrect_password, Toast.LENGTH_SHORT).show();
                        et_password.setText("");
                    } else if (UserNetAnswers.userDoesNotExistException.equals(flag)) {
                        Toast.makeText(this.getContext(), R.string.login_no_such_user, Toast.LENGTH_SHORT).show();
                        et_password.setText("");
                    } else if (UserNetAnswers.userOtherError.equals(flag)) {
                        Toast.makeText(this.getContext(), R.string.login_some_error, Toast.LENGTH_SHORT).show();
                        Toast.makeText(this.getContext(), flag, Toast.LENGTH_LONG).show();
                    }
                }
                }, 1500);
        }
    }

    private void logUserIn() {
        new Thread(() -> flag = AppStart.uLoginUC.login(
                et_email.getText().toString(), et_password.getText().toString())
        ).start();
    }

    private boolean checkLoginData() {
        boolean check = true;

        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (password.isEmpty()) {
            check = false;
            et_password.setError("Password is required");
            et_password.requestFocus();
        } else if (password.length() < 6) {
            check = false;
            et_password.setError("Min Password length should be of 6 characters! ");
            et_password.requestFocus();
        }
        if (email.isEmpty()) {
            check = false;
            et_email.setError("Email is required");
            et_email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            check = false;
            et_email.setError("Please provide your valid Email");
            et_email.requestFocus();
        }
        return check;
    }

    private void savePrefs() {
        //make object of SharedPreferences, in case we have just one file we call
        //getPreferences() passing with context of app/activity
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext());
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

    private void startMainActivity() {
        Intent intent = new Intent(this.requireActivity().getBaseContext(), MainActivity.class);
        startActivity(intent);
        closeActivity();
    }

    private void closeActivity() {
        requireActivity().finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}