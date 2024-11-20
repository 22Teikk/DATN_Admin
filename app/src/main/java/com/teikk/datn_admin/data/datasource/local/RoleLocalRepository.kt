package com.teikk.datn_admin.data.datasource.local

import com.teikk.datn_admin.data.model.Role
import com.teikk.datn_admin.data.service.dao.RoleDao
import javax.inject.Inject

class RoleLocalRepository @Inject constructor(
    private val roleDao: RoleDao
) {
    suspend fun getAllRoles() = roleDao.getAllRole()
    suspend fun insertRoles(roles: List<Role>) = roleDao.insertRoles(roles)
    suspend fun deleteRole(role: Role) = roleDao.delete(role)
}