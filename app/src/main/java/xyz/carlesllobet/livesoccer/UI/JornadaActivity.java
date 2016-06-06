package xyz.carlesllobet.livesoccer.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.Objects.Partit;
import xyz.carlesllobet.livesoccer.Domain.PartitAdapter;
import xyz.carlesllobet.livesoccer.R;

/**
 * Created by JEDI on 10/08/2015.
 */
public class JornadaActivity extends AppCompatActivity implements View.OnClickListener {

    private UserFunctions uf;

    private TextView nomLocal;
    private TextView nomVisitant;
    private TextView golsLocal;
    private TextView golsVisitant;
    private ImageView escutLocal;
    private ImageView escutVisitant;

    private PartitAdapter gols;

    private FloatingActionButton left;
    private FloatingActionButton right;

    private TextView titol;

    private Partit p1;
    private Partit p2;
    private Partit p3;
    private Partit p4;
    private Partit p5;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jornada);

        setTitle(R.string.tituloDetalles);

        uf = new UserFunctions();

        Integer pos = getIntent().getExtras().getInt("jornada");
        if (pos != null) {
            p1 = uf.getJornada(getApplicationContext(), pos).getPrimer();
            p2 = uf.getJornada(getApplicationContext(), pos).getSegon();
            p3 = uf.getJornada(getApplicationContext(), pos).getTercer();
            p4 = uf.getJornada(getApplicationContext(), pos).getQuart();
            p5 = uf.getJornada(getApplicationContext(), pos).getCinque();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.golsRecyclerView);

        mLinearLayout = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLinearLayout);

        titol = (TextView) findViewById(R.id.partit);

        left = (FloatingActionButton) findViewById(R.id.left);
        right = (FloatingActionButton) findViewById(R.id.right);

        left.setOnClickListener(this);
        right.setOnClickListener(this);

        left.setVisibility(View.INVISIBLE);
        left.setClickable(false);

        gols = new PartitAdapter(getApplicationContext(), p1.getGolejadors());
        mRecyclerView.setAdapter(gols);

        nomLocal = (TextView) findViewById(R.id.name);
        nomVisitant = (TextView) findViewById(R.id.name2);
        golsLocal = (TextView) findViewById(R.id.punt);
        golsVisitant = (TextView) findViewById(R.id.punt2);
        escutLocal = (ImageView) findViewById(R.id.foto);
        escutVisitant = (ImageView) findViewById(R.id.foto2);

        nomLocal.setText(p1.getLocal().getName());
        nomVisitant.setText(p1.getVisitant().getName());
        golsLocal.setText(p1.getPuntLocal().toString());
        golsVisitant.setText(p1.getPuntVisitant().toString());
        escutLocal.setImageURI(p1.getLocal().getEscut());
        escutVisitant.setImageURI(p1.getVisitant().getEscut());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left:
                switch (titol.getText().toString()) {
                    case "Partit 2":
                        //Canviem de pantalla
                        titol.setText("Partit 1");
                        gols = new PartitAdapter(getApplicationContext(), p1.getGolejadors());
                        mRecyclerView.setAdapter(gols);
                        left.setVisibility(View.INVISIBLE);
                        left.setClickable(false);

                        nomLocal.setText(p1.getLocal().getName());
                        nomVisitant.setText(p1.getVisitant().getName());
                        golsLocal.setText(p1.getPuntLocal().toString());
                        golsVisitant.setText(p1.getPuntVisitant().toString());
                        escutLocal.setImageURI(p1.getLocal().getEscut());
                        escutVisitant.setImageURI(p1.getVisitant().getEscut());
                        break;
                    case "Partit 3":
                        //Canviem de pantalla
                        titol.setText("Partit 2");
                        gols = new PartitAdapter(getApplicationContext(), p2.getGolejadors());
                        mRecyclerView.setAdapter(gols);

                        nomLocal.setText(p2.getLocal().getName());
                        nomVisitant.setText(p2.getVisitant().getName());
                        golsLocal.setText(p2.getPuntLocal().toString());
                        golsVisitant.setText(p2.getPuntVisitant().toString());
                        escutLocal.setImageURI(p2.getLocal().getEscut());
                        escutVisitant.setImageURI(p2.getVisitant().getEscut());
                        break;
                    case "Partit 4":
                        //Canviem de pantalla
                        titol.setText("Partit 3");
                        gols = new PartitAdapter(getApplicationContext(), p3.getGolejadors());
                        mRecyclerView.setAdapter(gols);

                        nomLocal.setText(p3.getLocal().getName());
                        nomVisitant.setText(p3.getVisitant().getName());
                        golsLocal.setText(p3.getPuntLocal().toString());
                        golsVisitant.setText(p3.getPuntVisitant().toString());
                        escutLocal.setImageURI(p3.getLocal().getEscut());
                        escutVisitant.setImageURI(p3.getVisitant().getEscut());
                        break;
                    case "Partit 5":
                        //Canviem de pantalla
                        titol.setText("Partit 4");
                        gols = new PartitAdapter(getApplicationContext(), p4.getGolejadors());
                        mRecyclerView.setAdapter(gols);
                        //Set icono i background verd
                        right.setVisibility(View.VISIBLE);
                        right.setClickable(true);

                        nomLocal.setText(p4.getLocal().getName());
                        nomVisitant.setText(p4.getVisitant().getName());
                        golsLocal.setText(p4.getPuntLocal().toString());
                        golsVisitant.setText(p4.getPuntVisitant().toString());
                        escutLocal.setImageURI(p4.getLocal().getEscut());
                        escutVisitant.setImageURI(p4.getVisitant().getEscut());
                        break;
                }
                break;
            case R.id.right:
                switch (titol.getText().toString()) {
                    case "Partit 1":
                        //Canviem de pantalla
                        titol.setText("Partit 2");
                        gols = new PartitAdapter(getApplicationContext(), p2.getGolejadors());
                        mRecyclerView.setAdapter(gols);
                        //Activem boto esquerre
                        left.setVisibility(View.VISIBLE);
                        left.setClickable(true);

                        nomLocal.setText(p2.getLocal().getName());
                        nomVisitant.setText(p2.getVisitant().getName());
                        golsLocal.setText(p2.getPuntLocal().toString());
                        golsVisitant.setText(p2.getPuntVisitant().toString());
                        escutLocal.setImageURI(p2.getLocal().getEscut());
                        escutVisitant.setImageURI(p2.getVisitant().getEscut());
                        break;
                    case "Partit 2":
                        //Canviem de pantalla
                        titol.setText("Partit 3");
                        gols = new PartitAdapter(getApplicationContext(), p3.getGolejadors());
                        mRecyclerView.setAdapter(gols);

                        nomLocal.setText(p3.getLocal().getName());
                        nomVisitant.setText(p3.getVisitant().getName());
                        golsLocal.setText(p3.getPuntLocal().toString());
                        golsVisitant.setText(p3.getPuntVisitant().toString());
                        escutLocal.setImageURI(p3.getLocal().getEscut());
                        escutVisitant.setImageURI(p3.getVisitant().getEscut());
                        break;
                    case "Partit 3":
                        //Canviem de pantalla
                        titol.setText("Partit 4");
                        gols = new PartitAdapter(getApplicationContext(), p4.getGolejadors());
                        mRecyclerView.setAdapter(gols);

                        nomLocal.setText(p4.getLocal().getName());
                        nomVisitant.setText(p4.getVisitant().getName());
                        golsLocal.setText(p4.getPuntLocal().toString());
                        golsVisitant.setText(p4.getPuntVisitant().toString());
                        escutLocal.setImageURI(p4.getLocal().getEscut());
                        escutVisitant.setImageURI(p4.getVisitant().getEscut());
                        break;
                    case "Partit 4":
                        //Canviem de pantalla
                        titol.setText("Partit 5");
                        gols = new PartitAdapter(getApplicationContext(), p3.getGolejadors());
                        mRecyclerView.setAdapter(gols);
                        right.setVisibility(View.INVISIBLE);
                        right.setClickable(false);

                        nomLocal.setText(p5.getLocal().getName());
                        nomVisitant.setText(p5.getVisitant().getName());
                        golsLocal.setText(p5.getPuntLocal().toString());
                        golsVisitant.setText(p5.getPuntVisitant().toString());
                        escutLocal.setImageURI(p5.getLocal().getEscut());
                        escutVisitant.setImageURI(p5.getVisitant().getEscut());
                        break;
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
