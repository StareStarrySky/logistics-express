package com.dduptop.logistics.server.model.request.json.classification

import javax.validation.constraints.NotNull

class ClaAddress {
    /**
     * 省
     */
    @NotNull
    var province: String? = null

    /**
     * 市
     */
    var city: String? = null

    /**
     * 区
     */
    var area: String? = null

    /**
     * 街道/镇
     */
    var town: String? = null

    /**
     * 剩余详细地址
     */
    @NotNull
    var detail: String? = null

    /**
     * 邮编
     */
    var zip: String? = null
}
