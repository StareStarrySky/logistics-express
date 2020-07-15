package com.dduptop.logistics.server.service

import com.dduptop.logistics.server.model.common.BaseRequest
import com.dduptop.logistics.server.model.common.BaseResponse

interface ServiceRunner<PT : BaseRequest, RT : BaseResponse> {
    fun process(paramType: PT): RT
}
