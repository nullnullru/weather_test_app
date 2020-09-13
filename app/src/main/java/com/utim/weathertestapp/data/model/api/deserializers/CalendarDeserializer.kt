package com.utim.weathertestapp.data.model.api.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CalendarDeserializer : JsonDeserializer<Calendar>{

    companion object {
        val DATE_FORMAT: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Calendar {
        val stringDateTime = json.asString
        return Calendar.getInstance().apply {
            time = DATE_FORMAT.parse(stringDateTime)!!
        }
    }

}