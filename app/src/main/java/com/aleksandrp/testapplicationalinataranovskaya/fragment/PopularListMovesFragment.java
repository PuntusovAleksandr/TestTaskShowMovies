package com.aleksandrp.testapplicationalinataranovskaya.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.activity.MainActivity;
import com.aleksandrp.testapplicationalinataranovskaya.activity.SearchActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;

import butterknife.OnClick;

import static com.aleksandrp.testapplicationalinataranovskaya.utils.InternetUtils.checkInternetConnection;
import static com.aleksandrp.testapplicationalinataranovskaya.utils.ShowToast.showMessageError;

/**
 * Created by AleksandrP on 11.09.2017.
 */

@SuppressLint("ValidFragment")
public class PopularListMovesFragment extends BaseFragment {


    private Handler mUiHandler = new Handler();
    private int current_page = 1;

    public PopularListMovesFragment() {
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            ((MainActivity) getActivity()).getLostMoves(current_page);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carentPage = 1;
        totalPages = 1;
        ((MainActivity) getActivity()).setListenerPopular(new MainActivity.UpdatePopular() {
            @Override
            public void updateList(ListMoveModel mData) {
                carentPage = mData.page;
                totalPages = mData.total_pages;
                adapter.addAll(mData);
            }

            @Override
            public void updateListMore(ListMoveModel mData) {
                carentPage = mData.page;
                totalPages = mData.total_pages;
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

    @Override
    public void loadMoreChatItems(int current_page) {
        this.current_page = current_page;
        if (checkInternetConnection()) {
            try {
                ((MainActivity) getActivity()).getLostMoves(current_page);
            } catch (Exception mE) {
                mE.printStackTrace();
            }
        } else {
            showMessageError(App.getContext().getString(R.string.no_internet));
        }
    }
}
