package com.enciyo.flippermocknetworkplugin

import com.enciyo.flippermocknetworkplugin.model.Mock
import com.enciyo.flippermocknetworkplugin.model.MockRequestMethods
import com.enciyo.flippermocknetworkplugin.model.Config
import com.facebook.flipper.core.FlipperArray
import com.facebook.flipper.core.FlipperObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.NotSerializableException

/**
 * @since created on 21.05.2021
 * @author mustafa.kilic
 */

internal fun Request.mapMock() = Mock(
    endpoint = url.encodedPath,
    dummyJsonData = "",
    uniqueId = "",
    queryParams = url.query.orEmpty(),
    requestType = MockRequestMethods.safeValueOf(method)
)

internal fun Mock.mapResponseBody(request: Request) = Response.Builder()
    .addHeader("content-type", contentTypes.name)
    .code(httpCode)
    .body(dummyJsonData.toResponseBody(contentTypes.name.toMediaTypeOrNull()))
    .protocol(Protocol.HTTP_1_1)
    .message("Mocked $uniqueId")
    .request(request)
    .build()

internal fun FlipperObject.mapMock() = Mock(
    endpoint = map("endpoint"),
    dummyJsonData = map("dummyJsonData"),
    uniqueId = map("uniqueId")
)

internal fun FlipperObject.mapConfig() = Config(
    isLoggable = map("isLogging"),
    isMockEnable = map("isMockEnable"),
)

internal fun FlipperArray.mapMock(): List<Mock> {
    return (0 until this.length()).map {
        this.getObject(it).mapMock()
    }
}

inline fun <reified T> FlipperObject.map(param:String) : T{
    val result = this[param]
    if (result as? T == null){
        throw NotSerializableException("$param expected ${T::class.simpleName} but return $result")
    }
    return result
}

