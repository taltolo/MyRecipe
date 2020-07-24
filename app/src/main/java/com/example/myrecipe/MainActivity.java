package com.example.myrecipe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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


public class MainActivity extends AppCompatActivity  implements Serializable ,ExitDialog.OnExitDialogListener{

    RecyclerView myRV;
    static List<FoodData> myFoodList = new ArrayList<>();
    FoodData myFoodData;
    public static String URL = "https://api.spoonacular.com/recipes/findByIngredients?apiKey=fb64e87a367141fbb89777e6459669b9&ingredients=";
    public static String API_KEY = "fb64e87a367141fbb89777e6459669b9";
    private RequestQueue mQueue;
    MyAdapter myAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bnt_search = findViewById((R.id.bnt_search));
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        myRV = (RecyclerView) findViewById((R.id.rv));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        myRV.setLayoutManager(gridLayoutManager);



        bnt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchRecipe.class);
                startActivity(intent);
                finish();
            }
        });

        getRecipe();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startService() {

        String textToService="Long Time no Cook, come and join us";
        Intent serviceIntent = new Intent(this, MyFoodService.class);
        serviceIntent.putExtra("textToService",textToService);
        //ContextCompat.startForegroundService(this, serviceIntent);
        startForegroundService(serviceIntent);
       // startService(serviceIntent);

    }
    public void stopService() {


        Intent serviceIntent = new Intent(this, MyFoodService.class);
        //  ContextCompat.startForegroundService(this, serviceIntent);
        stopService(serviceIntent);

    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {


            myRV.setAdapter(myAdapter);
        }
        return super.onKeyDown(keyCode, event);
    }


    private void getRecipe() {
        class GetRecipe extends AsyncTask<Void, Void, List<FoodData>> {

            @Override
            protected List<FoodData> doInBackground(Void... voids) {
                List<FoodData> foodDataList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .FoodDao()
                        .getAll();
                return foodDataList;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(List<FoodData> foodData) {
                super.onPostExecute(foodData);

//                if(ConnectToInternet)
                startService();


                MyAdapter myAdapter = new MyAdapter(MainActivity.this, foodData);
                myRV.setAdapter(myAdapter);
            }
        }

        GetRecipe gt = new GetRecipe();
        gt.execute();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.Exit:
                ExitDialog exitDialog=new ExitDialog();
                exitDialog.show((getSupportFragmentManager()),"Exitdialog");
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }


}

    @Override
    protected void onDestroy() {
        stopService();
        super.onDestroy();
    }

    @Override
    public void onExitpress() {
        finish();
        System.exit(0);
    }
}


