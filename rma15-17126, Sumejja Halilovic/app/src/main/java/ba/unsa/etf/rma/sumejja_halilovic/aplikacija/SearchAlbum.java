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
public class SearchAlbum extends AsyncTask<String, Void, Object> {
    List<Album> albumi =new ArrayList<Album>();

    public interface OnAlbumSearch{
        public void onAlbumSearch(List<Album> lista);
    }

    OnAlbumSearch pozivatelj;

    SearchAlbum(OnAlbumSearch p) {pozivatelj = p;}

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
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return albumi;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pozivatelj.onAlbumSearch(albumi);
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
