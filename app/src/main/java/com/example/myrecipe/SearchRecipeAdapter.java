package com.example.myrecipe;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeAdapter.FoodSearchViewHolder> {
    private static SearchRecipe searchRecipe;
    public static final String MYTAG = "MYTAG";
     Context mcontext;
    private List<FoodData> mySearchFoodList;

    public SearchRecipeAdapter(Context mcontext, List<FoodData> mySearchFoodList) {
        searchRecipe= (SearchRecipe) mcontext;
        this.mcontext = mcontext;
        this.mySearchFoodList = mySearchFoodList;
    }


    @Override
    public SearchRecipeAdapter.FoodSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row,parent,false);
        return new FoodSearchViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodSearchViewHolder holder, final int position) {
        FoodData food= mySearchFoodList.get(position);
        holder.title.setText(mySearchFoodList.get(position).getItemName());
        holder.preparationMinutes.setText("Preparation time: "+mySearchFoodList.get(position).getPreparationMinutes()+" min");
        holder.description.setText((mySearchFoodList.get(position).getItemDescription()));
        if(!food.getItemImage().isEmpty()){
            Picasso.get().load(food.getItemImage()).into(holder.imageView);}




    }

    @Override
    public int getItemCount() {
        return mySearchFoodList.size();
    }



class FoodSearchViewHolder extends RecyclerView.ViewHolder {

    private static final String MYTAG ="MYTAG" ;
    ImageView imageView;
    TextView title;
    TextView description;
    TextView preparationMinutes;
    Button add;
    CardView cardView;

    public FoodSearchViewHolder(View itemView) {
        super(itemView);
        preparationMinutes=(TextView)itemView.findViewById(R.id.tvpreparationMinutes);
        imageView=(ImageView)itemView.findViewById(R.id.ivImage);
        title=(TextView)itemView.findViewById(R.id.tvTitle);
        description=(TextView)itemView.findViewById(R.id.tvDescription);
        cardView=(CardView)itemView.findViewById(R.id.myCardViewSearch);
        add=(Button)itemView.findViewById(R.id.button_add);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(mcontext, RecipeView.class);
                FoodData foodData=mySearchFoodList.get(getAdapterPosition());
                intent.putExtra("foodData",foodData);
                mcontext.startActivity(intent);

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodData foodData=mySearchFoodList.get(getAdapterPosition());
                searchRecipe.onClickAdd(foodData);
            }
        });
    }


}



}
