package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

/**
 * Created by Sumeja on 19.3.2016..
 */
public class Provider
{
    private int slika_resource;
    private String ime;
    private String zanr;

    public Provider(int slika_resource, String ime, String zanr) {
        this.slika_resource = slika_resource;
        this.ime = ime;
        this.zanr = zanr;
    }

    public int getSlika_resource() {
        return slika_resource;
    }

    public String getIme() {
        return ime;
    }

    public String getZanr() {
        return zanr;
    }

    public void setSlika_resource(int slika_resource) {
        this.slika_resource = slika_resource;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }
}
