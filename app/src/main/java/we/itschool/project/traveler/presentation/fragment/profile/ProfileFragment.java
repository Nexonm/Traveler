package we.itschool.project.traveler.presentation.fragment.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Context context;

    private ImageView iv_avatar;
    private TextView tv_first_name;
    private TextView tv_second_name;
    private TextView tv_phone;
    private TextView tv_email;
    private TextView tv_is_male;
    private TextView tv_birthday;

    private FloatingActionButton fab_edit_avatar;

    private static final int PERMISSION_CODE = 1001;
    private String bufString = "null";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        iv_avatar = view.findViewById(R.id.iv_profile_avatar);

        tv_first_name = view.findViewById(R.id.tv_profile_first_name);
        tv_first_name.setText(AppStart.getUser().getUserInfo().getFirstName());
        tv_second_name = view.findViewById(R.id.tv_profile_second_name);
        tv_second_name.setText(AppStart.getUser().getUserInfo().getSecondName());
        tv_phone = view.findViewById(R.id.tv_profile_phone);
        tv_phone.setText(AppStart.getUser().getUserInfo().getPhoneNumber());
        tv_email = view.findViewById(R.id.tv_profile_email);
        tv_email.setText(AppStart.getUser().getUserInfo().getEmail());
        tv_is_male = view.findViewById(R.id.tv_profile_is_male);
        tv_is_male.setText(AppStart.getUser().getUserInfo().isMale()?"Мужской":"Женский");
        tv_birthday = view.findViewById(R.id.tv_profile_birthday);
        tv_birthday.setText(AppStart.getUser().getUserInfo().getDateOfBirth());

//        fab_edit_avatar = view.findViewById(R.id.fab_profile_edit_avatar);
//        fab_edit_avatar.setOnClickListener(v -> {
//            try {
//                if (!hasPermissions()) {
//                    requestPermissionsMy();
//                } else {
//                    //permission Granted we can pick image
//                    pickImageFromGallery();
//                }
//                //TODO send file to server
//            }catch (Exception ignored){}
//        });
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
                            Uri imageUri = result.getData().getData();
                            bufString = getRealPathFromURI(imageUri);
                            Log.v("fileName", "the file path: "+ bufString);
                            Glide.with(context)
                                    .load(imageUri)
                                    .into(iv_avatar);
                        }catch (NullPointerException e){
                            Toast.makeText(context, "Видимо вы прекратили выбор фото, не забудьте выбрать:)", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Please allow us to upload photo from your gallery", Toast.LENGTH_LONG).show();
                    }
                }
            }
    );

    private String getRealPathFromURI(Uri uri) {
        Cursor cursor = this.getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    /**
     * Checking if there is permission to EXTERNAL_STORAGE
     * @return true if there is permission
     */
    private boolean hasPermissions() {
        return ActivityCompat.checkSelfPermission(
                this.getActivity().getBaseContext(),
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
