package cz.wild.routerecord;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cz.wild.routerecord.listeners.OnRouteDeletedListener;
import cz.wild.routerecord.objects.Route;

/**
 * Třída slouží k propojení datové sady s třídou RecycleView. Zajišťuje zobrazování dat v jednotlivých
 * položkách seznamu a řídí tvorbu nebo recyklování jednotlivých jeho položek během skrolování.
 */
public class AdapterRoutes extends RecyclerView.Adapter<AdapterRoutes.MyViewHolder> {

    /**
     * Třída slouží k obalení Java reprezentací komponent XML návrhu položek seznamu.
     * Deklarujeme zde pouze ty komponenty položky, se kterými budeme v kódu adaptéru pracovat.
     */
    static class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout root;      // Hlavní layout položky
        ImageView imgActivityType;  // ImageView pro zobrazení ikony typu aktivity
        TextView labelDateTime;     // TextView pro zobrazení datumu a času
        TextView labelDuration;     // TextView pro zobrazení celkového času
        TextView labelLenght;       // TextView pro zobrazení vzdálenosti

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    MainActivity activity;      // Context hlavní aktivity
    ArrayList<Route> items;     // Datová sada - seznam tras načtených t databáze

    public AdapterRoutes(MainActivity activity, ArrayList<Route> items) {
        this.activity = activity;
        this.items = items;
    }

    /**
     * Naplní promněnné třídy MyViewHolder komponentami XML návrhu položky seznamu
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return vh instance MyViewHolder
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_route, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        vh.root = v.findViewById(R.id.root);
        vh.imgActivityType = v.findViewById(R.id.imgActivityType);
        vh.labelDateTime = v.findViewById(R.id.labelDateTime);
        vh.labelDuration = v.findViewById(R.id.labelDuration);
        vh.labelLenght = v.findViewById(R.id.labelLenght);

        return vh;
    }

    /**
     *
     * @param holder   Zpřístupní komponenty GUI zobrazeného prvku z naší datové sady items.
     * @param position Odkazuje na pozici konkrétního prvku z datové sadě items.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Route route = items.get(position);    // Získání aktuálně zpracovávaného prvku

        // Posluchač. Po krátkém kliknutí na položku se zobrazí její detail(detail trasy).
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showRouteDetail(route);
            }
        });

        // Posluchač. Po dlouhém kliknutí na položku se zobrazí defaultní dialogové okno z otázkou,
        // zda chceme záznam trasy odstranit z databáze.
        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDeleteDialog(route);
                return true;
            }
        });

        setIconActivityType(route, holder.root);                            // Nastavení ikony typu aktivity
        holder.labelDateTime.setText(route.getDateTimeBegin());                  // Nastavení obsahu textView data a času
        holder.labelDuration.setText(String.valueOf(route.getDuration()));  // Nastavení obsahu textView celkového času
        holder.labelLenght.setText(String.valueOf(route.getLenght()));      // Nastavení obsahu textView vzdálenosti
    }

    /**
     * Metoda slouží adaptéru k získání informace o počtu položek datové sady items typu ArrayList<Note>.
     * @return Vrací počet položek datové sady. Pokud instance items nebude vytvořena, vrátíme 0.
     */
    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    /**
     * Setter datové sady seznamu tras
     * @param items datová sada
     */
    public void setItems(ArrayList<Route> items) {
        this.items = new ArrayList<Route>(items);
    }

    private void deleteRoute(Route route) {
        activity.getDataSource().removeRoute(route.getId(), new OnRouteDeletedListener() {
            @Override
            public void onRouteDeleted(int rowsDeleted) {
                //activity.updateData();
            }
        });
    }

    private void showDeleteDialog(Route route) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(R.string.delete_route);
        alertDialogBuilder.setMessage(activity.getResources().getString(R.string.question_delete_route) + " " +route.getId())
                .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        deleteRoute(route);
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setIconActivityType(Route route, View layout){
    }
}
