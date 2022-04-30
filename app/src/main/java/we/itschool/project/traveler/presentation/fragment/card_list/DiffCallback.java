package we.itschool.project.traveler.presentation.fragment.card_list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import we.itschool.project.traveler.domain.entity.CardEntity;

public class DiffCallback extends DiffUtil.ItemCallback<CardEntity> {
    @Override
    public boolean areItemsTheSame(@NonNull CardEntity oldCard, @NonNull CardEntity newCard) {
        return (oldCard.get_id()) == (newCard.get_id());
    }

    @Override
    public boolean areContentsTheSame(@NonNull CardEntity oldCard, @NonNull CardEntity newCard) {
        return oldCard.equals(newCard);
    }
}
