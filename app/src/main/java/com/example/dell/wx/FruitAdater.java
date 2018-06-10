package com.example.dell.wx;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FruitAdater extends RecyclerView.Adapter<FruitAdater.ViewHolder> {


    @BindView(R.id.fruit_image)
    ImageView fruitImage;
    @BindView(R.id.fruit_name)
    TextView fruitName;
    private List<Fruit> mFruitList;




    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fruit_image)
        ImageView fruitImage;
        @BindView(R.id.fruit_name)
        TextView fruitName;
        View fruitView;

        ViewHolder(View itemView) {
            super(itemView);
            fruitView = itemView;
            ButterKnife.bind(this, itemView);
        }

    }

    FruitAdater(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=viewHolder.getAdapterPosition();
                Fruit fruit=mFruitList.get(position);
                Toast.makeText(view.getContext(),"you,clicked view"+fruit.getName(),Toast.LENGTH_LONG).show();
            }
        });
        return viewHolder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImage());
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
