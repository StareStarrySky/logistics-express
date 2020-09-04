package com.dduptop.logistics.server.manager.impl

import com.dduptop.logistics.server.config.feign.FeignBeanConfig
import com.dduptop.logistics.server.manager.LogisticsManager
import com.dduptop.logistics.server.model.BillModel
import com.dduptop.logistics.server.model.common.EcCompanyId
import com.dduptop.logistics.server.model.common.MsgType
import com.dduptop.logistics.server.model.common.WrongCode
import com.dduptop.logistics.server.model.request.json.CSBRequest
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
import com.dduptop.logistics.server.util.BarCodeUtils
import com.dduptop.logistics.server.util.SignUtils
import com.dduptop.logistics.server.util.WordUtils
import com.dduptop.logistics.server.util.ZipUtils
import com.dduptop.micro.service.client.TokenUtils
import com.dduptop.service.auth.client.ApiUserLoginModel
import com.dduptop.service.auth.client.NonCloudAuthClient
import com.dduptop.service.document.client.DocumentClient
import com.zy.mylib.utils.DateUtils
import com.zy.mylib.utils.HashUtils
import com.zy.mylib.webmvc.model.RestMessage
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant
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
    private lateinit var nonCloudAuthClient: NonCloudAuthClient

    @Autowired
    private lateinit var documentClient: DocumentClient

    @Autowired
    private lateinit var feignBeanConfig: FeignBeanConfig

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
            val wrong = WrongCode.valueOf(resp.reason!!)
            RestMessage(wrong.name, wrong.message)
        }
    }

    override fun classification(req: List<ClassificationReq>): CSBResponse<ClassificationRes> {
//        val classification = ClassificationReq()
//        classification.objectId = UUID.randomUUID().toString()
//        val senderAddress = ClaAddress().apply {
//            town = "余杭街道"
//            city = "杭州市"
//            area = "余杭"
//            detail = "狮山路11号"
//            province = "浙江省"
//            zip = "123456"
//        }
//        val receiverAddress = ClaAddress().apply {
//            town = "东山街道"
//            city = "南京市"
//            area = "江宁"
//            detail = "东麒路33号A座"
//            province = "江苏省"
//            zip = "123456"
//        }
//        classification.senderAddress = senderAddress
//        classification.receiverAddress = receiverAddress
//        val logisticsInterface = arrayListOf(classification)

        val json = emsJsonRequest.jsonMapper.writeValueAsString(req)

        val csbRequest = CSBRequest()
        csbRequest.wpCode = classificationConfig.wpCode
        csbRequest.logisticsInterface = json
        csbRequest.dataDigest = SignUtils.makeSignEMS(json)
        return emsJsonRequest.sendRequest(serviceClassificationRunner, csbRequest)
    }

    override fun orderLine(traceNo: String): JsonResponse {
        val orderLine = OrderLine()
        orderLine.traceNo = traceNo
//        orderLine.traceNo = "9876532415690"

        val json = emsJsonRequest.jsonMapper.writeValueAsString(orderLine)
        val digest = SignUtils.makeSignEMSNew("$json${orderLineConfig.appKey}")
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
            customerNo = batchNoConfig.customerNo
//            customerNo = "90000001885440"
            mailType = "6"
            count = noCount
        }
        val xmlRequest = emsXmlRequest.buildReq(batchGetWaybillNo, batchNoConfig.key, MsgType.BatchGetWaybillNo)
        val responses = emsXmlRequest.sendRequest(serviceBatchNoRunner, xmlRequest)

        return if (responses.result!!) RestMessage.SUCCESS.apply { message = responses.waybillNo } else RestMessage(responses.errorCode, WrongCode.get(responses.errorMsg))
    }

    override fun orderInsert(form: OrderNormals): RestMessage {
        val orderNormalsList = arrayListOf<OrderNormal>()
        for (orderNormalForm in form.orderNormals) {
            val orderNormal = orderNormalForm.apply {
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
                logisticsOrderNo = orderNormalForm.logisticsOrderNo
//            batchNo = null
                waybillNo = orderNormalForm.waybillNo
//            mailNo = "3131312"
                oneBillFlag = "0"
                productType = "1"
                projectId = EcCompanyId.whzcwyh
                sender = orderNormalForm.sender
//                sender = OrderAddress().apply {
//                    name = "陆安波"
//                    postCode = "510000"
//                    phone = "020-86210730"
//                    mobile = "13111111"
//                    prov = "广东"
//                    city = "广州"
//                    county = "白云区"
//                    address = "广州市白云区均禾街道夏花二路66号401"
//                }
//                pickup = sender
//                receiver = OrderAddress().apply {
//                    name = "王丽婷"
//                    phone = "18690972424"
//                    mobile = "18690972424"
//                    prov = "新疆维吾尔自治区"
//                    city = "新疆维吾尔自治区"
//                    county = "沙依巴克区"
//                    address = "新疆维吾尔自治区乌鲁木齐市沙依巴克区西山街道沙区西环中路151号中山花苑小区"
//                }
                receiver = orderNormalForm.receiver
//                cargos = arrayListOf(Cargo().apply {
//                    cargoName = "医用棉签(B型)"
//                }, Cargo().apply {
//                    cargoName = "过氧苯甲酰凝胶"
//                })
                cargos = orderNormalForm.cargos
            }
            orderNormalsList.add(orderNormal)
        }
        val logisticsInterface = OrderNormals().apply { orderNormals = orderNormalsList }
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

    override fun printBill(waybillNo: String, form: BillModel): ByteArray {
        val documentByteArray = WordUtils.templateProcess("document.ftl", WordUtils.bean2Map(form))

        val barCodeBigStream = ByteArrayOutputStream()
        BarCodeUtils.barCode(waybillNo, 500, 80, waybillNo, barCodeBigStream)
        val barCodeSmallStream = ByteArrayOutputStream()
        BarCodeUtils.barCode(waybillNo, 600, 60, waybillNo, barCodeSmallStream)

        val newEntry = hashMapOf("word/document.xml" to documentByteArray,
            "word/media/image3.png" to barCodeBigStream.toByteArray(),
            "word/media/image6.png" to barCodeSmallStream.toByteArray())
        val docxByteArray = ZipUtils.toDocx("bill.docx", newEntry)

        val random = UUID.randomUUID().toString()
        val second = Instant.now().epochSecond
        val model = ApiUserLoginModel().apply {
            loginId = feignBeanConfig.appId
            nonce = random
            timestamp = second
            system = feignBeanConfig.system
            sign = Base64.encodeBase64String(HashUtils.sha256("${loginId}${nonce}${timestamp}${feignBeanConfig.secret}"))
        }
        val token = nonCloudAuthClient.apiUserLogin(model)
        TokenUtils.setToken(token)
        return documentClient.convert(docxByteArray)
    }
}
