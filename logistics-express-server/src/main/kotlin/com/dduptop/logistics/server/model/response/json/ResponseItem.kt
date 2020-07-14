package com.dduptop.logistics.server.model.response.json

import java.util.*

class ResponseItem {
    /**
     * 运单号
     */
    lateinit var traceNo: String

    /**
     * 操作时间
     */
    lateinit var opTime: Date

    /**
     * 操作码
     */
    lateinit var opCode: String

    /**
     * 操作名
     */
    lateinit var opName: String

    /**
     * 操作描述
     */
    lateinit var opDesc: String

    /**
     * 操作网点省名
     */
    lateinit var opOrgProvName: String

    /**
     * 操作网点城市
     */
    lateinit var opOrgCity: String

    /**
     * 操作网点编码
     */
    lateinit var opOrgCode: String

    /**
     * 操作网点名称
     */
    lateinit var opOrgName: String

    /**
     * 操作员工号
     */
    lateinit var operatorNo: String

    /**
     * 操作员工名称
     */
    lateinit var operatorName: String
}
