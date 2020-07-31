package com.dduptop.logistics.server.rest

import com.dduptop.logistics.server.entity.ApiUser
import com.dduptop.logistics.server.manager.ApiUserManager
import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel
import com.zy.mylib.base.model.BaseModel.BaseView
import com.zy.mylib.data.jpa.JpaManager
import com.zy.mylib.data.jpa.PageUtils.Operate
import com.zy.mylib.mvc.logger.ApiLogger
import com.zy.mylib.security.LoginUser
import com.zy.mylib.webmvc.data.jpa.JpaEntityRestController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/user")
class ApiUserRest : JpaEntityRestController<ApiUser, String>() {
    @Autowired
    private lateinit var apiUserMananger: ApiUserManager

    override fun getManager(): JpaManager<ApiUser, String> {
        return apiUserMananger
    }

    override fun getPageOperate(entity: ApiUser?, operateMap: Map<String?, Operate?>?): Map<String?, Operate?>? {
        return operateMap
    }

    override fun getPageExtendParam(entity: ApiUser?, request: HttpServletRequest?, extendParams: Map<String?, Any?>?): Map<String?, Any?>? {
        return extendParams
    }

    /**
     * 新增API用户
     *
     * @param entity
     * @param loginUser
     * @return
     */
    @PostMapping
    @ApiLogger(request = "\${loginUser.userName}新增API用户[\${entity.userName}]", type = "API用户管理",
        success = ",id为\${returnValue.id}", error = "失败，\${exception.message}")
    @JsonView(BaseModel.DetailView::class)
    fun addApiUser(@RequestBody @Validated(BaseModel.AddCheck::class) entity: ApiUser, loginUser: LoginUser): ApiUser {
        return apiUserMananger.add(entity)
    }

    /**
     * 修改API用户
     *
     * @param entity
     * @param loginUser
     * @return
     */
    @PutMapping
    @ApiLogger(request = "\${loginUser.userName}新增API用户[\${entity.userName}]", type = "API用户管理",
        success = ",id为\${returnValue.id}", error = "失败，\${exception.message}")
    @JsonView(BaseModel.DetailView::class)
    fun updateApiUser(@RequestBody @Validated(BaseModel.AddCheck::class) entity: ApiUser, loginUser: LoginUser): ApiUser {
        return apiUserMananger.update(entity)
    }

    @ApiLogger(type = "API用户管理", request = "启用API用户id=\${id}", id = "\${id}")
    @PutMapping("/enable/{id}")
    @JsonView(BaseView::class)
    fun enable(@PathVariable("id") id: String): ApiUser {
        return apiUserMananger.enable(id)
    }

    @ApiLogger(type = "API用户管理", request = "禁用API用户id=\${id}", id = "\${id}")
    @PutMapping("/disable/{id}")
    @JsonView(BaseView::class)
    fun disable(@PathVariable("id") id: String): ApiUser {
        return apiUserMananger.disable(id)
    }

    @GetMapping("disabledBaseUpdate")
    override fun updateEntity(entity: ApiUser?): ApiUser? {
        return null
    }

    @GetMapping("/disableBaseAdd")
    override fun addEntity(@RequestBody @Validated(BaseModel.AddCheck::class) entity: ApiUser?): ApiUser? {
        return null
    }
}
