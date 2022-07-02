package we.itschool.project.traveler.presentation.fragment.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import traveler.module.data.travelerapi.APIConfigTraveler;
import traveler.module.domain.entity.UserEntity;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    public static final String KEY_PREF_USER_PASSWORD = "UserPassword";
    private static final int PERMISSION_CODE = 1001;
    private FragmentProfileBinding binding;
    private Context context;
    private ProfileViewModel viewModel;
    private ImageView iv_avatar;
    /**
     * this code provide uri from chosen image. This work if permission is granted
     */
    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        try {
                            assert result.getData() != null;
                            Uri imageUri = result.getData().getData();
                            String bufString = getRealPathFromURI(imageUri);
                            Glide.with(context)
                                    .load(imageUri)
                                    .into(iv_avatar);
                            //send data to server
                            AppStart.uAddPhotoUC.addPhoto(bufString);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, R.string.profile_photo_break, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.profile_photo_can_not, Toast.LENGTH_LONG).show();
                    }
                }
            }
    );
    private TextView tv_contacts;
    private EditText et_edit_social_contacts;
    private boolean counter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        initViewModel();
        initView(view);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    private void initView(View view) {
        counter = false;
        TextView tv_first_name = view.findViewById(R.id.tv_profile_first_name);
        tv_first_name.setText(viewModel.getMainUser().getUserInfo().getFirstName());
        TextView tv_second_name = view.findViewById(R.id.tv_profile_second_name);
        tv_second_name.setText(viewModel.getMainUser().getUserInfo().getSecondName());
        TextView tv_phone = view.findViewById(R.id.tv_profile_phone);
        tv_phone.setText(viewModel.getMainUser().getUserInfo().getPhoneNumber());
        TextView tv_email = view.findViewById(R.id.tv_profile_email);
        tv_email.setText(viewModel.getMainUser().getUserInfo().getEmail());
        TextView tv_is_male = view.findViewById(R.id.tv_profile_is_male);
        tv_is_male.setText(viewModel.getMainUser().getUserInfo().isMale() ? R.string.man : R.string.woman);
        TextView tv_birthday = view.findViewById(R.id.tv_profile_birthday);
        tv_birthday.setText(viewModel.getMainUser().getUserInfo().getDateOfBirth());
        tv_contacts = view.findViewById(R.id.tv_profile_contacts);
        tv_contacts.setText(viewModel.getMainUser().getUserInfo().getSocialContacts());

        et_edit_social_contacts = view.findViewById(R.id.et_profile_edit_contacts);

        iv_avatar = view.findViewById(R.id.iv_profile_avatar);
        Picasso.with(context)
                .load(APIConfigTraveler.STORAGE_USER_PHOTO_METHOD + viewModel.getMainUser().get_id())
                .into(iv_avatar);

        Button bt_update_photo = view.findViewById(R.id.bt_profile_update_photo);
        bt_update_photo.setOnClickListener(v -> updatePhoto());

        Button bt_save_social_contacts = view.findViewById(R.id.bt_profile_save_social_contacts);
        bt_save_social_contacts.setOnClickListener(v -> saveSocialContacts(view));

        ImageButton ib_edit_social_contacts = view.findViewById(R.id.ib_profile_edit_social_contacts);
        ib_edit_social_contacts.setOnClickListener(v -> editSocialContacts(view));

    }

    private void editSocialContacts(View view) {
        counter = !counter;
        if (counter) {
            view.findViewById(R.id.tr_edit_social_contacts).setClickable(true);
            view.findViewById(R.id.tr_edit_social_contacts).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.tr_edit_social_contacts).setClickable(false);
            view.findViewById(R.id.tr_edit_social_contacts).setVisibility(View.INVISIBLE);
        }

    }

    private void saveSocialContacts(View view) {
        view.findViewById(R.id.tr_edit_social_contacts).setClickable(false);
        view.findViewById(R.id.tr_edit_social_contacts).setVisibility(View.INVISIBLE);
        String newSC = et_edit_social_contacts.getText().toString();
        UserEntity entity = viewModel.getMainUser();
        entity.getUserInfo().setSocialContacts(newSC);
        viewModel.editContacts(entity, userDataFromSPPass());
        tv_contacts.setText(newSC);
        viewModel.updateUserMain();
    }

    /**
     * Gets user's password from SharedPreferences
     *
     * @return user password
     */
    private String userDataFromSPPass() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext());
        return pref.getString(KEY_PREF_USER_PASSWORD, "null");
    }

    private void updatePhoto() {
        try {
            if (!hasPermissions()) {
                requestPermissionsMy();
            } else {
                //permission Granted we can pick image
                pickImageFromGallery();
            }
        } catch (Exception e) {
            //TODO make String resource
            Toast.makeText(this.getContext(), "Поизошла ошибка, попробуйте снова", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method to start new activity to get photo from storage
     */
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerActivityResult.launch(intent);
    }

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = this.requireActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        cursor.close();
        return cursor.getString(idx);
    }

    /**
     * Checking if there is permission to EXTERNAL_STORAGE
     *
     * @return true if there is permission
     */
    private boolean hasPermissions() {
        return ActivityCompat.checkSelfPermission(
                this.requireActivity().getBaseContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * In case there is no permission we ask for it
     */
    private void requestPermissionsMy() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        //show popup for runtime permission
        requestPermissions(permissions, PERMISSION_CODE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
