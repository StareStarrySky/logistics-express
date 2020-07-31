package com.dduptop.logistics.server.entity

import com.fasterxml.jackson.annotation.JsonView
import com.zy.mylib.base.model.BaseModel
import com.zy.mylib.data.jpa.HistoryEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class ApiUser : HistoryEntity() {
    companion object {
        private const val SERIAL_VERSION_UID = 4146363551751313851L
    }

    /**
     * APPID
     */
    @Column(length = 64)
    @JsonView(BaseModel.BaseView::class)
    lateinit var appId: String

    /**
     * 密钥
     */
    @Column(length = 64)
    @JsonView(BaseModel.BaseView::class)
    lateinit var secret: String

    /**
     * 启用
     */
    @Column
    @JsonView(BaseModel.BaseView::class)
    var enable: Boolean? = null
}
