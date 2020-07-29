package com.dduptop.logistics.server.model.request.json.classification

import javax.validation.constraints.NotNull

class Address {
    /**
     * 省
     */
    @NotNull
    lateinit var province: String

    /**
     * 市
     */
    var city: String? = null

    /**
     * 区
     */
    var area: String? = null

    /**
     * 街道/镇
     */
    var town: String? = null

    /**
     * 剩余详细地址
     */
    @NotNull
    lateinit var detail: String

    /**
     * 邮编
     */
    var zip: String? = null
}
