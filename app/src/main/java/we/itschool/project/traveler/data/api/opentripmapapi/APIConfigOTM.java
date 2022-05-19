package we.itschool.project.traveler.data.api.opentripmapapi;

import java.util.ArrayList;
import java.util.Arrays;

public class APIConfigOTM {
    //the main lib
    public static final String HOST_URL = "https://api.opentripmap.com/";
    public static String KINDS_OF_PLACES = "interesting_places";
    //yandex key
    public static final String API_YANDEX_MAP_KEY = "27dd15fb-428f-4450-8171-83bd321981e9";
    //open trip map key
    public static final String API_OTM_KEY = "5ae2e3f221c38a28845f05b6389535f5b10bb70b3ae47cc6c02d31f6";
    public static String LANGUAGE = "ru";
    public static ArrayList<String> CENSORED_KINDS_OF_PLACES = new ArrayList<>();
    public static final ArrayList<String> ALLOWED_KINDS_OF_PLACES = new ArrayList<String>(Arrays.asList(
            "historic",
            "cultural",
            "industrial_facilities",
            "natural",
            "architecture",
            "other"
    ));
}
