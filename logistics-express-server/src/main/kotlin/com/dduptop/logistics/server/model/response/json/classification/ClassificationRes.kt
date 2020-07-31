package com.dduptop.logistics.server.model.response.json.classification

import com.dduptop.logistics.server.model.common.BaseResponse

class ClassificationRes : BaseResponse() {
    /**
     * 请求是否成功
     */
    var success: Boolean? = null

    /**
     * 错误码
     */
    var errorCode: String? = null

    /**
     * 错误信息
     */
    var errorMsg: String? = null

    /**
     * 请求成功时非空
     */
    var result: List<SingleRouteInfoResponse>? = null
}
