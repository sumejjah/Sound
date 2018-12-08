package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FragmentDetalji extends Fragment {
    private boolean a = false; //da pamtimo da li je kliknuto dugme jos
    private Muzicar muzicarTren;
    Button josDugme;
    Button posaljiPoruku;

    private ArrayList<String> slicniMuzicari = new ArrayList<String>();
    private ArrayList<Album> albumi = new ArrayList<Album>();
    private ArrayList<String> albumiNazivi = new ArrayList<String>();
    private ArrayList<String> top5Pjesama = new ArrayList<String>();
    private Boolean prikazanePjesme = true;
    private View view;
    private ArrayList<String> slicni = new ArrayList<String>();
    ImageView slika;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View iv= inflater.inflate(R.layout.fragment_detalji, container, false);
        if(getArguments()!=null&&getArguments().containsKey("muzicar")){
            final Muzicar muzicar=getArguments().getParcelable("muzicar");

            //upis u bazu
            MuzicarDBOpenHelper muzicarDBOpenHelper = new MuzicarDBOpenHelper(getActivity());
            //Pretraga pretraga = new Pretraga(muzicar.getIme(), "", "");
            //muzicarDBOpenHelper.insert(muzicarDBOpenHelper, muzicar, pretraga);

            muzicarTren = muzicar;
            top5Pjesama.addAll(muzicarTren.getTop5());
            slicniMuzicari.addAll(muzicarTren.getSlicniMuzicari());
            albumi.addAll(muzicarTren.getAlbumi());

            TextView ime = (TextView) iv.findViewById(R.id.imePjevaca);
            TextView zanr = (TextView) iv.findViewById(R.id.zanr); //bio web
            TextView bio = (TextView) iv.findViewById(R.id.biografija);
            TextView web = (TextView) iv.findViewById(R.id.web);
            slika = (ImageView) iv.findViewById(R.id.imageView);
            final ListView listaAlbuma = (ListView) iv.findViewById(R.id.albumiLista);
//Postavljanje i ostalih vrijednosti na isti način
            ime.setText(muzicarTren.getIme() + muzicar.getPrezime());
            zanr.setText(muzicarTren.getZanr());
            bio.setText(muzicarTren.getBiografija());
            web.setText(muzicarTren.getWebStranica());
            slika.setImageBitmap(muzicarTren.getSlika_());

            for(int i = 0; i < muzicarTren.getAlbumi().size(); i++)
                albumiNazivi.add(muzicarTren.getAlbumi().get(i).getIme());

            ArrayAdapter<String> adapterAlbumi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, albumiNazivi);
            listaAlbuma.setAdapter(adapterAlbumi);
            adapterAlbumi.notifyDataSetChanged();


            web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = muzicarTren.getWebStranica();
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });


            /* ovaj dio nam vise ne treba  :'(
            //download slike muzicara
            new SlikaDownload((SlikaDownload.OnDownloadDone) FragmentDetalji.this).execute(muzicarTren.getSlika_url());

            //download najslusanijih pjesama, u ovom trenutku sam morala dodati i atribut iD u klasi Muzicar
            new SearchSongs((SearchSongs.OnSongSearchDone) FragmentDetalji.this).execute(muzicarTren.getId());

            //download albuma trazenog pjevaca, vise nije ovako
            //new SearchAlbum((SearchAlbum.OnAlbumSearch) FragmentDetalji.this).execute(muzicarTren.getId());

            //poveyivanje liste albuma sa Listview-om
            ArrayAdapter<String> adapterAlbumi = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, albumiNazivi);
            listaAlbuma.setAdapter(adapterAlbumi);
            adapterAlbumi.notifyDataSetChanged();

            //nalayenje slisnih muzicara
            new SearchSlicniMuzicari((SearchSlicniMuzicari.OnSlicniSearch) FragmentDetalji.this).execute(muzicarTren.getId());
*/
            FragmentManager fragmentManager = getChildFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentTop5 fragmentTop5 = new FragmentTop5();
            Bundle argumenti=new Bundle();
            argumenti.putStringArrayList("ListaPjesama", top5Pjesama);
            argumenti.putString("ImePjevaca", muzicar.getIme());
            argumenti.putString("PrezimePjevaca", muzicar.getPrezime());
            fragmentTop5.setArguments(argumenti);
            fragmentTransaction.replace(R.id.dodatni_fragment, fragmentTop5).commit();

            josDugme = (Button)iv.findViewById(R.id.jos);

            josDugme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (a == true) {
                        FragmentTop5 ftop5 = new FragmentTop5();
                        Bundle arg = new Bundle();
                        arg.putStringArrayList("ListaPjesama", (ArrayList<String>) muzicarTren.getTop5());
                        arg.putString("ImePjevaca", muzicarTren.getIme());
                        arg.putString("PrezimePjevaca", muzicarTren.getPrezime());
                        ftop5.setArguments(arg);
                        getChildFragmentManager().beginTransaction().replace(R.id.dodatni_fragment, ftop5).commit();
                        a = false;
                    } else {
                        a = true;

                        FragmentSlicni fragmentMuzicari = new FragmentSlicni();
                        Bundle argu = new Bundle();
                        argu.putStringArrayList("SlicniMuzicari", (ArrayList<String>) muzicarTren.getSlicniMuzicari());
                        fragmentMuzicari.setArguments(argu);
                        getChildFragmentManager().beginTransaction().replace(R.id.dodatni_fragment, fragmentMuzicari).commit();


                    }
                }
            });

            posaljiPoruku = (Button) iv.findViewById(R.id.button);
            posaljiPoruku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, muzicarTren.getBiografija());
                    sendIntent.setType("text/plain");

                    if(sendIntent.resolveActivity(getActivity().getPackageManager())!=null)
                    {
                        startActivity(sendIntent);
                    }
                }
            });


            listaAlbuma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url = "https://open.spotify.com/album/" + albumi.get(position).getId();
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            });

        }
        return iv;
    }


    //implementacija interfejsaa, ne treba nam vise
    /*@Override
    public void onDownload(Bitmap slika) {
        this.slika.setImageBitmap(slika);
    }

    @Override
    public void onSongDone(List<String> lista) {
        this.top5Pjesama.addAll(lista);
        this.muzicarTren.setTop5(lista);
    }

    @Override
    public void onAlbumSearch(List<Album> lista) {
            albumi.addAll(lista);
        for(int i = 0; i < lista.size(); i++){
            albumiNazivi.add(lista.get(i).getIme());
        }

    }

    @Override
    public void onSlicniSearch(List<Muzicar> lista) {
        for(int i=0; i < lista.size(); i++)
            slicniMuzicari.add(lista.get(i).getIme());
    }*/
}

