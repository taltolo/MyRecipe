package com.example.myrecipe;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchRecipe extends AppCompatActivity implements Serializable {
    private RequestQueue mQueue;
    public List<FoodData> mySearchFoodList;
    FoodData myFoodData;
    RecyclerView myRecyclerSearch;
    private String instructions="" ;
     String ingredientsSearch="";
    public static final String MYTAG = "MYTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_recipe);
        final EditText editText_keySearch=(EditText)findViewById(R.id.editText_keySearch);
        myRecyclerSearch =(RecyclerView)findViewById((R.id.rvRecipe));

        GridLayoutManager gridLayoutManager=new GridLayoutManager(SearchRecipe.this,1);
        myRecyclerSearch.setLayoutManager(gridLayoutManager);

        mySearchFoodList =new ArrayList<>();


        findViewById(R.id.bnt_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName=editText_keySearch.getText().toString();
                if(foodName.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please type in any dish you desire so we can help you find the best recipe";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                Search_Recipe(foodName);
                }

            }
        });
    }

    public void onClickAdd(final FoodData foodData){
        if (!RecipeView.ingredients.equals("")){
            foodData.setIngredients(RecipeView.ingredients);

        }
        else{
            getRecipeIngredients(foodData);

        }
        MainActivity.myFoodList.add(foodData);
        addRecipe(foodData );
    }

    private void addRecipe(final FoodData foodData) {


        class SaveRecipe extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {


                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .FoodDao()
                        .insert(foodData);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }

        SaveRecipe st = new SaveRecipe();
        st.execute();
        Toast.makeText(this, "You add a new RECIPE!", Toast.LENGTH_SHORT).show();


    }


    void Search_Recipe( final String foodName){
        mySearchFoodList.clear();

            mQueue= Volley.newRequestQueue(this);
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, "https://api.spoonacular.com/recipes/complexSearch?apiKey=9a330cc68f824713aa0f70cc80e2dd5f&query="+foodName+"&addRecipeInformation=true&instructionsRequired=true", null
                    , new com.android.volley.Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        JSONArray jsonArray=response.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject recipes = jsonArray.getJSONObject(i);
                            JSONArray analyzedInstructions=recipes.getJSONArray("analyzedInstructions");

                            for(int j=0;j<analyzedInstructions.length();j++){
                                JSONObject STEPS=analyzedInstructions.getJSONObject(j);
                                JSONArray STEPSANDSTEP=STEPS.getJSONArray("steps");


                                for(int k=0;k<STEPSANDSTEP.length();k++){
                                JSONObject STEP=STEPSANDSTEP.getJSONObject(k);

                                    String descriptionSteps=STEP.getString("step");
                                    SearchRecipe.this.instructions = SearchRecipe.this.instructions +descriptionSteps+" ";

                                }

                            }

                            String title = recipes.getString("title");
                            String description = recipes.getString("summary");
                            description=cleanString(description);
                            description=description.replaceAll("<b>","");
                            description=description.replaceAll("</b>","");

//                            description.split(">");
                            int index= description.indexOf(".");
                            description = description.substring(0,index+1);
                            instructions=instructions+"\n";
                            String idNumber=recipes.getString("id");

                            String image=recipes.getString("image");
                            String PreparationMinutes=recipes.getString("readyInMinutes");

                            myFoodData= new FoodData(title, description,image,PreparationMinutes,idNumber,instructions);
                            mySearchFoodList.add(myFoodData);
                            SearchRecipe.this.instructions ="";
                        }

                            SearchRecipeAdapter searchRecipeAdapter= new SearchRecipeAdapter(SearchRecipe.this, mySearchFoodList);
                            myRecyclerSearch.setAdapter(searchRecipeAdapter);



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
    @RequiresApi(api = Build.VERSION_CODES.O)
    String cleanString(String summary) {

        String temp = "";
        String array[] = summary.split("");
        for (int i = 1; i < summary.length(); i++) {

            if (array[i].equals("<")) {

              //  array[i].split("<");
                array[i].replace("<","");
            }
            if (array[i].equals(">")) {

               // array[i].split(">");
                array[i].replace(">","");

            }
            if (array[i].equals("/")) {

                array[i].split("/");
            }

        }


        return String.join(temp, array);
    }


    public void getRecipeIngredients( final FoodData foodData){

        {
            ingredientsSearch="";
            mQueue= Volley.newRequestQueue(this);
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, "https://api.spoonacular.com/recipes/"+ foodData.getId() +"/ingredientWidget.json?apiKey=9a330cc68f824713aa0f70cc80e2dd5f", null
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
                            ingredientsSearch=ingredientsSearch+ingredientsTemp+" "+value+" "+unit+"\n";

                        }
                        foodData.setIngredients(ingredientsSearch);

                        Log.i(MYTAG, "********************************************************************print from recipe search id : "+foodData.getIngredients());
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


            Intent intent = new Intent(SearchRecipe.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }









    }







