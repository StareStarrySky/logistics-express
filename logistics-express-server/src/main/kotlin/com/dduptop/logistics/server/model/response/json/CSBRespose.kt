package com.dduptop.logistics.server.model.response.json

import com.dduptop.logistics.server.model.common.BaseResponse
import java.util.*

class CSBRespose : BaseResponse() {
    /**
     * Csb 返回的 body，包含了实际服务返回的内容
     */
    var body: String? = null

    /**
     * 消息唯一流水号，为请求时填写的 serialNo
     */
    var serialNo: String? = null

    /**
     * 返回代码
     *
     * 000 成功
     * 001 授权错误
     * 002 越权查询
     * 010 业务异常
     * 020 系统异常
     * 099 其他
     */
    var retCode: String? = null

    /**
     * 返回消息
     */
    var retMsg: String? = null

    /**
     * 返回时间
     */
    var retDate: Date? = null

    /**
     * 实际接口返回的内容
     * 具体格式内容在各接口规范中定义
     */
    var retBody: String? = null

    /**
     * csb 平台返回代码
     *
     * 200 访问处理成功
     * 500 CSB 内部错误
     * 501 所访问的服务 API 未经授权
     * 502 访问签名未通过验证，也包括所提供的访问凭证不存在的情况
     * 504 所访问的服务 API 不存在
     * 505 访问凭证缺失
     * 506 访问签名缺失
     * 507 访问参数缺失
     * 508 访问要求通过安全通道进行
     * 509 访问时间戳缺失
     * 510 访问过期，请检查网络通路是否稳定
     * 800 提供端协议错误码 提供端协议错误信息
     * 801 服务提供端不可达。如果提供端协议会报告该类型错误，将会通过上面的 800 Escape Code 体现
     */
    var code: String? = null

    /**
     * csb 平台返回消息
     */
    var message: String? = null
}
