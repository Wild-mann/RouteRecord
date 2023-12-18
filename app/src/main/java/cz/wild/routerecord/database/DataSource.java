package cz.wild.routerecord.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import cz.wild.routerecord.listeners.OnDataLoadedListener;
import cz.wild.routerecord.listeners.OnRouteAddedListener;
import cz.wild.routerecord.listeners.OnRouteDeletedListener;
import cz.wild.routerecord.objects.Route;

/**
 * Třída s metodami pro přístup k datům databáze
 */
public class DataSource {
    SQLiteDatabase database;	    // reference na databázi
    Context context;		        // reference na context
    DbHelper dbHelper;		        // instance třídy DbHelper

    public DataSource(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    /**
     * Touto metodou získáme přístup k databázi.
     * Metoda getWritableDatabase() otevře databázi ke čtení a zápisu dat.
     * Pokud dosud naše databáze nebyla vytvořena, metoda getWritableDatabase() jí nejprve vytvoří.
     * @throws SQLException chyba přístupu k databázi
     */
    public void open() throws SQLException {
        if (database != null) {
            return;
        }

        try {
            database = dbHelper.getWritableDatabase();
        } catch (NullPointerException e) {
            e.printStackTrace();
            dbHelper = new DbHelper(context);
            database = dbHelper.getWritableDatabase();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tuto metodu voláme po ukončení práce s databází
     */
    public void closeDatabase() {
        if (dbHelper != null) dbHelper.close();
        dbHelper = null;
    }

    /**
     * Přidá trasu do databáze
     * @param route trasa
     * @param listener Reference posluchače, kterého upozorníme po přidání poznámky a předáme mu ID přidané poznámky.
     */
    public void addRoute(final Route route, final OnRouteAddedListener listener) {
        if (route == null) {
            if (listener != null) listener.onRouteAdded(-1);
            return;
        }

        open();

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_DATE_TIME_BEGIN, route.getDateTimeBegin());
        values.put(DbHelper.COLUMN_LENGHT, route.getLenght());
        values.put(DbHelper.COLUMN_DATE_TIME_END, route.getDateTimeEnd());
        values.put(DbHelper.COLUMN_AVERAGE_SPEED, route.getAverageSpeed());
        values.put(DbHelper.COLUMN_MAX_SPEED, route.getMaxSpeed());

        long insertId = database.insert(DbHelper.TABLE_ROUTE, null, values);

        if (listener != null) listener.onRouteAdded(insertId);
    }

    /**
     * Převede data jednoho řádku tabulky na objekt typu Route
     * @param cursor objekt, který obsahuje data vyčtená z databáze v podobě řádků.
     * @return Route
     */
    private Route cursorToNote(Cursor cursor) {
        return new Route(
                cursor.getInt(0),
                cursor.getLong(1),
                cursor.getDouble(2),
                cursor.getLong(3),
                cursor.getDouble(4),
                cursor.getDouble(5));
    }


    /**
     * Metoda načte data tras z databáze. Data uloží jako objekty Route do ArrayListu roReturn. Tento Arraylist předá v parametru posluchači.
     * @param offset Počet řádků tabulky, které budou při čtení peskočeny.
     * @param limit Maximální počet řádků, které budou načteny z databáze.
     * @param listener Reference na posluchače, kterého upozorníme na dokončení čtení a kterému načtená data předáme.
     */
    public void getRoutes(int offset, int limit, OnDataLoadedListener listener) {
        open();
        ArrayList<Route> toReturn = new ArrayList<>();
        Route route;
        Cursor cursor;

        String tempOffsetString = null;
        if (offset >= 0) tempOffsetString = "" + offset + ", " + limit;
        cursor = database.query(
                DbHelper.TABLE_ROUTE,
                null,
                null,
                null,
                null,
                null,
                DbHelper.COLUMN_DATE_TIME_BEGIN + " DESC",
                tempOffsetString);

        cursor.moveToFirst();

        for(int i = 0, count = cursor.getCount(); i < count; i ++) {
            route = cursorToNote(cursor);
            toReturn.add(route);

            if(cursor.isLast()) break;
            cursor.moveToNext();
        }

        cursor.close();
        if (listener != null) listener.onDataLoaded(toReturn);
    }

    /**
     * Odstraní trasu z daným ID z databáze.
     * @param routeId id trasy
     * @param listener Reference na posluchač, kterého upozorníme po odstranění trasy a předáme mu počet odstraněných řádků.
     */
    public void removeRoute(int routeId, OnRouteDeletedListener listener) {
        open();
        int rowsRemoved = database.delete(DbHelper.TABLE_ROUTE, DbHelper.COLUMN_ID + " = ?", new String[] {String.valueOf(routeId)});
        if (listener != null) listener.onRouteDeleted(rowsRemoved);
    }
}