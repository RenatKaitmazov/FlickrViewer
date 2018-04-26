package lz.renatkaitmazov.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.schedulers.Schedulers
import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import lz.renatkaitmazov.data.rest.IPhotoRestRepository
import java.io.File
import java.io.FileOutputStream

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoRepository(
  private val context: Context,
  private val restRepository: IPhotoRestRepository
) : IPhotoRepository {

  companion object {
    /**
     * The name of the temporary image.
     */
    private const val TEMP_IMG_NAME = "temp_image"

    /**
     * The extension of the temporary image.
     */
    private const val TEMP_IMG_EXTENSION = ".png"

    /**
     * The compression quality of the image being compressed.
     */
    private const val IMG_COMPRESSION_QUALITY = 100
  }

  override fun getPhotoListAtFirstPage(): Single<List<RecentPhotoEntity>> {
    return restRepository.getRecentPhotosAtFirstPage()
  }

  override fun updatePhotoList(): Single<List<RecentPhotoEntity>> {
    return restRepository.updatePhotoList()
  }

  override fun getNextPage(page: Int): Single<List<RecentPhotoEntity>> {
    return restRepository.getNextPage(page)
  }

  override fun createImageFileInCache(imageUrl: String, subDirectoryName: String): Single<File> {
    return Single.create { emitter ->
      if (emitter.isDisposed) {
        return@create
      }
      getImage(imageUrl)
        .flatMap {
          getTempImageCacheFile(it, subDirectoryName)
            .subscribeOn(Schedulers.io())
        }
        .subscribe(emitter::onSuccess, {error -> handleError(emitter, error)})
    }
  }

  private fun getImage(url: String): Single<Bitmap> {
    return Single.create { emitter ->
      if (emitter.isDisposed) {
        return@create
      }
      try {
        Glide.with(context)
          .asBitmap()
          .load(url)
          .into(object : SimpleTarget<Bitmap>() {
            // This bitmap should be retrieved from Glide's internal cache.
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
              if (!emitter.isDisposed) {
                emitter.onSuccess(resource)
              }
            }
          })
      } catch (error: Throwable) {
        handleError(emitter, error)
      }
    }
  }

  private fun getTempImageCacheFile(bitmap: Bitmap, subDirectoryName: String): Single<File> {
    return Single.create { emitter ->
      if (emitter.isDisposed) {
        return@create
      }

      try {
        val cacheSubDirectory = File(context.cacheDir, subDirectoryName)
        if (!cacheSubDirectory.exists()) {
          // Create this subdirectory if it does not exist.
          cacheSubDirectory.mkdir()
        }

        // Always rewrite the old image file.
        val tempImageName = "${cacheSubDirectory.absolutePath}/$TEMP_IMG_NAME$TEMP_IMG_EXTENSION"
        val tempImageOutputStream = FileOutputStream(tempImageName)
        bitmap.compress(
          Bitmap.CompressFormat.PNG,
          IMG_COMPRESSION_QUALITY,
          tempImageOutputStream
        )

        // Close the stream when the compression step is completed.
        tempImageOutputStream.flush()
        tempImageOutputStream.close()

        val tempImgFile = File(cacheSubDirectory, "$TEMP_IMG_NAME$TEMP_IMG_EXTENSION")
        if (!emitter.isDisposed) {
          emitter.onSuccess(tempImgFile)
        }
      } catch (error: Throwable) {
        handleError(emitter, error)
      }
    }
  }

  private fun handleError(emitter: SingleEmitter<*>, error: Throwable) {
    if (!emitter.isDisposed) {
      emitter.onError(error)
    }
  }
}