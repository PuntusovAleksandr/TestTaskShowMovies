package com.aleksandrp.testapplicationalinataranovskaya.activity;

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
import com.aleksandrp.testapplicationalinataranovskaya.api.model.GenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.di.MainActivityComponent;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.MainActivityModule;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.MaiPresenter;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpActionView;
import com.aleksandrp.testapplicationalinataranovskaya.utils.ShowToast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.aleksandrp.testapplicationalinataranovskaya.utils.InternetUtils.checkInternetConnection;

public class MainActivity extends AppCompatActivity implements MvpActionView {

    @Bind(R.id.progressBar_registration)
    RelativeLayout progressBar_registration;

    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.tabs)
    TabLayout tabs;

    private UpdatePopular mUpdatePopular;

    private FilterDialog mFilterDialog;

    private String genres;
    private int current_page = 1;

    @Inject
    MaiPresenter mPresenter;
    @Inject
    Realm realm;

    MainActivityComponent getAppComponent() {
        return ((App) getApplication())
                .getAppComponent()
                .plus(new MainActivityModule(MainActivity.this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getAppComponent().inject(this);

        initViewPager();

        genres = "";

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
                new ViewAdapter(fragmentManager, realm);

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

    public interface UpdatePopular {
        void updateList(ListMoveModel mData);

        void updateListMore(ListMoveModel mData);
    }
}

