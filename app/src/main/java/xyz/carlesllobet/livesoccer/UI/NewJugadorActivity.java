package xyz.carlesllobet.livesoccer.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.EquipSpinnerAdapter;
import xyz.carlesllobet.livesoccer.Domain.Objects.Equip;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.Domain.PlayerSpinnerAdapter;
import xyz.carlesllobet.livesoccer.R;

/**
 * Created by JEDI on 10/08/2015.
 */
public class NewJugadorActivity extends AppCompatActivity {

    private UserFunctions uf;

    private EditText nom;
    private EditText dorsal;
    private CheckedTextView titular;

    private ImageView escut;
    private ImageView pic;
    private Spinner spinnerJugadors;

    private Button afegir;

    private Jugador j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_jugador);

        setTitle("Nou Jugador");

        uf = new UserFunctions();

        ArrayList<Jugador> jugadors = uf.getJugadors(getApplicationContext());

        spinnerJugadors = (Spinner) findViewById(R.id.player);
        final PlayerSpinnerAdapter playerSpinnerAdapter = new PlayerSpinnerAdapter(NewJugadorActivity.this, jugadors);
        spinnerJugadors.setAdapter(playerSpinnerAdapter);

        escut = (ImageView) findViewById(R.id.escut);
        pic = (ImageView) findViewById(R.id.pic);

        spinnerJugadors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                j = playerSpinnerAdapter.getItem(position);
                escut.setImageURI(uf.getEquip(getApplicationContext(),j.getEquip()).getEscut());
                pic.setImageURI(uf.getEquip(getApplicationContext(),j.getEquip()).getEscut());
                titular.setChecked(j.getTitular());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Integer pos = getIntent().getExtras().getInt("jugador");
        if (pos != null) {
            j = uf.getJugador(getApplicationContext(),pos);
            escut.setImageURI(uf.getEquip(getApplicationContext(),j.getEquip()).getEscut());
            pic.setImageURI(uf.getEquip(getApplicationContext(),j.getEquip()).getEscut());
            spinnerJugadors.setSelection(pos);
            titular.setChecked(j.getTitular());
        }

        nom = (EditText) findViewById(R.id.jugador);
        afegir = (Button) findViewById(R.id.afegirJugador);
        dorsal = (EditText) findViewById(R.id.dorsal);
        titular = (CheckedTextView) findViewById(R.id.titular);

        afegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedValues()) {
                    uf.deleteJugador(getApplicationContext(), j.getName());
                    uf.addJugador(getApplicationContext(), nom.getText().toString(), Integer.valueOf(dorsal.getText().toString()), j.getEquip(), titular.isChecked());
                }
            }
        });
    }

    private Boolean checkedValues(){
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
