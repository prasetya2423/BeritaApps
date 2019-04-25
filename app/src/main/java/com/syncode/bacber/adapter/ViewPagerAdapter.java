package com.syncode.bacber.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.syncode.bacber.R;
import com.syncode.bacber.model.Berita;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerAdapter extends PagerAdapter {


    @BindView(R.id.dateandtime)
    TextView dateandtime;
    @BindView(R.id.author)
    TextView author;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.title)
    TextView title;

    private List<Berita> list;

    public ViewPagerAdapter(List<Berita> list) {
        this.list = list;


    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.column_viewpager, container, false);
        ButterKnife.bind(this, view);
        Berita berita = list.get(position);
        dateandtime.setText(berita.getDateandtime());
        author.setText(berita.getAuthor());
        title.setText(berita.getTitle());
        description.setText(berita.getDescription().concat("..."));
        Picasso.get().load(berita.getUrlimage()).placeholder(R.drawable.bg_spinner).into(image);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
