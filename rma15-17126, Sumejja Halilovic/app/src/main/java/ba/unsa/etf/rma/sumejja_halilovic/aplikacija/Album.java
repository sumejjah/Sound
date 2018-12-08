package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumeja on 29.5.2016..
 */
public class Album implements Parcelable {
    private String ime;
    private String id;
    private String slika_url;

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlika_url() {
        return slika_url;
    }

    public void setSlika_url(String slika_url) {
        this.slika_url = slika_url;
    }

    public Album(String i, String id, String slika)
    {
        this.ime = i;
        this.id = id;
        this.slika_url = slika;
    }

    protected Album(Parcel in){
        ime = in.readString();
        id = in.readString();
        slika_url = in.readString();
    }

    public static final Creator<Album> CREATOR= new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ime);
            dest.writeString(id);
            dest.writeString(slika_url);
    }
}
