package com.syncode.bacber.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.syncode.bacber.R;
import com.syncode.bacber.model.Berita;
import com.syncode.bacber.view.SingleViewBerita;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private List<Berita> beritaList;

    private Context context;

    public RecycleViewAdapter(List<Berita> beritaList, Context context) {
        this.beritaList = beritaList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_berita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Berita berita = beritaList.get(position);
        holder.title.setText(berita.getTitle());
        holder.description.setText(berita.getDescription().concat("..."));
        holder.author.setText(berita.getAuthor());
        holder.dateandtime.setText(berita.getDateandtime());
        Picasso.get().load(berita.getUrlimage()).placeholder(R.drawable.placeholder).resize(300, 290).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return beritaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView description;

        @BindView(R.id.dateandtime)
        TextView dateandtime;

        @BindView(R.id.image)
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), SingleViewBerita.class);
                intent.putExtra("image", beritaList.get(getAdapterPosition()).getUrlimage());
                intent.putExtra("dateandtime", beritaList.get(getAdapterPosition()).getDateandtime());
                intent.putExtra("author", beritaList.get(getAdapterPosition()).getAuthor());
                intent.putExtra("title", beritaList.get(getAdapterPosition()).getTitle());
                intent.putExtra("content", beritaList.get(getAdapterPosition()).getContent());
                context.startActivity(intent);
            });
        }
    }

}
