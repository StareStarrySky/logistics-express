package com.dduptop.logistics.server.rest

//import com.dduptop.logistics.server.manager.ApiUserManager
import com.dduptop.logistics.server.util.SignUtils
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import com.zy.mylib.utils.StringUtils
import com.zy.mylib.webmvc.base.BaseRest
import com.zy.mylib.webmvc.model.RestMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.data.redis.core.RedisOperations
import org.springframework.web.bind.annotation.*
import java.time.Instant

@RestController
@RequestMapping("/auth")
@ConfigurationProperties(prefix = "ems.auth")
class AuthRest : BaseRest() {
    @Autowired
    lateinit var redisOperations: RedisOperations<String, String>

//    @Autowired
//    lateinit var apiUserManager: ApiUserManager

    @Autowired
    lateinit var passport: Passport<LoginUser>

    final lateinit var appIds: List<String>
    final lateinit var secrets: List<String>
    final lateinit var enables: List<Boolean>
    init {
        val isEmpty = appIds.isEmpty() || secrets.isEmpty() || enables.isEmpty()
        val notEqualNumber = appIds.size != secrets.size && appIds.size != enables.size

        if (isEmpty || notEqualNumber) {
            throw BusException.builder().build()
        }
    }

    @PostMapping("/login")
    fun login(@RequestParam("now") timestamp: Long, @RequestParam("signNonce") signNonce: String,
                   @RequestParam("sign") sign: String, @RequestParam("appId") appId: String,
                   @RequestBody loginUser: LoginUser): RestMessage {
        validUser(appId, timestamp, signNonce, loginUser, sign)
        val token = passport.login(loginUser)
        return RestMessage(token, "登录成功")
    }

    private fun validUser(appId: String, timestamp: Long, signNonce: String, loginUser: LoginUser, sign: String) {
//        val apiUser = apiUserManager.findByAppId(appId)
        val apiUserIndex = appIds.indexOf(appId)
        if (!enables[apiUserIndex]) {
            throw BusException.builder().message("appId无效").code("403").build()
        }
        val now = Instant.now().epochSecond
        if (now - 60 > timestamp || now + 60 < timestamp) {
            throw BusException.builder().message("请求已失效$now").code("403").build()
        }
        // 如果随机串已存在则拒绝请求
        if (redisOperations.hasKey(signNonce)!!) {
            throw BusException.builder().message("请求已失效").code("403").build()
        }
        // 验证是否通过
        val signContent = getContent(appId, timestamp, signNonce, loginUser)
        val pass = SignUtils.verifySign(sign, signContent, secrets[apiUserIndex])
        if (!pass) {
            throw BusException.builder().message("鉴权失败！").code("403").build()
        }
    }

    private fun getContent(appId: String, timestamp: Long, signNonce: String, loginUser: LoginUser): String {
        val content = StringBuilder(1024)
        content.append(appId)
            .append(timestamp)
            .append(signNonce)
            .append(loginUser.telephone)
            .append(loginUser.userId)
            .append(loginUser.userName)
            .append(StringUtils.join(loginUser.roles.toTypedArray(), "", 0))
            .append(loginUser.type)
        return content.toString()
    }
}
