package com.example.myrecipe;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeView extends AppCompatActivity {
    private TextView tvTitleView,tvpreparationMinutes2,tvnIngredientsView,tvDescriptionView,IngredientsText,InstructionstextView;
    private ImageView ivImage;
    private RequestQueue mQueue;
    static String ingredients="",description="";
    private CardView cardView;
    public static final String MYTAG = "MYTAG";
    FoodData foodData=new FoodData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        IngredientsText=(TextView)findViewById(R.id.IngredientsText);
        InstructionstextView=(TextView)findViewById(R.id.InstructionstextView);
         ivImage=(ImageView)findViewById(R.id.ivImage);
         tvDescriptionView=(TextView)findViewById(R.id.tvDescriptionView);
         tvnIngredientsView=(TextView)findViewById(R.id.tvnIngredientsView);
         tvpreparationMinutes2=(TextView)findViewById((R.id.tvpreparationMinutes2));
         tvTitleView=(TextView)findViewById(R.id.tvTitleView);
        cardView=(CardView)findViewById(R.id.CardVIewViewRecipe);
         foodData = (FoodData) getIntent().getSerializableExtra("foodData");
      //  getStepsRecipe(foodData.getId());
        getRecipeIngredients(foodData.getId());



    }



    protected void onActivityResult(int restCode, int resultCode, Intent Data){
        super.onActivityResult(restCode,resultCode,Data);
    }

    private void loadFoodData(FoodData foodData) {
        tvTitleView.setText(foodData.getItemName()+"");
        tvpreparationMinutes2.setText("Preparation time : "+foodData.getPreparationMinutes()+" min");
        tvnIngredientsView.setText(foodData.getIngredients());
        tvDescriptionView.setText(foodData.getInstructions());
        Picasso.get().load(foodData.getItemImage()).into(ivImage);

    }



public void getRecipeIngredients(String idrecipe){

    {
        ingredients="";
        mQueue= Volley.newRequestQueue(this);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, "https://api.spoonacular.com/recipes/"+ idrecipe +"/ingredientWidget.json?apiKey=9a330cc68f824713aa0f70cc80e2dd5f", null
                , new com.android.volley.Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray=response.getJSONArray("ingredients");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject recipes = jsonArray.getJSONObject(i);
                        JSONObject amount=recipes.getJSONObject("amount");
                        JSONObject metric=amount.getJSONObject("metric");
                        String ingredientsTemp=recipes.getString("name");
                        String value=metric.getString("value");
                        String unit=metric.getString("unit");
                        ingredients=ingredients+ingredientsTemp+" "+value+" "+unit+"\n";

                    }
                    foodData.setIngredients(ingredients);
                    loadFoodData(foodData);


                    Log.i(MYTAG, "********************************************************************print from recipe view id : "+foodData.getIngredients());
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {



            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }



}





