package tjk.itservice.demodelivery.data.model

import com.google.gson.annotations.SerializedName

data class TrackModel(
    @SerializedName("locations")
    val locations:LocationsDelivery
)

data class LocationsDelivery(
    @SerializedName("date")
    val data:String,
    @SerializedName("latitude")
    val latitude:String,
    @SerializedName("longitude")
    val longitude:String
)
