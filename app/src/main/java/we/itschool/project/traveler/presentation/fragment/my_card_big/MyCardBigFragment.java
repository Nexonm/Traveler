package we.itschool.project.traveler.presentation.fragment.my_card_big;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import traveler.module.data.travelerapi.APIConfigTraveler;
import traveler.module.domain.entity.CardEntity;
import we.itschool.project.traveler.R;

public class MyCardBigFragment extends Fragment {

    public static final String KEY_PREF_USER_PASSWORD = "UserPassword";
    private static final String ARGUMENT_CARD_GSON = "card Gson";
    private CardEntity card;

    private MyCardBigViewModel viewModel;

    public static MyCardBigFragment newInstance(
            String cardGson
    ) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_CARD_GSON, cardGson);
        MyCardBigFragment fragment = new MyCardBigFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void parseParams() {
        Bundle args = requireArguments();
        if (!args.containsKey(ARGUMENT_CARD_GSON))
            throw new RuntimeException("Argument card gson is absent");
        String cardGson = args.getString(ARGUMENT_CARD_GSON);
        card = (new Gson()).fromJson(cardGson, CardEntity.class);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseParams();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(
                R.layout.fragment_my_card_big,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initView(view);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MyCardBigViewModel.class);
    }

    private void initView(View view) {
        String mDrawableCard = card.getCardInfo().getPathToPhoto();
        view.getContext().getResources().getIdentifier(
                mDrawableCard,
                "drawable",
                view.getContext().getPackageName()
        );

        //TODO create View model and then call data methods
        //take image of this user as it is his photo
        String mDrawableUser = viewModel.getMainUser().getUserInfo().getPathToPhoto();
        view.getContext().getResources().getIdentifier(
                mDrawableUser,
                "drawable",
                view.getContext().getPackageName()
        );

        Picasso.with(this.getContext())
                .load(APIConfigTraveler.STORAGE_CARD_PHOTO_METHOD + card.get_id())
                .into(
                        ((ImageView) view.findViewById(R.id.iv_main_image_big_card_my_card_big))
                );

        //TODO create View model and then call data methods
        Picasso.with(this.getContext())
                .load(APIConfigTraveler.STORAGE_USER_PHOTO_METHOD + viewModel.getMainUser().get_id())
                .into(
                        ((ImageView) view.findViewById(R.id.iv_avatar_image_big_card_my_card_big))
                );

        ((TextView) view.findViewById(R.id.tv_avatar_profile_data_small_FN_my_card_big))
                .setText(
                        viewModel.getMainUser().getUserInfo().getFirstName()
                );
        ((TextView) view.findViewById(R.id.tv_avatar_profile_data_small_SN_my_card_big))
                .setText(
                        viewModel.getMainUser().getUserInfo().getSecondName()
                );
        //code for social contacts
        ((TextView) view.findViewById(R.id.tv_mcb_contacts_field))
                .setText(
                        viewModel.getMainUser().getUserInfo().getSocialContacts()
                );

        ((TextView) view.findViewById(R.id.tv_name_of_city_my_card_big))
                .setText(card.getCardInfo().getCity());
        ((TextView) view.findViewById(R.id.tv_description_long_my_card_big))
                .setText(card.getCardInfo().getFullDescription());

        Button bt_delete_card = view.findViewById(R.id.bt_mcb_delete_card);
        //todo make method to delete card
        //call method from this all class
        bt_delete_card.setOnClickListener(v -> {
            this.deleteCard();
            finishFragment();
        });
    }

    private void deleteCard() {
        viewModel.deleteCard(
                card.get_id(),
                viewModel.getMainUser().getUserInfo().getEmail(),
                userDataFromSPPass()
        );
    }

    //TODO make this method in data module

    /**
     * Gets user's password from SharedPreferences
     *
     * @return user password
     */
    private String userDataFromSPPass() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext());
        return pref.getString(KEY_PREF_USER_PASSWORD, "null");
    }

    private void finishFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
