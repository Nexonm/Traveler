package traveler.module.data.usecaseinterface.user;

import java.util.ArrayList;

public interface UserAddCardToFavoritesUCI {

    ArrayList<Long> addCardToFavorites(long uid, long cid);
}
