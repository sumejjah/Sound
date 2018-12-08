package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sumeja on 19.5.2016..
 */
public class FragmentTop5 extends Fragment {
    TextView textView;
    private ArrayList<String> pjesme;
    private String ime, prezime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pogled = inflater.inflate(R.layout.fragment_top5, container, false);

        if (getArguments().containsKey("ListaPjesama") && getArguments().containsKey("ImePjevaca") && getArguments().containsKey("PrezimePjevaca"))
        {
            pjesme = getArguments().getStringArrayList("ListaPjesama");
            ime = getArguments().getString("ImePjevaca");
            prezime = getArguments().getString("PrezimePjevaca");
            final ListView listView = (ListView) pogled.findViewById(R.id.listView);
            textView = (TextView) pogled.findViewById(R.id.top5);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pjesme);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            // Prosljedjivaje na youtube ako se klikne na pjesmu
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String imePjesme = (String) listView.getItemAtPosition(position);
                    boolean instaliranyt = isAppInstalled("https://www.youtube.com/results?search_query");
                    if(!instaliranyt){
                    Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + ime + "+" + prezime + "+" + imePjesme));
                    startActivity(ytIntent);
                    }
                    else {
                        Toast.makeText(getActivity(), "Nije instalirana youtube aplikacija!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        return pogled;
    }

    protected boolean isAppInstalled(String packageName) {
        Intent mIntent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
