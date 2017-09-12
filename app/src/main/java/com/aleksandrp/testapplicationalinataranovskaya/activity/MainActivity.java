package com.aleksandrp.testapplicationalinataranovskaya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.activity.dialog.FilterDialog;
import com.aleksandrp.testapplicationalinataranovskaya.adapter.ViewAdapter;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkRequestEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.GenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.service.ServiceApi;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.MaiPresenter;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpActionView;
import com.aleksandrp.testapplicationalinataranovskaya.utils.ShowToast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_GENRES_ID;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_PAGE;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_SEARCH;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.aleksandrp.testapplicationalinataranovskaya.utils.InternetUtils.checkInternetConnection;

public class MainActivity extends AppCompatActivity implements MvpActionView {

    @Bind(R.id.progressBar_registration)
    RelativeLayout progressBar_registration;

    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tabs)
    TabLayout tabs;

    private UpdatePopular mUpdatePopular;
    private UpdateFavorite mListenerFavorite;

    private Intent serviceIntent;
    private MaiPresenter mPresenter;

    private FilterDialog mFilterDialog;

    private String genres;

    private int current_page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        serviceIntent = new Intent(this, ServiceApi.class);
        mPresenter = new MaiPresenter();
        mPresenter.setMvpView(this);
        mPresenter.init();

        initViewPager();

        genres = "";

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.registerSubscriber();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unRegisterSubscriber();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unRegisterSubscriber();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
        stopService(serviceIntent);
        super.onDestroy();
    }

    //    ===========================================


    public void showDialogFilter() {
        if (mFilterDialog != null && mFilterDialog.isShowing()) return;

        mPresenter.getListGenres();

        mFilterDialog = new FilterDialog(this);
        mFilterDialog.setListener(new FilterDialog.SelectGenresListener() {
            @Override
            public void showMovies(String genres) {
                MainActivity.this.genres = genres;
                mPresenter.getLostMoves(genres);
            }
        });
        mFilterDialog.show();
    }

    //    ===========================================
    private void initViewPager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewAdapter mTabAdapter =
                new ViewAdapter(fragmentManager);

        tabs.addTab(tabs.newTab().setText(R.string.popular));
        tabs.addTab(tabs.newTab().setText(R.string.favorite));

        pager.setAdapter(mTabAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //    ===========================================
    public void setListenerPopular(UpdatePopular mListenerPopular) {
        this.mUpdatePopular = mListenerPopular;
    }

    public void setListenerPresenter(UpdateFavorite mListenerFavorite) {
        this.mListenerFavorite = mListenerFavorite;
    }

    //    ===========================================

    public void makeRequest(NetworkRequestEvent mEvent, String mSearch, String filter, int current_page) {
        showProgress(true);
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, mEvent.getId());
        serviceIntent.putExtra(EXTRA_SEARCH, mSearch);
        serviceIntent.putExtra(EXTRA_GENRES_ID, filter);
        serviceIntent.putExtra(EXTRA_PAGE, current_page);
        startService(serviceIntent);
    }

    //    ===========================================
    public void getLostMoves(int mCurrent_page) {
        this.current_page = mCurrent_page;
        if (checkInternetConnection()) {
            mPresenter.getLostMoves(genres, current_page);
        } else {
            showMessageError(App.getContext().getString(R.string.no_internet));
        }
    }
    //    ===========================================

    public void showProgress(boolean mShowPhone) {
        if (mShowPhone) {
            progressBar_registration.setVisibility(View.VISIBLE);
        } else {
            progressBar_registration.setVisibility(View.GONE);
        }
    }

    public void showListPopular(ListMoveModel mData) {
        showProgress(false);
        if (mUpdatePopular != null) {
            if (mData.page < 2) {
                mUpdatePopular.updateList(mData);
            } else {
                mUpdatePopular.updateListMore(mData);
            }
        }
    }

    public void showGenres(List<GenresModel> mData) {
        showProgress(false);
        if (mFilterDialog != null && mFilterDialog.isShowing()) {
            mFilterDialog.updateListGenres(mData);
        }
    }

    public void showMessageError(String mData) {
        if (mFilterDialog != null && mFilterDialog.isShowing()) {
            mFilterDialog.showProgress(false);
        }
        showProgress(false);
        ShowToast.showMessageError(mData);
    }

    public void makeStopService() {
        stopService(serviceIntent);
    }


    public interface UpdatePopular {
        void updateList(ListMoveModel mData);

        void updateListMore(ListMoveModel mData);
    }

    public interface UpdateFavorite {
        void updateList(ListMoveModel mData);
    }
}

