package com.dduptop.logistics.server.rest

import com.zy.mylib.base.exception.BusException
import com.zy.mylib.webmvc.model.RestMessage
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class JsonErrorController : ErrorController {
    override fun getErrorPath(): String {
        return "/error"
    }

    @RequestMapping("/error")
    fun error(request: HttpServletRequest, response: HttpServletResponse): RestMessage {
        response.status = 400
        val e = request.getAttribute("javax.servlet.error.exception")
        if (e == null) {
            response.status = (request.getAttribute("javax.servlet.error.status_code") as Int)
            return RestMessage("" + response.status, request.getAttribute("javax.servlet.error.message") as String)
        } else if (e is BusException) {
            response.status = e.httpStatus
            return RestMessage(e.code, e.message)
        }
        return RestMessage("500", e.toString())
    }
}
