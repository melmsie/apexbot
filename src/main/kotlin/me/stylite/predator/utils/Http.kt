package me.stylite.predator.utils

import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import me.stylite.predator.Config
import okhttp3.HttpUrl

class Http {
    private val apiUrl = "https://api.mozambiquehe.re"
    private val apiKey = Config.apiKey

    private val client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .followRedirects(HttpClient.Redirect.NEVER)
        .connectTimeout(Duration.ofSeconds(20))
        .build()

    suspend fun fetchStats(platform: String, username: String) = fetch {
        buildUrl("$apiUrl/bridge", mapOf("version" to "4", "platform" to platform, "player" to username, "auth" to apiKey))
    }

    suspend fun fetchNews() = fetch {
        buildUrl("$apiUrl/news.php", mapOf("lang" to "en-us", "auth" to apiKey))
    }

    suspend fun fetchStatus() = fetch {
        uri("https://apexlegendsstatus.com/servers.json")
    }

    suspend fun fetchHistory(platform: String, username: String): HttpResponse<String> {
        val params = mapOf(
            "version" to "4",
            "platform" to platform,
            "player" to username,
            "history" to "3",
            "action" to "get",
            "auth" to apiKey
        )

        return fetch {
            buildUrl("$apiUrl/bridge", params)
        }
    }

    private suspend fun fetch(opts: HttpRequest.Builder.() -> Unit): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(20))
            .apply(opts)
            .build()

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).await()
    }

    private fun HttpRequest.Builder.buildUrl(url: String, queryParams: Map<String, String> = mapOf()) {
        val builder = HttpUrl.get(url).newBuilder()

        for ((key, value) in queryParams) {
            builder.setQueryParameter(key, value)
        }

        this.uri(builder.build().uri()) // God bless OkHttp
    }

    private fun HttpRequest.Builder.uri(uri: String) {
        this.uri(URI.create(uri))
    }
}
