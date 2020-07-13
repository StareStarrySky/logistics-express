package com.dduptop.logistics.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.zy.mylib.data.jpa.DefaultHibernateModule
import com.zy.mylib.data.jpa.ModulesObjectMapper
import com.zy.mylib.security.LoginUser
import com.zy.mylib.security.Passport
import com.zy.mylib.webmvc.security.LoginUserArgumentResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.domain.Page
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration

@Configuration
class MvcConfig : DelegatingWebMvcConfiguration() {
    @Autowired
    private lateinit var passport: Passport<LoginUser>

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(argumentResolvers)
        argumentResolvers.add(LoginUserArgumentResolver().apply { passport = this@MvcConfig.passport })
    }

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val modulesObjectMapper = ModulesObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            configure(SerializationFeature.WRAP_EXCEPTIONS, true)
            configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
//        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH,false);
//        mapper.configure(Des)
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        mapper.setFilterProvider()
            modules = arrayListOf(DefaultHibernateModule(),
                SimpleModule("jackson-page-with-jsonview", Version.unknownVersion()).apply { addSerializer(Page::class.java, PageSerializer()) })

//        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//        List<MediaType> stringSupports = Lists.newArrayList();
//        stringSupports.add(MediaType.valueOf("text/html;charset=UTF-8"));
//        stringSupports.add(MediaType.valueOf("text/xml;charset=UTF-8"));
//        stringSupports.add(MediaType.TEXT_XML);

//        jsonConvert.setObjectMapper(mapper);
        }

        var xmlMapIndex = -1
        for (i in converters.indices) {
            val c = converters[i]
            if (c is MappingJackson2HttpMessageConverter) {
                c.objectMapper = modulesObjectMapper
            } else if (c is MappingJackson2XmlHttpMessageConverter) {
                xmlMapIndex = i
            }
        }
        if (xmlMapIndex >= 0) {
            converters.removeAt(xmlMapIndex)
        }
        super.extendMessageConverters(converters)
    }

    class PageSerializer : StdSerializer<Page<*>>(Page::class.java) {
        override fun serialize(page: Page<*>, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
            jsonGenerator.run {
                writeStartObject()
                writeNumberField("number", page.number)
                writeNumberField("numberOfElements", page.numberOfElements)
                writeNumberField("totalElements", page.totalElements)
                writeNumberField("totalPages", page.totalPages)
                writeNumberField("size", page.size)
                writeFieldName("content")
                serializerProvider.defaultSerializeValue(page.content, this)
                writeEndObject()
            }
        }
    }

}
