package xyz.carlesllobet.livesoccer.Domain;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xyz.carlesllobet.livesoccer.UI.HomeActivity;
import xyz.carlesllobet.livesoccer.UI.HomeFragment;


public class PagerAdapter extends FragmentPagerAdapter {


    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Partits", "Equips", "Jugadors" };
    private Context context;
    Fragment tab = null;
    private Integer tabActual = 1;

    //creadora
    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    //crea las tabs, siempre tiene que retornar con el numero de tabs que queremos mostrar
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //Lanza el fragment asociado con el numero de tab
    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                tab = new HomeFragment(1, context);
                tabActual = 1;
                break;
            case 1:
                tab = new HomeFragment(2, context);
                tabActual = 2;
                break;
            case 2:
                tab = new HomeFragment(3, context);
                tabActual = 3;
                break;
        }
        return tab;
    }

    //pone el nombre en cada tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
