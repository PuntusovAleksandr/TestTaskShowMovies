package com.aleksandrp.testapplicationalinataranovskaya.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.activity.DetailsMoveActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.MoveModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter.API_BASE_URL_IMAGE;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_ID_MOVE;
import static com.aleksandrp.testapplicationalinataranovskaya.utils.ShowImage.showImageFromFile;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MoveModel> mItemMoves;
    private Context mActivity;

    public MoviesListAdapter(Context mActivity) {
        this.mActivity = mActivity;
        mItemMoves = new ArrayList<>();
    }

    public void addAll(ListMoveModel mListMoveModel) {
        removeAll();
        mItemMoves.addAll(mListMoveModel.results);
        this.notifyDataSetChanged();
    }

    public void addAllMore(ListMoveModel mListMoveModel) {
        mItemMoves.addAll(mListMoveModel.results);
        this.notifyDataSetChanged();
    }

    public void removeAll() {
        mItemMoves.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_move, parent, false);
        RecyclerView.ViewHolder viewHolder = new ContentHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (mItemMoves.size() > 0) {
            final MoveModel model = mItemMoves.get(position);

            ((ContentHolder) holder).tv_title.setText(model.title);
            ((ContentHolder) holder).tv_overview.setText(model.overview);

            showImageFromFile(
                    API_BASE_URL_IMAGE + model.poster_path,
                    ((ContentHolder) holder).iv_icon_from_call);

            ((ContentHolder) holder).cv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View mView) {
                    Intent intent = new Intent(mActivity, DetailsMoveActivity.class);
                    intent.putExtra(EXTRA_ID_MOVE, model.id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mActivity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mItemMoves != null && !mItemMoves.isEmpty()) {
            size = mItemMoves.size();
        }
        return size;
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.cv_item)
        RelativeLayout cv_item;

        @Nullable
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Nullable
        @Bind(R.id.tv_overview)
        TextView tv_overview;

        @Nullable
        @Bind(R.id.iv_icon_from_call)
        ImageView iv_icon_from_call;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}