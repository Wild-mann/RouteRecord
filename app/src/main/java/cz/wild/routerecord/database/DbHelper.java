package cz.wild.routerecord.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;

/**
 * Definuje struktiru databáze včetně tabulek
 */
public class DbHelper extends SQLiteOpenHelper {
    public Context context;                                                 // context
    public static final String TABLE_ROUTE = "table_route";                 // náázev tabulky

    // názvy slopců tabulky
    public static final String COLUMN_ID = "id";                            // id
    public static final String COLUMN_DATE_TIME_BEGIN = "date_time_begin";  // datum a čas
    public static final String COLUMN_LENGHT = "lenght";                    // délka trasy
    public static final String COLUMN_DATE_TIME_END = "date_time_end";      // celkový čas
    public static final String COLUMN_AVERAGE_SPEED = "average_speed";      // průměrná rychlost
    public static final String COLUMN_MAX_SPEED = "max_speed";              // maximální rychlostt

    private static final String DATABASE_NAME = "database_route_record.db"; // název databáze

    private static final int DATABASE_VERSION = 2;                          // číselná hodnota verze databáze

    // textový řetězec s SQL příkazem pro vytvoření konkrétní tabulky
    private static final String TABLE_ROUTE_CREATE = "CREATE TABLE "
            + TABLE_ROUTE
            + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE_TIME_BEGIN + " BIGINT, "
            + COLUMN_LENGHT + " DOUBLE, "
            + COLUMN_DATE_TIME_END + " BIGINT, "
            + COLUMN_AVERAGE_SPEED + " DOUBLE, "
            + COLUMN_MAX_SPEED + " DOUBLE "
            + ");";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Metoda je volána při vytvoření databáze a dochází zde i k vytvoření všech databázových tabulek.
     * @param database databáze
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_ROUTE_CREATE);
    }

    /** Metoda je volána při potřebě upgradovat databázi po změně její struktury
     *  a je také automaticky volána při přechodu databáze na vyšší verzi.
     *  U změny struktury databáze musíme zvýšit hodnotu naší konstanty DATABASE_VERSION.
     *  Po každé změně verze databáze budou ztracena veškerá data, která dosud byla v databázi uložena!!!
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTE);
        onCreate(db);
    }

    /**
     * Metoda vrací true pokud databáze existuje
     * @param context context
     * @param dbName název databáze
     * @return vrací, zda databáze existtuje
     */
    public static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
