package com.even.common.request.gson

import com.google.gson.*
import java.lang.reflect.Type

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 * 处理Long类型，如果后台返回""或者null,则返回0
 */
class LongDefaultAdapter : JsonSerializer<Long>, JsonDeserializer<Long> {
    override fun serialize(src: Long?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long {
        return if (null == json || "" == json.asString || "null" == json.asString) {
            0L
        } else json.asLong
    }
}