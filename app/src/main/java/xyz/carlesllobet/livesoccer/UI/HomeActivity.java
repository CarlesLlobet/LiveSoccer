package xyz.carlesllobet.livesoccer.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.PagerAdapter;
import xyz.carlesllobet.livesoccer.R;

public class HomeActivity extends FragmentActivity {

    private Toolbar toolbar;
    private UserFunctions userFunctions;

    private MenuItem language;
    private String lang;

    private PagerAdapter pa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userFunctions = new UserFunctions();
        userFunctions.checkInitValues(getApplicationContext());
        lang = userFunctions.getLang(getApplicationContext());
        if (!lang.equals("notExists")) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        }
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setActionBar(toolbar);

        setTitle("LiveSoccer");


        pa = new PagerAdapter(getSupportFragmentManager(), HomeActivity.this);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pa);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(Color.LTGRAY, Color.WHITE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        language = menu.findItem(R.id.action_language);
        String aux = Locale.getDefault().toString();
        if (aux.equals("ca")) language.setIcon(R.mipmap.ic_catalonia);
        if (aux.equals("en")) language.setIcon(R.mipmap.ic_uk);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_spa:
                Locale locale = new Locale("es");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getApplicationContext().getResources().updateConfiguration(config, null);
                language.setIcon(R.mipmap.ic_spain);
                lang = "es";
                userFunctions.setLang(getApplicationContext(), lang);
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                break;
            case R.id.action_cat:
                Locale locale2 = new Locale("ca");
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getApplicationContext().getResources().updateConfiguration(config2, null);
                language.setIcon(R.mipmap.ic_catalonia);
                lang = "ca";
                userFunctions.setLang(getApplicationContext(), lang);
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                break;
            case R.id.action_en:
                Locale locale3 = new Locale("en");
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config3.locale = locale3;
                getApplicationContext().getResources().updateConfiguration(config3, null);
                language.setIcon(R.mipmap.ic_uk);
                lang = "en";
                userFunctions.setLang(getApplicationContext(), lang);
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                break;
            case R.id.action_help:
                Intent intent = new Intent(this, HelpActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }


}
