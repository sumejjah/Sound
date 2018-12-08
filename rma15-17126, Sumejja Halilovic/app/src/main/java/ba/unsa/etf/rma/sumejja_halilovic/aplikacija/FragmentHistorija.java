package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by sumejja on 6/13/16.
 */
public class FragmentHistorija extends Fragment {
    ArrayList<Pretraga> pretrage = new ArrayList<Pretraga>();
    HistorijaAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rView = inflater.inflate(R.layout.fragment_historija, container, false);

        if (getArguments().containsKey("pretrage")) {

            pretrage = getArguments().getParcelableArrayList("pretrage");
            ListView pretrageListView = (ListView) rView.findViewById(R.id.historija_lw);
            adapter = new HistorijaAdapter(getActivity(), R.layout.roww_layout, pretrage);
            pretrageListView.setAdapter(adapter);

            pretrageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ((FragmentLista)getParentFragment()).dodajUListu(pretrage.get(position));
                }
            });
        }

        return rView;
    }

}
