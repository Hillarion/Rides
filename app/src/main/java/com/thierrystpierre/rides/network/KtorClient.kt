package com.thierrystpierre.rides.network

import android.util.Log
import com.thierrystpierre.rides.util.JsonSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

object KtorClient {
    fun initHttp(
        contentNegotiation : (ContentNegotiation.Config) -> Unit = { it.json(JsonSerializer) }
    ) = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            contentNegotiation(this)
        }
        engine {
            config {
                retryOnConnectionFailure(true)
            }
            addInterceptor {chain ->
                chain.request().newBuilder()
                    .addHeader("Connection", "close")
                    .build()
                    .let(chain::proceed)
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000L
        }

/*        install(ResponseObserver) {
            onResponse { response ->
                Log.d("http_status", "${response.call.request} $(response.status.value")
            }
        }*/

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}
