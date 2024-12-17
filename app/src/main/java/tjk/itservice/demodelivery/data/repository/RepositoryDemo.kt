package tjk.itservice.demodelivery.data.repository

import kotlinx.coroutines.flow.Flow
import tjk.itservice.demodelivery.data.model.TrackModel

interface RepositoryDemo {

    fun getTrack(): Flow<TrackModel>
}