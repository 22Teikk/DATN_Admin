package com.teikk.datn_admin.data.datasource.service

import com.google.gson.JsonObject
import com.teikk.datn_admin.data.model.Category
import com.teikk.datn_admin.data.model.PaymentMethod
import com.teikk.datn_admin.data.model.Role
import com.teikk.datn_admin.data.model.Store
import com.teikk.datn_admin.data.model.UserProfile
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("api/v1/user_profiles/admin_login")
    suspend fun login(@Body userProfile: UserProfile) : Response<JsonObject>

    @POST("api/v1/user_profiles/register")
    suspend fun register(@Body userProfile: UserProfile) : Response<JsonObject>

    @Multipart
    @POST("/upload")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
    ): Response<JsonObject>

    @GET("api/v1/roles")
    suspend fun getAllRoles(@HeaderMap header: Map<String, String>) : Response<List<Role>>

    @GET("api/v1/payment_methods")
    suspend fun getAllPaymentMethods(@HeaderMap adminHeaders: Map<String, String>): Response<List<PaymentMethod>>

    // Category
    @GET("api/v1/categories")
    suspend fun getAllCategories(@HeaderMap adminHeaders: Map<String, String>): Response<List<Category>>
    @GET("api/v1/categories/{id}")
    suspend fun getCategoryById(@HeaderMap adminHeaders: Map<String, String>, @Path("id") id: String): Response<Category>
    @POST("api/v1/categories")
    suspend fun createCategory(@HeaderMap adminHeaders: Map<String, String>, @Body category: Category): Response<Category>
    @PUT("api/v1/categories/{id}")
    suspend fun updateCategory(@HeaderMap adminHeaders: Map<String, String>, @Path("id") id: String, @Body category: Category): Response<Category>
    @DELETE("api/v1/categories/{id}")
    suspend fun deleteCategory(@HeaderMap adminHeaders: Map<String, String>, @Path("id") id: String) : Response<Void>
    // Category

    // Store
    @GET("api/v1/stores/{id}")
    suspend fun getStoresById(@HeaderMap adminHeaders: Map<String, String>, @Path("id") id : String): Response<List<Store>>
    @POST("api/v1/stores")
    suspend fun createStore(@HeaderMap adminHeaders: Map<String, String>, @Body store: Store): Response<Store>
    @PUT("api/v1/stores/{id}")
    suspend fun updateStore(@HeaderMap adminHeaders: Map<String, String>, @Path("id") id: String, @Body store: Store): Response<Store>
    // Store

    // User Profile
    @PUT("api/v1/user_profiles/{id}")
    suspend fun updateUserProfile(@HeaderMap adminHeaders: Map<String, String> ,@Path("id") id: String,@Body userProfile: UserProfile) : Response<UserProfile>
    // User Profile
}