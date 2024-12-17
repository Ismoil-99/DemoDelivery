package tjk.itservice.demodelivery.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import tjk.itservice.demodelivery.R


@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()

    private lateinit var job:Job

    private val callback = OnMapReadyCallback { googleMap ->


        val userMarker = LatLng(38.566107,68.776689)
        googleMap.addMarker(MarkerOptions().position(userMarker).title("User").icon(getBitmapDescriptorFromVector(requireContext(),R.drawable.user_group)))
        val aptekaMarker = LatLng(38.583185,68.785685)
        googleMap.addMarker(MarkerOptions().position(aptekaMarker).title("Apteka").icon(getBitmapDescriptorFromVector(requireContext(),R.drawable.apteka_icon)))

        val delivery = LatLng(0.0,0.0)
        val deliveryMarker = googleMap.addMarker(MarkerOptions().position(delivery).title("Delivery").icon(getBitmapDescriptorFromVector(requireContext(),R.drawable.curer_icon1)))

        job = startRepeatingJob(4000L){
            lifecycleScope.launch {
                viewModel.getTrack().collectLatest { location ->
                    if (deliveryMarker != null){
                        deliveryMarker.position = LatLng(location.locations.latitude.toDouble(),location.locations.longitude.toDouble())
                    }
                }
            }
        }
        job.start()

        val locations = CameraUpdateFactory.newLatLngZoom(
            LatLng(38.5652352,68.7812215), 13f
        )
        googleMap.animateCamera(locations)

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

    private fun getBitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        val bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun startRepeatingJob(timeInterval: Long,work:() -> Unit): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.job.isActive) {
                work()
                delay(timeInterval)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
    }
}