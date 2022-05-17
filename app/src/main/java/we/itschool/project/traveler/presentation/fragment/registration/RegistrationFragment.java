package we.itschool.project.traveler.presentation.fragment.registration;

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

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.presentation.activity.LoginActivity;
import we.itschool.project.traveler.presentation.activity.MainActivity;

public class RegistrationFragment extends Fragment {
    EditText et_first_name;
    EditText et_second_name;
    EditText et_email;
    EditText et_password;
    EditText et_password_check;
    EditText et_birth_date;
    EditText et_phone;
    Button bt_register;

    public static RegistrationFragment newInstance(){
        return new RegistrationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        et_first_name = view.findViewById(R.id.et_reg_field_firs_name);
        et_second_name = view.findViewById(R.id.et_reg_field_second_name);
        et_email = view.findViewById(R.id.et_reg_field_email);
        et_password = view.findViewById(R.id.et_reg_field_password);
        et_password_check = view.findViewById(R.id.et_reg_field_password_two);
        et_birth_date = view.findViewById(R.id.et_reg_field_date_of_birth);
        et_phone = view.findViewById(R.id.et_reg_field_phone_number);
        bt_register = view.findViewById(R.id.bt_reg_register_new_user);
        bt_register.setOnClickListener(v -> {
            if(checkAllData()){
                Intent intent = new Intent(this.requireActivity().getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkAllData() {
        boolean check = true;
        if (et_first_name.getText().length() <= 0) {
            check = false;
            et_first_name.setHintTextColor(Color.RED);
            et_first_name.setHint(R.string.reg_title_first_name);
        }
        if (et_second_name.getText().length() <= 0) {
            check = false;
            et_second_name.setHintTextColor(Color.RED);
            et_second_name.setHint(R.string.reg_title_second_name);
        }
        if (et_email.getText().length() <= 0) {
            check = false;
            et_email.setHintTextColor(Color.RED);
            et_email.setHint(R.string.reg_title_email);
        }
        boolean can1 = true, can2 = true;
        if (et_password.getText().length() <= 0) {
            can1 = false;
            check = false;
            et_password.setHintTextColor(Color.RED);
            et_password.setHint(R.string.reg_title_password);
        }
        if (et_password_check.getText().length() <= 0) {
            can2 = false;
            check = false;
            et_password_check.setHintTextColor(Color.RED);
            et_password_check.setHint(R.string.reg_title_password_repeat);
        }
        if (can1 && can2 && !et_password.getText().toString().equals(et_password_check.getText().toString())) {
            check = false;
            et_password.setHintTextColor(Color.RED);
            et_password.setText("");
            et_password.setHint(R.string.reg_title_password);
            et_password_check.setHintTextColor(Color.RED);
            et_password_check.setText("");
            et_password_check.setHint(R.string.reg_title_password_repeat_not_match);
        }
        if (et_birth_date.getText().length() <= 0) {
            check = false;
            et_birth_date.setHintTextColor(Color.RED);
            et_birth_date.setHint(R.string.reg_title_date_of_birth_hint);
        }
        if (et_phone.getText().length() <= 0) {
            check = false;
            et_phone.setHintTextColor(Color.RED);
            et_phone.setHint(R.string.reg_title_phone_number);
        }
        if (check) {
            AppStart.personAddNewUC.userAddNew(new String[]{
                    et_email.getText().toString(),
                    et_password.getText().toString(),
                    et_first_name.getText().toString(),
                    et_second_name.getText().toString(),
                    et_birth_date.getText().toString(),
                    et_phone.getText().toString()
            });
            savePrefs();
        }
        return check;
    }
    private void savePrefs() {
        SharedPreferences pref = this.requireActivity().getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(LoginActivity.KEY_PREF_USER_PASSWORD, et_password.getText().toString());
        editor.putString(LoginActivity.KEY_PREF_USER_EMAIL, et_email.getText().toString());

        editor.apply();
    }
}