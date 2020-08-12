package com.dduptop.logistics.client.not.boot.form

class ClaAddress {
    /**
     * 省
     */
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
    lateinit var detail: String

    /**
     * 邮编
     */
    var zip: String? = null
}
