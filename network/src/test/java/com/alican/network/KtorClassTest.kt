package com.alican.network

import com.alican.NetworkResult
import com.alican.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlinx.io.IOException
import kotlinx.serialization.Serializable
import org.junit.Assert.assertEquals
import org.junit.Test

@Serializable
data class IpResponse(val ip: String)

class KtorClassTest {

    @Test
    fun `safeApiCall returns Success on successful response`() = runBlocking {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }

        val result = safeApiCall<IpResponse>(client) {
            url("https://api.ipify.org/?format=json")
        }

        assert(result is NetworkResult.Success)
        assertEquals("127.0.0.1", (result as NetworkResult.Success).data.ip)
    }


    @Test
    fun `safeApiCall returns Error on non-successful response`() = runBlocking {
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel("""{"message":"Unauthorized"}"""),
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine)

        val result = safeApiCall<IpResponse>(client) {
            url("https://api.ipify.org/?format=json")
        }

        assert(result is NetworkResult.Error)
        assertEquals("Unauthorized", (result as NetworkResult.Error).message)
        assertEquals(HttpStatusCode.Unauthorized.value, result.statusCode)
    }

    @Test
    fun `safeApiCall returns Error on exception`() = runBlocking {
        val mockEngine = MockEngine { request ->
            throw IOException("Network error")
        }
        val client = HttpClient(mockEngine)

        val result = safeApiCall<IpResponse>(client) {
            url("https://api.ipify.org/?format=json")
        }

        assert(result is NetworkResult.Error)
        assert((result as NetworkResult.Error).message!!.contains("Network error"))
    }
}