package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.net.NetworkInterface;

public class SynhronizeBroadcastReciever extends BroadcastReceiver {
    public SynhronizeBroadcastReciever() {
    }

    boolean wifi = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final boolean isWifiConn = networkInfo.isConnected();

        Log.d("debug", "Wifi connected: " + isWifiConn);

        Toast toast = Toast.makeText(context, "Wifi connected: " + isWifiConn, Toast.LENGTH_SHORT);
        toast.show();

    }
}