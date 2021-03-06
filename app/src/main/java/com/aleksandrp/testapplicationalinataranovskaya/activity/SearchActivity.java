package com.aleksandrp.testapplicationalinataranovskaya.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.adapter.MoviesListAdapter;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.MoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.di.SearchActivityComponent;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.SearchActivityModule;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.SearchPresenter;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpActionView;
import com.aleksandrp.testapplicationalinataranovskaya.utils.ShowToast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.aleksandrp.testapplicationalinataranovskaya.App.getContext;
import static com.aleksandrp.testapplicationalinataranovskaya.utils.InternetUtils.checkInternetConnection;

public class SearchActivity extends AppCompatActivity implements MvpActionView {

    @Bind(R.id.progressBar_registration)
    RelativeLayout progressBar_registration;

    @Bind(R.id.rv_list_tasks)
    RecyclerView mRecyclerView;

    @Bind(R.id.et_search)
    EditText et_search;

    public MoviesListAdapter adapter;
    public LinearLayoutManager linearLayoutManager;

    private String textSearch;
    private boolean blockSearch;

    private final int TIME_OUT_SEARCH = 500;

    @Inject
    SearchPresenter mPresenter;

    SearchActivityComponent getAppComponent() {
        return ((App) getApplication())
                .getAppComponent()
                .plus(new SearchActivityModule(SearchActivity.this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        ButterKnife.bind(this);

        getAppComponent().inject(this);

        linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MoviesListAdapter(this);
        mRecyclerView.setAdapter(adapter);

        textSearch = "";
        blockSearch = true;
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {

            }

            @Override
            public void onTextChanged(CharSequence mCharSequence, int mI, int mI1, int mI2) {
                SearchActivity.this.textSearch = mCharSequence.toString();
                makeSearch();
            }

            @Override
            public void afterTextChanged(Editable mEditable) {

            }
        });
        et_search.requestFocus();
    }

    //    ===========================================

    private void makeSearch() {
        if (blockSearch) {
            blockSearch = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (checkInternetConnection()) {
                        blockSearch = true;
                        if (textSearch.length() > 0) {
                            mPresenter.searchMovies(textSearch);
                        } else {
                            ListMoveModel listMoveModel = new ListMoveModel();
                            listMoveModel.results = new ArrayList<MoveModel>();
                            adapter.addAll(listMoveModel);
                        }
                    } else {
                        showMessageError(getContext().getString(R.string.no_internet));
                    }
                }
            }, TIME_OUT_SEARCH);
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

    public void showMessageError(String mData) {
        showProgress(false);
        ShowToast.showMessageError(mData);
    }


    public void showListFilter(ListMoveModel mData) {
        showProgress(false);
        adapter.addAll(mData);
    }
}
