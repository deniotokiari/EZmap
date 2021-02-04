package by.deniotokiari.feature.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import org.osmdroid.api.IMapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.Overlay.Snappable
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider

class MyLocationOverlay(
    private val mapView: MapView,
    private val locationProvider: IMyLocationProvider
) : Overlay(), IMyLocationConsumer, Snappable {

    private val icon: Bitmap? by lazy { ContextCompat.getDrawable(mapView.context, R.drawable.twotone_navigation_black_48)?.toBitmap() }
    private val iconCenterX: Float? by lazy { icon?.let { it.width / 2.0F - 0.5F } }
    private val iconCenterY: Float? by lazy { icon?.let { it.height / 2.0F - 0.5F } }
    private val geoPoint = GeoPoint(0.0, 0.0)
    private val drawPixel = Point()
    private val snapPixel = Point()
    private val paint = Paint().apply { isFilterBitmap = true }
    private var location: Location? = null

    override fun onLocationChanged(location: Location?, source: IMyLocationProvider?) {
        this.location = location

        location?.let { geoPoint.setCoords(it.latitude, it.longitude) }

        mapView.postInvalidate()
    }

    override fun onSnapToItem(x: Int, y: Int, snapPoint: Point?, mapView: IMapView?): Boolean {
        return location?.let {
            this.mapView.projection.apply {
                toPixels(geoPoint, this@MyLocationOverlay.snapPixel)
            }

            snapPoint?.apply {
                this.x = this@MyLocationOverlay.snapPixel.x
                this.y = this@MyLocationOverlay.snapPixel.y
            }

            val xDiff = x - this.snapPixel.x
            val yDIff = y - this.snapPixel.y

            return xDiff * xDiff + yDIff * yDIff < 64
        } ?: false
    }

    override fun onResume() {
        locationProvider.startLocationProvider(this)
    }

    override fun onPause() {
        locationProvider.stopLocationProvider()
    }

    override fun onDetach(mapView: MapView?) {
        locationProvider.stopLocationProvider()
    }

    override fun draw(canvas: Canvas?, projection: Projection?) {
        projection?.toPixels(geoPoint, drawPixel)

        val c = canvas ?: return
        val l = location ?: return
        val i = icon ?: return
        val centerX = iconCenterX ?: return
        val centerY = iconCenterY ?: return

        if (l.hasBearing()) {
            c.save()

            val mapRotation = l.bearing.let {
                if (it >= 360F) {
                    it - 360F
                } else {
                    it
                }
            }

            c.rotate(mapRotation, drawPixel.x.toFloat(), drawPixel.y.toFloat())
            c.drawBitmap(
                i,
                drawPixel.x.toFloat() - centerX,
                drawPixel.y.toFloat() - centerY,
                paint
            )

            c.restore()
        }
    }
}
