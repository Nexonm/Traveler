package we.itschool.project.traveler.presentation.fragment.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.FragmentLoginBinding;
import we.itschool.project.traveler.presentation.activity.LoginActivity;
import we.itschool.project.traveler.presentation.activity.MainActivity;
import we.itschool.project.traveler.presentation.fragment.registration.RegistrationFragment;


public class LoginFragment extends Fragment {

    EditText et_email;
    EditText et_password;
    Button bt_login;
    Button bt_to_reg;

    private FragmentLoginBinding binding;

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
        // Inflate the layout for this fragment

//        binding = FragmentLoginBinding.inflate(inflater, container, false);
//
//        View root = binding.getRoot();
//        root.findViewById(R.id.toolbar).setTooltipText("Registration");
//        return root;

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
                AppStart.loginUC.login(et_email.getText().toString(), et_password.getText().toString());
                savePrefs();
                Intent intent = new Intent(this.getActivity().getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        bt_to_reg = view.findViewById(R.id.bt_login_register);
        bt_to_reg.setOnClickListener(v -> {
            startRegistrationActivity();
        });
    }

    private boolean checkLoginData() {
        boolean check = true;
        if (et_email.getText().length() <= 0) {
            check = false;
            et_email.setHintTextColor(Color.RED);
            et_email.setHint(R.string.login_email);
        }
        if (et_password.getText().length() <= 0) {
            check = false;
            et_password.setHintTextColor(Color.RED);
            et_password.setHint(R.string.login_password);
        }
        return check;
    }

    private void savePrefs() {
        //make object of SharedPreferences, in case we have just one file we call
        //getPreferences() passing with context of app/activity
        SharedPreferences pref = this.getActivity().getPreferences(Context.MODE_PRIVATE);
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

    private void startRegistrationActivity() {
        Fragment fragment = RegistrationFragment.newInstance();

        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager
                .beginTransaction()
                .addToBackStack("null")
                .replace(R.id.fcv_login, fragment, null)
                .commit();
    }
}