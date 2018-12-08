package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sumeja on 19.5.2016..
 */
public class MuzicarAdapter extends BaseAdapter{
    Context context;
    ArrayList<Muzicar> muzicari;
    private static LayoutInflater inflater = null;

    public MuzicarAdapter(Context context, ArrayList<Muzicar> muzicari) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.muzicari = muzicari;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return muzicari.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return muzicari.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.roww_layout, null);

        TextView textIme = (TextView)vi.findViewById(R.id.imePjevaca);
        textIme.setText(muzicari.get(position).getIme());

        String zanr = muzicari.get(position).getZanr();
        TextView textZanr = (TextView)vi.findViewById(R.id.zanr);
        textZanr.setText(zanr);

        ImageView ikona = (ImageView)vi.findViewById(R.id.slika);

        zanr = zanr.toLowerCase();
        if(zanr.indexOf("rock") != -1)
            ikona.setImageResource(R.drawable.rock);
        else if(zanr.indexOf("pop") != -1)
            ikona.setImageResource(R.drawable.pop);
        else if(zanr.indexOf("traditional") != -1)
            ikona.setImageResource(R.drawable.sevdah);
        else if(zanr.indexOf("jazz") != -1)
            ikona.setImageResource(R.drawable.jazz);
        else if(zanr.indexOf("oriental") != -1)
            ikona.setImageResource(R.drawable.oriental);
        else
            ikona.setImageResource(R.drawable.images);

        return vi;
    }
}
