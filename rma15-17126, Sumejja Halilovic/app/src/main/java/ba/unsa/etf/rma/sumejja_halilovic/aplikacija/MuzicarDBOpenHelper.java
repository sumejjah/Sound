package ba.unsa.etf.rma.sumejja_halilovic.aplikacija;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sumeja on 30.5.2016..
 */
public class MuzicarDBOpenHelper extends SQLiteOpenHelper {

    private Context helpContext;
    private MuzicarDBOpenHelper dbSve;
    private int[] sviKljucevi = new int[1000];
    private int pomocniKljuc;

    public static final String DATABASE_NAME = "bazaMuzicari.db";
    public static final String DATABASE_TABLE = "Muzicari";
    public static final int DATABASE_VERSION = 2;
    public static final String MUZICAR_ID = "_id";
    public static final String MUZICAR_IME = "ime";
    public static final String MUZICAR_ZANR = "zanr";
    public static final String MUZICAR_WEB = "webStranica";
    public static final String MUZICAR_BIOGRAFIJA = "biografija";
    public static final String MUZICAR_SLIKA = "slika";

    public static final String MUZICARI_TABLE = "muzicari";
    public static final String PRETRAGE_TABLE = "pretrage";
    public static final String ALBUMI_TABLE = "albumi";
    public static final String PJESME_TABLE = "pjesme";

    //jedan je primary key
    public static final String KEY_ID = "_id";
    //foreign key-s
    public static final String MUZICAR_ALBUM_FK = "album_fk";
    public static final String MUZICAR_PJESME_FK = "pjesme_fk";

    //albumi tabela
    public static final String ALBUM_IME = "ime_albuma";
    public static final String ALBUM_URL = "link_albuma";
    public static final String ALBUM_SPOTIFYID = "spotify_id_album";
    public static final String MUZICAR_FK_ALBUMI = "muzicar_fk_albumi";

    // pjesme tabela
    public static final String PJESMA_IME = "ime_pjesme";
    public static final String MUZICAR_FK_PJESME = "muzicar_fk_pjesme";

    // pretrage tabela
    public static final String PRETRAGA_QUERY = "query";
    public static final String PRETRAGA_VRIJEME = "time";
    public static final String PRETRAGA_STATUS = "status";

    // SQL upit za kreiranje baze, ne koristi se vise,
    private static final String DATABASE_CREATE = "create table " +
            DATABASE_TABLE + " (" + MUZICAR_ID + " integer primary key autoincrement, " +
            MUZICAR_IME + " text not null, " +
            MUZICAR_WEB + "text not null," +
            MUZICAR_BIOGRAFIJA + "text not null," +
            MUZICAR_SLIKA + "text not null," +
            MUZICAR_ZANR + " text not null);";

    //kreiranje tabele muzicari
    private static final String MUZICARI_CREATE = "create table " +
            MUZICARI_TABLE + " (" + KEY_ID +
            " integer primary key autoincrement, " +
            MUZICAR_IME + " text not null, " +
            MUZICAR_ZANR + " text not null, " +
            MUZICAR_WEB + " text not null, " +
            MUZICAR_BIOGRAFIJA + " text not null, " +
            MUZICAR_SLIKA + " text not null, " +
            MUZICAR_ALBUM_FK + " integer not null, " +
            MUZICAR_PJESME_FK + " integer not null); ";

    //kreiranje tabele albumi
    private static final String ALBUMI_CREATE = "create table " +
            ALBUMI_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
            ALBUM_IME + " text not null, " +
            ALBUM_URL + " text not null, " +
            ALBUM_SPOTIFYID + " text not null, " +
            MUZICAR_FK_ALBUMI + " integer not null); ";

    //kreiranje tabele pjesme
    private static final String PJESME_CREATE = "create table " +
            PJESME_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
            PJESMA_IME + " text not null, " +
            MUZICAR_FK_PJESME + " integer not null); ";

    //kreiranje tabele pretrage
    private static final String PRETRAGE_CREATE = "create table " +
            PRETRAGE_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
            PRETRAGA_QUERY + " text not null, " +
            PRETRAGA_VRIJEME + " text not null, " +
            PRETRAGA_STATUS + " text not null); ";


    public MuzicarDBOpenHelper(Context context)//, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        helpContext = context;
    }

    //Poziva se kada ne postoji baza
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(MUZICARI_CREATE);
        db.execSQL(PRETRAGE_CREATE);
        db.execSQL(PJESME_CREATE);
        db.execSQL(ALBUMI_CREATE);
    }

    // Poziva se kada se ne poklapaju verzije baze na disku i trenutne baze
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // brisanje stare verzije
        db.execSQL("DROP TABLE IF EXISTS " + MUZICARI_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ALBUMI_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PJESME_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRETRAGE_TABLE);

        // kreiranje nove
        onCreate(db);
    }

    public void insert(MuzicarDBOpenHelper muzicarDBOpenHelper, Muzicar muzicar, Pretraga p) {

        SQLiteDatabase sdb = muzicarDBOpenHelper.getWritableDatabase();
        dbSve = muzicarDBOpenHelper;

        if (muzicar != null) {
            ContentValues newContentValues = new ContentValues();
            newContentValues.put(MUZICAR_IME, muzicar.getIme());
            newContentValues.put(MUZICAR_ZANR, muzicar.getZanr());
            newContentValues.put(MUZICAR_WEB, muzicar.getWebStranica());
            newContentValues.put(MUZICAR_BIOGRAFIJA, muzicar.getBiografija());
            newContentValues.put(MUZICAR_SLIKA, muzicar.getSlika_url());

            Random rand = new Random();
            int kljuc = rand.nextInt(1000);
            for (int i = 0; i < sviKljucevi.length; ++i) {
                if (kljuc == sviKljucevi[i]) {
                    kljuc = rand.nextInt(1000);
                    i = -1;
                }
            }

            newContentValues.put(MUZICAR_ALBUM_FK, kljuc);
            newContentValues.put(MUZICAR_PJESME_FK, kljuc);
            pomocniKljuc = kljuc;

            // inser pjesme
            ArrayList<String> pjesme = (ArrayList<String>) muzicar.getTop5();
            for (String pjesma : pjesme) {

                ContentValues pjesmeContent = new ContentValues();
                pjesmeContent.put(PJESMA_IME, pjesma);
                pjesmeContent.put(MUZICAR_FK_PJESME, pomocniKljuc);

                sdb.insert(PJESME_TABLE, null, pjesmeContent);
            }

            // albumi insetr
            ArrayList<Album> albums = (ArrayList<Album>) muzicar.getAlbumi();
            ContentValues newContentValuesAlbums = new ContentValues();
            for (Album album : albums) {

                newContentValuesAlbums.put(ALBUM_IME, album.getIme());
                newContentValuesAlbums.put(ALBUM_URL, album.getSlika_url());
                newContentValuesAlbums.put(ALBUM_SPOTIFYID, album.getId());
                newContentValuesAlbums.put(MUZICAR_FK_ALBUMI, pomocniKljuc);

                sdb.insert(ALBUMI_TABLE, null, newContentValuesAlbums);
            }

            sdb.insert(MUZICARI_TABLE, null, newContentValues);
        }
        else if (p != null) {
            dodajNovog(muzicarDBOpenHelper, p);
        }
    }

    public void dodajNovog(MuzicarDBOpenHelper muzicarDBOpenHelper, Pretraga pretraga) {

        SQLiteDatabase sdb = muzicarDBOpenHelper.getWritableDatabase();
        ContentValues novi = new ContentValues();
        novi.put(PRETRAGA_QUERY, pretraga.getPretraga());
        novi.put(PRETRAGA_VRIJEME, pretraga.getVrijeme());
        novi.put(PRETRAGA_STATUS, pretraga.getStatus());

        sdb.insert(PRETRAGE_TABLE, null, novi);

    }

    public Cursor getPretrage(MuzicarDBOpenHelper muzicarDBOpenHelper) {

        SQLiteDatabase sdb = muzicarDBOpenHelper.getReadableDatabase();
        String[] columns = {PRETRAGA_QUERY, PRETRAGA_VRIJEME, PRETRAGA_STATUS};
        Cursor cursor = sdb.query(PRETRAGE_TABLE, columns, null, null, null, null, null);

        return cursor;
    }

    public Cursor dajPodatke(MuzicarDBOpenHelper muzicarDBOpenHelper) {

        SQLiteDatabase sdb = muzicarDBOpenHelper.getReadableDatabase();
        String[] columns = {MUZICAR_IME, MUZICAR_ZANR, MUZICAR_WEB, MUZICAR_BIOGRAFIJA, MUZICAR_SLIKA, MUZICAR_ALBUM_FK, MUZICAR_PJESME_FK};

        String orderBy = KEY_ID + " DESC";
        Cursor cursor = sdb.query(MUZICARI_TABLE, columns, null, null, null, null, orderBy);

        return cursor;
    }
}
