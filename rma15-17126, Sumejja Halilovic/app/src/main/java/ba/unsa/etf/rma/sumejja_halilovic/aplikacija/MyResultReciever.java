package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Sumeja on 29.5.2016..
 */
@SuppressLint("ParcelCreator")
public class MyResultReciever extends ResultReceiver {
    private Receiver mReceiver;
    public MyResultReciever(Handler handler) {
        super(handler);
    }
    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
