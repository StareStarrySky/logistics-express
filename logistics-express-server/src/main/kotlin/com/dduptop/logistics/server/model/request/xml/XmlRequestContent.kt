package com.dduptop.logistics.server.model.request.xml

import com.dduptop.logistics.server.model.common.BaseContent

/**
 * 请求内容
 */
class XmlRequestContent<T : BaseContent> {
    /**
     * xml内容
     */
    var logisticsInterface: T? = null
}
