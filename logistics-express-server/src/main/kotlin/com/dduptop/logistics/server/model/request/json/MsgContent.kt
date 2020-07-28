package com.dduptop.logistics.server.model.request.json

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

/**
 * 请求报文
 */
class MsgContent<T> : MsgHeader() {
    @NotNull
    @JsonProperty("msgBody")
    lateinit var msgBody: String
}
