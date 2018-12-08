package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sumeja on 19.3.2016..
 */
public class Adapterr extends ArrayAdapter {
    //ovo je adapter sa prve zadace, vise ga ne koristim
    List list = new ArrayList();
    static  class DataHandler
    {
        ImageView slika;
        TextView ime;
        TextView zanr;
    }
    public Adapterr(Context context, int resource) {
        super(context, resource);
    }
    @Override
    public void add(Object object)
    {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        DataHandler handler;
        if(convertView == null)
        {
            LayoutInflater inflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.roww_layout, parent, false);
            handler = new DataHandler();
            handler.slika =(ImageView) row.findViewById(R.id.slika);
            handler.ime = (TextView) row.findViewById(R.id.zanr);
            handler.zanr = (TextView) row.findViewById(R.id.zanr);
            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler) row.getTag();
        }
        Provider provider;
        provider = (Provider) this.getItem(position);
        handler.slika.setImageResource(provider.getSlika_resource());
        handler.ime.setText(provider.getIme());
        handler.zanr.setText(provider.getZanr());

        return row;
    }
}
