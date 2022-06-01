package we.itschool.project.traveler.presentation.fragment.create_new_card;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.entity.CardInfo;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.databinding.FragmentCreateNewCardBinding;
import we.itschool.project.traveler.presentation.fragment.my_cards.MyCardsFragment;

public class CreateNewCardFragment extends Fragment {

    private Context context;
    private ImageView iv_card_photo;
    private Switch sw_payment;

    private EditText et_city;
    private EditText et_country;
    private EditText et_address;
    private EditText et_short_desc;
    private EditText et_full_desc;
    private EditText et_hashtags;

    private String bufString = "null";

    private FragmentCreateNewCardBinding binding;
    private static final int IMAGE_PIC_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    //new Instance method
    public static CreateNewCardFragment newInstance() {
        return new CreateNewCardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_new_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.requireActivity().getBaseContext();
        initView(view);
    }


    private void initView(View view) {
        //card photo that must be downloaded by user
        iv_card_photo = view.findViewById(R.id.iv_new_card_photo);
        //on this button's click photo must be found
        Button bt_upload_photo = view.findViewById(R.id.bt_new_card_upload_photo);
        //it was clicked, start finding photo
        bt_upload_photo.setOnClickListener(v -> {
            //check runtime permission
            try {
                if (!hasPermissions()) {
                    requestPermissionsMy();
                } else {
                    //permission Granted we can pick image
                    iv_card_photo.getLayoutParams().height = 400;
                    iv_card_photo.getLayoutParams().width = AppStart.getInstance().getDisplayWidth();
                    pickImageFromGallery();
                }
            }catch (Exception ignored){}

        });
        //create new card button, on this click all check are made and data sends
        Button bt_new_card = view.findViewById(R.id.bt_new_card_add_new_card);
        //main onClick method
        bt_new_card.setOnClickListener(v -> {
            if (checkAllData()) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //EditText's data
        et_city = view.findViewById(R.id.et_new_card_city);
        et_country = view.findViewById(R.id.et_new_card_country);
        et_address = view.findViewById(R.id.et_new_card_address);
        et_short_desc = view.findViewById(R.id.et_new_card_short_description);
        et_full_desc = view.findViewById(R.id.et_new_card_full_description);
        et_hashtags = view.findViewById(R.id.et_new_card_hashtags);
    }

    /**
     * Checks all the fields in fragment and sends to server new card
     *
     */
    private boolean checkAllData() {
        boolean check = true;
        //city
        if (!(et_city.getText().length() > 0)) {
            check = false;
            et_city.setHintTextColor(Color.RED);
            et_city.setHint(R.string.cnc_city_error);
        }
        //country
        if (!(et_country.getText().length() > 0)) {
            check = false;
            et_country.setHintTextColor(Color.RED);
            et_country.setHint(R.string.cnc_country_error);
        }
        //address
        if (!(et_address.getText().length() > 0)) {
            check = false;
            et_address.setHintTextColor(Color.RED);
            et_address.setHint(R.string.cnc_address_error);
        }
        //short description
        if (!(et_short_desc.getText().length() > 0)) {
            check = false;
            et_short_desc.setHintTextColor(Color.RED);
            et_short_desc.setHint(R.string.cnc_few_information_error);
        }
        //full description
        if (!(et_full_desc.getText().length() > 0)) {
            check = false;
            et_full_desc.setHintTextColor(Color.RED);
            et_full_desc.setHint(R.string.cnc_many_information_error);
        }
        if ("null".equals(bufString)){
            check = false;
            Toast.makeText(context, R.string.cnc_photo_addition_error, Toast.LENGTH_LONG).show();
        }
        if (check) {
                AppStart.uCreateNewCardUC.createNewCard(
                        new CardEntity(
                                new CardInfo(
                                        null,
                                        et_city.getText().toString() + "",
                                        et_country.getText().toString() + "",
                                        et_full_desc.getText().toString() + "",
                                        et_short_desc.getText().toString() + "",
                                        et_address.getText().toString() + "",
                                        bufString,
                                        et_hashtags.getText().toString()
                                ),
                                -1
                        )
                );
            return true;
        }
        return false;
    }

    private boolean isNum(String a) {
        try {
            Integer.parseInt(a);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int getNum(String a) {
        try {
            return Integer.parseInt(a);
        } catch (NumberFormatException e) {
            return 0;
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
                            bufString = getRealPathFromURI(imageUri);
                            Log.v("fileName", "the file path: "+ bufString);
                            Glide.with(context)
                                    .load(imageUri)
                                    .into(iv_card_photo);
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
        Cursor cursor = this.requireActivity().getContentResolver().query(uri, null, null, null, null);
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

    //methods for ui changes (fragments)

    private void startMyCardsFragment() {
        Fragment fragment = MyCardsFragment.newInstance();

        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager
                .beginTransaction()
                .addToBackStack("null")
                .replace(R.id.nav_host_fragment_content_main, fragment, null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
