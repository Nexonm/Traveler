package we.itschool.project.traveler.presentation.fragment.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.FragmentProfileBinding;
import we.itschool.project.traveler.presentation.activity.MainActivity;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;

    private TextView tv_first_name;
    private TextView tv_second_name;
    private TextView tv_phone;
    private TextView tv_email;
    private TextView tv_male;
    private TextView tv_birth;
    private ImageView iv_avatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        iv_avatar = view.findViewById(R.id.iv_profile_avatar);

        tv_first_name = view.findViewById(R.id.tv_profile_first_name);
        tv_second_name = view.findViewById(R.id.tv_profile_second_name);
        tv_phone = view.findViewById(R.id.tv_profile_phone);
        tv_email = view.findViewById(R.id.tv_profile_email);
        tv_male = view.findViewById(R.id.tv_profile_is_male);
        tv_birth = view.findViewById(R.id.tv_profile_birthday);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
