package com.teikk.datn_admin.data.datasource.repository

import com.teikk.datn_admin.data.datasource.service.ApiService
import com.teikk.datn_admin.data.model.Image
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class UploadFileRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun uploadFile(file: File) : String? {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        val response = apiService.uploadFile(body)
        if (response.isSuccessful) {
            return response.body()?.get("data")?.asString
            // Handle successful upload
            println("File uploaded successfully: ${response.body()}")
        } else {
            // Handle failure
            println("File upload failed: ${response.message()}")
            return null
        }
    }

    suspend fun uploadImages(feedbackId: String?, productId: String?, files: List<File>) : Response<List<Image>> {
        val feedbackIdBody = feedbackId?.toRequestBody("text/plain".toMediaTypeOrNull())
        val productIdBody = productId?.toRequestBody("text/plain".toMediaTypeOrNull())

        val multipartFiles = files.map { file ->
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files", file.name, requestFile)
        }

        // Gửi yêu cầu lên server
        return apiService.uploadFiles(feedbackIdBody, productIdBody, multipartFiles)
    }
}