package we.itschool.project.traveler.presentation.fragment.map;


import static com.yandex.runtime.Runtime.getApplicationContext;
import static we.itschool.project.traveler.data.api.opentripmapapi.APIConfigOTM.ALLOWED_KINDS_OF_PLACES;
import static we.itschool.project.traveler.data.api.opentripmapapi.APIConfigOTM.CENSORED_KINDS_OF_PLACES;
import static we.itschool.project.traveler.data.api.opentripmapapi.APIConfigOTM.LANGUAGE;
import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.mapObjectTapListeners;
import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.mapView;
import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.mf;
import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.myPosition;
import static we.itschool.project.traveler.presentation.fragment.map.MapFragment.tf;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import we.itschool.project.traveler.R;

public class SettingsDialogFragment extends DialogFragment{
    int indx_str_apply;
    String title;
    ArrayList<String> CENSORED_KINDS_OF_PLACES_P = CENSORED_KINDS_OF_PLACES;
    String LANGUAGE_P = LANGUAGE.toString();
    String tx_lang_str;
    TableLayout tableLayout;
    Switch switc;
    TextView tx_lang;

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        tableLayout = view.findViewById(R.id.contentDialogFragment);

        switc = new Switch(mf.getContext());
        tx_lang = new TextView(mf.getContext());

        if (!LANGUAGE.equals("ru")){
            indx_str_apply = R.string.ok_eng;
            title = "Settings";
            tx_lang_str = "Change language";
            switc.setChecked(false);
        } else{
            title = "Настройки";
            tx_lang_str = "Сменить язык";
            indx_str_apply = R.string.ok;
            switc.setChecked(true);
        }

        AlertDialog.Builder placeInfo = new AlertDialog.Builder(getActivity());
        placeInfo.setTitle(title);
        placeInfo.setIcon(R.drawable.wing);
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 250;

        switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    LANGUAGE_P = "en";
                } else {
                    LANGUAGE_P = "ru";
                }
            }
        });
        switc.setShowText(true);
        switc.setTextOn("РУС");
        switc.setTextOff("ENG");
        switc.setText(tx_lang_str);

        tableLayout.addView(switc, params);

        for (String str: ALLOWED_KINDS_OF_PLACES){
            @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switc = new Switch(mf.getContext());
            switc.setShowText(true);
            switc.setText("#"+str);
            if (CENSORED_KINDS_OF_PLACES_P.contains(str)){
                switc.setChecked(true);
            }
            switc.setPadding(14,7,7,7);
            switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        CENSORED_KINDS_OF_PLACES_P.add(str);
                    } else {
                        if (CENSORED_KINDS_OF_PLACES_P.contains(str)){
                            CENSORED_KINDS_OF_PLACES_P.remove(str);
                        }
                    }
                }
            });
            tableLayout.addView(switc,params);
        }
        placeInfo.setView(view);
        placeInfo.setNegativeButton(indx_str_apply, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CENSORED_KINDS_OF_PLACES = CENSORED_KINDS_OF_PLACES_P;
                LANGUAGE = LANGUAGE_P;
                if (myPosition.getLatitude() != 0.0){
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                            .putString("lang", LANGUAGE)
                            .putString("kinds",TextUtils.join(",", CENSORED_KINDS_OF_PLACES))
                            .apply();
                    mapObjectTapListeners.clear();
                    mapView.getMap().getMapObjects().clear();
                    mf.SetPlacesInMap(myPosition);
                    new GetGeoLocation(mf, myPosition, tf).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                dialog.dismiss();
            }
        });
        return placeInfo.create();
    }
}
