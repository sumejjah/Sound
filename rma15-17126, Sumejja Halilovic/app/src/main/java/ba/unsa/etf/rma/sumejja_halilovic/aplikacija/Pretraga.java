package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Time;
import java.util.Date;

/**
 * Created by sumejja on 6/13/16.
 */
public class Pretraga implements Parcelable{
    String pretraga;
    String vrijeme;
    String status;

    public Pretraga(String p, String vr, String st) {
        pretraga = p;
        vrijeme = vr;
        status = st;
    }

    public String getPretraga() {
        return pretraga;
    }

    protected Pretraga(Parcel in) {
        pretraga = in.readString();
        vrijeme = in.readString();
        status = in.readString();
    }

    public static final Creator<Pretraga> CREATOR = new Creator<Pretraga>() {
        @Override
        public Pretraga createFromParcel(Parcel in) {
            return new Pretraga(in);
        }
        @Override
        public Pretraga[] newArray(int size) {
            return new Pretraga[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(pretraga);
        dest.writeString(vrijeme);
        dest.writeString(status);
    }

    public String getVrijeme() {
        return vrijeme;
    }

    public String getStatus() {
        return status;
    }
}
