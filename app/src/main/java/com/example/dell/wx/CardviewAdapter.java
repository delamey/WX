package com.example.dell.wx;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardviewAdapter extends RecyclerView.Adapter <CardviewAdapter.ViewHolder>{

    private Context mContext;
    private List<Fruit> mfruitList;
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view2)
        ImageView imageView2;
        @BindView(R.id.fruit_name2)
        TextView fruitName2;
        CardView cardView;

       public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positon=holder.getAdapterPosition();
                Fruit fruit=mfruitList.get(positon);
                Intent intent=new Intent(mContext,detail_item_activity.class);
                intent.putExtra("name",fruit.getName());
                intent.putExtra("image",fruit.getImage());
                mContext.startActivity(intent);
            }
        });return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit=mfruitList.get(position);
        holder.fruitName2.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImage()).into(holder.imageView2);
    }




    @Override
    public int getItemCount() {
        return mfruitList.size();
    }

    public CardviewAdapter(List<Fruit> mfruitList) {
        this.mfruitList = mfruitList;
    }
}
