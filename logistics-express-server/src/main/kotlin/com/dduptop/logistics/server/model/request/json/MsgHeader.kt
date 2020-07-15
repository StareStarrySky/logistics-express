package com.dduptop.logistics.server.model.request.json

import com.dduptop.logistics.server.model.common.BaseRequest
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

/**
 * 消息头
 */
open class MsgHeader : BaseRequest() {
    /**
     * 发送方标识
     */
    @NotNull
    @JsonProperty("sendID")
    lateinit var sendId: String

    /**
     * 消息类别
     */
    @NotNull
    @JsonProperty("msgKind")
    lateinit var msgKind: String

    /**
     * 消息唯一序列号
     */
    @NotNull
    @JsonProperty("serialNo")
    lateinit var serialNo: String

    /**
     * 消息发送日期时间
     * 格式：YYYYMMDDHHMISS 示例：20171111151501
     */
    @NotNull
    @JsonProperty("sendDate")
    lateinit var sendDate: String

    /**
     * 接收方标识
     * XX：XX 系统
     */
    @NotNull
    @JsonProperty("receiveID")
    lateinit var receiveId: String

    /**
     * 数据类型
     * 1-JSON
     * 2-XML
     * 3-压缩后的 Byte[]
     */
    @NotNull
    @JsonProperty("dataType")
    lateinit var dataType: String

    /**
     * 数据验证
     */
    @NotNull
    @JsonProperty("dataDigest")
    lateinit var dataDigest: String
}
