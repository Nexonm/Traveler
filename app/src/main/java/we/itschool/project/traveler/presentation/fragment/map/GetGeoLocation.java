package we.itschool.project.traveler.presentation.fragment.map;

import static we.itschool.project.traveler.data.api.opentripmapapi.APIConfigOTM.LANGUAGE;
import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.ll;
import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.tx_town;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.yandex.mapkit.geometry.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import we.itschool.project.traveler.R;

public class GetGeoLocation extends AsyncTask<String, Void, ArrayList<String>> {
    private MapFragment mapFragment;
    private Point position;
    private Typeface tf;

    public GetGeoLocation(MapFragment mf, Point myPosition, Typeface tipeface) {
        mapFragment = mf;
        position = myPosition;
        tf = tipeface;
    }

    @Override
    protected ArrayList<String> doInBackground(String... parameter) {
        Geocoder geocoder;
        if (LANGUAGE.equals("ru")){
            geocoder = new Geocoder(mapFragment.getContext(), Locale.getDefault());
        }else{
            geocoder = new Geocoder(mapFragment.getContext(), Locale.UK);
        }
        ArrayList<String> arr = new ArrayList<>();
        try {
            List<Address> location = geocoder.getFromLocation(position.getLatitude(), position.getLongitude(), 1);
            arr.add(location.get(0).getThoroughfare()); //route
            arr.add(location.get(0).getLocality()); //town
            arr.add( location.get(0).getAdminArea()); //admin
            arr.add(location.get(0).getCountryName()); //country
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onPostExecute(ArrayList<String> result) {
        super.onPostExecute(result);
        mapFragment.regions = result;
        if (ll.findViewById(1) instanceof TextView){
            tx_town.setText(result.get(0));
        } else if (result.size() > 0){
            tx_town.setTextSize(30);
            tx_town.setTypeface(tf);
            tx_town.setId(1);
            tx_town.setTextColor(ContextCompat.getColor(mapFragment.getContext(), R.color.black));
            tx_town.setPadding(20, 70, 0, 0);

            tx_town.setText(result.get(0));

            tx_town.setGravity(Gravity.TOP);
            ll.addView(tx_town);
        }
    }
}