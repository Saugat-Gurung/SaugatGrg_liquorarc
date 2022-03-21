package com.example.saugatgrg_liquorarc.api;



import com.example.saugatgrg_liquorarc.api.responses.AddressResponse;
import com.example.saugatgrg_liquorarc.api.responses.AllProductResponse;
import com.example.saugatgrg_liquorarc.api.responses.CategoryResponse;
import com.example.saugatgrg_liquorarc.api.responses.LoginResponse;
import com.example.saugatgrg_liquorarc.api.responses.OrderHistoryResponse;
import com.example.saugatgrg_liquorarc.api.responses.RegisterResponse;
import com.example.saugatgrg_liquorarc.api.responses.SingleProductResponse;
import com.example.saugatgrg_liquorarc.api.responses.SliderResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("ecommerce/api/v1/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("ecommerce/api/v1/register")
    Call<RegisterResponse> register(@Field("name") String names, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("ecommerce/api/v1/order")
    Call<RegisterResponse> order(@Header("api_key") String apikey,
                                 @Field("p_type") int p_type,
                                 @Field("address_id") int address_id,
                                 @Field("payment_refrence") String paymentRefrence);

    @FormUrlEncoded
    @POST("ecommerce/api/v1/cart")
    Call<RegisterResponse> addToCart(@Header("api_key") String apikey, @Field("p_id") int p, @Field("quantity") int q);


    @GET("ecommerce/api/v1/order")
    Call<OrderHistoryResponse> orderHistory(@Header("api_key") String apikey
    );

    @GET("ecommerce/api/v1/address")
    Call<AddressResponse> getMyAddresses(@Header("api_key") String apikey);

    @FormUrlEncoded
    @POST("ecommerce/api/v1/address")
    Call<RegisterResponse> addAddress(
            @Header("Apikey") String apikey,
            @Field("city") String city,
            @Field("street") String street,
            @Field("province") String province,
            @Field("description") String description);


    @GET("ecommerce/api/v1/get-all-products")
    Call<AllProductResponse> getAllProducts();


    @GET("ecommerce/api/v1/get-categories")
    Call<CategoryResponse> getCategories();

    @GET("ecommerce/api/v1/get-categories")
    Call<CategoryResponse> getAllCategories();

    @GET("ecommerce/api/v1/slider")
    Call<SliderResponse> getSliders();

    @GET("ecommerce/api/v1/get-products-by-category")
    Call<AllProductResponse> getProductsByCategory(@Query("c_id") int c_id);

    @GET("ecommerce/api/v1/cart")
    Call<AllProductResponse> getMyCart(@Header("api_key") String apikey);

    @DELETE("ecommerce/api/v1/cart")
    Call<RegisterResponse> deleteFromCart(@Header("api_key") String apikey, @Query("c_id") int cartID);

    @GET("ecommerce/api/v1/get-all-products")
    Call<SingleProductResponse> getProductById(@Query("id") int c_id);
}
