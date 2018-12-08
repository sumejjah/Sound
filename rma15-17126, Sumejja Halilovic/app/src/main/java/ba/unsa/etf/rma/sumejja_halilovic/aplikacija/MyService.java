package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sumeja on 29.5.2016..
 */
public class MyService extends IntentService {
    final public static int STATUS_RUNNING=0;
    final public static int STATUS_FINISHED=1;
    final public static int STATUS_ERROR=2;

    List<Muzicar> nadjeniMuzicari = new ArrayList<Muzicar>();
    List<Album> albumi = new ArrayList<Album>();
    List<String> najslusanijePjesme = new ArrayList<String>();
    List<String> slicniMuzicari = new ArrayList<String>();

    public MyService() {
        super(null);
    }
    public MyService(String name) {
        super(name);
// Sav posao koji treba da obavi konstruktor treba da se
// nalazi ovdje
    }
    @Override
    public void onCreate() {
        super.onCreate();
// Akcije koje se trebaju obaviti pri kreiranju servisa
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();
        /* Update UI: Početak taska */
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);

        String params = intent.getStringExtra("imeMuzicara");

        try {
            String  query = URLEncoder.encode(params, "utf-8");

            URL url = null;
            String json_url = "https://api.spotify.com/v1/search?q=" + query + "&type=artist&limit=5";
            try {
                url = new URL(json_url);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONObject artists = jo.getJSONObject("artists");

            JSONArray items = artists.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject muzicar = items.getJSONObject(i);
                String ime = muzicar.getString("name");
                String muzicar_ID = muzicar.getString("id");
                String website = "";
                String zanr = "";
                String slika_url = "";
                JSONObject json_website = muzicar.getJSONObject("external_urls");
                if (json_website != null)
                    website = json_website.getString("spotify");

                JSONArray json_genres = muzicar.getJSONArray("genres");
                if (json_genres != null) {
                    for (int j = 0; j < json_genres.length(); j++) {
                        String thisGenre = json_genres.getString(j);
                        if (!thisGenre.equals("")) {
                            thisGenre = thisGenre.substring(0, 1).toUpperCase() + thisGenre.substring(1);
                            zanr += thisGenre + ", ";
                        }
                    }
                    if (zanr.indexOf(", ") != -1)
                        zanr = zanr.substring(0, zanr.length() - 2);
                }

                JSONArray json_images = muzicar.getJSONArray("images");
                if (json_images != null) {
                    if (json_images.length() > 0)
                        slika_url = json_images.getJSONObject(0).getString("url");
                }

                List<Album> listaAlbuma = new ArrayList<Album>();

                dajNajslusanijePjesme(muzicar_ID);
                dajSlicneMuzicare(muzicar_ID);

                Muzicar trenutniMuzicar = new Muzicar(ime, "", zanr, website,
                        "Ovdje treba da piše biografija, ali ne piše.",
                        najslusanijePjesme, listaAlbuma, slicniMuzicari, muzicar_ID, slika_url, getBaseContext());
                trenutniMuzicar.setAlbumi(dajAlbume(muzicar_ID));
                trenutniMuzicar.setSlika_(dajSliku(slika_url));

                nadjeniMuzicari.add(trenutniMuzicar);

            }
        }
        catch (MalformedURLException e) {
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        } catch (IOException e) {
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        } catch (JSONException e) {
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        }
        /* Proslijedi rezultate nazad u pozivatelja */
        bundle.putParcelableArrayList("nadjeniMuzicari",(ArrayList<Muzicar>) nadjeniMuzicari);
        receiver.send(STATUS_FINISHED, bundle);
    }

    public List<Album> dajAlbume(String... params){
        String idMuzicar = null;
        try
        {
            idMuzicar = URLEncoder.encode((String) params[0], "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        //albumi muzicara
        String url_albums = "https://api.spotify.com/v1/artists/" + idMuzicar + "/albums";
        try
        {
            URL url = new URL(url_albums);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject album = items.getJSONObject(i);
                String name = album.getString("name");
                String album_ID = album.getString("id");
                String image_url = "";

                JSONArray json_images = album.getJSONArray("images");
                if(json_images != null)
                {
                    if(json_images.length() > 0)
                        image_url = json_images.getJSONObject(0).getString("url");
                }

                albumi.add(new Album(name, album_ID, image_url));

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albumi;
    }

    void dajNajslusanijePjesme(String... params){
        String idMuzicar = null;
        try
        {
            idMuzicar = URLEncoder.encode((String) params[0], "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String json_url = "https://api.spotify.com/v1/artists/" + idMuzicar + "/top-tracks?country=US";

        try{
            URL url = new URL(json_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("tracks");
            int duzina = 0;
            if (items.length() > 5) duzina = 5;
            else duzina = items.length();

            for (int i = 0; i < duzina; i++)
            {
                JSONObject track = items.getJSONObject(i);
                String name = track.getString("name");

                najslusanijePjesme.add(name);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dajSlicneMuzicare(String... params){
        String idMuzicar = null;
        try
        {
            idMuzicar = URLEncoder.encode((String) params[0], "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        String url_related = "https://api.spotify.com/v1/artists/" + idMuzicar + "/related-artists";
        try
        {
            URL url = new URL(url_related);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);

            JSONArray items = jo.getJSONArray("artists");
            int duzina = 0;
            if (items.length() > 5) duzina = 5;
            else duzina = items.length();

            for (int i = 0; i < duzina; i++) {

                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");

                slicniMuzicari.add(name);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e)
        {
        }
        finally {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
            }
        }
        return sb.toString();
    }

    private Bitmap dajSliku(String... params){
        String urldisplay = params[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }
}
