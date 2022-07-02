package we.itschool.project.traveler.presentation.fragment.card_big;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import traveler.module.data.travelerapi.APIConfigTraveler;
import traveler.module.domain.entity.CardEntity;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;

public class CardFragment extends Fragment {

    private static final String ARGUMENT_CARD_GSON = "card Gson";

    private CardEntity card;

    public static CardFragment newInstance(
            String cardGson
    ) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_CARD_GSON, cardGson);
        CardFragment fragment = new CardFragment();
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
                R.layout.fragment_card_big_view,
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
        initView(view);
    }

    private void initView(View view) {
        String mDrawableCard = card.getCardInfo().getPathToPhoto();
        view.getContext().getResources().getIdentifier(
                mDrawableCard,
                "drawable",
                view.getContext().getPackageName()
        );
        String mDrawableUser = card.getCardInfo()
                .getUser().getUserInfo().getPathToPhoto();
        view.getContext().getResources().getIdentifier(
                mDrawableUser,
                "drawable",
                view.getContext().getPackageName()
        );

        Picasso.with(this.getContext())
                .load(APIConfigTraveler.STORAGE_CARD_PHOTO_METHOD + card.get_id())
//                .resize(
//                        AppStart.getInstance().getDisplayHeight()/2,
//                        AppStart.getInstance().getDisplayHeight()/2
//                )
                .into(
                        ((ImageView) view.findViewById(R.id.iv_main_image_big_card))
                );

        Picasso.with(this.getContext())
                .load(APIConfigTraveler.STORAGE_USER_PHOTO_METHOD + card.getCardInfo().getUser().get_id())
                .into(
                        ((ImageView) view.findViewById(R.id.iv_avatar_image_big_card))
                );

        ((TextView) view.findViewById(R.id.tv_avatar_profile_data_small_FN))
                .setText(
                        card.getCardInfo()
                                .getUser().getUserInfo().getFirstName()
                );
        ((TextView) view.findViewById(R.id.tv_avatar_profile_data_small_SN))
                .setText(
                        card.getCardInfo()
                                .getUser().getUserInfo().getSecondName()
                );
        //code for social contacts
        ((TextView) view.findViewById(R.id.tv_cbv_contacts_field))
                .setText(
                        card.getCardInfo()
                                .getUser().getUserInfo().getSocialContacts()
                );

        ((TextView) view.findViewById(R.id.tv_name_of_city))
                .setText(card.getCardInfo().getCity());
        ((TextView) view.findViewById(R.id.tv_description_long))
                .setText(card.getCardInfo().getFullDescription());

        Button bt_add_faves = view.findViewById(R.id.bt_cbv_add_to_favourites);
        bt_add_faves.setOnClickListener(v -> AppStart.uAddCardToFavesUC.addCardToFavorites(card.get_id()));
    }
}
