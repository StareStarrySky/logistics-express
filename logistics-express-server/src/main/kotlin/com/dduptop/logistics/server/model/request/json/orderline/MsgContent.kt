package com.dduptop.logistics.server.model.request.json.orderline

import com.dduptop.logistics.server.model.common.BaseRequest
import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.common.MsgType
import javax.validation.constraints.NotNull

class MsgContent : BaseRequest() {
    /**
     * 发送方标识
     */
    @NotNull
    lateinit var sendID: EcCompanyId

    /**
     * 数据生产的省公司代码
     * 对不能确定的省份取 99
     */
    lateinit var proviceNo: String

    /**
     * 消息类别
     */
    @NotNull
    lateinit var msgKind: MsgType

    /**
     * 消息唯一序列号
     */
    @NotNull
    lateinit var serialNo: String

    /**
     * 消息发送日期时间
     * 格式：YYYYMMDDHHMISS 示例：20171111151501
     */
    @NotNull
    lateinit var sendDate: String

    /**
     * 接收方标识
     * XX：XX 系统
     */
    @NotNull
    lateinit var receiveID: EcCompanyId

    /**
     * 批次号
     */
    var batchNo: String? = null

    /**
     * 数据类型
     * 1-JSON
     * 2-XML
     * 3-压缩后的 Byte[]
     */
    @NotNull
    lateinit var dataType: String

    /**
     * 数据验证
     */
    @NotNull
    lateinit var dataDigest: String

    @NotNull
    lateinit var msgBody: String
}
