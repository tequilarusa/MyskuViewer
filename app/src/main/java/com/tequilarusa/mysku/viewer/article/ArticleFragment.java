package com.tequilarusa.mysku.viewer.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tequilarusa.mysku.R;

/**
 * Created by MaximKashapov on 05.01.2017.
 */

public class ArticleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.article_activity, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.article_actions, menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Get item selected and deal with it
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                //called when the up affordance/carat in actionbar is pressed
//                getActivity().onBackPressed();
//                return true;
//        }
//        return false;
//    }
}
