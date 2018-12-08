package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.Context;
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
public class NoviAdapter extends ArrayAdapter<Muzicar> {
    int resource;
    Context ctxt;

    public NoviAdapter(Context context, int _resource, List<Muzicar> items) {
        super(context, _resource, items);
        ctxt = context;
        resource = _resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout newView;

        if (convertView == null) {

            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, newView, true);
        }
        else {
            newView = (LinearLayout) convertView;
        }

        Muzicar muzicar = getItem(position);

        TextView textView = (TextView) newView.findViewById(R.id.imePjevaca);
        textView.setText(muzicar.getIme());

        TextView genreTextView = (TextView) newView.findViewById(R.id.zanr);
        genreTextView.setText(muzicar.getZanr());

        ImageView imageView = (ImageView) newView.findViewById(R.id.slika);
        String genreMatch = muzicar.getZanr();
        if (genreMatch.contains("pop")) {
            imageView.setImageResource(R.drawable.pop);
        }
        else if (genreMatch.contains("rock") || genreMatch.contains("rock n' roll")) {
            imageView.setImageResource(R.drawable.rock);
        }
        else if (genreMatch.equals("traditional")) {
            imageView.setImageResource(R.drawable.sevdah);
        }
        else if (genreMatch.contains("jazz")) {
            imageView.setImageResource(R.drawable.jazz);
        }
        else
        {
            imageView.setImageResource(R.drawable.images);
        }

        return newView;
    }

    public Muzicar getItemClicked(int position) {
        return getItem(position);
    }
}
