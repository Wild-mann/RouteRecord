package cz.wild.routerecord.listeners;

/**
 * Posluchač. Informuje o dokončení zápisu v tom místě Java kódu, ze kterého byl zápis dat požadován.
 */
public interface OnRouteAddedListener {
    void onRouteAdded(long newId);
}
