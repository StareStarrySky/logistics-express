package com.dduptop.logistics.server.dao

import com.dduptop.logistics.server.entity.ApiUser
import com.zy.mylib.data.jpa.JpaRepository

interface ApiUserDao : JpaRepository<ApiUser, String> {
    fun findByAppId(appId: String): List<ApiUser>
}
