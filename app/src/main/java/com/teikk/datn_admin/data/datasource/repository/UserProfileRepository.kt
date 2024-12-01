package com.teikk.datn_admin.data.datasource.repository

import com.teikk.datn_admin.data.datasource.local.UserProfileLocalRepository
import com.teikk.datn_admin.data.datasource.remote.UserProfileRemoteRepository
import com.teikk.datn_admin.data.model.UserProfile
import javax.inject.Inject

class UserProfileRepository @Inject constructor(
    private val userProfileRemoteRepository: UserProfileRemoteRepository,
    private val userProfileLocalRepository: UserProfileLocalRepository,
) {
    suspend fun insertUserProfile(userProfile: UserProfile) = userProfileLocalRepository.insertUserProfile(userProfile)
    suspend  fun saveUserToLocal(userProfile: UserProfile) = userProfileLocalRepository.updateUserProfile(userProfile)

    suspend fun saveUserToRemote(userProfile: UserProfile) = userProfileRemoteRepository.updateUserProfile(userProfile)
    suspend fun getUserProfile(uid: String) = userProfileRemoteRepository.getUserProfile(uid)
    suspend fun deleteAllUser() = userProfileLocalRepository.deleteAllUser()
}