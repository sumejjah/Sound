package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.os.AsyncTask;
import android.renderscript.ScriptIntrinsicLUT;
import android.widget.Toast;

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
 * Created by Sumeja on 25.5.2016..
 */
public class SearchMusician extends AsyncTask<String, Void, Object>
{
    String json_url;
    ArrayList<Muzicar> nadjeniMuzicari = new ArrayList<Muzicar>();

    @Override
    protected Object doInBackground(String... params) {
        String query = null;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        json_url = "https://api.spotify.com/v1/search?q=" + query + "&type=artist";
        try {
            URL url = new URL(json_url);
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

                List<String> lista = new ArrayList<String>() {};
                List<Album> albumiNeki = new ArrayList<Album>();

                // Spotify nema biografije  //top5, albumi, slicni, id, slika_ur, slikaB
               // nadjeniMuzicari.add(new Muzicar(ime, "", zanr, website, "Ovdje treba da piše biografija, ali ne piše.", lista, albumiNeki, lista, muzicar_ID, slika_url, ));
                //nadam se da ovo vise ne treba haha
            }
            return nadjeniMuzicari;
        }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
            e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return false;
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

    public interface OnMuzicarSearchDone{
        public void onDone(List<Muzicar> nadjeniMuzicari);
    }

    private OnMuzicarSearchDone  pozivatelj;

    public SearchMusician(OnMuzicarSearchDone p){
        this.pozivatelj = p;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pozivatelj.onDone(nadjeniMuzicari);
    }
}
