package traveler.module.domain.entity;

import org.jetbrains.annotations.Nullable;

public class CardEntity {
    public static final int UNDEFINED_ID;
    private int _id;

    private final CardInfo cardInfo;

    static{
        UNDEFINED_ID = -1;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public CardEntity(CardInfo cardInfo, int id) {
        this.cardInfo = cardInfo;
        this._id = id;
    }

    @Override
    public String toString(){
        return cardInfo.toString();
    }

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        CardEntity guest = (CardEntity) obj;
        return
                _id == guest.get_id() && (
                        cardInfo == guest.getCardInfo()
                        || (cardInfo != null && cardInfo.equals(guest.getCardInfo()))
                );
    }
}
