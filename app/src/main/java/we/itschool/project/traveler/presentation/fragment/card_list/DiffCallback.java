package we.itschool.project.traveler.presentation.fragment.card_list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import we.itschool.project.traveler.domain.entity.Card;

public class DiffCallback extends DiffUtil.ItemCallback<Card> {
    @Override
    public boolean areItemsTheSame(@NonNull Card oldCard, @NonNull Card newCard) {
        return (oldCard.get_id()) == (newCard.get_id());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Card oldCard, @NonNull Card newCard) {
        return oldCard.equals(newCard);
    }
}
