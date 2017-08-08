package com.whatsapp_cursoandroid.activity.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.whatsapp_cursoandroid.activity.Fragment.Contatos;
import com.whatsapp_cursoandroid.activity.Fragment.Conversas;

/**
 * Created by Admin on 07/08/2017.
 */

public class SlidingTabAdapter extends FragmentStatePagerAdapter {

    private static final String Tabs[] = new String[]{"CONVERSAS","CONTATOS"};

    public SlidingTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new Conversas();break;
            case 1: fragment = new Contatos();break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return Tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Tabs[position];
    }
}
