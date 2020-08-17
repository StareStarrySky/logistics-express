package com.dduptop.logistics.server.manager.impl

import com.dduptop.logistics.server.manager.LogisticsManager
import com.dduptop.logistics.server.model.BillModel
import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.common.MsgType
import com.dduptop.logistics.server.model.common.WrongCodeBus
import com.dduptop.logistics.server.model.request.json.CSBRequest
import com.dduptop.logistics.server.model.request.json.classification.ClaAddress
import com.dduptop.logistics.server.model.request.json.classification.ClassificationReq
import com.dduptop.logistics.server.model.request.json.orderline.MsgContent
import com.dduptop.logistics.server.model.request.json.orderline.OrderLine
import com.dduptop.logistics.server.model.request.xml.BaseXmlRequest
import com.dduptop.logistics.server.model.request.xml.batch.BatchGetWaybillNo
import com.dduptop.logistics.server.model.request.xml.create.OrderAddress
import com.dduptop.logistics.server.model.request.xml.create.Cargo
import com.dduptop.logistics.server.model.request.xml.create.OrderNormal
import com.dduptop.logistics.server.model.request.xml.insert.OrderNormals
import com.dduptop.logistics.server.model.response.json.CSBResponse
import com.dduptop.logistics.server.model.response.json.classification.ClassificationRes
import com.dduptop.logistics.server.model.response.json.orderline.JsonResponse
import com.dduptop.logistics.server.model.response.xml.XmlResponses
import com.dduptop.logistics.server.model.response.xml.create.OrderCreateResponse
import com.dduptop.logistics.server.model.response.xml.insert.OrderInsertResponse
import com.dduptop.logistics.server.service.ServiceRunner
import com.dduptop.logistics.server.service.impl.*
import com.dduptop.logistics.server.util.SignUtils
import com.dduptop.logistics.server.util.WordUtils
import com.dduptop.service.document.client.DocumentClient
import com.zy.mylib.utils.DateUtils
import com.zy.mylib.utils.HashUtils
import com.zy.mylib.webmvc.model.RestMessage
import org.apache.commons.beanutils.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cglib.beans.BeanMap
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class LogisticsManagerImpl : LogisticsManager {
    @Autowired
    private lateinit var emsXmlRequest: EMSXmlRequest

    @Autowired
    private lateinit var emsJsonRequest: EMSJsonRequest

    @Autowired
    private lateinit var createOrderConfig: ServiceCreateOrderRunnerConfig

    @Autowired
    private lateinit var serviceCreateOrderRunner: ServiceRunner<BaseXmlRequest, XmlResponses<OrderCreateResponse>>

    @Autowired
    private lateinit var classificationConfig: ServiceClassificationRunnerConfig

    @Autowired
    private lateinit var serviceClassificationRunner: ServiceRunner<CSBRequest, CSBResponse<ClassificationRes>>

    @Autowired
    private lateinit var orderLineConfig: ServiceOrderLineRunnerConfig

    @Autowired
    private lateinit var serviceOrderLineRunner: ServiceRunner<MsgContent, JsonResponse>

    @Autowired
    private lateinit var batchNoConfig: ServiceBatchNoRunnerConfig

    @Autowired
    private lateinit var serviceBatchNoRunner: ServiceBatchNoRunnerProcessor

    @Autowired
    private lateinit var orderInsertConfig: ServiceOrderInsertRunnerConfig

    @Autowired
    private lateinit var serviceOrderInsertRunner: ServiceRunner<BaseXmlRequest, XmlResponses<OrderInsertResponse>>

    @Autowired
    private lateinit var documentClient: DocumentClient

    override fun createOrder(form: OrderNormal): RestMessage {
        val logisticsInterface = OrderNormal().apply {
            createdTime = DateUtils.getNowTime()
            logisticsProvider = 'B'
            baseProductNo = "1"
            ecommerceNo = EcCompanyId.whzcwyh
            ecommerceUserId = "<50位随机数"
            senderType = 1
            senderNo = "???"
            innerChannel = 0
            logisticsOrderNo = "自定义"
            batchNo = null
            waybillNo = null
            bizProductNo = "1"
            sender = OrderAddress().apply {
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
            receiver = OrderAddress().apply {
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
        val xmlRequest = emsXmlRequest.buildReq(logisticsInterface, createOrderConfig.parentId, MsgType.ORDERCREATE)

        val responses = emsXmlRequest.sendRequest(serviceCreateOrderRunner, xmlRequest)

        val items = responses.responseItems!!
        val resp = items.response!!
        return if (resp.success!!) {
            RestMessage.SUCCESS.apply { message = resp.waybillNo }
        } else {
            val wrong = WrongCodeBus.valueOf(resp.reason!!)
            RestMessage(wrong.name, wrong.message)
        }
    }

    override fun classification(req: List<ClassificationReq>): CSBResponse<ClassificationRes> {
        val logisticsInterface: MutableList<ClassificationReq> = ArrayList()

        val classification = ClassificationReq()
        classification.objectId = UUID.randomUUID().toString()
        val senderAddress = ClaAddress()
        val receiverAddress = ClaAddress()

        senderAddress.town = "余杭街道"
        senderAddress.city = "杭州市"
        senderAddress.area = "余杭"
        senderAddress.detail = "狮山路11号"
        senderAddress.province = "浙江省"
        senderAddress.zip = "123456"
        classification.senderClaAddress = senderAddress
        receiverAddress.town = "东山街道"
        receiverAddress.city = "南京市"
        receiverAddress.area = "江宁"
        receiverAddress.detail = "东麒路33号A座"
        receiverAddress.province = "江苏省"
        receiverAddress.zip = "123456"
        classification.receiverClaAddress = receiverAddress

        logisticsInterface.add(classification)

        val json = emsJsonRequest.jsonMapper.writeValueAsString(logisticsInterface)

        val csbRequest = CSBRequest()
        csbRequest.wpCode = classificationConfig.wpCode
        csbRequest.logisticsInterface = json
        csbRequest.dataDigest = SignUtils.makeSignEMS(json)
        return emsJsonRequest.sendRequest(serviceClassificationRunner, csbRequest)
    }

    override fun orderLine(traceNo: String): JsonResponse {
        val orderLine = OrderLine()
//        orderLine.traceNo = traceNo
        orderLine.traceNo = "9876532415690"

        val json = emsJsonRequest.jsonMapper.writeValueAsString(orderLine)
        val digest = Base64Utils.encodeToUrlSafeString(HashUtils.getMd5("$json${orderLineConfig.appKey}").toByteArray(StandardCharsets.UTF_8))
        val reqParam = MsgContent().apply {
            sendID = EcCompanyId.whzcwyh
            proviceNo = "99"
            msgKind = MsgType.whzcwyh_JDPT_TRACE
            serialNo = UUID.randomUUID().toString()
            sendDate = DateUtils.getToday("yyyyMMddHHmmss")
            receiveID = EcCompanyId.JDPT
            dataType = "1"
            dataDigest = digest
            msgBody = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
        }

        return emsJsonRequest.sendRequest(serviceOrderLineRunner, reqParam)
    }

    override fun batchNo(noCount: Int): RestMessage {
        val batchGetWaybillNo = BatchGetWaybillNo().apply {
            createdTime = DateUtils.getNowTime()
            eventSource = EcCompanyId.whzcwyh
//            customerNo = batchNoConfig.customerNo
            customerNo = "90000001885440"
            mailType = "6"
            count = noCount
        }
        val xmlRequest = emsXmlRequest.buildReq(batchGetWaybillNo, batchNoConfig.key, MsgType.BatchGetWaybillNo)
        val responses = emsXmlRequest.sendRequest(serviceBatchNoRunner, xmlRequest)

        return if (responses.result!!) RestMessage.SUCCESS.apply { message = responses.waybillNo } else RestMessage(responses.errorCode, responses.errorMsg)
    }

    override fun orderInsert(form: OrderNormals): RestMessage {
        val orderNormal = OrderNormal().apply {
            createdTime = DateUtils.getNowTime()
            logisticsProvider = 'B'
            baseProductNo = "1"
            ecommerceNo = EcCompanyId.whzcwyh
//            ecommerceUserId = "<50位随机数"
//            ecommerceUserId = "1231232"
            senderType = 1
//            senderNo = "???"
            senderNo = orderInsertConfig.senderNo
            innerChannel = 0
//            logisticsOrderNo = "自定义"
            logisticsOrderNo = "231313"
//            batchNo = null
            waybillNo = "3131312"
//            mailNo = "3131312"
            oneBillFlag = "1"
            productType = "1"
            projectId = EcCompanyId.whzcwyh
            sender = OrderAddress().apply {
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
            receiver = OrderAddress().apply {
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
        val logisticsInterface = OrderNormals().apply { orderNormals = arrayListOf(orderNormal) }
        val xmlRequest = emsXmlRequest.buildReq(logisticsInterface, orderInsertConfig.parentId, MsgType.ORDERCREATE)

        val responses = emsXmlRequest.sendRequest(serviceOrderInsertRunner, xmlRequest)

        val items = responses.responseItems!!
        val resp = items.response!!
        return if (resp.success!!) {
            RestMessage.SUCCESS.apply { message = resp.txLogisticID }
        } else {
            RestMessage.UNKNOW_ERROR.apply { message = resp.reason }
        }
    }

    override fun printBill(sourceFileType: String, form: BillModel): ByteArray {
        val docxByteArray = WordUtils.templateProcess(WordUtils.bean2Map(form), "bill.ftl")
        return documentClient.convert(sourceFileType, docxByteArray)
    }
}
