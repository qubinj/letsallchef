package com.letsallchef.letsallchef;

import com.letsallchef.letsallchef.models.grocery.*;
import com.letsallchef.letsallchef.models.recipe.*;
import com.letsallchef.letsallchef.models.recipelist.*;
import com.letsallchef.letsallchef.models.restaurantlist.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by qubin on 22/4/17.
 */
public interface LacClient {
    String ENDPOINT = "https://api.myjson.com";
    @GET("/groceries")
    Call<ArrayList<GroceryItem>> groceryList();

    @GET("/groceries/{groceryId}")
    Call<GroceryItem> groceryItem(@Path("groceryId") String groceryId);

    @GET("/recipes")
    Call<ArrayList<RecipeListItem>> recipeList();

    @GET("/recipes/{recipeId}")
    Call<RecipeItem> recipeItem(@Path("recipeId") String recipeId);

    @GET("/restaurants")
    Call<ArrayList<RestaurantListItem>> restaurantList();

    @GET("/restaurants/{restaurantId}")
    Call<GroceryItem> restaurantItem(@Path("restaurantId") String restaurantId);

//    @GET("")
//    Call<ArrayList<RecipeItem>> commentList();
}
