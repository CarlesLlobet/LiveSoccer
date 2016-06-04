package xyz.carlesllobet.livesoccer.Domain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.Objects.Equip;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.R;


public class GolsAdapter extends RecyclerView.Adapter<GolsAdapter.AdapterViewHolder> {
    ArrayList<Jugador> gols;
    UserFunctions userFunctions = new UserFunctions();
    private Context context;

    public GolsAdapter(Context con,String partit){
        context = con;
        gols = userFunctions.getGols(con,partit);
    }


    @Override
    public GolsAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.players_row, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GolsAdapter.AdapterViewHolder adapterViewholder, int position) {
        Equip equipDelJugador = userFunctions.getEquip(context,gols.get(position).getEquip());
        adapterViewholder.name.setText(gols.get(position).getName());
        adapterViewholder.escut.setImageURI(equipDelJugador.getEscut());
        adapterViewholder.dorsal.setText(gols.get(position).getDorsal().toString());
    }

    @Override
    public int getItemCount() {
        //Debemos retornar el tamaño de todos los elementos contenidos en el viewholder
        //Por defecto es return 0 --> No se mostrará nada.
        return gols.size();
    }

    public ArrayList<Jugador> getItems(){
        return gols;
    }



    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public ImageView escut;
        public TextView name;
        public TextView dorsal;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.escut = (ImageView) itemView.findViewById(R.id.foto);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.dorsal = (TextView) itemView.findViewById(R.id.dorsal);
        }
    }
}
