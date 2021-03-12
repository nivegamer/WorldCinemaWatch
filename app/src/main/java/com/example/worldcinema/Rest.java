package com.example.worldcinema;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface Rest {
    //auth
//    @Headers("Content-Type: application/json")
    @POST("/auth/register")
    public Call<String> createUser(@Body User body);

    @POST("auth/login")
    public Call<JsonObject> getToken(@Header ("Authorization: Bearer ") String token, @Body JsonObject body);

    @GET("user/auth/{email}")
    public Call<User> getStatus(@Path("username") String username);

    //User
    @GET("user")
    Call<JsonArray> getUser(@Header ("Authorization: Bearer ") String token, @Path("id") int id);

    @PUT("user/{username}")
    Call<JsonArray> putUser(@Path("username") String username, @Body JsonArray body);

}
