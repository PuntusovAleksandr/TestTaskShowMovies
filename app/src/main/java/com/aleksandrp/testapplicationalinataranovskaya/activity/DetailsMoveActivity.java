package com.aleksandrp.testapplicationalinataranovskaya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkRequestEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.FullInfoMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.GenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.service.ServiceApi;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.DetailsPresenter;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpActionView;
import com.aleksandrp.testapplicationalinataranovskaya.utils.ShowToast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter.API_BASE_URL_IMAGE;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_ID_MOVE;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.SERVICE_JOB_ID_TITLE;
import static com.aleksandrp.testapplicationalinataranovskaya.utils.ShowImage.showImageFromFile;

public class DetailsMoveActivity extends AppCompatActivity implements MvpActionView {

    @Bind(R.id.progressBar_registration)
    RelativeLayout progressBar_registration;

    @Bind(R.id.iv_icon_move)
    ImageView iv_icon_move;

    @Bind(R.id.tv_tile)
    TextView tv_tile;
    @Bind(R.id.tv_overview)
    TextView tv_overview;
    @Bind(R.id.tv_release_date)
    TextView tv_release_date;
    @Bind(R.id.tv_vote_average)
    TextView tv_vote_average;
    @Bind(R.id.tv_vote_count)
    TextView tv_vote_count;
    @Bind(R.id.tv_genres)
    TextView tv_genres;


    private Intent serviceIntent;
    private DetailsPresenter mPresenter;

    private long idMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_move);
        ButterKnife.bind(this);

        serviceIntent = new Intent(this, ServiceApi.class);
        mPresenter = new DetailsPresenter();
        mPresenter.setMvpView(this);
        mPresenter.init();

        idMove = getIntent().getLongExtra(EXTRA_ID_MOVE, -1);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.registerSubscriber();
        if (idMove > 0) {
            mPresenter.getDetailsMove(idMove);
        }
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

    public void makeRequest(NetworkRequestEvent mEvent, long id) {
        showProgress(true);
        serviceIntent.putExtra(SERVICE_JOB_ID_TITLE, mEvent.getId());
        serviceIntent.putExtra(EXTRA_ID_MOVE, id);
        startService(serviceIntent);
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
        if ((mData.contains("The resource you requested could not be found")) ||
                (mData.toLowerCase().contains("error"))) {
            finish();
        }else {
            showProgress(false);
        }
        ShowToast.showMessageError(mData);
    }

    public void showDetails(FullInfoMoveModel mData) {
        showProgress(false);
        if (mData != null) {

            showImageFromFile(
                    API_BASE_URL_IMAGE + mData.poster_path,
                    iv_icon_move);

            List<GenresModel> genres = mData.genres;
            String genre = "";
            for (GenresModel model : genres) {
                genre = genre + model.name + "\n";
            }

            tv_tile.setText(mData.title);
            tv_overview.setText(mData.overview);
            tv_release_date.setText("Release: " + mData.release_date);
            tv_vote_average.setText("Vote average: " + mData.vote_average);
            tv_vote_count.setText("Vte count: " + mData.vote_count);
            tv_genres.setText(genre);
        }
    }
}
