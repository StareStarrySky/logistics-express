package com.dduptop.logistics.server.config.auth

//import com.dduptop.logistics.server.manager.ApiUserManager
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.casbin.EnforcerManager
import com.zy.mylib.security.casbin.ModelAndPolicy
import com.zy.mylib.security.casbin.StringAdapter
import com.zy.mylib.security.casbin.StringModel
import com.zy.mylib.utils.FileUtils
import org.casbin.jcasbin.main.Enforcer
//import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.IOException

@Component
@ConfigurationProperties(prefix = "mylib.casbin.enforcer")
class EnforcerManagerImpl : EnforcerManager<LoginUser> {
//    @Autowired
//    private lateinit var apiUserManager: ApiUserManager

    private var enableLog = false

    override fun getEnforcer(loginUser: LoginUser?): Enforcer {
        val modelRes = ClassPathResource("./authz/authz_model.conf")
        val policyRes = ClassPathResource("./authz/authz_policy.csv")
        try {
            val modelAndPolicy = ModelAndPolicy()
            modelAndPolicy.model = FileUtils.readAllText(modelRes.inputStream)
            modelAndPolicy.policy = FileUtils.readAllText(policyRes.inputStream)
            val enforcer = Enforcer(StringModel(modelAndPolicy.model), StringAdapter(modelAndPolicy.policy))
            enforcer.enableLog(enableLog)
//            if (loginUser != null) {
//                val optional = apiUserManager.findById(loginUser.userId)
//                if (optional.isPresent) {
//                    enforcer.addRoleForUser(optional.get().id, optional.get().system)
//                }
//            }
            return enforcer
        } catch (e: IOException) {
            e.printStackTrace()
            throw BusException.builder().message("读取authz配置失败").httpStatus(500).build()
        }
    }
}
