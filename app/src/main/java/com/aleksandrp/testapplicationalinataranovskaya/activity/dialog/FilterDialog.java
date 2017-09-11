package com.aleksandrp.testapplicationalinataranovskaya.activity.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.GenresModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class FilterDialog extends AlertDialog {


    @Bind(R.id.progressBar_registration)
    RelativeLayout progressBar_registration;

    @Bind(R.id.tv_cancel)
    TextView tv_cancel;
    @Bind(R.id.tv_ok)
    TextView tv_ok;

    @Bind(R.id.listView1)
    ListView listView1;


    private SelectGenresListener mListener;
    private Context mContext;
    private List<GenresModel> mData;
    private List<String> mDataString;
    private ArrayAdapter<String> adapter;
    private String checked = "";

    public FilterDialog(Context mContext) {
        super(mContext);
        this.mContext = mContext;

        mData = new ArrayList<>();
        mDataString = new ArrayList<>();

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filter, null);
        ButterKnife.bind(this, view);

        adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_multiple_choice, mDataString);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                int cntChoice = listView1.getCount();
                checked = "";
                SparseBooleanArray sparseBooleanArray = listView1.getCheckedItemPositions();
                for (int i = 0; i < cntChoice; i++) {
                    if (sparseBooleanArray.get(i) == true) {
//                        checked += listView1.getItemAtPosition(i).toString() + "\n";
                        checked += mData.get(i).id + ", ";
                    }
                }
                checked.trim();
                if (!checked.isEmpty()) {
                    int i = checked.length();
                    checked = checked.substring(0, i - 2);
                }
            }
        });

        showProgress(true);
        this.setView(view);
    }
// ==============================================

    @OnClick(R.id.tv_ok)
    public void tv_okClick() {
        if (mListener != null) {
            mListener.showMovies(checked);
        }
        cancel();
    }

    @OnClick(R.id.tv_cancel)
    public void tv_cancelClick() {
        cancel();
    }
// ==============================================


    public void updateListGenres(List<GenresModel> mData) {
        showProgress(false);
        this.mData = mData;
        this.mDataString.clear();
        for (GenresModel model : mData) {
            this.mDataString.add(model.name);
        }

        adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_multiple_choice, mDataString);
        listView1.setAdapter(adapter);
    }

    public void setListener(SelectGenresListener mListener) {
        this.mListener = mListener;
    }

    public interface SelectGenresListener {
        void showMovies(String genres);
    }


    public void showProgress(boolean mShowPhone) {
        if (mShowPhone) {
            progressBar_registration.setVisibility(View.VISIBLE);
        } else {
            progressBar_registration.setVisibility(View.GONE);
        }
    }


}
