package tjk.itservice.demodelivery.ui.home

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tjk.itservice.demodelivery.R

@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()

    private val callback = OnMapReadyCallback { googleMap ->


        val userMarker = LatLng(38.566107,68.776689)
        googleMap.addMarker(MarkerOptions().position(userMarker).title("Delivery"))

        lifecycleScope.launch {
            viewModel.getTrack().collectLatest { location ->
                val sydney = LatLng(location.locations.latitude.toDouble(), location.locations.longitude.toDouble())
                googleMap.addMarker(MarkerOptions().position(sydney).title("Delivery"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13f))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
}