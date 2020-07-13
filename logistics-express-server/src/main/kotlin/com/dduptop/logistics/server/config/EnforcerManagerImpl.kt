package com.dduptop.logistics.server.config

import com.zy.mylib.base.exception.BusException
import com.zy.mylib.security.casbin.EnforcerManager
import com.zy.mylib.security.casbin.ModelAndPolicy
import com.zy.mylib.security.casbin.StringAdapter
import com.zy.mylib.security.casbin.StringModel
import com.zy.mylib.utils.FileUtils
import org.casbin.jcasbin.main.Enforcer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.IOException

@Component
@ConfigurationProperties(prefix = "mylib.casbin.enforcer")
class EnforcerManagerImpl : EnforcerManager<String> {
    private var enableLog = false

    override fun getEnforcer(role: String?): Enforcer {
        val modelRes = ClassPathResource("./authz/authz_model.conf")
        val policyRes = ClassPathResource("./authz/authz_policy.csv")
        try {
            val modelAndPolicy = ModelAndPolicy()
            modelAndPolicy.model = FileUtils.readAllText(modelRes.inputStream)
            modelAndPolicy.policy = FileUtils.readAllText(policyRes.inputStream)
            val enforcer = Enforcer(StringModel(modelAndPolicy.model), StringAdapter(modelAndPolicy.policy))
            enforcer.enableLog(enableLog)
            role ?: enforcer.addRoleForUser(role, role)
            return enforcer
        } catch (e: IOException) {
            e.printStackTrace()
            throw BusException.builder().message("读取authz配置失败").httpStatus(500).build()
        }
    }
}
