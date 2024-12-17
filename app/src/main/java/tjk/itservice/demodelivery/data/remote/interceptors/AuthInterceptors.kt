package tjk.itservice.demodelivery.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import tjk.itservice.demodelivery.ui.utils.TOKEN
import java.io.IOException

class AuthInterceptors : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "$TOKEN")
            .build()
        return chain.proceed(newRequest)
    }
}