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
    var sendID: EcCompanyId? = null

    /**
     * 数据生产的省公司代码
     * 对不能确定的省份取 99
     */
    var proviceNo: String? = null

    /**
     * 消息类别
     */
    @NotNull
    var msgKind: MsgType? = null

    /**
     * 消息唯一序列号
     */
    @NotNull
    var serialNo: String? = null

    /**
     * 消息发送日期时间
     * 格式：YYYYMMDDHHMISS 示例：20171111151501
     */
    @NotNull
    var sendDate: String? = null

    /**
     * 接收方标识
     * XX：XX 系统
     */
    @NotNull
    var receiveID: EcCompanyId? = null

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
    var dataType: String? = null

    /**
     * 数据验证
     */
    @NotNull
    var dataDigest: String? = null

    @NotNull
    var msgBody: String? = null
}
