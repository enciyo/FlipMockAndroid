package com.enciyo.flippermocknetworkplugin.model

import androidx.annotation.Keep
import java.net.HttpURLConnection

/**
 * @since created on 21.05.2021
 * @author mustafa.kilic
 */

@Keep
internal data class Mock @JvmOverloads constructor(
    val endpoint: String,
    val dummyJsonData: String,
    val uniqueId: String,
    val queryParams: String = "", // Feature
    val httpCode: Int = HttpURLConnection.HTTP_OK, // Feature
    val requestType: MockRequestMethods? = null, // Feature
    val contentTypes: MockContentTypes = MockContentTypes.JSON, // Feature
)

internal fun Mock.isSameEndpoint(outMock: Mock) =
    uniqueId == outMock.uniqueId

internal fun Mock.isSameMock(outMock: Mock) =
    endpoint == outMock.endpoint &&
            (if (queryParams.isEmpty()) true else queryParams == outMock.queryParams) && isSameRequestType(outMock)



internal fun Mock.isSameRequestType(outMock: Mock) =
    requestType == null ||
            outMock.requestType == requestType