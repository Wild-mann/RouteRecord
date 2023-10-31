package cz.wild.routerecord.listeners;

/**
 * Informuje o odstranění položky(trasy)
 */
public interface OnRouteDeletedListener {
    void onRouteDeleted(int rowsDeleted);
}
