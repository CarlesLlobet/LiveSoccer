package xyz.carlesllobet.livesoccer.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.PlayGifView;
import xyz.carlesllobet.livesoccer.R;

/**
 * Created by CarlesLlobet on 10/03/2016.
 */
public class GifActivity extends AppCompatActivity {

    private UserFunctions userFunctions;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userFunctions = new UserFunctions();
        lang = userFunctions.getLang(getApplicationContext());
        if (!lang.equals("notExists")) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        }

        setContentView(R.layout.activity_gif);

        PlayGifView pGif = (PlayGifView) findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.gif_livesoccer);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        }, 1550);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}