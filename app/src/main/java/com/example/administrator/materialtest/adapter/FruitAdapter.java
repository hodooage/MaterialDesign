package com.example.administrator.materialtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.materialtest.R;
import com.example.administrator.materialtest.activity.FruitActivity;
import com.example.administrator.materialtest.entity.Fruit;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context context;
    private List<Fruit> fruits;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.fruit_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        //touch事件
        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    v.setElevation(20);
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    v.setElevation(4);
                    int position=holder.getAdapterPosition();
                    Fruit fruit=fruits.get(position);
                    Intent intent=new Intent(context, FruitActivity.class);
                    intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                    intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                    context.startActivity(intent);
                }
                return true;
            }
        });


        //点击事件
        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                v.setElevation(20);
                *//*int position=holder.getAdapterPosition();
                Fruit fruit=fruits.get(position);
                Intent intent=new Intent(context, FruitActivity.class);
                intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
                context.startActivity(intent);*//*
            }
        });*/

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit=fruits.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView;
            fruitImage= (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName= (TextView) itemView.findViewById(R.id.fruit_name);

        }
    }

    public FruitAdapter(List<Fruit> fruits){
        this.fruits=fruits;
    }
}
