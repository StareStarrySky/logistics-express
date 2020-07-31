package com.dduptop.logistics.client.form

/**
 * 参与人信息
 */
class OrderAddress {
    /**
     * 用户姓名
     */
    lateinit var name: String

    /**
     * 用户邮编
     */
    var postCode: String? = null

    /**
     * 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
     */
    var phone: String? = null

    /**
     * 用户移动电话
     */
    lateinit var mobile: String

    /**
     * 用户所在省，使用国标全称
     */
    lateinit var prov: String

    /**
     * 用户所在市，使用国标全称
     */
    lateinit var city: String

    /**
     * 用户所在县（区），使用国标全称
     */
    var county: String? = null

    /**
     * 用户详细地址
     */
    lateinit var address: String
}
