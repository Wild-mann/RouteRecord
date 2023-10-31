package cz.wild.routerecord.listeners;

import java.util.List;

import cz.wild.routerecord.objects.Route;

/**
 * Posluchač. Informuje o dokončení načítání dat.
 */
public interface OnDataLoadedListener {
    void onDataLoaded(List<Route> items);
}
