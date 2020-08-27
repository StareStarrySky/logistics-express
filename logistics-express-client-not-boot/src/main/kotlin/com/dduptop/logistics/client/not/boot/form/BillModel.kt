package com.dduptop.logistics.client.not.boot.form

import java.io.Serializable

class BillModel : Serializable {
    companion object {
        private const val SERIAL_VERSION_UID = 4146395517513913851L
    }

    /**
     * 产品类型
     * 标准快递
     */
    var productType: String? = null

    /**
     * 时间
     */
    var createDate: String? = null

    /**
     * 时间
     */
    var createTime: String? = null

    /**
     * 分拣码
     */
    var classification: String? = null

    /**
     * 收名
     */
    var receiverName: String? = null

    /**
     * 收机
     */
    var receiverMobile: String? = null

    /**
     * 收址
     */
    var receiverAddress: String? = null

    /**
     * 寄名
     */
    var senderName: String? = null

    /**
     * 寄机
     */
    var senderMobile: String? = null

    /**
     * 寄短机
     */
    var senderMobileShort: String? = null

    /**
     * 寄址
     */
    var senderAddress: String? = null

    /**
     * 寄短址
     */
    var senderAddressShort: String? = null
}
