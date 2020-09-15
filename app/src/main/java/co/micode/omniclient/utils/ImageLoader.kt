package co.micode.omniclient.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImageLoader
@Inject
constructor(private val picasso: Picasso) {

    fun loadImage(imageId: String, imageView: ImageView) {
        picasso
            .load(IMAGE_BASE_URL + imageId)
            .into(imageView)
    }

    companion object {
        private const val IMAGE_BASE_URL = "https://gfx-android.omni.se/images/"
    }
}