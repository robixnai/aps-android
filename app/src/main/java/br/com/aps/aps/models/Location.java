package br.com.aps.aps.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Location implements Parcelable {

    private User mUser;

    @JsonProperty(value = "id")
    private Integer mId;

    @JsonProperty(value = "latitude")
    private double mLatitude;

    @JsonProperty(value = "longitude")
    private double mLongitude;

    @JsonProperty(value = "date")
    private Date mDate;

    public Location() {}

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (Double.compare(location.getLatitude(), getLatitude()) != 0) return false;
        if (Double.compare(location.getLongitude(), getLongitude()) != 0) return false;
        if (!getId().equals(location.getId())) return false;
        if (!getUser().equals(location.getUser())) return false;
        return getDate().equals(location.getDate());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId().hashCode();
        result = 31 * result + getUser().hashCode();
        temp = Double.doubleToLongBits(getLatitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLongitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getDate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "mId=" + mId +
                ", mUser=" + mUser +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", mDate=" + mDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeParcelable(this.mUser, 0);
        dest.writeDouble(this.mLatitude);
        dest.writeDouble(this.mLongitude);
        dest.writeLong(mDate != null ? mDate.getTime() : -1);
    }

    protected Location(Parcel in) {
        this.mId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mUser = in.readParcelable(User.class.getClassLoader());
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();
        long tmpDate = in.readLong();
        this.mDate = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}
