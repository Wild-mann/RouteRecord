package cz.wild.routerecord;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.wild.routerecord.database.DataSource;
import cz.wild.routerecord.listeners.OnRouteAddedListener;
import cz.wild.routerecord.listeners.OnRouteUpdateListener;
import cz.wild.routerecord.objects.Route;

import cz.wild.routerecord.listeners.OnDataLoadedListener;

public class MainActivity extends AppCompatActivity {
    TextView testTextView;
    RecyclerView recyclerView;
    Button toRecFragmentButton;                 // Tlačítko pro spuštění záznamu trasy
    Button toListfragmentButton;                // Tlačítko pro přepnutí na fragment se seznamem tras
    AdapterRoutes adapter;
    DataSource dataSource;                      // Slouží pro práci z databází
    int editedRouteId;
    FragmentManager fragmentManager = getSupportFragmentManager(); // Fragment manažer
    boolean doubleBackToExitPressed = false;    // Příznak kliknutí(gesta) pro ukončení aplikace

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toRecFragmentButton = findViewById(R.id.toRecFragmentButton);
        toListfragmentButton = findViewById(R.id.toListFragmentButton);
        //recyclerView = findViewById(R.id.recyclerView);
        //testTextView = findViewById(R.id.testTextView);

        toRecFragmentButton.setOnClickListener(view -> {
            showRecFragment();
        });

        toListfragmentButton.setOnClickListener(view -> {
            showRouteListFragment();
        });

        showRecFragment();
/**-----------------------------------Testovací data------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        getDataSource();
        Route[] routes = new Route[10];
        routes[0] = new Route(-1, "1.července 2021", 42.43, 20638, 18.2, 52.1);
        routes[1] = new Route(-1, "2.července 2021", 42.43, 20638, 18.2, 52.1);
        routes[2] = new Route(-1, "3.července 2021", 42.43, 20638, 18.2, 52.1);
        routes[3] = new Route(-1, "4.července 2021", 42.43, 20638, 18.2, 52.1);
        routes[4] = new Route(-1, "5.července 2021", 42.43, 20638, 18.2, 52.1);
        routes[5] = new Route(-1, "6.července 2021", 42.43, 20638, 18.2, 52.1);
        routes[6] = new Route(-1, "7.července 2020", 42.43, 20638, 18.2, 52.1);
        routes[7] = new Route(-1, "8.července 2020", 42.43, 20638, 18.2, 52.1);
        routes[8] = new Route(-1, "9.července 2020", 42.43, 20638, 18.2, 52.1);
        routes[9] = new Route(-1, "10.července 2020", 42.43, 20638, 18.2, 52.1);
        //testTextView.setText(route.getDateTime() + " , " + route.getLenght() + " , " + route.getDuration() + " , " + route.getAverageSpeed() + " , " + route.getMaxSpeed());
        //updateData();
        //Route newRoute = items.get(0);
        //testTextView.setText("ID: " + newRoute.getId() + "DATE_TIME " + newRoute.getDateTime() + " , " + newRoute.getLenght() + " , " + newRoute.getDuration() + " , " + newRoute.getAverageSpeed() + " , " + newRoute.getMaxSpeed());
        for(int i=0; i<10; i++) {
            dataSource.addRoute(routes[i], new OnRouteAddedListener() {
                @Override
                public void onRouteAdded(long newId) {
                    //updateData();
                }
            });
        }   */
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    }

    @Override
    protected void onResume() {
        super.onResume();
        //updateData();
    }

    /**
     * Tuto metodu budeme volat vždy, když budeme chtít pracovat z databází
     * @return DataSource
     */
    public DataSource getDataSource() {
        if (dataSource == null) dataSource = new DataSource(this);
        return dataSource;
    }

    /**
     * Metoda pro inicializaci seznamu uložených poznámek z naší datové sady items v naší komponentě RecyclerView
     */
    /**public void initRoutesList() {
        if (recyclerView == null) return;
        adapter = new AdapterRoutes(MainActivity.this, items);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this)); // Nastavuje vertikální srolování komponentě RecyclerView
    }*/

    /**
     * Na instanci třídy Data source zavoláme metodu getRoutes, která má v parametru instanci onDattaLoadedListener.
     * OnDataLoadedListener obsahuje metodu onDataLoaded(), kterou musíme implementovat.
     * Metoda getRoutes() načte data z databáze a vloží je do parametru metody onDataLoaded().
     * A tyto data si z toho parametru uložíme do promněnné items.
     */
    /**public void updateData() {
        getDataSource().getRoutes(0, 0, new OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<Route> loadedItems) {
                items = new ArrayList<>(loadedItems);

                if (adapter != null) {
                    adapter.setItems(new ArrayList<>(items));
                    adapter.notifyDataSetChanged();
                } else {
                    initRoutesList();
                }
            }
        });
    }*/

    /**
     * Zobrazí detail trasy
     * @param route
     */
    public void showRouteDetail(Route route) {
        toListfragmentButton.setVisibility(View.GONE);
        toRecFragmentButton.setVisibility(View.GONE);
        RouteDetailFragment routeDetailFragment = (RouteDetailFragment)fragmentManager.findFragmentByTag(RouteDetailFragment.class.getName());

        if(routeDetailFragment == null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            routeDetailFragment = new RouteDetailFragment();
            fragmentTransaction.add(R.id.containerForFragment, routeDetailFragment, RouteDetailFragment.class.getName());
            fragmentTransaction.addToBackStack(routeDetailFragment.getTag());
            fragmentTransaction.commit();
        }
    }


    private void showRecFragment() {
        RecordFragment recordFragment = (RecordFragment)fragmentManager.findFragmentByTag(RecordFragment.class.getName());

        if(recordFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            recordFragment = new RecordFragment();
            fragmentTransaction.add(R.id.containerForFragment, recordFragment, RecordFragment.class.getName());
            fragmentTransaction.addToBackStack(recordFragment.getTag());
            fragmentTransaction.commit();
        }
        else{
            fragmentManager.popBackStack(recordFragment.getTag(),0);
        }

        toRecFragmentButton.setEnabled(false);
        toListfragmentButton.setEnabled(true);
    }

    private void showRouteListFragment(){
        RouteListFragment routeListFragment = (RouteListFragment)fragmentManager.findFragmentByTag(RouteListFragment.class.getName());

        if(routeListFragment == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            routeListFragment = new RouteListFragment();
            fragmentTransaction.add(R.id.containerForFragment, routeListFragment);
            fragmentTransaction.addToBackStack(routeListFragment.getTag());
            fragmentTransaction.commit();
        }
        else{
            fragmentManager.popBackStack(routeListFragment.getTag(),0);
        }

        toListfragmentButton.setEnabled(false);
        toRecFragmentButton.setEnabled(true);
    }

    /**
     * Zmáčknutí tlačítka zpět nebo použití gesta zpět
     */
    @Override
    public void onBackPressed() {
        toRecFragmentButton.setVisibility(View.VISIBLE);
        toListfragmentButton.setVisibility(View.VISIBLE);
        // pokud je v zásobníku právě jeden fragment
        if(fragmentManager.getBackStackEntryCount() == 1){
            // pokud klikáme podruhé provede ukončí se aplikace
            if(doubleBackToExitPressed) {
                finish();
            }
            // pokud klikáme poprvé nebo po dvou vteřinách, zobrazí se hlášení "Opakováním ukončíte"
            this.doubleBackToExitPressed = true;
            Toast.makeText(this, "Opakováním ukončíte.", Toast.LENGTH_SHORT).show();

            // odpočítávání času, kdy může uživatel stisknout tačítko podruhé
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
                @Override
                public void run() {
                    doubleBackToExitPressed = false;
                }
            }, 2000);
        }
        // pokud je v zásobníku více než jeden fragment
        else{
            super.onBackPressed();
        }
    }
}