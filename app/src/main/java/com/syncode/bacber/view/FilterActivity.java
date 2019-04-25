package com.syncode.bacber.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.syncode.bacber.R;
import com.syncode.bacber.adapter.RecycleViewAdapter;
import com.syncode.bacber.constanta.Api;
import com.syncode.bacber.contract.FilterActivityContract;
import com.syncode.bacber.model.Berita;
import com.syncode.bacber.presenter.FilterActivityPresenterImpl;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FilterActivity extends AppCompatActivity implements FilterActivityContract.View {


    @BindView(R.id.recycleview)
    RecyclerView recyclerView;


    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    FilterActivityContract.Presenter presenter;

    RecycleViewAdapter recycleViewAdapter;


    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout refresh;


    private String iCategory;


    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.texttitle)
    TextView txttitle;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        unbinder = ButterKnife.bind(this);
        presenter = new FilterActivityPresenterImpl(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            iCategory = bundle.getString("category");
            txttitle.setText(iCategory);
            presenter.loadData("id", iCategory, Api.API_KEY);
        } else {
            Toast.makeText(this, "Bundle Null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.startShimmerAnimation();
        refresh.setOnRefreshListener(() -> {
            presenter.onRefresh();
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmerAnimation();
        });

        super.onResume();

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
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            refresh.setRefreshing(false);
        }
    }

    @Override
    public void errorData(String message) {
        Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSwipeRefresh() {
        presenter.loadData("id", iCategory, Api.API_KEY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.startShimmerAnimation();
        super.onPause();
    }
}
