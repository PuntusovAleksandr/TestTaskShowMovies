package com.aleksandrp.testapplicationalinataranovskaya.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.FullInfoMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.GenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.db.RealmObj;
import com.aleksandrp.testapplicationalinataranovskaya.db.models.MoveModelDb;
import com.aleksandrp.testapplicationalinataranovskaya.di.DetailsActivityComponent;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.DetailsActivityModule;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.DetailsPresenter;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpActionView;
import com.aleksandrp.testapplicationalinataranovskaya.utils.ShowToast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter.API_BASE_URL_IMAGE;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_ID_MOVE;
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


    @Bind(R.id.menu_fab)
    FloatingActionMenu menu_fab;

    @Bind(R.id.fab_save)
    FloatingActionButton fab_save;
    @Bind(R.id.fab_deleter)
    FloatingActionButton fab_deleter;

    @Inject
    DetailsPresenter mPresenter;
    @Inject
    Realm realm;

    private Handler mUiHandler = new Handler();

    private long idMove;
    private FullInfoMoveModel mData;


    DetailsActivityComponent getAppComponent() {
        return ((App) getApplication())
                .getAppComponent()
                .plus(new DetailsActivityModule(DetailsMoveActivity.this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_move);
        ButterKnife.bind(this);

        getAppComponent().inject(this);

        idMove = getIntent().getLongExtra(EXTRA_ID_MOVE, -1);

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


    @Override
    protected void onStart() {
        super.onStart();
        showProgress(true);
        if (idMove > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.getDetailsMove(idMove);
                }
            }, 750);
        }
    }

    //    ===========================================

    @OnClick(R.id.fab_save)
    public void fab_saveClick() {
        saveMoveInDb(true);
        menu_fab.toggle(true);
    }

    @OnClick(R.id.fab_deleter)
    public void fab_deleterClick() {
        saveMoveInDb(false);
        menu_fab.toggle(true);
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
        if ((mData.toLowerCase().contains("not found")) ||
                (mData.toLowerCase().contains("error"))) {
            finish();
        } else {
            showProgress(false);
        }
        ShowToast.showMessageError(mData);
    }

    public void showDetails(FullInfoMoveModel mData) {
        this.mData = mData;
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

    public void saveMoveInDb(boolean isSave) {
        MoveModelDb modelDb = new MoveModelDb();
        modelDb.setId(mData.id);
        modelDb.setPoster_path(mData.poster_path);
        modelDb.setTitle(mData.title);
        modelDb.setOverview(mData.overview);
        modelDb.setSave(isSave);

        RealmObj.getInstance().updateMove(modelDb, mPresenter, realm);
    }
}
