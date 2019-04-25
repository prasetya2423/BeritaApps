package com.syncode.bacber.view;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.syncode.bacber.R;

import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleViewBerita extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.content)
    TextView content;


    @BindView(R.id.dateandtime)
    TextView dateandtime;

    @BindView(R.id.author)
    TextView author;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String iImage;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_view_berita);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            iImage = bundle.getString("image");
            String iDateandtime = bundle.getString("dateandtime");
            String iAuthor = bundle.getString("author");
            String iTitle = bundle.getString("title");
            String iContent = bundle.getString("content");
            title.setText(iTitle);
            Picasso.get().load(iImage).into(image);
            dateandtime.setText(iDateandtime);
            content.setText(iContent);
            author.setText(iAuthor);
        } else {
            Toast.makeText(getApplicationContext(), "Bundle null", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.image)
    public void showFullPhoto() {
        showDisplayPhoto(iImage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showDisplayPhoto(String photo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.photopreview, null);
        ImageView imageView = v.findViewById(R.id.image);
        Picasso.get().load(photo).placeholder(R.drawable.placeholder).into(imageView);
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
