package tjk.itservice.demodelivery.data.remote

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tjk.itservice.demodelivery.data.remote.apiservice.ApiInterface
import tjk.itservice.demodelivery.data.remote.interceptors.AuthInterceptors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://doru.tj/"

@Module
@InstallIn(SingletonComponent::class)
object ApiDoru {

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptors())
        .build()

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofitClient: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun getRetrofitRequest() : ApiInterface = retrofitClient.create(ApiInterface::class.java)

}