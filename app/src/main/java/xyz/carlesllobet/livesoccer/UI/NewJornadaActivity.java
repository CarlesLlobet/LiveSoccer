package xyz.carlesllobet.livesoccer.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import xyz.carlesllobet.livesoccer.R;

public class NewJornadaActivity extends AppCompatActivity implements View.OnClickListener{

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

        titol = (TextView) findViewById(R.id.partit);

        foto = (ImageView) findViewById(R.id.escut);
        foto2 = (ImageView) findViewById(R.id.escut2);

        afegirGol = (Button) findViewById(R.id.afegirGol);
        left = (FloatingActionButton) findViewById(R.id.left);
        right = (FloatingActionButton) findViewById(R.id.right);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        afegirGol.setOnClickListener(this);

        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);

        gols = new GolsAdapter(getApplicationContext(),titol.getText().toString());
        mRecyclerView.setAdapter(gols);


        Spinner spinnerGols= (Spinner) findViewById(R.id.spinner);
        Spinner spinnerEquip1= (Spinner) findViewById(R.id.team1);
        Spinner spinnerEquip2= (Spinner) findViewById(R.id.team2);
        // Spinner Drop down elements
        ArrayList<Jugador> jugadors = uf.getJugadors(getApplicationContext());
        ArrayList<Equip> equips = uf.getEquips(getApplicationContext());

        final PlayerSpinnerAdapter golsSpinnerAdapter=new PlayerSpinnerAdapter(NewJornadaActivity.this,jugadors);
        final EquipSpinnerAdapter equip1SpinnerAdapter=new EquipSpinnerAdapter(NewJornadaActivity.this,equips);
        final EquipSpinnerAdapter equip2SpinnerAdapter=new EquipSpinnerAdapter(NewJornadaActivity.this,equips);

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
                Equip item = equip1SpinnerAdapter.getItem(position);
                foto.setImageURI(item.getEscut());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerEquip2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Equip item = equip2SpinnerAdapter.getItem(position);
                foto2.setImageURI(item.getEscut());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left:
                switch(titol.getText().toString()){
                    case "Partit 2":
                        titol.setText("Partit 1");
                        //button left not clickable and gone
                        //carregar gols partit 1
                        break;
                    case "Partit 3":
                        titol.setText("Partit 2");
                        //carregar gols partit 2
                        break;
                    case "Partit 4":
                        titol.setText("Partit 3");
                        //carregar gols partit 3
                        break;
                    case "Partit 5":
                        titol.setText("Partit 4");
                        //carregar gols partit 1
                        break;
                }
                break;
            case R.id.right:
                switch(titol.getText().toString()) {
                    case "Partit 1":
                        titol.setText("Partit 1");
                        //button left not clickable and gone
                        //carregar gols partit 1
                        partit1.setGolejadors(gols.getItems());
                        break;
                    case "Partit 2":
                        titol.setText("Partit 2");
                        //carregar gols partit 2
                        partit2.setGolejadors(gols.getItems());
                        break;
                    case "Partit 3":
                        titol.setText("Partit 3");
                        //carregar gols partit 3
                        partit3.setGolejadors(gols.getItems());
                        break;
                    case "Partit 4":
                        titol.setText("Partit 4");
                        //carregar gols partit 1
                        partit4.setGolejadors(gols.getItems());
                        //set icono tic i set background verd!
                        break;
                    case "Partit 5":
                        //Guardar partit
                        partit5.setGolejadors(gols.getItems());
                        startActivity(new Intent(NewJornadaActivity.this, HomeActivity.class));
                        Toast.makeText(getApplicationContext(),"Jornada creada",Toast.LENGTH_SHORT);
                        break;
                }
                break;
            case R.id.afegirGol:
                uf.setGol(getApplicationContext(),titol.getText().toString(),selected.getName());
                gols = new GolsAdapter(getApplicationContext(),titol.getText().toString());
                mRecyclerView.setAdapter(gols);
                break;
        }
    }
}
