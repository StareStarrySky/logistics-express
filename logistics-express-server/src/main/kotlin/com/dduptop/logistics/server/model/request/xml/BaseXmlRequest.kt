package com.dduptop.logistics.server.model.request.xml

import com.dduptop.logistics.server.model.common.BaseRequest
import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.common.MsgType

/**
 * 基础请求体
 */
class BaseXmlRequest : BaseRequest() {
    /**
     * XML内容
     */
    var logistics_interface: String? = null

    /**
     * 签名验证：xml+parternID，然后进行MD5，转换为Base64字符串。
     */
    var data_digest: String? = null

    /**
     * 消息类型
     */
    var msg_type: MsgType? = null

    /**
     * 电商标识，如“DKH”
     */
    var ecCompanyId: EcCompanyId? = null
}
