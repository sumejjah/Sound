package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.os.AsyncTask;

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
public class SearchSlicniMuzicari extends AsyncTask<String, Void, Object> {
    List<Muzicar> slicniMuzicari =new ArrayList<Muzicar>();

    public interface OnSlicniSearch{
        public void onSlicniSearch(List<Muzicar> lista);
    }

    OnSlicniSearch pozivatelj;

    SearchSlicniMuzicari(OnSlicniSearch p) {pozivatelj = p;}

    @Override
    protected Object doInBackground(String... params) {
        String idMuzicar = null;
        try
        {
            idMuzicar = URLEncoder.encode((String) params[0], "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        // Prvo trazimo slicne muzicare
        String url_related = "https://api.spotify.com/v1/artists/" + idMuzicar + "/related-artists";
        try
        {
            URL url = new URL(url_related);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);

            JSONArray items = jo.getJSONArray("artists");

            int length = items.length() > 5 ? 5 : items.length();
            for (int i = 0; i < length; i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                String artist_ID = artist.getString("id");
                String website = "";
                String genre = "";
                String image_url = "";
                JSONObject json_website = artist.getJSONObject("external_urls");
                if(json_website != null)
                    website = json_website.getString("spotify");

                JSONArray json_genres = artist.getJSONArray("genres");
                if(json_genres != null)
                {
                    for(int j = 0; j < json_genres.length(); j++)
                    {
                        String thisGenre = json_genres.getString(j).trim();
                        if(!thisGenre.equals("")) {
                            thisGenre = thisGenre.substring(0, 1).toUpperCase() + thisGenre.substring(1);
                            genre += thisGenre + ", ";
                        }
                    }
                    if(genre.indexOf(", ") != -1)
                        genre = genre.substring(0, genre.length() - 2);
                }

                JSONArray json_images = artist.getJSONArray("images");
                if(json_images != null)
                {
                    if(json_images.length() > 0)
                        image_url = json_images.getJSONObject(0).getString("url");
                }

                List<String> listaPomocna = new ArrayList<String>();
                List<Album> pomocna2 =new ArrayList<Album>();
                 //top5, albumi, slicni, id, slika_ur, slikaB
                //slicniMuzicari.add(new Muzicar(name,"", genre, website, "", listaPomocna, pomocna2, listaPomocna, artist_ID, image_url));
               //i za ovo se nadam da ne treba
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();

        }

        return  slicniMuzicari;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pozivatelj.onSlicniSearch(slicniMuzicari);
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
}
