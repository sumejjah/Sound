package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import android.os.Handler;

public class FragmentLista extends Fragment implements SearchMusician.OnMuzicarSearchDone, MyResultReciever.Receiver {
    View this_view;
    private OnItemClick oic;
    Button search; //da nadje nove
    Button historija; //da prikaze sve pretrage bilo da ima ili nema interneta
    EditText editText;
    String ime;

    MyResultReciever mReceiver;

    List<Muzicar> nadjeniMuzicari = new ArrayList<Muzicar>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {
        final View rview = inflater.inflate(R.layout.fragment_lista, container, false);
        this_view = rview;
        //ListView lv = (ListView)rview.findViewById(R.id.list_view);
        search = (Button) rview.findViewById(R.id.search);
        historija = (Button) rview.findViewById(R.id.historija_btn);

       // muzicaris = getResources().getStringArray(R.array.muzicari);
       // zanrovi = getResources().getStringArray(R.array.zanrovi);


        if(getArguments().containsKey("Alista"))
        {
            final ArrayList<Muzicar> muzicari = getArguments().getParcelableArrayList("Alista");

            /*final MuzicarAdapter adapter = new MuzicarAdapter(getActivity(), muzicari);
            ListView lista = (ListView)rview.findViewById(R.id.list_view);
            lista.setAdapter((ListAdapter) adapter);*/



        }

        try
        {
            oic = (OnItemClick)getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString() +
                    "Treba implementirati OnItemClick");
        }
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oic.onItemClicked(position, nadjeniMuzicari);
            }
        });*/

        historija.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle arguments = new Bundle();
                ArrayList<Pretraga> pretrage = new ArrayList<Pretraga>();
                MuzicarDBOpenHelper mdb = new MuzicarDBOpenHelper(getActivity());
                Cursor cursor = mdb.getPretrage(mdb);
                cursor.moveToFirst();

                try {
                    do {

                        String pretraga = cursor.getString(cursor.getColumnIndex(MuzicarDBOpenHelper.PRETRAGA_QUERY));
                        String vr = cursor.getString(cursor.getColumnIndex(MuzicarDBOpenHelper.PRETRAGA_VRIJEME));
                        String status = cursor.getString(cursor.getColumnIndex(MuzicarDBOpenHelper.PRETRAGA_STATUS));

                        pretrage.add(0, new Pretraga(pretraga, vr, status));

                    } while (cursor.moveToNext());
                }
                catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                arguments.putParcelableArrayList("pretrage", pretrage);
                FragmentHistorija fragmentHistorija = new FragmentHistorija();
                fragmentHistorija.setArguments(arguments);

                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.pretrage_layout, fragmentHistorija).commit();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) this_view.findViewById(R.id.editText);
                ime = editText.getText().toString();
                if (ime.length() > 2)
                {
                    // linija u nastavku se koristila u tutorijalu 4
                    //new SearchMusician((SearchMusician.OnMuzicarSearchDone) FragmentLista.this).execute(ime);
                    try {
                        if (isNetworkAvailable()) {
                            Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MyService.class);
                            intent.putExtra("imeMuzicara", ime);
                            mReceiver = new MyResultReciever(new Handler());
                            mReceiver.setReceiver(FragmentLista.this);
                            intent.putExtra("receiver", mReceiver);

                            dodajPretragu(true);
                            getActivity().startService(intent);
                        }
                        else {
                            Toast toast = Toast.makeText(getActivity(), "NISTE SPOJENI NA INTERNET!", Toast.LENGTH_LONG);
                            toast.show();

                            dodajPretragu(false);
                        }
                    }
                    catch (Exception e){
                        System.out.println(e.getLocalizedMessage());
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Unos je prekratak", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rview;
    }

    @Override
    public void onDone(List<Muzicar> muzicari) {  //no ova metoda se ne koristi vise, ali dosta toga
        //bih trebala obrisati ako nju obrise, tako da sam je ostavila tu gdje jest
        this.nadjeniMuzicari = muzicari;
        final MuzicarAdapter adapter = new MuzicarAdapter(getActivity(), (ArrayList<Muzicar>) nadjeniMuzicari);
        //ListView lista = (ListView)this_view.findViewById(R.id.list_view);
        //lista.setAdapter((ListAdapter) adapter);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case MyService.STATUS_RUNNING:
                Toast.makeText(getActivity(), "Pretraživanje je počelo!", Toast.LENGTH_LONG).show();
                break;
            case MyService.STATUS_FINISHED:
/* Dohvatanje rezultata i update UI */
                List<Muzicar> tmpMuzicari = new ArrayList<Muzicar>();
                tmpMuzicari = resultData.getParcelableArrayList("nadjeniMuzicari");
                if(tmpMuzicari.size() != 0){
                     nadjeniMuzicari.add(0, tmpMuzicari.get(0));
                    nadjeniMuzicari.get(0).setPomocniContext(getActivity());
                    //da se uzme samo jedan muzicar iz liste nadjenih
                }

            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("pretrazeni", (ArrayList<? extends Parcelable>) nadjeniMuzicari);
            FragmentPretrazeni pretrazeniFragment = new FragmentPretrazeni();
            pretrazeniFragment.setArguments(arguments);

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.pretrage_layout, pretrazeniFragment).commit();
            break;

            case MyService.STATUS_ERROR:
/* Slučaj kada je došlo do greške */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }


    public interface OnItemClick {
        public void onItemClicked(int position, List<Muzicar> nadjeniMuzicari);
    }

    public interface OnContextMenuItemSelect {

        public void onContextMenuItemSelected(Muzicar mProslijedjeni);
    }


    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void dodajUListu(Pretraga p) {

        if (isNetworkAvailable()) {

            Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MyService.class);
            MyResultReciever mReceiver = new MyResultReciever(new Handler());
            mReceiver.setReceiver(FragmentLista.this);

            intent.putExtra("imeMuzicara", p.getPretraga());
            intent.putExtra("receiver", mReceiver);

            ime = p.getPretraga();

            dodajPretragu(true);

            getActivity().startService(intent);
        }
        else {
            Toast toast = Toast.makeText(getActivity(), "NEMA KONEKCIJE!", Toast.LENGTH_SHORT);
            toast.show();

            dodajPretragu(false);
        }
    }

    public void dodajPretragu(boolean status) {

        MuzicarDBOpenHelper muzicarDBOpenHelper = new MuzicarDBOpenHelper(getActivity());
        //da dobijem vrijeme

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String vrijeme = dateFormat.format(date);
        if (ime.equals("") == false) {

            muzicarDBOpenHelper.insert(muzicarDBOpenHelper, null,
                                            new Pretraga(ime, vrijeme, status ? "uspjesna" : "neuspjesna"));
            ime = "";
        }

    }
}

