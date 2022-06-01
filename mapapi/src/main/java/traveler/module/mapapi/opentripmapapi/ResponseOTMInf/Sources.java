
package traveler.module.mapapi.opentripmapapi.ResponseOTMInf;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Sources implements Parcelable {

    @SerializedName("geometry")
    @Expose
    private String geometry;
    @SerializedName("attributes")
    @Expose
    private List<String> attributes = null;
    public final static Creator<Sources> CREATOR = new Creator<Sources>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Sources createFromParcel(Parcel in) {
            return new Sources(in);
        }

        public Sources[] newArray(int size) {
            return (new Sources[size]);
        }

    };

    protected Sources(Parcel in) {
        this.geometry = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.attributes, (String.class.getClassLoader()));
    }

    public Sources() {
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(geometry);
        dest.writeList(attributes);
    }

    public int describeContents() {
        return 0;
    }

}
