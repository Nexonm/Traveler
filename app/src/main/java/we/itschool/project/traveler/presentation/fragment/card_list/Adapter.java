package we.itschool.project.traveler.presentation.fragment.card_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import traveler.module.data.travelerapi.APIConfigTraveler;
import traveler.module.domain.entity.CardEntity;
import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;

public class Adapter extends ListAdapter<CardEntity, ViewHolder> {

    public static final int MAX_POOL_SIZE = 20;
    public static final int VIEW_TYPE_CARD_VISITOR = 100;
    public OnCardClickListener cardClickListener = null;
    String name_of_city;

    public Adapter(DiffCallback diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        layout = R.layout.fragment_card_list_item_visitor;

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_CARD_VISITOR;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CardEntity card = getItem(position);
        name_of_city = card.getCardInfo().getCity() + ", " + card.getCardInfo().getCountry();
        viewHolder.tv_name_of_city.setText(name_of_city);
        viewHolder.tv_short_description.setText(card.getCardInfo().getShortDescription());
        Context context = viewHolder.itemView.getContext();
        Picasso.with(context)
                .load(APIConfigTraveler.STORAGE_CARD_PHOTO_METHOD + card.get_id())
                .resize(
                        AppStart.getInstance().getDisplayHeight() / 2,
                        AppStart.getInstance().getDisplayHeight() / 2
                ).into(viewHolder.iv_avatar_image);
        viewHolder.itemView.setOnClickListener(
                v -> cardClickListener.onCardClick(getCurrentList().get(position))
        );
    }

    public interface OnCardClickListener {
        void onCardClick(CardEntity card);
    }
}
