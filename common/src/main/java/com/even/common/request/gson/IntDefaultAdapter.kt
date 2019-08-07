package com.even.common.request.gson

import com.google.gson.*
import java.lang.reflect.Type

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 *  处理int类型，如果后台返回""或者null,则返回0
 */
class IntDefaultAdapter : JsonSerializer<Int>, JsonDeserializer<Int> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Int {
        return if (null == json || "" == json.asString || null == json.asString) {
            0
        } else json.asInt
    }

    override fun serialize(src: Int?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }
}