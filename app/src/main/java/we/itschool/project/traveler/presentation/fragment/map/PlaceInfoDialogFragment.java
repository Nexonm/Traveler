package we.itschool.project.traveler.presentation.fragment.map;

import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.mf;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import retrofit2.Response;
import traveler.module.mapapi.opentripmapapi.ResponseOTMInf.ResponseOTMInfo;
import we.itschool.project.traveler.R;

public class PlaceInfoDialogFragment extends DialogFragment {
    private final Response<ResponseOTMInfo> response;
    private String dist;
    private String tittle = "Описание ";
    private String text = "отсутствует \n";

    private String checkIfNull(String str){ if (str==null){ return ""; }else { return str; } }

    public PlaceInfoDialogFragment(Response<ResponseOTMInfo> card, String distance) {
        this.response = card;
        this.dist = distance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        TableLayout tableLayout = view.findViewById(R.id.tl_dialog_content);

        assert response.body() != null;
        String name = response.body().getName();


        if (Objects.equals(name, "")) {
            //get string from resources
            name = requireContext().getResources().getString(R.string.map_no_name);
        }
        dist = requireContext().getResources().getString(R.string.map_dist) + (int) Double.parseDouble(dist);

        String url = checkIfNull(response.body().getWikipedia());
        String address = checkIfNull(response.body().getAddress().getRoad()) + ", " +
                checkIfNull(response.body().getAddress().getHouse()) + " " +
                checkIfNull(response.body().getAddress().getHouseNumber()) + "\n";
        try {
            tittle = response.body().getWikipediaExtracts().getTitle();
            text = response.body().getWikipediaExtracts().getText() + "\n";
        } catch (NullPointerException ignored) {
        }

        TextView tv_title = new TextView(mf.getContext());
        tv_title.setText(tittle);
        tv_title.setTextColor(Color.BLACK);
        tv_title.setTextSize(20);
        tv_title.setPadding(14,7,14,7);

        TextView tv_text = new TextView(mf.getContext());
        tv_text.setTextSize(19);
        tv_text.setTextColor(Color.BLACK);
        tv_text.setText(text);
        tv_text.setPadding(14,7,14,7);

        TextView tv_dist = new TextView(mf.getContext());
        tv_dist.setText(dist);
        tv_dist.setTextSize(17);
        tv_dist.setPadding(14,7,14,7);

        TextView tv_address = new TextView(mf.getContext());
        tv_address.setText(address);
        tv_address.setTextSize(17);
        tv_address.setPadding(14,7,14,7);

        TextView tv_url = new TextView(mf.getContext());
        tv_url.setText(url);
        tv_url.setPadding(14,7,14,7);
        Linkify.addLinks(tv_url, Linkify.ALL);

        tableLayout.addView(tv_title);
        tableLayout.addView(tv_text);
        tableLayout.addView(tv_dist);
        tableLayout.addView(tv_address);
        tableLayout.addView(tv_url);
        tableLayout.setClickable(true);

        String kinds = response.body().getKinds();
        int bit;
        if (kinds.contains(MapPointsKinds.KIND_HISTORIC)){
                bit = R.drawable.map_monument;
        }else if (kinds.contains(MapPointsKinds.KIND_CULTURAL)){
                bit = R.drawable.map_historical;
        }else if (kinds.contains(MapPointsKinds.KIND_INDUSTRIAL_FACILITIES)){
                bit = R.drawable.map_industrial;
        }else if (response.body().getName().length() == 0){
                bit = R.drawable.map_unknown;
        } else if (kinds.contains(MapPointsKinds.KIND_NATURAL)){
                bit = R.drawable.map_nature;
        } else if (kinds.contains(MapPointsKinds.KIND_ARCHITECTURE)){
                bit = R.drawable.map_buildings;
        }else if (kinds.contains(MapPointsKinds.KIND_OTHER)){
                bit = R.drawable.map_forphoto;
        }else{
                bit = R.drawable.map_unknown;
        }

        AlertDialog.Builder placeInfo = new AlertDialog.Builder(getActivity());
        placeInfo.setTitle(name);
        placeInfo.setIcon(bit);
        placeInfo.setView(view);

        return placeInfo.create();
    }

}
