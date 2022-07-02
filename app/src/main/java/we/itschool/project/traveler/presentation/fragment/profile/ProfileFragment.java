package we.itschool.project.traveler.presentation.fragment.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import traveler.module.data.travelerapi.APIConfigTraveler;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;

    private ImageView iv_avatar;

    private static final int PERMISSION_CODE = 1001;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        initView(view);
    }

    private void initView(View view) {
        TextView tv_first_name = view.findViewById(R.id.tv_profile_first_name);
        tv_first_name.setText(AppStart.uGetMainUserUC.getMainUser().getUserInfo().getFirstName());
        TextView tv_second_name = view.findViewById(R.id.tv_profile_second_name);
        tv_second_name.setText(AppStart.uGetMainUserUC.getMainUser().getUserInfo().getSecondName());
        TextView tv_phone = view.findViewById(R.id.tv_profile_phone);
        tv_phone.setText(AppStart.uGetMainUserUC.getMainUser().getUserInfo().getPhoneNumber());
        TextView tv_email = view.findViewById(R.id.tv_profile_email);
        tv_email.setText(AppStart.uGetMainUserUC.getMainUser().getUserInfo().getEmail());
        TextView tv_is_male = view.findViewById(R.id.tv_profile_is_male);
        tv_is_male.setText(AppStart.uGetMainUserUC.getMainUser().getUserInfo().isMale() ? requireContext().getResources().getString(R.string.man) : requireContext().getResources().getString(R.string.woman));
        TextView tv_birthday = view.findViewById(R.id.tv_profile_birthday);
        tv_birthday.setText(AppStart.uGetMainUserUC.getMainUser().getUserInfo().getDateOfBirth());
        TextView tv_contacts = view.findViewById(R.id.tv_profile_contacts);
        tv_contacts.setText(AppStart.uGetMainUserUC.getMainUser().getUserInfo().getSocialContacts());

        iv_avatar = view.findViewById(R.id.iv_profile_avatar);
        Picasso.with(context)
                .load(APIConfigTraveler.STORAGE_USER_PHOTO_METHOD + AppStart.uGetMainUserUC.getMainUser().get_id())
                .into(iv_avatar);

        Button bt_update_photo = view.findViewById(R.id.bt_profile_update_photo);
        bt_update_photo.setOnClickListener(v -> {
            try {
                if (!hasPermissions()) {
                    requestPermissionsMy();
                } else {
                    //permission Granted we can pick image
                    pickImageFromGallery();
                }
            } catch (Exception e) {
                Toast.makeText(this.getContext(), R.string.profile_error, Toast.LENGTH_LONG).show();
            }
        });

    }


    /**
     * Method to start new activity to get photo from storage
     */
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerActivityResult.launch(intent);
    }

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
//                            Log.v("fileName", "the file path: " + bufString);
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
