package com.aleksandrp.testapplicationalinataranovskaya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.adapter.ViewAdapter;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkRequestEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.service.ServiceApi;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.MaiPresenter;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpActionView;
import com.aleksandrp.testapplicationalinataranovskaya.utils.ShowToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_GENRES_ID;
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

    @Bind(R.id.et_search)
    EditText et_search;

    @Bind(R.id.iv_filter)
    ImageView iv_filter;

    private UpdatePopular ьДUpdatePopular;
    private UpdateFavorite mListenerFavorite;

    private Intent serviceIntent;
    private MaiPresenter mPresenter;

    private FragmentManager mFragmentManager;

    private String textSearch;
    private boolean blockSearch;
    private final int TIME_OUT_SEARCH = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        serviceIntent = new Intent(this, ServiceApi.class);
        mPresenter = new MaiPresenter();
        mPresenter.setMvpView(this);
        mPresenter.init();
        mFragmentManager = getSupportFragmentManager();

        initViewPager();

        blockSearch = true;
        textSearch = "";
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                MainActivity.this.textSearch = mCharSequence.toString();
                makeSearch();
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });
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

    @OnClick(R.id.iv_filter)
    public void iv_filterClick() {
        // TODO: 12.09.2017 DIALOG
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

    private void makeSearch() {
        if (blockSearch) {
            blockSearch = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (checkInternetConnection()) {
                        blockSearch = true;
                        showProgress(true);
                        if (textSearch.length() > 0) {
                            mPresenter.searchMovies(textSearch);
                        } else {
                            mPresenter.getLostMoves();
                        }
                    } else {
                        showMessageError(App.getContext().getString(R.string.no_internet));
                    }
                }
            }, TIME_OUT_SEARCH);
        }
    }

    //    ===========================================
    public void setListenerPopular(UpdatePopular mListenerPopular) {
        this.ьДUpdatePopular = mListenerPopular;
    }

    public void setListenerPresenter(UpdateFavorite mListenerFavorite) {
        this.mListenerFavorite = mListenerFavorite;
    }

    //    ===========================================

    public void makeRequest(NetworkRequestEvent mEvent, String mSearch, String filter) {
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, mEvent.getId());
        serviceIntent.putExtra(EXTRA_SEARCH, mSearch);
        serviceIntent.putExtra(EXTRA_GENRES_ID, filter);
        startService(serviceIntent);
    }

    //    ===========================================
    public void getLostMoves() {
        if (checkInternetConnection()) {
            showProgress(true);
            mPresenter.getLostMoves();
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
        if (ьДUpdatePopular != null) {
            ьДUpdatePopular.updateList(mData);
        }
    }

    public void showMessageError(String mData) {
        showProgress(false);
        ShowToast.showMessageError(mData);
    }


    public interface UpdatePopular {
        void updateList(ListMoveModel mData);

        void updateListMore(ListMoveModel mData);
    }

    public interface UpdateFavorite {
        void updateList(ListMoveModel mData);
    }
}

