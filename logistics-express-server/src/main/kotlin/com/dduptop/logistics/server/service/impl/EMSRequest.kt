package com.dduptop.logistics.server.service.impl

import com.dduptop.logistics.server.model.common.BaseRequest
import com.dduptop.logistics.server.model.common.BaseResponse
import com.dduptop.logistics.server.service.ServiceRunner

abstract class EMSRequest {
    abstract fun <T> toBean(content: String?, valueType: Class<T>, vararg parameterClasses: Class<*>?): T

    fun <PT : BaseRequest, RT : BaseResponse> sendRequest(runner: ServiceRunner<PT, RT>, jsonRequest: PT): RT {
        return runner.process(jsonRequest)
    }
}
