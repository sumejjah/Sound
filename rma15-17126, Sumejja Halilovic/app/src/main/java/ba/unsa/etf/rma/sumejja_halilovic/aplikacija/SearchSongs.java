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
import java.util.Objects;

/**
 * Created by Sumeja on 29.5.2016..
 */
public class SearchSongs extends AsyncTask<Object, Void, Object> {

    List<String> najslusanijePjesme = new ArrayList<String>();

    @Override
    protected Object doInBackground(Object... params) {
        String idMuzicar = null;
        try
        {
            idMuzicar = URLEncoder.encode((String)params[0], "utf-8");
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

        return najslusanijePjesme;
    }

    public interface OnSongSearchDone{
        public void onSongDone(List<String> lista);
    }

    private OnSongSearchDone pozivatelj;

    public SearchSongs(OnSongSearchDone p) {pozivatelj = p;}

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pozivatelj.onSongDone(najslusanijePjesme);
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
