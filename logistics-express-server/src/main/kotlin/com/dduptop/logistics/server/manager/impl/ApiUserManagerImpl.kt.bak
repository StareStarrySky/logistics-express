package com.dduptop.logistics.server.manager.impl

import com.dduptop.logistics.server.dao.ApiUserDao
import com.dduptop.logistics.server.entity.ApiUser
import com.dduptop.logistics.server.manager.ApiUserManager
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.data.jpa.BaseJpaManager
import com.zy.mylib.data.jpa.JpaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApiUserManagerImpl : ApiUserManager, BaseJpaManager<ApiUser, String>() {
    @Autowired
    lateinit var apiUserDao: ApiUserDao

    override fun getRepository(): JpaRepository<ApiUser, String> {
        return apiUserDao
    }

    @Transactional(readOnly = true)
    override fun findByAppId(appId: String): ApiUser {
        val list = apiUserDao.findByAppId(appId)
        if (list.isEmpty()) {
            throw BusException.builder().message("无效api用户").build()
        }
        return list[0]
    }

    @Transactional(rollbackFor = [RuntimeException::class])
    override fun update(apiUser: ApiUser): ApiUser {
        val old = apiUserDao.findById(apiUser.id)
        if (!old.isPresent) {
            throw BusException.builder().message("API用户不存在").build()
        }
        val list = apiUserDao.findByAppId(apiUser.appId)
        if (list.isNotEmpty()) {
            if (list[0].id != apiUser.id) {
                throw BusException.builder().message("当前API ID已存在").build()
            }
        }
        apiUser.enable = old.get().enable
        return save(apiUser)
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = [RuntimeException::class])
    override fun add(entity: ApiUser): ApiUser {
        val list: List<ApiUser> = apiUserDao.findByAppId(entity.appId)
        if (list.isNotEmpty()) {
            throw BusException.builder().message("当前API ID已存在").build()
        }
        entity.enable = true
        return save(entity)
    }

    @Transactional(rollbackFor = [RuntimeException::class])
    override fun enable(id: String): ApiUser {
        val opt = findById(id)
        if (!opt.isPresent) {
            throw BusException.builder().message("api user不存在").httpStatus(404).build()
        }
        opt.get().enable = true
        return save(opt.get())
    }

    @Transactional(rollbackFor = [RuntimeException::class])
    override fun disable(id: String): ApiUser {
        val opt = findById(id)
        if (!opt.isPresent) {
            throw BusException.builder().message("api user不存在").httpStatus(404).build()
        }
        opt.get().enable = false
        return save(opt.get())
    }
}
