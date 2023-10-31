package cz.wild.routerecord;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cz.wild.routerecord.database.DataSource;
import cz.wild.routerecord.listeners.OnDataLoadedListener;
import cz.wild.routerecord.objects.Route;

public class RouteListFragment extends Fragment {
    RecyclerView recyclerView;

    MainActivity mainActivity;
    DataSource dataSource;
    AdapterRoutes adapter;
    ArrayList<Route> items = new ArrayList<>();

    public RouteListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_route_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity)context;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    /**
     * Na instanci třídy Data source zavoláme metodu getRoutes, která má v parametru instanci onDattaLoadedListener.
     * OnDataLoadedListener obsahuje metodu onDataLoaded(), kterou musíme implementovat.
     * Metoda getRoutes() načte data z databáze a vloží je do parametru metody onDataLoaded().
     * A tyto data si z toho parametru uložíme do promněnné items.
     */
    public void updateData() {
        this.mainActivity.getDataSource().getRoutes(0, 10000, new OnDataLoadedListener() {
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
    }

    /**
     * Metoda pro inicializaci seznamu uložených poznámek z naší datové sady items v naší komponentě RecyclerView
     */
    public void initRoutesList() {
        if (recyclerView == null) return;
        adapter = new AdapterRoutes(mainActivity, items);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity)); // Nastavuje vertikální srolování komponentě RecyclerView
    }
}