package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sumejja on 6/13/16.
 */
public class HistorijaAdapter extends ArrayAdapter<Pretraga> {
    int resource;
    Context context;

    public HistorijaAdapter(Context context, int res, List<Pretraga> pretrage) {
        super(context, res, pretrage);
        this.context = context;
        this.resource = res;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout noviView;

        if (convertView == null) {
            noviView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater inflaterL;
            inflaterL = (LayoutInflater) getContext().getSystemService(inflater);
            inflaterL.inflate(resource, noviView, true);
        }
        else {
            noviView = (LinearLayout) convertView;
        }

        Pretraga pretragaHelp = getItem(position);

        //sliku jos uvijek cuvam kako ne bih morala praviti novi element liste
        ImageView imageView = (ImageView) noviView.findViewById(R.id.slika);
        imageView.setImageResource(R.drawable.images);
        TextView pretraga = (TextView) noviView.findViewById(R.id.imePjevaca);
        TextView vrijeme = (TextView) noviView.findViewById(R.id.zanr);

        pretraga.setText(pretragaHelp.getPretraga());
        vrijeme.setText(pretragaHelp.getVrijeme());

        if (pretragaHelp.getStatus().equals("uspjesna")) {
            pretraga.setTextColor(Color.parseColor("#00FF00"));
            vrijeme.setTextColor(Color.parseColor("#00FF00"));
        }
        else {
            pretraga.setTextColor(Color.parseColor("#FF0000"));
            vrijeme.setTextColor(Color.parseColor("#FF0000"));
        }

        return noviView;
    }
}
