package xyz.carlesllobet.livesoccer.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.Objects.Equip;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.R;

/**
 * Created by JEDI on 10/08/2015.
 */
public class JugadorActivity extends AppCompatActivity {

    private UserFunctions uf;

    private TextView nom;
    private TextView gols;
    private TextView dorsal;
    private TextView equip;

    private ImageView escut;

    private Button delete;

    private Jugador j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador);

        setTitle(R.string.tituloDetalles);

        uf = new UserFunctions();

        final Integer pos = getIntent().getExtras().getInt("jugador");
        if (pos != null) {
            j = uf.getJugador(getApplicationContext(),pos);
        }

        nom = (TextView)findViewById(R.id.jugador);
        gols = (TextView)findViewById(R.id.gols);
        equip = (TextView)findViewById(R.id.equip);
        dorsal = (TextView)findViewById(R.id.dorsal);

        delete = (Button) findViewById(R.id.btnDeleteJug);

        escut = (ImageView)findViewById(R.id.pic);

        nom.setText(j.getName());
        equip.setText(j.getEquip());
        dorsal.setText(j.getDorsal().toString());
        gols.setText(j.getGols().toString());

        escut.setImageURI(uf.getEquip(getApplicationContext(),j.getEquip()).getEscut());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NewJugadorActivity.class);
                i.putExtra("equip", pos);
                startActivity(i);
            }
        });
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
