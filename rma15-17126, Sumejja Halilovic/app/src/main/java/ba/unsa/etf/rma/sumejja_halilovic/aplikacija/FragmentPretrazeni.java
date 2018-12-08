package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumejja on 6/13/16.
 */
public class FragmentPretrazeni extends Fragment {
    ArrayList<Muzicar> muzicari;
    NoviAdapter adapter;
    private FragmentLista.OnItemClick onItemClick;
    private FragmentLista.OnContextMenuItemSelect onContextMenuItemSelect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rView = inflater.inflate(R.layout.fragment_pretrazeni, container, false);

        if (getArguments().containsKey("pretrazeni")) {

            muzicari = getArguments().getParcelableArrayList("pretrazeni");

            ListView listView = (ListView) rView.findViewById(R.id.pretrazeniM);

            // za prikaz albuma
            registerForContextMenu(listView);

            adapter = new NoviAdapter(getActivity(), R.layout.roww_layout, muzicari);
            listView.setAdapter(adapter);

            try {

                onItemClick = (FragmentLista.OnItemClick) getActivity();
            }
            catch (ClassCastException e) {

                throw new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemClick.onItemClicked(position, muzicari);
                }
            });
        }

        return rView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            if(item.getItemId() == R.id.delete_context_option){
                muzicari.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            }
            else if(item.getItemId() == R.id.albums_context_option) {
                onContextMenuItemSelect = (FragmentLista.OnContextMenuItemSelect) getActivity();
                onContextMenuItemSelect.onContextMenuItemSelected(muzicari.get(info.position));
                return true;
            }
            else
                return super.onContextItemSelected(item);
    }


}
