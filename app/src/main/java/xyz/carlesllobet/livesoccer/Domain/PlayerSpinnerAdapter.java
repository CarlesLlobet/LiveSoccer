package xyz.carlesllobet.livesoccer.Domain;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.carlesllobet.livesoccer.DB.UserFunctions;
import xyz.carlesllobet.livesoccer.Domain.Objects.Jugador;
import xyz.carlesllobet.livesoccer.R;
import xyz.carlesllobet.livesoccer.UI.NewJornadaActivity;


public class PlayerSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {


    private final Context activity;
    private ArrayList<Jugador> asr;

    public PlayerSpinnerAdapter(Context context,ArrayList<Jugador> asr) {
        this.asr=asr;
        activity = context;
    }



    public int getCount()
    {
        return asr.size();
    }

    public Jugador getItem(int i)
    {
        return asr.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_soccer_white_24dp, 0, 0, 0);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(asr.get(position).getName());
        txt.setTextColor(Color.BLACK);
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_soccer_white_24dp, 0, 0, 0);
        txt.setText(asr.get(i).getName());
        txt.setTextColor(Color.WHITE);
        return  txt;
    }
}
