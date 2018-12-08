package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sumeja on 18.3.2016..
 */
public class Muzicar implements Parcelable {
    String id;
    String ime;
    String prezime;
    String zanr;
    String webStranica;
    String biografija;
    int slika;
    String slika_url;
    Bitmap slika_;
    List<String> top5;
    List<String> slicniMuzicari;
    List<Album> albumi;

    public void setPomocniContext(Context pomocniContext) {
        this.pomocniContext = pomocniContext;
    }

    public Context getPomocniContext() {
        return pomocniContext;
    }

    Context pomocniContext;


    public Muzicar(String ime, String prezime, String zanr, String webStranica, String biografija, int slika, List<String> top5, List<String> slicniMuzicari)
    {
        this.ime = ime;
        this.prezime = prezime;
        this.zanr = zanr;
        this.webStranica = webStranica;
        this.biografija = biografija;
        this.slika = slika;
        this.top5 = new ArrayList<String>();
        this.top5.addAll(top5);
        this.slicniMuzicari = new ArrayList<String>();
        this.slicniMuzicari.addAll(slicniMuzicari);
    }

    public Muzicar(String ime, String s, String zanr, String web, String bio, String spotify, ArrayList<String> pjesme, ArrayList<Album> albumiPass, List<String> pom, Context context)
    {

    }
        //zadnja verzija konstrutora
    public Muzicar(String ime, String prezime, String zanr, String webStranica, String biografija, List<String> top5, List<Album> albumi, List<String> slicni, String id, String slika_url, Context c)
    {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.zanr = zanr;
        this.webStranica = webStranica;
        this.biografija = biografija;
        this.top5 = new ArrayList<String>();
        this.top5.addAll(top5);
        this.slika_url = slika_url;
        this.slicniMuzicari = slicni;
        this.albumi = albumi;
        this.pomocniContext = c;
    }

    protected Muzicar(Parcel in) {
        id = in.readString();
        ime = in.readString();
        prezime = in.readString();
        zanr = in.readString();
        webStranica = in.readString();
        biografija = in.readString();
        slika = in.readInt();
        setTop5(in.createStringArrayList());
        setSlicniMuzicari(in.createStringArrayList());
        slika_ = in.readParcelable(Bitmap.class.getClassLoader());
        albumi = new ArrayList<Album>();
        //albumi = in.readTypedList(albumi, Album.CREATOR);
    }

    public static final Creator<Muzicar> CREATOR = new Creator<Muzicar>() {
        @Override
        public Muzicar createFromParcel(Parcel in) {
            return new Muzicar(in);
        }
        @Override
        public Muzicar[] newArray(int size) {
            return new Muzicar[size];
        }
    };

    public String getId() {return id;}

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getZanr() {
        return zanr;
    }

    public String getBiografija() {
        return biografija;
    }

    public String getWebStranica() {
        return webStranica;
    }

    public int getSlika() { return slika; }

    public void setSlika(int slika) {
        this.slika = slika;
    }

    public List<String> getTop5() {
        return top5;
    }

    public void setTop5(List<String> top5) {
        this.top5 = new ArrayList<String>();
        this.top5.addAll(top5);
    }

    public List<String> getSlicniMuzicari() {
        return slicniMuzicari;
    }

    public void setSlicniMuzicari(List<String> slicniMuzicari) {
        this.slicniMuzicari = new ArrayList<String>();
        this.slicniMuzicari.addAll(slicniMuzicari);
    }
    public String getSlika_url() {
        return slika_url;
    }

    public void setSlika_url(String slika_url) {
        this.slika_url = slika_url;
    }

    public List<Album> getAlbumi() {
        return albumi;
    }

    public void setAlbumi(List<Album> albumi) {
        this.albumi = albumi;
    }

    public Bitmap getSlika_() {
        return slika_;
    }

    public void setSlika_(Bitmap slika_) {
        this.slika_ = slika_;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ime);
        dest.writeString(prezime);
        dest.writeString(zanr);
        dest.writeString(webStranica);
        dest.writeString(biografija);
        dest.writeStringList(getTop5());
        dest.writeInt(slika);
        dest.writeStringList(getSlicniMuzicari());
    }
}
