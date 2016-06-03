package xyz.carlesllobet.livesoccer.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
public class EquipActivity extends AppCompatActivity {

    private UserFunctions uf;

    private TextView nom;
    private TextView punt;
    private TextView pg;
    private TextView pp;
    private TextView pe;
    private TextView gols;

    private ImageView escut;

    private Equip e;

    private ArrayList<Jugador> plantilla;

    private TextView dorsal1,dorsal2,dorsal3,dorsal4,dorsal5,dorsal6,dorsal7,dorsal8,dorsal9,dorsal10,dorsal11,dorsal12;
    private TextView name1,name2,name3,name4,name5,name6,name7,name8,name9,name10,name11,name12;
    private TextView golsJug1,golsJug2,golsJug3,golsJug4,golsJug5,golsJug6,golsJug7,golsJug8,golsJug9,golsJug10,golsJug11,golsJug12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip);

        setTitle(R.string.tituloDetalles);

        uf = new UserFunctions();

        Integer pos = getIntent().getExtras().getInt("equip");
        if (pos != null) {
            e = uf.getEquip(getApplicationContext(),pos);
        }

        nom = (TextView)findViewById(R.id.equip);
        punt = (TextView)findViewById(R.id.punt);
        pg = (TextView)findViewById(R.id.pg);
        pp = (TextView)findViewById(R.id.pp);
        pe = (TextView)findViewById(R.id.pe);
        gols = (TextView)findViewById(R.id.gols);

        escut = (ImageView)findViewById(R.id.pic);

        nom.setText(e.getName());
        punt.setText(e.getPunt().toString());
        pg.setText(e.getGuanyats().toString());
        pp.setText(e.getPerduts().toString());
        pe.setText(e.getEmpatats().toString());
        gols.setText(e.getGols().toString());

        escut.setImageURI(e.getEscut());


        plantilla = uf.getJugadors(getApplicationContext(),e.getName());

        name1 = (TextView)findViewById(R.id.name1);
        name2 = (TextView)findViewById(R.id.name2);
        name3 = (TextView)findViewById(R.id.name3);
        name4 = (TextView)findViewById(R.id.name4);
        name5 = (TextView)findViewById(R.id.name5);
        name6 = (TextView)findViewById(R.id.name6);
        name7 = (TextView)findViewById(R.id.name7);
        name8 = (TextView)findViewById(R.id.name8);
        name9 = (TextView)findViewById(R.id.name9);
        name10 = (TextView)findViewById(R.id.name10);
        name11 = (TextView)findViewById(R.id.name11);
        name12 = (TextView)findViewById(R.id.name12);

        dorsal1 = (TextView)findViewById(R.id.dorsal1);
        dorsal2 = (TextView)findViewById(R.id.dorsal2);
        dorsal3 = (TextView)findViewById(R.id.dorsal3);
        dorsal4 = (TextView)findViewById(R.id.dorsal4);
        dorsal4 = (TextView)findViewById(R.id.dorsal4);
        dorsal5 = (TextView)findViewById(R.id.dorsal5);
        dorsal6 = (TextView)findViewById(R.id.dorsal6);
        dorsal7 = (TextView)findViewById(R.id.dorsal7);
        dorsal8 = (TextView)findViewById(R.id.dorsal8);
        dorsal9 = (TextView)findViewById(R.id.dorsal9);
        dorsal10 = (TextView)findViewById(R.id.dorsal10);
        dorsal11 = (TextView)findViewById(R.id.dorsal11);
        dorsal12 = (TextView)findViewById(R.id.dorsal12);

        golsJug1 = (TextView)findViewById(R.id.golsJug1);
        golsJug2 = (TextView)findViewById(R.id.golsJug2);
        golsJug3 = (TextView)findViewById(R.id.golsJug3);
        golsJug4 = (TextView)findViewById(R.id.golsJug4);
        golsJug5 = (TextView)findViewById(R.id.golsJug5);
        golsJug6 = (TextView)findViewById(R.id.golsJug6);
        golsJug7 = (TextView)findViewById(R.id.golsJug7);
        golsJug8 = (TextView)findViewById(R.id.golsJug8);
        golsJug9 = (TextView)findViewById(R.id.golsJug9);
        golsJug10 = (TextView)findViewById(R.id.golsJug10);
        golsJug11 = (TextView)findViewById(R.id.golsJug11);
        golsJug12 = (TextView)findViewById(R.id.golsJug12);

        name1.setText(plantilla.get(0).getName());
        name2.setText(plantilla.get(1).getName());
        name3.setText(plantilla.get(2).getName());
        name4.setText(plantilla.get(3).getName());
        name5.setText(plantilla.get(4).getName());
        name6.setText(plantilla.get(5).getName());
        name7.setText(plantilla.get(6).getName());
        name8.setText(plantilla.get(7).getName());
        name9.setText(plantilla.get(8).getName());
        name10.setText(plantilla.get(9).getName());
        name11.setText(plantilla.get(10).getName());
        name12.setText(plantilla.get(11).getName());

        dorsal1.setText(plantilla.get(0).getDorsal().toString());
        dorsal2.setText(plantilla.get(1).getDorsal().toString());
        dorsal3.setText(plantilla.get(2).getDorsal().toString());
        dorsal4.setText(plantilla.get(3).getDorsal().toString());
        dorsal5.setText(plantilla.get(4).getDorsal().toString());
        dorsal6.setText(plantilla.get(5).getDorsal().toString());
        dorsal7.setText(plantilla.get(6).getDorsal().toString());
        dorsal8.setText(plantilla.get(7).getDorsal().toString());
        dorsal9.setText(plantilla.get(8).getDorsal().toString());
        dorsal10.setText(plantilla.get(9).getDorsal().toString());
        dorsal11.setText(plantilla.get(10).getDorsal().toString());
        dorsal12.setText(plantilla.get(11).getDorsal().toString());

        golsJug1.setText(plantilla.get(0).getGols().toString());
        golsJug2.setText(plantilla.get(1).getGols().toString());
        golsJug3.setText(plantilla.get(2).getGols().toString());
        golsJug4.setText(plantilla.get(3).getGols().toString());
        golsJug5.setText(plantilla.get(4).getGols().toString());
        golsJug6.setText(plantilla.get(5).getGols().toString());
        golsJug7.setText(plantilla.get(6).getGols().toString());
        golsJug8.setText(plantilla.get(7).getGols().toString());
        golsJug9.setText(plantilla.get(8).getGols().toString());
        golsJug10.setText(plantilla.get(9).getGols().toString());
        golsJug11.setText(plantilla.get(10).getGols().toString());
        golsJug12.setText(plantilla.get(11).getGols().toString());
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
