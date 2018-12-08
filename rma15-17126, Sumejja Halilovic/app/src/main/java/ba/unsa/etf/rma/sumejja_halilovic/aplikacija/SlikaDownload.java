package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by Sumeja on 26.5.2016..
 */
public class SlikaDownload extends AsyncTask<String, Void, Bitmap> {
    //1. Tip parametra (u ovom slučaju String)
    // 2. Tip jedinice za mjerenja progresa izvršavanja zadatka
    // 3. Tip rezultata (Bitmap)

    public interface OnDownloadDone{
        public void onDownload(Bitmap slika);
    }

    private OnDownloadDone pozivatelj;

    public SlikaDownload(OnDownloadDone p) {this.pozivatelj = p;}

    @Override
    protected Bitmap doInBackground(String... params) {
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

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        pozivatelj.onDownload(bitmap);
    }
}
