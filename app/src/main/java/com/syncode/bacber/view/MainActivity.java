package com.syncode.bacber.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.syncode.bacber.R;
import com.syncode.bacber.adapter.RecycleViewAdapter;
import com.syncode.bacber.adapter.ViewPagerAdapter;
import com.syncode.bacber.constanta.Api;
import com.syncode.bacber.contract.MainActivityContract;
import com.syncode.bacber.model.Berita;
import com.syncode.bacber.presenter.MainActivityPresenterImpl;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainActivityContract.view {


    @BindView(R.id.contentfilter)
    CardView contentfilter;


    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;


    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    MainActivityContract.Presenter presenter;

    RecycleViewAdapter recycleViewAdapter;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;
    @BindView(R.id.shimmer_container2)
    ShimmerFrameLayout mShimmerViewContainer2;

    @BindView(R.id.viewpager)
    ViewPager viewPager;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refresh;


    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        presenter = new MainActivityPresenterImpl(this, this);
        presenter.loadData("id", "", Api.API_KEY);
        presenter.loadDataSlider(Api.API_KEY, "technology", "id");

    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    @Override
    public void setData(List<Berita> beritaList) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recycleViewAdapter = new RecycleViewAdapter(beritaList, this);
        recyclerView.setAdapter(recycleViewAdapter);
        recycleViewAdapter.notifyDataSetChanged();
        if (beritaList.size() > 0) {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer2.stopShimmerAnimation();
            mShimmerViewContainer2.setVisibility(View.GONE);
            refresh.setRefreshing(false);
        }

    }


    @Override
    public void errorData(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDataslider(List<Berita> beritaList) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(beritaList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwipeRefresh() {
        presenter.loadData("id", "", Api.API_KEY);
    }

    @Override
    public void onShowDialogFilter() {
        presenter.createDialog();
    }


    @OnClick(R.id.textfilter)
    public void showDialog() {
        presenter.dialogShow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer2.startShimmerAnimation();
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset > -600) {
                contentfilter.animate().translationY(-150f);
                contentfilter.setVisibility(View.GONE);
            } else {
                contentfilter.animate().translationY(0f);
                contentfilter.setVisibility(View.VISIBLE);
            }
        });
        refresh.setOnRefreshListener(() -> {
            presenter.onRefresh();
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmerAnimation();
        });


    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer2.startShimmerAnimation();
        super.onPause();
    }
}
