package tjk.itservice.demodelivery.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import tjk.itservice.demodelivery.data.repository.RepositoryImpl
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repositoryImpl: RepositoryImpl) : ViewModel() {


    fun getTrack() = repositoryImpl.getTrack()
}