package xyz.carlesllobet.livesoccer.UI;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyz.carlesllobet.livesoccer.Domain.RecyclerItemClickListener;
import xyz.carlesllobet.livesoccer.R;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerList;
    private LinearLayoutManager linearLayout;

    private View rootView;

    private int tab;
    private Context context;

    private FloatingActionButton afegir;

    private LinearLayout info;
    private TextView punt;
    private TextView gols;
    private TextView pg;
    private TextView pp;
    private TextView pe;

    public HomeFragment() {

    }

    public HomeFragment(int t, Context c) {
        this.tab = t;
        this.context = c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerList = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        linearLayout = new LinearLayoutManager(getContext());
        recyclerList.setLayoutManager(linearLayout);
        recyclerList.setAdapter(new HomeAdapter(getContext(), tab));

        afegir = (FloatingActionButton) rootView.findViewById(R.id.fab);
        info = (LinearLayout) rootView.findViewById(R.id.info);
        punt = (TextView) rootView.findViewById(R.id.punt);
        gols = (TextView) rootView.findViewById(R.id.gols);
        pg = (TextView) rootView.findViewById(R.id.pg);
        pp = (TextView) rootView.findViewById(R.id.pp);
        pe = (TextView) rootView.findViewById(R.id.pe);
        switch (tab) {
            case 1:
                info.setVisibility(View.GONE);
                afegir.setImageURI(Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.drawable.ic_plus_white_18dp));
                break;
            case 2:
                info.setVisibility(View.VISIBLE);
                punt.setVisibility(View.VISIBLE);
                gols.setVisibility(View.VISIBLE);
                pg.setVisibility(View.VISIBLE);
                pe.setVisibility(View.VISIBLE);
                pp.setText("PE");
                pp.setTypeface(null, Typeface.NORMAL);
                pp.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
                afegir.setImageURI(Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.drawable.ic_account_multiple_plus_white_18dp));
                break;
            case 3:
                info.setVisibility(View.VISIBLE);
                punt.setVisibility(View.INVISIBLE);
                gols.setVisibility(View.INVISIBLE);
                pg.setVisibility(View.INVISIBLE);
                pe.setVisibility(View.INVISIBLE);
                pp.setTextColor(context.getResources().getColor(R.color.colorAccent));
                pp.setText("G");
                pp.setTypeface(null, Typeface.BOLD);
                afegir.setImageURI(Uri.parse("android.resource://xyz.carlesllobet.livesoccer/" + R.drawable.ic_account_plus_white_18dp));
                break;
        }

        afegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tab) {
                    case 1:
                        startActivity(new Intent(context, NewJornadaActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, NewEquipActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, NewJugadorActivity.class));
                        break;
                }
            }
        });

        recyclerList.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (tab) {
                            case 1:
                                //Activity de veure dades partit
                                Intent i = new Intent(context, JornadaActivity.class);
                                i.putExtra("jornada", position);
                                startActivity(i);
                                break;
                            case 2:
                                //Activity de veure dades equip
                                Intent i2 = new Intent(context, EquipActivity.class);
                                i2.putExtra("equip", position);
                                startActivity(i2);
                                break;
                            case 3:
                                //Activity de veure dades jugador
                                Intent i3 = new Intent(context, JugadorActivity.class);
                                i3.putExtra("jugador", position);
                                startActivity(i3);
                                break;
                        }
                    }
                })
        );

        return rootView;
    }
}
