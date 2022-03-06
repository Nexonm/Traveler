package we.itschool.project.traveler.presentation.fragment.card_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.domain.entity.Card;

public class Adapter extends ListAdapter<Card, ViewHolder> {

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Card card = getItem(position);
        viewHolder.tv_name_of_city.setText(card.getCardInfo().getCity());
        viewHolder.tv_short_description.setText(card.getCardInfo().getShortDescription());
        String mDrawableName = card.getCardInfo().getPathToPhoto();
        //TODO  getResources() here
        Context context = viewHolder.itemView.getContext();
        int resId = context.getResources().getIdentifier(
                mDrawableName,
                "drawable",
                context.getPackageName()
        );
        viewHolder.iv_avatar_image.setImageResource(resId);
        viewHolder.itemView.setOnClickListener(
                v -> {
                    Toast.makeText(context, "card position = "+ position+"\ncard _id = "+ card.get_id(), Toast.LENGTH_SHORT).show();
                    // TODO how it work now
                    cardClickListener.onCardClick(getCurrentList().get(position));
                    // TODO how it must be work
                    //peopleClickListener.onPeopleClick(card.get_id());
                }
        );
    }

    interface OnCardClickListener {
        void onCardClick(Card card);
    }
}
