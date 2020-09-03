package com.dduptop.logistics.client.not.boot.form

/**
 * 参与人信息
 */
class OrderAddress {
    /**
     * 用户姓名
     */
    var name: String? = null

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
    var mobile: String? = null

    /**
     * 用户所在省，使用国标全称
     */
    var prov: String? = null

    /**
     * 用户所在市，使用国标全称
     */
    var city: String? = null

    /**
     * 用户所在县（区），使用国标全称
     */
    var county: String? = null

    /**
     * 用户详细地址
     */
    var address: String? = null
}
