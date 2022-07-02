package we.itschool.project.traveler.presentation.fragment.card_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import we.itschool.project.traveler.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView iv_avatar_image;
    TextView tv_name_of_city;
    TextView tv_short_description;

    public ViewHolder(View item) {
        super(item);
        iv_avatar_image = item.findViewById(R.id.iv_avatar_image);
        tv_name_of_city = item.findViewById(R.id.tv_name_of_city);
        tv_short_description = item.findViewById(R.id.tv_short_description);
    }
}
