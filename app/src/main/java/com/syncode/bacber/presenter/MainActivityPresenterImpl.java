package com.syncode.bacber.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.syncode.bacber.R;
import com.syncode.bacber.contract.MainActivityContract;
import com.syncode.bacber.interactor.BeritaInteractorImpl;
import com.syncode.bacber.view.FilterActivity;

import java.util.Objects;

import javax.security.auth.callback.Callback;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MainActivityPresenterImpl implements MainActivityContract.Presenter {

    private MainActivityContract.view view;
    private MainActivityContract.Interactor interactor;

    private Context context;
    @Nullable
    @BindView(R.id.listview)
    ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    AlertDialog dialog;

    public MainActivityPresenterImpl(MainActivityContract.view view, Context context) {
        this.view = view;
        interactor = new BeritaInteractorImpl();
        this.context = context;
    }

    @Override
    public void loadData(String country, String category, String apikey) {
        interactor.loadData(100, country, apikey, category, new Callback() {
            @Override
            public boolean equals(@Nullable Object obj) {
                if (interactor.checkExist() > 0) {
                    view.setData(interactor.getData());
                } else {
                    view.errorData("Data Not Found");
                }
                return true;
            }
        });
    }

    @Override
    public void loadDataSlider(String apikey, String category, String country) {
        interactor.loadDataSlider(country, apikey, category, new Callback() {
            @Override
            public boolean equals(@Nullable Object obj) {
                if (interactor.checkExistSlider() > 0) {
                    view.setDataslider(interactor.getDataSlider());
                } else {
                    view.errorData("Data Not Found");
                }
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        if (interactor.checkExist() > 0) {
            interactor.getData().clear();
            view.onSwipeRefresh();
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public void dialogShow() {
        view.onShowDialogFilter();
    }

    @Override
    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_filter_layout, null);
        ButterKnife.bind(this, view);
        arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, context.getResources().getStringArray(R.array.filter));
        assert listView != null;
        listView.setAdapter(arrayAdapter);
        builder.setView(view);
        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.dialogAnim;
        dialog.show();
    }

    @OnItemClick(R.id.listview)
    public void selectItemCategory(int position) {
        dialog.dismiss();
        Intent intent = new Intent(context, FilterActivity.class);
        intent.putExtra("category", arrayAdapter.getItem(position));
        context.startActivity(intent);
    }
}
