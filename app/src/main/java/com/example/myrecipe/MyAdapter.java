package com.example.myrecipe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.myrecipe.R.layout.row_recipe;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FoodViewHolder> {

    private Context mcontext;
    private List<FoodData> mFoodList;

    public MyAdapter(Context mcontext, List<FoodData> mFoodList) {
        this.mcontext = mcontext;
        this.mFoodList = mFoodList;
        MainActivity.myFoodList=mFoodList;
    }


    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe,parent,false);
        return new FoodViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
       FoodData food=mFoodList.get(position);
        holder.title.setText(mFoodList.get(position).getItemName());
        holder.description.setText((mFoodList.get(position).getItemDescription()));
        if(!food.getItemImage().isEmpty()){
        Picasso.get().load(food.getItemImage()).into(holder.imageView);}
        holder.tvpreparationMinutes3.setText("Preparation time: "+mFoodList.get(position).getPreparationMinutes());
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }


class FoodViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView title;
    TextView description;
    TextView tvpreparationMinutes3;
    CardView cardView;

    public FoodViewHolder(View itemView) {
        super(itemView);
        tvpreparationMinutes3=(TextView)itemView.findViewById(R.id.tvpreparationMinutes3);
        imageView=(ImageView)itemView.findViewById(R.id.ivImage);
        title=(TextView)itemView.findViewById(R.id.tvTitle);
        description=(TextView)itemView.findViewById(R.id.tvDescription);
        cardView=(CardView)itemView.findViewById(R.id.myCardView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(mcontext, MyRecipeView_Add.class);
                FoodData foodData=mFoodList.get(getAdapterPosition());
                intent.putExtra("foodData",foodData);
                mcontext.startActivity(intent);


            }
        });

         }


}
}