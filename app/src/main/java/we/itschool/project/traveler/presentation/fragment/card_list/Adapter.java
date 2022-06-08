package we.itschool.project.traveler.presentation.fragment.card_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import traveler.module.data.travelerapi.APIConfigTraveler;
import traveler.module.domain.entity.CardEntity;

public class Adapter extends ListAdapter<CardEntity, ViewHolder> {

    public static final int MAX_POOL_SIZE = 20;
    public static final int VIEW_TYPE_CARD_VISITOR = 100;

    private static int count = 0;


    public OnCardClickListener cardClickListener = null;

    public Adapter(DiffCallback diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (AppStart.isLog)
            Log.w("onCreateViewHolder", "onCreateViewHolder, count = " + (++count));
        int layout;
        switch (viewType) {
            case VIEW_TYPE_CARD_VISITOR:
                layout = R.layout.fragment_card_list_item_visitor;
                break;

            default:
                if (AppStart.isLog)
                    Log.wtf("onCreateViewHolder", "onCreateViewHolder, THERE IS NO SUCH TYPE!!!");

                //TODO find how to do it smartly without errors
                layout = R.layout.fragment_card_list_item_visitor;
        }

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public int getItemViewType(int position){
        return VIEW_TYPE_CARD_VISITOR;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CardEntity card = getItem(position);
        viewHolder.tv_name_of_city.setText(String.valueOf(card.getCardInfo().getCity()+", "+card.getCardInfo().getCountry()));
        viewHolder.tv_short_description.setText(card.getCardInfo().getShortDescription());
        //TODO  asynk Picasso
        Context context = viewHolder.itemView.getContext();
//        viewHolder.iv_avatar_image.setMaxHeight(AppStart.getInstance().getDisplayHeight()/2);
        Picasso.with(context)
                .load(APIConfigTraveler.STORAGE_CARD_PHOTO_METHOD+card.get_id())
                .resize(
                        AppStart.getInstance().getDisplayHeight()/2,
                        AppStart.getInstance().getDisplayHeight()/2
                ).into(viewHolder.iv_avatar_image);
        viewHolder.itemView.setOnClickListener(
                v -> {
//                    Toast.makeText(viewHolder.itemView.getContext(), "card position = "+ position+"\ncard _id = "+ card.get_id(), Toast.LENGTH_SHORT).show();
                    // TODO how it work now
                    cardClickListener.onCardClick(getCurrentList().get(position));
                    // TODO how it must be work
                    //peopleClickListener.onPeopleClick(card.get_id());
                }
        );
    }

    public void updateSet(){
        notifyDataSetChanged();
    }

    public interface OnCardClickListener {
        void onCardClick(CardEntity card);
    }
}
