package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Sumeja on 30.5.2016..
 */
public class MuzicarCursorAdapter extends ResourceCursorAdapter {
    public MuzicarCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView ime = (TextView) view.findViewById(R.id.textView2);
        //ime.setText(cursor.getString(cursor.getColumnIndex(MUZICAR_IME)));
        TextView zanr = (TextView) view.findViewById(R.id.zanr);
        //zanr.setText(cursor.getString(cursor.getColumnIndex(MUZICAR_ZANR)));
    }

}
