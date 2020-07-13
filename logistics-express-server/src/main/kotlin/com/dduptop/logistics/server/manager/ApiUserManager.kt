package com.dduptop.logistics.server.manager

import com.dduptop.logistics.server.entity.ApiUser
import com.zy.mylib.data.jpa.JpaManager

interface ApiUserManager : JpaManager<ApiUser, String> {
    fun findByAppId(appId: String): ApiUser

    override fun update(apiUser: ApiUser): ApiUser

    /**
     * 添加
     *
     * @param entity
     * @return
     */
    override fun add(entity: ApiUser): ApiUser

    /**
     * 启用
     * @param id
     * @return
     */
    fun enable(id: String): ApiUser

    /**
     * 停用
     * @param id
     * @return
     */
    fun disable(id: String): ApiUser
}
