package xyz.carlesllobet.livesoccer.UI;

/**
 * Created by CarlesLlobet on 01/02/2016.
 */

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
import xyz.carlesllobet.livesoccer.Domain.Objects.Jornada;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.Domain.Objects.Partit;
import xyz.carlesllobet.livesoccer.R;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.AdapterViewHolder> {

    private Context context;
    UserFunctions userFunctions = new UserFunctions();
    Integer actualTab;
    ArrayList<Jornada> jornades;
    ArrayList<Equip> equips;
    ArrayList<Jugador> jugadors;

    public HomeAdapter(Context con, int tab) {
        context = con;
        actualTab = tab;
        switch (actualTab) {
            case 1:
                jornades = userFunctions.getPartits(context);
                break;
            case 2:
                equips = userFunctions.getEquips(context);
                break;
            case 3:
                jugadors = userFunctions.getJugadors(context);
                break;
        }
    }

    @Override
    public HomeAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        switch (actualTab) {
            case 1:
                view = inflater.inflate(R.layout.match_row, viewGroup, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.teams_row, viewGroup, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.players_row, viewGroup, false);
                break;
            default:
                view = inflater.inflate(R.layout.match_row, viewGroup, false);
                break;
        }
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.AdapterViewHolder adapterViewholder, int position) {
        switch (actualTab) {
            case 1:
                adapterViewholder.jornada.setText(context.getString(R.string.jornada)+ " " + String.valueOf(position+1));
                
                //Partit 1
                adapterViewholder.name.setText(jornades.get(position).getPrimer().getLocal().getName());
                adapterViewholder.punt.setText(jornades.get(position).getPrimer().getPuntLocal().toString());
                adapterViewholder.foto.setImageURI(jornades.get(position).getPrimer().getLocal().getEscut());
                adapterViewholder.name2.setText(jornades.get(position).getPrimer().getVisitant().getName());
                adapterViewholder.punt2.setText(jornades.get(position).getPrimer().getPuntVisitant().toString());
                adapterViewholder.foto2.setImageURI(jornades.get(position).getPrimer().getVisitant().getEscut());

                //Partit 2
                adapterViewholder.name3.setText(jornades.get(position).getSegon().getLocal().getName());
                adapterViewholder.punt3.setText(jornades.get(position).getSegon().getPuntLocal().toString());
                adapterViewholder.foto3.setImageURI(jornades.get(position).getSegon().getLocal().getEscut());
                adapterViewholder.name4.setText(jornades.get(position).getSegon().getVisitant().getName());
                adapterViewholder.punt4.setText(jornades.get(position).getSegon().getPuntVisitant().toString());
                adapterViewholder.foto4.setImageURI(jornades.get(position).getSegon().getVisitant().getEscut());

                //Partit 3
                adapterViewholder.name5.setText(jornades.get(position).getTercer().getLocal().getName());
                adapterViewholder.punt5.setText(jornades.get(position).getTercer().getPuntLocal().toString());
                adapterViewholder.foto5.setImageURI(jornades.get(position).getTercer().getLocal().getEscut());
                adapterViewholder.name6.setText(jornades.get(position).getTercer().getVisitant().getName());
                adapterViewholder.punt6.setText(jornades.get(position).getTercer().getPuntVisitant().toString());
                adapterViewholder.foto6.setImageURI(jornades.get(position).getTercer().getVisitant().getEscut());

                //Partit 4
                adapterViewholder.name7.setText(jornades.get(position).getQuart().getLocal().getName());
                adapterViewholder.punt7.setText(jornades.get(position).getQuart().getPuntLocal().toString());
                adapterViewholder.foto7.setImageURI(jornades.get(position).getQuart().getLocal().getEscut());
                adapterViewholder.name8.setText(jornades.get(position).getQuart().getVisitant().getName());
                adapterViewholder.punt8.setText(jornades.get(position).getQuart().getPuntVisitant().toString());
                adapterViewholder.foto8.setImageURI(jornades.get(position).getQuart().getVisitant().getEscut());

                //Partit 5
                adapterViewholder.name9.setText(jornades.get(position).getCinque().getLocal().getName());
                adapterViewholder.punt9.setText(jornades.get(position).getCinque().getPuntLocal().toString());
                adapterViewholder.foto9.setImageURI(jornades.get(position).getCinque().getLocal().getEscut());
                adapterViewholder.name10.setText(jornades.get(position).getCinque().getVisitant().getName());
                adapterViewholder.punt10.setText(jornades.get(position).getCinque().getPuntVisitant().toString());
                adapterViewholder.foto10.setImageURI(jornades.get(position).getCinque().getVisitant().getEscut());
                break;
            case 2:
                adapterViewholder.foto.setImageURI(equips.get(position).getEscut());
                adapterViewholder.name.setText(equips.get(position).getName());
                adapterViewholder.punt.setText(equips.get(position).getPunt().toString());
                adapterViewholder.gols.setText(equips.get(position).getGols().toString());
                adapterViewholder.pg.setText(equips.get(position).getGuanyats().toString());
                adapterViewholder.pp.setText(equips.get(position).getPerduts().toString());
                adapterViewholder.pe.setText(equips.get(position).getEmpatats().toString());
                break;
            case 3:
                Equip equipDelJugador = userFunctions.getEquip(context,jugadors.get(position).getEquip());
                adapterViewholder.name.setText(jugadors.get(position).getName());
                adapterViewholder.foto.setImageURI(equipDelJugador.getEscut());
                adapterViewholder.dorsal.setText(jugadors.get(position).getDorsal().toString());
                adapterViewholder.gols.setText(jugadors.get(position).getGols().toString());
                break;
            default:
                adapterViewholder.jornada.setText(context.getString(R.string.jornada)+ " " + String.valueOf(position+1));

                //Partit 1
                adapterViewholder.name.setText(jornades.get(position).getPrimer().getLocal().getName());
                adapterViewholder.punt.setText(jornades.get(position).getPrimer().getPuntLocal().toString());
                adapterViewholder.foto.setImageURI(jornades.get(position).getPrimer().getLocal().getEscut());
                adapterViewholder.name2.setText(jornades.get(position).getPrimer().getVisitant().getName());
                adapterViewholder.punt2.setText(jornades.get(position).getPrimer().getPuntVisitant().toString());
                adapterViewholder.foto2.setImageURI(jornades.get(position).getPrimer().getVisitant().getEscut());

                //Partit 2
                adapterViewholder.name3.setText(jornades.get(position).getSegon().getLocal().getName());
                adapterViewholder.punt3.setText(jornades.get(position).getSegon().getPuntLocal().toString());
                adapterViewholder.foto3.setImageURI(jornades.get(position).getSegon().getLocal().getEscut());
                adapterViewholder.name4.setText(jornades.get(position).getSegon().getVisitant().getName());
                adapterViewholder.punt4.setText(jornades.get(position).getSegon().getPuntVisitant().toString());
                adapterViewholder.foto4.setImageURI(jornades.get(position).getSegon().getVisitant().getEscut());

                //Partit 3
                adapterViewholder.name5.setText(jornades.get(position).getTercer().getLocal().getName());
                adapterViewholder.punt5.setText(jornades.get(position).getTercer().getPuntLocal().toString());
                adapterViewholder.foto5.setImageURI(jornades.get(position).getTercer().getLocal().getEscut());
                adapterViewholder.name6.setText(jornades.get(position).getTercer().getVisitant().getName());
                adapterViewholder.punt6.setText(jornades.get(position).getTercer().getPuntVisitant().toString());
                adapterViewholder.foto6.setImageURI(jornades.get(position).getTercer().getVisitant().getEscut());

                //Partit 4
                adapterViewholder.name7.setText(jornades.get(position).getQuart().getLocal().getName());
                adapterViewholder.punt7.setText(jornades.get(position).getQuart().getPuntLocal().toString());
                adapterViewholder.foto7.setImageURI(jornades.get(position).getQuart().getLocal().getEscut());
                adapterViewholder.name8.setText(jornades.get(position).getQuart().getVisitant().getName());
                adapterViewholder.punt8.setText(jornades.get(position).getQuart().getPuntVisitant().toString());
                adapterViewholder.foto8.setImageURI(jornades.get(position).getQuart().getVisitant().getEscut());

                //Partit 5
                adapterViewholder.name9.setText(jornades.get(position).getCinque().getLocal().getName());
                adapterViewholder.punt9.setText(jornades.get(position).getCinque().getPuntLocal().toString());
                adapterViewholder.foto9.setImageURI(jornades.get(position).getCinque().getLocal().getEscut());
                adapterViewholder.name10.setText(jornades.get(position).getCinque().getVisitant().getName());
                adapterViewholder.punt10.setText(jornades.get(position).getCinque().getPuntVisitant().toString());
                adapterViewholder.foto10.setImageURI(jornades.get(position).getCinque().getVisitant().getEscut());
                break;
        }
    }

        @Override
        public int getItemCount () {
            switch (actualTab){
                case 1:
                    return jornades.size();
                case 2:
                    return equips.size();
                case 3:
                    return jugadors.size();
                default:
                    return 0;
            }
        }

        public class AdapterViewHolder extends RecyclerView.ViewHolder {

            public View v;

            //Partits
            public TextView jornada;
            public ImageView foto,foto2,foto3,foto4,foto5,foto6,foto7,foto8,foto9,foto10;
            public TextView name,name2,name3,name4,name5,name6,name7,name8,name9,name10;
            public TextView punt,punt2,punt3,punt4,punt5,punt6,punt7,punt8,punt9,punt10;
            //Equips
            public TextView gols;
            public TextView pg;
            public TextView pp;
            public TextView pe;
            //Jugadors
            public TextView dorsal;



            public AdapterViewHolder(View itemView) {
                super(itemView);
                this.v = itemView;
                switch (actualTab){
                    case 1:
                        this.jornada = (TextView) itemView.findViewById(R.id.jornada);
                        
                        //Partit 1
                        this.foto = (ImageView) itemView.findViewById(R.id.foto);
                        this.name = (TextView) itemView.findViewById(R.id.name);
                        this.punt = (TextView) itemView.findViewById(R.id.punt);
                        this.foto2 = (ImageView) itemView.findViewById(R.id.foto2);
                        this.name2 = (TextView) itemView.findViewById(R.id.name2);
                        this.punt2 = (TextView) itemView.findViewById(R.id.punt2);

                        //Partit 2
                        this.foto3 = (ImageView) itemView.findViewById(R.id.foto3);
                        this.name3 = (TextView) itemView.findViewById(R.id.name3);
                        this.punt3 = (TextView) itemView.findViewById(R.id.punt3);
                        this.foto4 = (ImageView) itemView.findViewById(R.id.foto4);
                        this.name4 = (TextView) itemView.findViewById(R.id.name4);
                        this.punt4 = (TextView) itemView.findViewById(R.id.punt4);

                        //Partit 3
                        this.foto5 = (ImageView) itemView.findViewById(R.id.foto5);
                        this.name5 = (TextView) itemView.findViewById(R.id.name5);
                        this.punt5 = (TextView) itemView.findViewById(R.id.punt5);
                        this.foto6 = (ImageView) itemView.findViewById(R.id.foto6);
                        this.name6 = (TextView) itemView.findViewById(R.id.name6);
                        this.punt6 = (TextView) itemView.findViewById(R.id.punt6);

                        //Partit 4
                        this.foto7 = (ImageView) itemView.findViewById(R.id.foto7);
                        this.name7 = (TextView) itemView.findViewById(R.id.name7);
                        this.punt7 = (TextView) itemView.findViewById(R.id.punt7);
                        this.foto8 = (ImageView) itemView.findViewById(R.id.foto8);
                        this.name8 = (TextView) itemView.findViewById(R.id.name8);
                        this.punt8 = (TextView) itemView.findViewById(R.id.punt8);

                        //Partit 5
                        this.foto9 = (ImageView) itemView.findViewById(R.id.foto9);
                        this.name9 = (TextView) itemView.findViewById(R.id.name9);
                        this.punt9 = (TextView) itemView.findViewById(R.id.punt9);
                        this.foto10 = (ImageView) itemView.findViewById(R.id.foto10);
                        this.name10 = (TextView) itemView.findViewById(R.id.name10);
                        this.punt10 = (TextView) itemView.findViewById(R.id.punt10);
                        break;
                    case 2:
                        this.foto = (ImageView) itemView.findViewById(R.id.foto);
                        this.name = (TextView) itemView.findViewById(R.id.name);
                        this.punt = (TextView) itemView.findViewById(R.id.punt);
                        this.gols = (TextView) itemView.findViewById(R.id.gols);
                        this.pg = (TextView) itemView.findViewById(R.id.pg);
                        this.pp = (TextView) itemView.findViewById(R.id.pp);
                        this.pe = (TextView) itemView.findViewById(R.id.pe);
                        break;
                    case 3:
                        this.foto = (ImageView) itemView.findViewById(R.id.foto);
                        this.name = (TextView) itemView.findViewById(R.id.name);
                        this.punt = (TextView) itemView.findViewById(R.id.punt);
                        this.dorsal = (TextView) itemView.findViewById(R.id.dorsal);
                        this.gols = (TextView) itemView.findViewById(R.id.gols);
                        break;
                    default:
                        this.foto = (ImageView) itemView.findViewById(R.id.foto);
                        this.name = (TextView) itemView.findViewById(R.id.name);
                        this.punt = (TextView) itemView.findViewById(R.id.punt);
                        this.foto2 = (ImageView) itemView.findViewById(R.id.foto2);
                        this.name2 = (TextView) itemView.findViewById(R.id.name2);
                        this.punt2 = (TextView) itemView.findViewById(R.id.punt2);
                        break;
                }
            }
        }
    }