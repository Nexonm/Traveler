
package we.itschool.project.traveler.data.api.opentripmapapi.ResponseOTM;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ResponseOTM implements Parcelable {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("features")
    @Expose
    public List<Feature> features = new ArrayList<Feature>();
    public final static Creator<ResponseOTM> CREATOR = new Creator<ResponseOTM>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ResponseOTM createFromParcel(Parcel in) {
            return new ResponseOTM(in);
        }

        public ResponseOTM[] newArray(int size) {
            return (new ResponseOTM[size]);
        }

    };

    protected ResponseOTM(Parcel in) {
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.features, (Feature.class.getClassLoader()));
    }

    public ResponseOTM() {
    }

    public ResponseOTM withType(String type) {
        this.type = type;
        return this;
    }

    public ResponseOTM withFeatures(List<Feature> features) {
        this.features = features;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeList(features);
    }

    public int describeContents() {
        return 0;
    }

}
