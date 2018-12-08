package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Sumeja on 20.5.2016..
 */
public class FragmentSlicni extends Fragment
{
    private ArrayList<String> muzicari;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_slicni, container, false);
        if (getArguments().containsKey("SlicniMuzicari"))
        {
            muzicari = getArguments().getStringArrayList("SlicniMuzicari");
            final ListView listView = (ListView) view.findViewById(R.id.listView2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, muzicari);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return view;
    }
}
