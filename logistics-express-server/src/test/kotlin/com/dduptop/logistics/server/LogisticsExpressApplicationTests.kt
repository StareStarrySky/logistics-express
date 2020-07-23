package com.dduptop.logistics.server

import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.request.xml.*
import com.dduptop.logistics.server.model.response.xml.OrderCreateResponse
import com.dduptop.logistics.server.model.response.xml.XmlResponses
import com.dduptop.logistics.server.service.ServiceRunner
import com.dduptop.logistics.server.service.impl.EmsRequest
import com.zy.mylib.utils.DateUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LogisticsExpressApplicationTests {
    private val parentId = "key123xydJDPT"
    @Autowired
    private lateinit var emsRequest: EmsRequest
    @Autowired
    private lateinit var serviceRunner: ServiceRunner<BaseXmlRequest, XmlResponses<OrderCreateResponse>>

    @Test
    fun createOrder() {
        val content = XmlRequestContent<OrderNormal>().apply {
            logisticsInterface = OrderNormal().apply {
                createdTime = DateUtils.getNowTime()
                logisticsProvider = 'B'
                ecommerceNo = EcCompanyId.whzcwyh
                ecommerceUserId = "?????"
                senderType = 1
                innerChannel = 0
                logisticsOrderNo = "??????"
                baseProductNo = "21210"
                bizProductNo = "1"
                sender = Address().apply {
                    name = "陆安波"
                    postCode = "510000"
                    phone = "020-86210730"
                    mobile = "13111111"
                    prov = "广东"
                    city = "广州"
                    county = "白云区"
                    address = "广州市白云区均禾街道夏花二路66号401"
                }
                pickup = sender
                receiver = Address().apply {
                    name = "王丽婷"
                    phone = "18690972424"
                    mobile = "18690972424"
                    prov = "新疆维吾尔自治区"
                    city = "新疆维吾尔自治区"
                    county = "沙依巴克区"
                    address = "新疆维吾尔自治区乌鲁木齐市沙依巴克区西山街道沙区西环中路151号中山花苑小区"
                }
                cargos = arrayListOf(Cargo().apply {
                    cargoName = "医用棉签(B型)"
                }, Cargo().apply {
                    cargoName = "过氧苯甲酰凝胶"
                })
            }
        }
        val xmlRequest = emsRequest.buildXmlRequest(content, parentId)

        val responses = emsRequest.sendRequest(serviceRunner, xmlRequest)

        val res = emsRequest.xmlMapper.writeValueAsString(responses)
        println(res)
    }
}
