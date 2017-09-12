package com.aleksandrp.testapplicationalinataranovskaya.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.activity.MainActivity;
import com.aleksandrp.testapplicationalinataranovskaya.activity.SearchActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;

import butterknife.OnClick;

/**
 * Created by AleksandrP on 11.09.2017.
 */

@SuppressLint("ValidFragment")
public class PopularListMovesFragment extends BaseFragment {


    private Handler mUiHandler = new Handler();

    public PopularListMovesFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).getLostMoves();
            }
        }, 750);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).setListenerPopular(new MainActivity.UpdatePopular() {
            @Override
            public void updateList(ListMoveModel mData) {
                adapter.addAll(mData);
            }

            @Override
            public void updateListMore(ListMoveModel mData) {
                adapter.addAllMore(mData);
            }
        });
        menu_fab.hideMenuButton(false);
        menu_fab.setClosedOnTouchOutside(true);
        menu_fab.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_fab.toggle(true);
            }
        });

        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu_fab.showMenuButton(true);
            }
        }, 400);
    }


    @OnClick(R.id.fab_filter)
    public void fab_filterClick() {
        menu_fab.toggle(true);
        ((MainActivity) getActivity()).showDialogFilter();
    }

    @OnClick(R.id.fab_search)
    public void fab_searClick() {
        menu_fab.toggle(true);
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

}
