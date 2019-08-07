package com.even.common.request.gson

import com.google.gson.*
import java.lang.reflect.Type

/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 * 处理double类型，如果后台返回""或者null,则返回0
 */
class DoubleDefaultAdapter : JsonSerializer<Double>, JsonDeserializer<Double> {
    override fun serialize(src: Double?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src)
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Double {
        return if (null == json || "" == json.asString || "null" == json.asString) {
            return 0.00
        } else json.asDouble
    }
}