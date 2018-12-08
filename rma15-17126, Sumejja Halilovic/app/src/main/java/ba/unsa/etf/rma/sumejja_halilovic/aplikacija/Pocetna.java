package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Pocetna extends AppCompatActivity implements FragmentLista.OnItemClick{
    private boolean siriL = false;


    List<Muzicar> unosi = new ArrayList<>();
    List<String> pjesme = new ArrayList<String>();
    List<String> slicni = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna);

  /*      pjesme.add(0, "Ako me ikada sretnes");
        pjesme.add(0, "Deset mladja");
        pjesme.add(0, "Nesto lijepo treba da se desi");
        pjesme.add(0, "Da je tuga snijeg");
        pjesme.add(0, "Da sutis");

        slicni.add(0, "Zdravko Colic");
        slicni.add(0, "Selma Bajrami");
        slicni.add(0, "Hari Mata Hari");
        slicni.add(0, "Zeljko Joskimovic");
        slicni.add(0, "Edin Dzeko");

        unosi.add(new Muzicar("Dino", "Merlin", "pop", "http://www.dinomerlin.com/", "Edin Dervišhalidović (Sarajevo, 12. rujna 1962.) " +
                "poznatiji kao Dino Merlin, bosanskohercegovački je pjevač rocka, etno popa i zabavne glazbe.", R.drawable.dinomerlin,
                pjesme, slicni));
        pjesme.clear();
        pjesme.add(0, "Mojoj majci");
        pjesme.add(0, "Zelene oci");
        pjesme.add(0, "Ja te volim");
        pjesme.add(0, "Crne kose");
        pjesme.add(0, "Ali pamtim jos");

        slicni.clear();
        slicni.add(0, "Halid Beslic");
        slicni.add(0, "Selma Bajrami");
        slicni.add(0, "Hari Mata Hari");
        slicni.add(0, "Nina Badric");
        slicni.add(0, "Severina");

        unosi.add(new Muzicar("Hanka", "Paldum", "sevdalinka", "https://en.wikipedia.org/wiki/Hanka_Paldum", "Rođena je 1953. u Čajniču u obitelji Muje i" +
                " Pembe Paldum, istočna Bosna, gdje je provela prve [godine svog života. U sedmoj godini se s obitelji seli u Sarajevo." +
                " Od prvog razreda osnovne škole je pjevala u školskom zboru. Često je nastupala kao solist na školskim priredbama, " +
                "pjevajući sevdalinke. Na nagovor brata Smaila počela je pjevati za KUD Bratstvo, u kojem je on bio član.",
                R.drawable.hankapaldum, pjesme, slicni));
//miljacka, u meni jesen je, malo je malo dana, okuj me care, zlatne strune

        pjesme.clear();
        pjesme.add(0, "Miljacka");
        pjesme.add(0, "U meni jesen je");
        pjesme.add(0, "Malo je malo dana");
        pjesme.add(0, "Okuj me care");
        pjesme.add(0, "Zlatne strune");

        slicni.clear();
        slicni.add(0, "Halid Muslimovic");
        slicni.add(0, "Sinan Sakic");
        slicni.add(0, "Šaban Šaulić");
        slicni.add(0, "Koke");
        slicni.add(0, "Keba");

        unosi.add(new Muzicar("Halid", "Beslic", "sevdalinka", "http://www.halid.info", "Halid Bešlić (Knežina, Sokolac; 20. studenoga 1953.)," +
                " bosanskohercegovački pjevač narodne glazbe.", R.drawable.halidbeslic, pjesme, slicni));
//        bitanga i princeza, selma, hajdemo u planine, ima neka tajna veza, nakon svih ovih godina

        pjesme.clear();
        pjesme.add(0, "Bitanga i princeza");
        pjesme.add(0, "Selma");
        pjesme.add(0, "Hajdemo u planine");
        pjesme.add(0, "Ima neka tajna veza");
        pjesme.add(0, "Nakon svih ovih godina");

        slicni.clear();
        slicni.add(0, "Parni valjak");
        slicni.add(0, "Crvena jabuka");
        slicni.add(0, "Flamingosi");
        slicni.add(0, "Tifa");
        slicni.add(0, "Bajaga");

        unosi.add(new Muzicar("Bijelo", "dugme", "rock", "http://www.bijelo-dugme.de/", "Bijelo Dugme was officially formed in 1974, " +
                "although the members of the default lineup, guitarist Goran Bregović, vocalist Željko Bebek, drummer Ipe Ivandić, " +
                "keyboardist Vlado Pravdić and bass guitarist Zoran Redžić, were previously active under the name Jutro. ",
                R.drawable.bijelodugme, pjesme, slicni));
        pjesme.clear(); //sanjam da smo skupa mi, za dobre i lose dane, moje oci pune ljubavi
        pjesme.add(0, "Sanjam da smo skupa mi");
        pjesme.add(0, "Takvi kao ti");
        pjesme.add(0, "Ti ne znas kako je");
        pjesme.add(0, "Nebo");
        pjesme.add(0, "Za dobre i lose dane");
        pjesme.add(0, "Moje oci pune ljubavi");

        slicni.clear();
        slicni.add(0, "Hanka Paldum");
        slicni.add(0, "Selma Bajrami");
        slicni.add(0, "Jelena Rozga");
        slicni.add(0, "Nina Badric");
        slicni.add(0, "Severina");

        unosi.add(new Muzicar("Nina", "Badric", "pop", "http://www.ninabadric.com/", "Nina Badrić (Zagreb, 4. srpnja 1972.), hrvatska " +
                "je pop pjevačica i višestruka osvajačica nagrade Porin. Jedna je od najtrofejnijih glazbenika čija se popularnost" +
                " proširila i izvan granica Hrvatske.", R.drawable.ninabadric, pjesme, slicni));
*/

        FragmentManager fm = getFragmentManager(); //dohvatanje FragmentManager-a
        FrameLayout ldetalji = (FrameLayout)findViewById(R.id.detalji_muzicar);

        if(ldetalji!=null)
        {
            siriL=true;
            Bundle arguments = new Bundle();
            arguments.putParcelable("muzicar", unosi.get(0));
            FragmentDetalji fd;
            fd = (FragmentDetalji)fm.findFragmentById(R.id.detalji_muzicar);
            fd.setArguments(arguments);

            if(fd==null)
            {
                fd = new FragmentDetalji();
                fd.setArguments(arguments);
                fm.beginTransaction().replace(R.id.detalji_muzicar, fd).commit();
            }
        }


        FragmentLista fl = (FragmentLista)fm.findFragmentByTag("Lista");

        if(fl==null)
        {
            fl = new FragmentLista();

            Bundle argumenti=new Bundle();
            argumenti.putParcelableArrayList("Alista", (ArrayList<? extends Parcelable>) unosi);

            fl.setArguments(argumenti);
            fm.beginTransaction().replace(R.id.muzicari_lista, fl).commit();
        }else
        {
            fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Intent myIntent = new Intent(Pocetna.this, Detalji.class);
                        myIntent.putExtra("imeAutora", "Izvodac: " + unosi.get(position).getIme()+ " " + unosi.get(position).getPrezime());
                        myIntent.putExtra("fotografija", unosi.get(position).getSlika());
                        myIntent.putExtra("imeAutora", unosi.get(position).getIme()+ " " + unosi.get(position).getPrezime());
                        myIntent.putExtra("Zanr", "Žanr: " + unosi.get(position).getZanr());
                        myIntent.putExtra("Biografija","Biografija: " + unosi.get(position).getBiografija());
                        myIntent.putExtra("Stranica", unosi.get(position).getWebStranica());
                        List<String> listaa = unosi.get(position).getTop5();
                        myIntent.putExtra("1", listaa.get(0));
                        myIntent.putExtra("2", listaa.get(1));
                        myIntent.putExtra("3", listaa.get(2));
                        myIntent.putExtra("4", listaa.get(3));
                        myIntent.putExtra("5", listaa.get(4));

                        Pocetna.this.startActivity(myIntent);

                    }
                });*/

        /*Intent intent = getIntent();
        String action = intent.getAction();//treba radi provjere
        String type = intent.getType();//i ovo isto
        if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type))//provjera da li je pravi intent dosao
            tekst.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
*/
    }

    @Override
    public void onItemClicked(int position, List<Muzicar> nadjeniMuzicari)
    {
        Bundle arguments = new Bundle();
        this.unosi = nadjeniMuzicari;
        arguments.putParcelable("muzicar", unosi.get(position));
        FragmentDetalji fd = new FragmentDetalji();
        fd.setArguments(arguments);

        if(siriL)
        {
            getFragmentManager().beginTransaction().replace(R.id.detalji_muzicar, fd).commit();
        }
        else{
//Slučaj za ekrane sa početno zadanom širinom
            getFragmentManager().beginTransaction().replace(R.id.muzicari_lista, fd).addToBackStack(null).commit();
//Primjetite liniju .addToBackStack(null)
        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
