package we.itschool.project.traveler.presentation.fragment.map;

import static com.yandex.mapkit.Animation.Type.SMOOTH;
import static traveler.module.mapapi.opentripmapapi.APIConfigOTM.ALLOWED_KINDS_OF_PLACES;
import static traveler.module.mapapi.opentripmapapi.APIConfigOTM.API_OTM_KEY;
import static traveler.module.mapapi.opentripmapapi.APIConfigOTM.CENSORED_KINDS_OF_PLACES;
import static traveler.module.mapapi.opentripmapapi.APIConfigOTM.KINDS_OF_PLACES;
import static traveler.module.mapapi.opentripmapapi.APIConfigOTM.LANGUAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.ScreenPoint;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateReason;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import we.itschool.project.traveler.R;
import traveler.module.mapapi.opentripmapapi.ResponseOTM.Feature;
import traveler.module.mapapi.opentripmapapi.ResponseOTM.ResponseOTM;
import traveler.module.mapapi.opentripmapapi.ResponseOTMInf.ResponseOTMInfo;
import traveler.module.mapapi.opentripmapapi.service.APIServiceOTMConstructor;
import traveler.module.mapapi.opentripmapapi.service.APIServiceOTMGetInfoOfPlaces;
import traveler.module.mapapi.opentripmapapi.service.APIServiceOTMGetPlaces;

public class MapFragment extends Fragment implements UserLocationObjectListener, CameraListener {

    private Context context;

    public static MapView mapView;
    public static Geocoder geocoder;
    public static Point myPosition;
    private UserLocationLayer userLocationLayer;
    private final Handler HandlerPlacesUpdater = new Handler();
    private final Handler HandlerCheckAllAccess = new Handler();
    public static MapFragment mf;
    private boolean followUserLocation;
    public static Button bt_crop_user;

    public static ConstraintLayout cl;
    public static Typeface tf;

    private int counter;
    public static TextView tx_town;

    public static java.util.Map<String, ArrayList<String>> ArrOfFavorite;
    public static ArrayList<String> regions;
    static List<MapObjectTapListener> mapObjectTapListeners = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        //context needed for future usage
        context = this.getContext();

        assert context != null;
        MapKitFactory.initialize(context);
        //inflate view with map
        return inflater.inflate(
                R.layout.fragment_map,
                container,
                false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        //start init all data
        initView(view);
    }


    @SuppressLint("ResourceAsColor")
    public void initView(View view) {

        geocoder = new Geocoder(context, Locale.getDefault());
        tx_town = new TextView(context);

        //main fragment_map views
        cl = view.findViewById(R.id.cl_map_main);
        mf = this;
        //the map
        mapView = view.findViewById(R.id.mv_map_map);

        tf = Typeface.createFromAsset(context.getAssets(), "karlocharm.otf");

        InitialisatePrefs();

        bt_crop_user = view.findViewById(R.id.bt_map_crop);
        bt_crop_user.setOnClickListener(v -> {
            try {
                if (userLocationLayer.cameraPosition() != null) {
                    Point toPoint = Objects.requireNonNull(userLocationLayer.cameraPosition()).getTarget();
                    mapView.getMap().move(
                            new CameraPosition(toPoint, 14.5f, 0.0f, 0.0f),
                            new Animation(SMOOTH, 3),
                            null);
                }
            }catch (NullPointerException e){
                Toast.makeText(this.getContext(), R.string.map_crop_error,Toast.LENGTH_LONG).show();
            }
        });


        mapView.setZoomFocusPoint(new ScreenPoint(5.f, 4.f));
        mapView.getMap().addCameraListener(this);

        // check if all preferences were granted
        HandlerCheckAllAccess.removeCallbacks(CheckAllAccess);
        HandlerPlacesUpdater.removeCallbacks(PlacesUpdater);

        HandlerCheckAllAccess.postDelayed(CheckAllAccess, 0);


    }

    private final Runnable CheckAllAccess = new Runnable() {
        @Override
        public void run() {
            if (checkLocationAccess() && checkConnection()) {
                FindUser();
            } else {
                HandlerCheckAllAccess.postDelayed(this, 9000);
            }
        }
    };

    private final Runnable PlacesUpdater = new Runnable() {

        @Override
        public void run() {
            if (myPosition.getLatitude() != 0.0) {
                if (counter == 0) {
                    counter++;

                    mapView.getMap().move(
                            new CameraPosition(myPosition, 11.5f, 3.0f, 1.0f),
                            new Animation(Animation.Type.LINEAR, 5),
                            null);
                    new GetGeoLocation(mf, myPosition, tf).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                SetPlacesInMap(myPosition);
                HandlerPlacesUpdater.postDelayed(this, 60000);
            } else {
                HandlerPlacesUpdater.postDelayed(this, 0);
            }
        }
    };

    // get all places around in 10 km
    public static void SetPlacesInMap(Point position) {
        Log.e("lon, lat", position.getLongitude() + " " + position.getLatitude());
        APIServiceOTMGetPlaces service = APIServiceOTMConstructor.CreateService(APIServiceOTMGetPlaces.class);
        //запрос к ретрофиту на получение данных
        Call<ResponseOTM> call = service.getPlaces(
                LANGUAGE,
                10000, //radius
                position.getLongitude(),
                position.getLatitude(),
                KINDS_OF_PLACES,
                API_OTM_KEY
        );

        call.enqueue(new Callback<ResponseOTM>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(@NonNull Call<ResponseOTM> call, @NonNull Response<ResponseOTM> response) {
                Log.e("url", CENSORED_KINDS_OF_PLACES + response.toString());
                if (response.body() != null) {
                    for (int i = 0; i < response.body().features.size(); i++) {
                        Feature card = response.body().features.get(i);
                        double lon = card.geometry.coordinates.get(1);
                        double lat = card.geometry.coordinates.get(0);
                        Bitmap bit;
                        String kinds = card.properties.kinds;
                        boolean flag = false;

                        for (String str : CENSORED_KINDS_OF_PLACES) {
                            if (kinds.contains(str)) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            if (kinds.contains("historic")) {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_monument)).getBitmap();
                            } else if (kinds.contains("cultural")) {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_historical)).getBitmap();
                            } else if (kinds.contains("industrial_facilities")) {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_industrial)).getBitmap();
                            } else if (card.properties.name.length() == 0) {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_unknown)).getBitmap();
                            } else if (kinds.contains("natural")) {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_nature)).getBitmap();
                            } else if (kinds.contains("architecture")) {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_buildings)).getBitmap();
                            } else if (kinds.contains("other")) {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_forphoto)).getBitmap();
                            } else {
                                bit = ((BitmapDrawable)mf .getResources().getDrawable(R.drawable.map_unknown)).getBitmap();
                            }

                            MapObjectTapListener mapObjectTapListener = (mapObject, point) -> {
                                GetInfoAbout(card.properties.xid, card.properties.dist.toString());
                                return true;
                            };
                            if (!mapObjectTapListeners.contains(mapObjectTapListener)) {
                                mapObjectTapListeners.add(mapObjectTapListener);
                            }
                            mapView.getMap().getMapObjects()
                                    .addPlacemark(new Point(lon, lat),
                                            ImageProvider.fromBitmap(Bitmap.createScaledBitmap(bit,
                                                    50,
                                                    50,
                                                    true))
                                    ).addTapListener(mapObjectTapListener);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOTM> call, @NonNull Throwable t) {
                Log.e("!SomethingWentWrong(P)!", t.toString());
                Toast.makeText(mf.getContext(), "[WARNING] " + t,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    // Get all info about places in  OpenTripMap
    public static void GetInfoAbout(String xid, String distance) {

        Log.e("xid", xid);
        APIServiceOTMGetInfoOfPlaces service = APIServiceOTMConstructor.CreateService(APIServiceOTMGetInfoOfPlaces.class);
        Call<ResponseOTMInfo> call = service.getInfo(
                xid,
                LANGUAGE,
                API_OTM_KEY
        );
        call.enqueue(new Callback<ResponseOTMInfo>() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onResponse(@NonNull Call<ResponseOTMInfo> call, @NonNull Response<ResponseOTMInfo> response) {
                Log.e("url", response.toString());
                if (response.body() != null) {
                    mapView.getMap().move(
                            new CameraPosition(new Point(response.body().getPoint().getLat(), response.body().getPoint().getLon()),
                                    18.5f, 0.0f, 0.0f),
                            new Animation(SMOOTH, 5),
                            null);
                    PlaceInfoDialogFragment newFragment = new PlaceInfoDialogFragment(response, distance);
                    newFragment.show(mf.requireActivity().getSupportFragmentManager().beginTransaction(), "info");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOTMInfo> call, @NonNull Throwable t) {
                Log.e("!SomethingWentWrong(I)!", t.toString());
                Toast.makeText(mf.context, "[WARNING] " + t,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void InitialisatePrefs() {
        CENSORED_KINDS_OF_PLACES = (ArrayList<String>) ALLOWED_KINDS_OF_PLACES.clone();
        ArrOfFavorite = new HashMap<>();
    }

    public void setAnchor() {
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.83))
        );
    }

    public void removeAnchor() {
        if (userLocationLayer != null) userLocationLayer.resetAnchor();
    }

    public void FindUser() {
        MapKit mapKit = MapKitFactory.getInstance();
        if (userLocationLayer == null) {
            userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        }
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }

    //check if location permission is granted
    public boolean checkLocationAccess() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mf.requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return false;
        }
        return checkLocation();
    }

    //check location???
    public boolean checkLocation() {
        LocationManager locationManager = (LocationManager) mf.requireActivity().getSystemService(Context.LOCATION_SERVICE);
        Log.e("checkLocation", "" + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final boolean[] result = new boolean[1];
            builder.setCancelable(false);
            builder.setMessage("Разрешите доступ к местоположению для работы карты");
            builder.setPositiveButton("Разрешить", (dialog, id) -> {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                checkLocation();
                result[0] = true;
            });
            final AlertDialog alert = builder.create();
            try {
                alert.show();
            } catch (Exception ignored) {
            }
            return result[0];
        } else {
            return true;
        }
    }

    public boolean checkConnection() {
        Log.e("checkConnection", hasConnection() + "");
        if (hasConnection()) {
            return true;
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final boolean[] result = new boolean[1];
            builder.setCancelable(false)
                    .setMessage("Влючи инет по-братски, а")
                    .setPositiveButton("Без б", (dialog, id) -> {
                        startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                        checkConnection();
                        result[0] = true;
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            return result[0];
        }
    }

    public boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager) mf.requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) return true;

        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) return true;

        wifiInfo = cm.getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        setAnchor();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable d = getResources().getDrawable(R.drawable.map_userpic);
        Bitmap bitmap1 = ((BitmapDrawable) d).getBitmap();
        userLocationView.getPin().setIcon(ImageProvider.fromBitmap(Bitmap.createScaledBitmap(bitmap1, 70, 70, true)));
        userLocationView.getArrow().setIcon(ImageProvider.fromBitmap(Bitmap.createScaledBitmap(bitmap1, 70, 70, true)));
        followUserLocation = false;
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
        HandlerPlacesUpdater.postDelayed(PlacesUpdater, 0);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onCameraPositionChanged(@NonNull Map m, @NonNull CameraPosition cP, @NonNull CameraUpdateReason cUR, boolean finished) {
        // Гениальный(sic!) текствью с описанием зума карты слева сверху
        if (regions != null && regions.size() > 0) {
            float zoom = cP.getZoom();
            if (zoom > 12f) {
                tx_town.setText(regions.get(0));
            } else if (zoom < 12f && zoom > 8f) {
                tx_town.setText(regions.get(1));
            } else if (zoom < 8f && zoom > 5f) {
                tx_town.setText(regions.get(2));
            } else if (zoom < 5f) {
                tx_town.setText(regions.get(3));
            }
        }

        if (finished) {
            if (followUserLocation) {
                setAnchor();
            }
        } else {
            if (!followUserLocation) {
                removeAnchor();
            }
        }
    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {
        double lat = userLocationView.getPin().getGeometry().getLatitude();
        double lon = userLocationView.getPin().getGeometry().getLongitude();
        myPosition = new Point(lat, lon);
    }
}
