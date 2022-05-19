
package we.itschool.project.traveler.data.api.opentripmapapi.ResponseOTMInf;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Preview implements Parcelable {

    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("width")
    @Expose
    private int width;
    public final static Creator<Preview> CREATOR = new Creator<Preview>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Preview createFromParcel(Parcel in) {
            return new Preview(in);
        }

        public Preview[] newArray(int size) {
            return (new Preview[size]);
        }

    };

    protected Preview(Parcel in) {
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((int) in.readValue((int.class.getClassLoader())));
        this.width = ((int) in.readValue((int.class.getClassLoader())));
    }

    public Preview() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(source);
        dest.writeValue(height);
        dest.writeValue(width);
    }

    public int describeContents() {
        return 0;
    }

}
