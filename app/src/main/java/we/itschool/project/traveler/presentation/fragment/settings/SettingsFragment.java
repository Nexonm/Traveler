package we.itschool.project.traveler.presentation.fragment.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import we.itschool.project.traveler.R;

public class SettingsFragment extends Fragment {
    Switch bt_language;
    private boolean isEnglish;

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        bt_language = view.findViewById(R.id.bt_settings_language);
        bt_language.setOnClickListener(v -> isEnglish = !isEnglish);
    }

    public boolean isValid(String dateStr) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate.parse(dateStr, dateFormatter);
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate.parse(dateStr, dateFormatter);
            } catch (DateTimeParseException e1) {
                try {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    LocalDate.parse(dateStr, dateFormatter);
                } catch (DateTimeParseException e2) {
                    try {
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                        LocalDate.parse(dateStr, dateFormatter);
                    } catch (DateTimeParseException e3) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}