package me.stylite.predator.utils

import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import me.stylite.predator.Config

class Http {

    val apiKey: String = Config.api_key
    private val client: HttpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .followRedirects(HttpClient.Redirect.NEVER)
        .connectTimeout(Duration.ofSeconds(20))
        .build()

    suspend fun fetchStats(platform: String, username: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(20))
            .uri(URI.create("https://api.mozambiquehe.re/bridge?version=4&platform=$platform&player=$username&auth=$apiKey"))
            .build()
        val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.await()
    }
    suspend fun fetchNews(): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(20))
            .uri(URI.create("https://api.mozambiquehe.re/news.php?lang=en-us&auth=$apiKey"))
            .build()
        val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        return response.await()
    }
    suspend fun fetchStatus(): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .timeout(Duration.ofSeconds(20))
            .uri(URI.create("https://apexlegendsstatus.com/servers.json"))
            .build()
        val response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        return response.await()
    }
}