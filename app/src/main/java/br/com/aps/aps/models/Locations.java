package br.com.aps.aps.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Locations implements Parcelable {

    @JsonProperty("result")
    private List<Location> locations;

    public Locations() {}

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Locations)) return false;

        Locations locations1 = (Locations) o;

        return getLocations().equals(locations1.getLocations());

    }

    @Override
    public int hashCode() {
        return getLocations().hashCode();
    }

    @Override
    public String toString() {
        return "Locations{" +
                "locations=" + locations +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(locations);
    }

    protected Locations(Parcel in) {
        this.locations = in.createTypedArrayList(Location.CREATOR);
    }

    public static final Parcelable.Creator<Locations> CREATOR = new Parcelable.Creator<Locations>() {
        public Locations createFromParcel(Parcel source) {
            return new Locations(source);
        }

        public Locations[] newArray(int size) {
            return new Locations[size];
        }
    };

}
