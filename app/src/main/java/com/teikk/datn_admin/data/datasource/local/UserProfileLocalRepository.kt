package com.teikk.datn_admin.data.datasource.local

import com.teikk.datn_admin.data.model.UserProfile
import com.teikk.datn_admin.data.service.dao.UserProfileDao
import javax.inject.Inject

class UserProfileLocalRepository @Inject constructor(
    private val userProfileDao: UserProfileDao, // Assuming UserProfileDao is injected here
) {
    suspend fun getUserProfileByID(id: String) = userProfileDao.getUserByID(id)
    suspend fun insertUserProfile(userProfile: UserProfile) = userProfileDao.insert(userProfile)
    suspend fun updateUserProfile(userProfile: UserProfile) = userProfileDao.update(userProfile)
    suspend fun deleteUserProfile(userProfile: UserProfile) = userProfileDao.delete(userProfile)
    suspend fun deleteAllUser() = userProfileDao.deleteAllUsers()
}