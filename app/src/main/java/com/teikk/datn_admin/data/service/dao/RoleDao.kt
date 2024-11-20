package com.teikk.datn_admin.data.service.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teikk.datn_admin.data.model.Role

@Dao
interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoles(roles: List<Role>)

    @Delete
    fun delete(role: Role)

    @Query("SELECT * FROM role_tables")
    fun getAllRole(): List<Role>

}