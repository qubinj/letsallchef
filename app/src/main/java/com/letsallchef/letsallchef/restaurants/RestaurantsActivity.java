package com.letsallchef.letsallchef.restaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.letsallchef.letsallchef.LacClient;
import com.letsallchef.letsallchef.R;
import com.letsallchef.letsallchef.models.restaurantlist.RestaurantListItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantsActivity extends AppCompatActivity {

    private ListView restaurantsListView;
    ArrayList<RestaurantListItem> restaurant;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        restaurantsListView = (ListView) findViewById(R.id.restaurantsListView);

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

        Call<ArrayList<RestaurantListItem>> call=client.restaurantList();
        call.enqueue(new Callback<ArrayList<RestaurantListItem>>() {

            @Override
            public void onResponse(Call<ArrayList<RestaurantListItem>> call, Response<ArrayList<RestaurantListItem>> response) {
                restaurant = response.body();
                RestaurantItemAdapter adapter = new RestaurantItemAdapter(getApplicationContext(),R.layout.row, restaurant); //Needs Edit
                restaurantsListView.setAdapter(adapter);
                restaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), RecipeviewActivity.class);
                        intent.putExtra("inIP", inIp);
                        intent.putExtra("restaurantId",restaurant.get(position).getId());
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<RestaurantListItem>> call, Throwable t) {

            }
        });

    }

}
