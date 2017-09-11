package com.aleksandrp.testapplicationalinataranovskaya.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.adapter.MoviesListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

/**
 * Created by AleksandrP on 11.09.2017.
 */

@SuppressLint("ValidFragment")
public class BaseFrament extends Fragment {

    @Bind(R.id.rv_list_tasks)
    RecyclerView mRecyclerView;

    public MoviesListAdapter adapter;
    public LinearLayoutManager linearLayoutManager;

    public BaseFrament() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_fragment, container, false);
        ButterKnife.bind(this, view);


        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MoviesListAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        return view;
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
//                if (mPayloadHistory != null && progressBar_registration.getVisibility() != View.VISIBLE) {
//                    PayloadHistory.AllHistory data = mPayloadHistory.getPayload().getData();
//                    if (data != null) {
//                        int current_page = data.current_page;
//                        if (current_page < data.total)
//                            loadMoreChatItems(current_page + 1);
//                    }
//                    PayloadHistory.AllHistory orders = mPayloadHistory.getPayload().orders;
//                    if (orders != null) {
//                        int current_page = orders.current_page;
//                        if (current_page < orders.total)
//                            loadMoreChatItems(current_page + 1);
//                    }
//                }
            }
        }
    };
}
