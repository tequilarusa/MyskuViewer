package com.tequilarusa.mysku.viewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import tequilarusa.mysku.R;

/**
 * Created by Maks on 29.12.2016.
 */

public class MyskuDrawer extends Drawer {

    private AppCompatActivity mainView;

    public MyskuDrawer(final AppCompatActivity mainView, Toolbar toolbar) {
        this.mainView = mainView;
        withActivity(mainView);
        withToolbar(toolbar);
        withActionBarDrawerToggle(true);
        withHeader(R.layout.drawer_header);
        addDrawerItems(
                new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                new PrimaryDrawerItem().withName(R.string.drawer_item_my_profile).withIcon(FontAwesome.Icon.faw_user).withIdentifier(2),
                new PrimaryDrawerItem().withName(R.string.drawer_item_message).withIcon(FontAwesome.Icon.faw_envelope).withIdentifier(3),
                new PrimaryDrawerItem().withName(R.string.drawer_item_comments).withIcon(FontAwesome.Icon.faw_comments).withIdentifier(4),
                new SectionDrawerItem().withName(R.string.drawer_item_settings),
                new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(5),
                new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).withIdentifier(6).setEnabled(false),
                new DividerDrawerItem(),
                new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(7)
        );
        withOnDrawerListener(new MyskuDrawerListener());
        withOnDrawerItemClickListener(new MyskuDrawerItemClickListener());
    }

    class MyskuDrawerListener implements OnDrawerListener {

        @Override
        public void onDrawerOpened(View drawerView) {
            InputMethodManager inputMethodManager = (InputMethodManager) mainView.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mainView.getCurrentFocus().getWindowToken(), 0);
        }

        @Override
        public void onDrawerClosed(View drawerView) {

        }
    }

    class MyskuDrawerItemClickListener implements OnDrawerItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
            Fragment fragment = null;
            switch (position) {
                case 1:
                    fragment = new MainFragment();
                    break;
                case 2:
                    fragment = new ProfileFragment();
                    break;
                case 3:

                    break;
                case 4:

                    break;
            }
            if (fragment == null) {
                return;
            }

            // Create a new fragment and specify the planet to show based on position

            Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = mainView.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
        }
    }


}
