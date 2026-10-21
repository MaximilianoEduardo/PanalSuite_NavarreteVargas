package Data

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url = request.url.toString()
        val method = request.method

        // Respuesta simulada para pruebas
        val mockJsonResponse = """
            [
                {
                    "id": 1,
                    "title": "Ticket de prueba",
                    "description": "Descripción del problema",
                    "status": "OPEN",
                    "priority": "HIGH"
                }
            ]
        """.trimIndent()

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(mockJsonResponse.toResponseBody("application/json".toMediaType()))
            .build()
    }
}