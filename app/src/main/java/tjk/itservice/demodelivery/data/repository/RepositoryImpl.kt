package tjk.itservice.demodelivery.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import tjk.itservice.demodelivery.data.model.TrackModel
import tjk.itservice.demodelivery.data.remote.apiservice.ApiInterface
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiInterface
) :RepositoryDemo {
    override fun getTrack(): Flow<TrackModel> {
        return flow{
            try {
                val response = api.getTrack()
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        emit(response.body()!!)
                    }
                } else {

                    Log.d("error2","${response.code()}")
                }
            } catch (e: HttpException) {

                Log.d("error3","${e.message()}")
            } catch (e: Throwable) {

                Log.d("error4","${e.message.toString()}")
            }
        }
    }
}