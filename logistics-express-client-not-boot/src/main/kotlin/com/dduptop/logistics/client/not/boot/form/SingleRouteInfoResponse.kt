package com.dduptop.logistics.client.not.boot.form

class SingleRouteInfoResponse    {
    /**
     * 发件/收件地址对ID，单次请求中唯一
     */
    var objectId: String? = null

    /**
     * 单条查询是否成功
     */
    var success: String? = null

    /**
     * 错误码
     */
    var errorCode: String? = null

    /**
     * 错误信息
     */
    var errorMsg: String? = null

    /**
     * 大头笔
     */
    var datoubi: String? = null

    /**
     * 大头笔编码
     */
    var datoubiCode: String? = null

    /**
     * 四段码
     */
    var routeCode: String? = null

    /**
     * 集包地列表
     */
    var consolidationList: List<Consolidation>? = null
}
