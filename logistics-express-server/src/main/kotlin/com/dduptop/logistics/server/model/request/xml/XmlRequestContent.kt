package com.dduptop.logistics.server.model.request.xml

/**
 * 请求内容
 */
class XmlRequestContent<T : BaseContent> {
    /**
     * xml内容
     */
    var logisticsInterface: T? = null
}
