package com.teikk.datn_admin.data.datasource.service

import com.google.gson.JsonObject
import com.teikk.datn_admin.data.model.Category
import com.teikk.datn_admin.data.model.Image
import com.teikk.datn_admin.data.model.Order
import com.teikk.datn_admin.data.model.OrderItem
import com.teikk.datn_admin.data.model.PaymentMethod
import com.teikk.datn_admin.data.model.Product
import com.teikk.datn_admin.data.model.Role
import com.teikk.datn_admin.data.model.Store
import com.teikk.datn_admin.data.model.UserProfile
import com.teikk.datn_admin.data.model.Working
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
import retrofit2.http.Query

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

    // Images
    @Multipart
    @POST("api/v1/images") // Thay "your_endpoint_here" bằng đường dẫn API
    suspend fun uploadFiles(
        @Part("feedback_id") feedbackId: RequestBody?,
        @Part("product_id") productId: RequestBody?,
        @Part files: List<MultipartBody.Part>
    ): Response<List<Image>>
    // Images


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

    // Product
    @POST("api/v1/products")
    suspend fun createProduct(@HeaderMap adminHeaders: Map<String, String>, @Body product: Product) : Response<Product>
    @GET("api/v1/products")
    suspend fun getAllProducts(@HeaderMap adminHeaders: Map<String, String>): Response<List<Product>>
    @GET("api/v1/products/{id}")
    suspend fun getProductById(@HeaderMap adminHeaders: Map<String, String>, @Path("id") id: String): Response<Product>
    @PUT("api/v1/products/{id}")
    suspend fun updateProduct(@HeaderMap adminHeaders: Map<String, String>, @Path("id") id: String, @Body product: Product): Response<Product>
    // Product

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
    @GET("api/v1/user_profiles")
    suspend fun getAllUserProfiles(): Response<List<UserProfile>>
    @GET("api/v1/user_profiles/{id}")
    suspend fun getAllUserProfilesByID(
        @Path("id") id: String
    ): Response<UserProfile>
    // User Profile

    // ORDER
    @GET("api/v1/orders/status")
    suspend fun getAllOrdersByStatus(
        @Query("status") status: String
    ): Response<List<Order>>
    @GET("api/v1/orders/{id}")
    suspend fun getOrderByID(
        @Path("id") id: String
    ): Response<Order>
    @GET("api/v1/order_items/user")
    suspend fun getOrderItemForOrder(
        @Query("order_id") orderId: String
    ): Response<List<OrderItem>>
    @PUT("api/v1/orders/{id}")
    suspend fun updateOrder(@Path("id") id: String,@Body order: Order) : Response<Order>
    // ORDER

    // Working
    @GET("api/v1/working/type")
    suspend fun getAllWorkingByType(
        @Query("user_id") uid: String,
        @Query("type") type: String
    ): Response<List<Working>>
    @POST("api/v1/working")
    suspend fun createWorking(@Body working: Working) : Response<Working>
    // Working
}