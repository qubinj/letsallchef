package com.letsallchef.letsallchef.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.letsallchef.letsallchef.LacClient;
import com.letsallchef.letsallchef.R;
import com.letsallchef.letsallchef.models.recipelist.RecipeListItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesActivity extends AppCompatActivity {

    private ListView recipeListView;
    ArrayList<RecipeListItem> recipe;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recipeListView = (ListView) findViewById(R.id.recipesListView);
        i= getIntent();
        final String inIp = i.getExtras().getString("inIP");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://" + inIp + ":3000")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();
        final LacClient client = retrofit.create(LacClient.class);

        Call<ArrayList<RecipeListItem>> call=client.recipeList();
        call.enqueue(new Callback<ArrayList<RecipeListItem>>() {

            @Override
            public void onResponse(Call<ArrayList<RecipeListItem>> call, Response<ArrayList<RecipeListItem>> response) {
                recipe = response.body();
                RecipeListItemAdapter adapter = new RecipeListItemAdapter(getApplicationContext(),R.layout.row, recipe); //Needs Edit
                recipeListView.setAdapter(adapter);
                recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), RecipeviewActivity.class);
                        intent.putExtra("inIP", inIp);
                        intent.putExtra("recipeId",recipe.get(position).getId());
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeListItem>> call, Throwable t) {

            }
        });

    }
}
