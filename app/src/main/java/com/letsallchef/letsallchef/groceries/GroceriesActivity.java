package com.letsallchef.letsallchef.groceries;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.letsallchef.letsallchef.LacClient;
import com.letsallchef.letsallchef.R;
import com.letsallchef.letsallchef.models.grocery.GroceryItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroceriesActivity extends AppCompatActivity {

    private ListView groceryListView;
    ArrayList<GroceryItem> groceries;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);

        groceryListView = (ListView) findViewById(R.id.groceriesListView);

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

        Call<ArrayList<GroceryItem>> call=client.groceryList();
        call.enqueue(new Callback<ArrayList<GroceryItem>>() {

            @Override
            public void onResponse(Call<ArrayList<GroceryItem>> call, Response<ArrayList<GroceryItem>> response) {
                groceries = response.body();
                GroceryItemAdapter adapter = new GroceryItemAdapter(getApplicationContext(),R.layout.row,groceries);
                groceryListView.setAdapter(adapter);
                groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), RecipeviewActivity.class);
                        intent.putExtra("inIP", inIp);
                        intent.putExtra("groceriesId",groceries.get(position).getId());
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<GroceryItem>> call, Throwable t) {

            }
        });
    }
}
