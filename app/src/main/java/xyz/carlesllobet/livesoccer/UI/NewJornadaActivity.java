package xyz.carlesllobet.livesoccer.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.EquipSpinnerAdapter;
import xyz.carlesllobet.livesoccer.Domain.GolsAdapter;
import xyz.carlesllobet.livesoccer.Domain.Objects.Equip;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.Domain.Objects.Partit;
import xyz.carlesllobet.livesoccer.Domain.PlayerSpinnerAdapter;
import xyz.carlesllobet.livesoccer.Domain.RecyclerItemClickListener;
import xyz.carlesllobet.livesoccer.R;

public class NewJornadaActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;

    private Partit partit1;
    private Partit partit2;
    private Partit partit3;
    private Partit partit4;
    private Partit partit5;

    private TextView titol;
    private Button afegirGol;

    private GolsAdapter gols;

    private ImageView foto;
    private ImageView foto2;

    private FloatingActionButton left;
    private FloatingActionButton right;

    private Jugador selected;
    private Equip local;
    private Equip visitant;

    private Spinner spinnerGols;
    private Spinner spinnerEquip1;
    private Spinner spinnerEquip2;

    private UserFunctions uf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_jornada);

        uf = new UserFunctions();

        partit1 = new Partit();
        partit2 = new Partit();
        partit3 = new Partit();
        partit4 = new Partit();
        partit5 = new Partit();

        //uf.borraGols(getApplicationContext());

        //findViewById del layout activity_main
        mRecyclerView = (RecyclerView) findViewById(R.id.golsRecyclerView);

        mLinearLayout = new LinearLayoutManager(this);

        local = visitant = uf.getEquip(getApplicationContext(),0);

        titol = (TextView) findViewById(R.id.partit);

        foto = (ImageView) findViewById(R.id.escut);
        foto2 = (ImageView) findViewById(R.id.escut2);

        afegirGol = (Button) findViewById(R.id.afegirGol);
        left = (FloatingActionButton) findViewById(R.id.left);
        right = (FloatingActionButton) findViewById(R.id.right);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        afegirGol.setOnClickListener(this);

        left.setVisibility(View.INVISIBLE);
        left.setClickable(false);

        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);

        gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
        mRecyclerView.setAdapter(gols);


        spinnerGols = (Spinner) findViewById(R.id.spinner);
        spinnerEquip1 = (Spinner) findViewById(R.id.team1);
        spinnerEquip2 = (Spinner) findViewById(R.id.team2);
        // Spinner Drop down elements
        ArrayList<Jugador> jugadors = uf.getJugadors(getApplicationContext());
        ArrayList<Equip> equips = uf.getEquips(getApplicationContext());

        final PlayerSpinnerAdapter golsSpinnerAdapter = new PlayerSpinnerAdapter(NewJornadaActivity.this, jugadors);
        final EquipSpinnerAdapter equip1SpinnerAdapter = new EquipSpinnerAdapter(NewJornadaActivity.this, equips);
        final EquipSpinnerAdapter equip2SpinnerAdapter = new EquipSpinnerAdapter(NewJornadaActivity.this, equips);

        spinnerGols.setAdapter(golsSpinnerAdapter);
        spinnerEquip1.setAdapter(equip1SpinnerAdapter);
        spinnerEquip2.setAdapter(equip2SpinnerAdapter);

        spinnerGols.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = golsSpinnerAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEquip1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                local = equip1SpinnerAdapter.getItem(position);
                foto.setImageURI(local.getEscut());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEquip2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                visitant = equip2SpinnerAdapter.getItem(position);
                foto2.setImageURI(visitant.getEscut());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //uf.borraGol(getApplicationContext(),titol.getText().toString(),position);
                        gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                        mRecyclerView.setAdapter(gols);
                    }
                })
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                switch (titol.getText().toString()) {
                    case "Partit 2":
                        //Guardem els gols fets al partit i els equips seleccionats
                        partit2.setGolejadors(gols.getItems());
                        partit2.setLocal(local);
                        partit2.setVisitant(visitant);
                        //Canviem de pantalla
                        titol.setText("Partit 1");
                        gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                        mRecyclerView.setAdapter(gols);
                        local = partit1.getLocal();
                        visitant = partit1.getVisitant();
                        if (local!=null && visitant!=null) {
                            spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                            spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                        } else {
                            spinnerEquip1.setSelection(0);
                            spinnerEquip2.setSelection(0);
                            local = visitant = uf.getEquip(getApplicationContext(),0);
                        }
                        left.setVisibility(View.INVISIBLE);
                        left.setClickable(false);
                        break;
                    case "Partit 3":
                        //Guardem els gols fets al partit i els equips seleccionats
                        partit3.setGolejadors(gols.getItems());
                        partit3.setLocal(local);
                        partit3.setVisitant(visitant);
                        //Canviem de pantalla
                        titol.setText("Partit 2");
                        gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                        mRecyclerView.setAdapter(gols);
                        local = partit2.getLocal();
                        visitant = partit2.getVisitant();
                        if (local!=null && visitant!=null) {
                            spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                            spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                        } else {
                            spinnerEquip1.setSelection(0);
                            spinnerEquip2.setSelection(0);
                            local = visitant = uf.getEquip(getApplicationContext(),0);
                        }
                        break;
                    case "Partit 4":
                        //Guardem els gols fets al partit i els equips seleccionats
                        partit4.setGolejadors(gols.getItems());
                        partit4.setLocal(local);
                        partit4.setVisitant(visitant);
                        //Canviem de pantalla
                        titol.setText("Partit 3");
                        gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                        mRecyclerView.setAdapter(gols);
                        local = partit3.getLocal();
                        visitant = partit3.getVisitant();
                        if (local!=null && visitant!=null) {
                            spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                            spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                        } else {
                            spinnerEquip1.setSelection(0);
                            spinnerEquip2.setSelection(0);
                            local = visitant = uf.getEquip(getApplicationContext(),0);
                        }
                        break;
                    case "Partit 5":
                        //Guardem els gols fets al partit i els equips seleccionats
                        partit5.setGolejadors(gols.getItems());
                        partit5.setLocal(local);
                        partit5.setVisitant(visitant);
                        //Canviem de pantalla
                        titol.setText("Partit 4");
                        gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                        mRecyclerView.setAdapter(gols);
                        local = partit4.getLocal();
                        visitant = partit4.getVisitant();
                        if (local!=null && visitant!=null) {
                            spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                            spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                        } else {
                            spinnerEquip1.setSelection(0);
                            spinnerEquip2.setSelection(0);
                            local = visitant = uf.getEquip(getApplicationContext(),0);
                        }
                        //Set icono i background verd
                        right.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        right.setBackgroundResource(R.drawable.ic_arrow_right_bold_white_18dp);
                        //carregar gols partit 1
                        break;
                }
                break;
            case R.id.right:
                switch (titol.getText().toString()) {
                    case "Partit 1":
                        if (!local.getName().equals(visitant.getName())) {
                            //Guardem els gols fets al partit i els equips seleccionats
                            partit1.setGolejadors(gols.getItems());
                            partit1.setLocal(local);
                            partit1.setVisitant(visitant);
                            //Canviem de pantalla
                            titol.setText("Partit 2");
                            gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                            mRecyclerView.setAdapter(gols);
                            //Activem boto esquerre
                            left.setVisibility(View.VISIBLE);
                            left.setClickable(true);
                            local = partit2.getLocal();
                            visitant = partit2.getVisitant();
                            if (local!=null && visitant!=null) {
                                spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                                spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                            } else {
                                spinnerEquip1.setSelection(0);
                                spinnerEquip2.setSelection(0);
                                local = visitant = uf.getEquip(getApplicationContext(),0);
                            }
                        } else {
                            Toast.makeText(this, "Un equip no pot jugar contra si mateix", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Partit 2":
                        if (!local.getName().equals(visitant.getName())) {
                            //Guardem els gols fets al partit i els equips seleccionats
                            partit2.setGolejadors(gols.getItems());
                            partit2.setLocal(local);
                            partit2.setVisitant(visitant);
                            //Canviem de pantalla
                            titol.setText("Partit 3");
                            gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                            mRecyclerView.setAdapter(gols);
                            local = partit3.getLocal();
                            visitant = partit3.getVisitant();
                            if (local!=null && visitant!=null) {
                                spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                                spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                            } else {
                                spinnerEquip1.setSelection(0);
                                spinnerEquip2.setSelection(0);
                                local = visitant = uf.getEquip(getApplicationContext(),0);
                            }
                        } else {
                            Toast.makeText(this, "Un equip no pot jugar contra si mateix", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Partit 3":
                        if (!local.getName().equals(visitant.getName())) {
                            //Guardem els gols fets al partit i els equips seleccionats
                            partit3.setGolejadors(gols.getItems());
                            partit3.setLocal(local);
                            partit3.setVisitant(visitant);
                            //Canviem de pantalla
                            titol.setText("Partit 4");
                            gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                            mRecyclerView.setAdapter(gols);
                            local = partit4.getLocal();
                            visitant = partit4.getVisitant();
                            if (local!=null && visitant!=null) {
                                spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                                spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                            } else {
                                spinnerEquip1.setSelection(0);
                                spinnerEquip2.setSelection(0);
                                local = visitant = uf.getEquip(getApplicationContext(),0);
                            }
                        } else {
                            Toast.makeText(this, "Un equip no pot jugar contra si mateix", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Partit 4":
                        if (!local.getName().equals(visitant.getName())) {
                            //Guardem els gols fets al partit i els equips seleccionats
                            partit4.setGolejadors(gols.getItems());
                            partit4.setLocal(local);
                            partit4.setVisitant(visitant);
                            //Canviem de pantalla
                            titol.setText("Partit 5");
                            gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                            mRecyclerView.setAdapter(gols);
                            local = partit5.getLocal();
                            visitant = partit5.getVisitant();
                            if (local!=null && visitant!=null) {
                                spinnerEquip1.setSelection(uf.getPosicioEquip(getApplicationContext(), local.getName()));
                                spinnerEquip2.setSelection(uf.getPosicioEquip(getApplicationContext(), visitant.getName()));
                            } else {
                                spinnerEquip1.setSelection(0);
                                spinnerEquip2.setSelection(0);
                                local = visitant = uf.getEquip(getApplicationContext(),0);
                            }
                            //Set icono i background verd
                            right.setBackgroundColor(getResources().getColor(R.color.colorAccent2));
                            right.setBackgroundResource(R.drawable.ic_check_white_18dp);
                        } else {
                            Toast.makeText(this, "Un equip no pot jugar contra si mateix", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Partit 5":
                        if (!local.getName().equals(visitant.getName())) {
                            //Guardem els gols fets al partit i els equips seleccionats
                            partit5.setGolejadors(gols.getItems());
                            partit5.setLocal(local);
                            partit5.setVisitant(visitant);
                            //Confirmem els partits a la DB
                            uf.addPartit(getApplicationContext(), partit1.getLocal(), partit1.getVisitant(), partit1.getPuntLocal(), partit1.getPuntVisitant());
                            uf.addPartit(getApplicationContext(), partit2.getLocal(), partit2.getVisitant(), partit2.getPuntLocal(), partit2.getPuntVisitant());
                            uf.addPartit(getApplicationContext(), partit3.getLocal(), partit3.getVisitant(), partit3.getPuntLocal(), partit3.getPuntVisitant());
                            uf.addPartit(getApplicationContext(), partit4.getLocal(), partit4.getVisitant(), partit4.getPuntLocal(), partit4.getPuntVisitant());
                            uf.addPartit(getApplicationContext(), partit5.getLocal(), partit5.getVisitant(), partit5.getPuntLocal(), partit5.getPuntVisitant());
                            uf.borraGols(getApplicationContext());
                            startActivity(new Intent(NewJornadaActivity.this, HomeActivity.class));
                            Toast.makeText(this, "Jornada creada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Un equip no pot jugar contra si mateix", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
            case R.id.afegirGol:
                uf.setGol(getApplicationContext(), titol.getText().toString(), selected.getName());
                gols = new GolsAdapter(getApplicationContext(), titol.getText().toString());
                mRecyclerView.setAdapter(gols);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        uf.borraGols(getApplicationContext());
        finish();
    }
}