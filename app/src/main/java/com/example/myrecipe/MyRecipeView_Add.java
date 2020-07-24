package com.example.myrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class MyRecipeView_Add extends AppCompatActivity implements Serializable {

    private TextView tvMyTitleView, tvMypreparationMinutes, MyIngredientsText, tvnMyIngredientsView, MyInstructionstextView, tvMyDescriptionView;
    private ImageView ivMyImage;
    private Button remove_button;
    private CardView CardVIewMyViewRecipe;
    private RatingBar ratingBar;
    FoodData foodData = new FoodData();
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipe_view__add);
        tvMyTitleView = (TextView) findViewById(R.id.tvMyTitleView);
        tvMypreparationMinutes = (TextView) findViewById(R.id.tvMypreparationMinutes);
        MyIngredientsText = (TextView) findViewById(R.id.MyIngredientsText);
        tvnMyIngredientsView = (TextView) findViewById(R.id.tvnMyIngredientsView);
        MyInstructionstextView = (TextView) findViewById(R.id.MyInstructionstextView);
        tvMyDescriptionView = (TextView) findViewById((R.id.tvMyDescriptionView));
        ivMyImage = (ImageView) findViewById(R.id.ivMyImage);
        remove_button = (Button) findViewById(R.id.remove_button);
        CardVIewMyViewRecipe = (CardView) findViewById(R.id.CardVIewMyViewRecipe);
        ratingBar = findViewById(R.id.ratingBar);
        foodData = (FoodData) getIntent().getSerializableExtra("foodData");
        loadFoodData(foodData);
        i=findObject(foodData);

        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteFoodData(foodData);

            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                foodData.setRating(ratingBar.getRating());
                updateFoodData(foodData);

            }
        });
    }


    private int findObject(FoodData foodData){
        int index=0;
        for (int i=0;i<MainActivity.myFoodList.size();i++){
            if(MainActivity.myFoodList.get(i).getId().equals(foodData.getId())){
                index= i;
                break;
            }

        }
        return index;
    }

    private void updateFoodData(final FoodData foodData) {

        class UpdateRating extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .FoodDao()
                        .update(foodData);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }
        MainActivity.myFoodList.get(i).setRating(foodData.getRating());
        UpdateRating ut = new UpdateRating();
        ut.execute();
    }


    private void loadFoodData(FoodData foodData) {
        tvMyTitleView.setText(foodData.getItemName()+"");
        tvMypreparationMinutes.setText("Preparation time : "+foodData.getPreparationMinutes()+" min");
        tvnMyIngredientsView.setText(foodData.getIngredients());
        tvMyDescriptionView.setText(foodData.getInstructions());
        Picasso.get().load(foodData.getItemImage()).into(ivMyImage);
        if (foodData.getRating()!=0){
            ratingBar.setRating(foodData.getRating());
        }

    }

    private void deleteFoodData(final FoodData foodData) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .FoodDao()
                        .delete(foodData);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent i=new Intent(MyRecipeView_Add.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }
        Context context = getApplicationContext();
        CharSequence text = "You deleted the recipe";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        MainActivity.myFoodList.remove(i);
        DeleteTask dt = new DeleteTask();
        dt.execute();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {


//            Intent intent = new Intent(MyRecipeView_Add.this, MainActivity.class);
//            startActivity(intent);
//            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
