package com.aleksandrp.testapplicationalinataranovskaya.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aleksandrp.testapplicationalinataranovskaya.activity.MainActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;

/**
 * Created by AleksandrP on 11.09.2017.
 */

@SuppressLint("ValidFragment")
public class PopularListMovesFragment extends BaseFrament {


    public PopularListMovesFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).getLostMoves();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getLostMoves();
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
    }

}
