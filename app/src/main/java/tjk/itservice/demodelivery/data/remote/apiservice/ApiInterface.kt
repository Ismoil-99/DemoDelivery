package tjk.itservice.demodelivery.data.remote.apiservice

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import tjk.itservice.demodelivery.data.model.TrackModel

interface ApiInterface {


    @GET("/courier/track")
    suspend fun getTrack(): Response<TrackModel>

}