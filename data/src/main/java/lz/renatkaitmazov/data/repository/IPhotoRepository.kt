package lz.renatkaitmazov.data.repository

import io.reactivex.Single
import lz.renatkaitmazov.data.model.entity.PhotoEntity
import java.io.File

/**
 * Provides a general API to retrieve data.
 * Abstracts the underlying data provider be it DB or Network or file system.
 *
 * @author Renat Kaitmazov
 */
interface IPhotoRepository {

  /**
   * Returns a list of recent photos at the first page.
   *
   * @return a list of recent photos.
   */
  fun getPhotoListAtFirstPage(): Single<List<PhotoEntity>>

  /**
   * Invalidates the cache and fetches the most recent photos.
   *
   * @return list of updates photos.
   */
  fun updatePhotoList(): Single<List<PhotoEntity>>

  /**
   * Returns a list of photos on the given [page].
   *
   * @param page the number of page from which photos should be extracted.
   * @return a list of photos.
   */
  fun getNextPage(page: Int): Single<List<PhotoEntity>>

  /**
   * Creates a temporary image file in the device's cache so that the image can be shared.
   *
   * @param imageUrl the url from which an image can be downloaded and saved into the cache.
   * @param subDirectoryName the name of a subdirectory in the cache hierarchy.
   * @return a file that wraps an image.
   */
  fun createImageFileInCache(imageUrl: String, subDirectoryName: String): Single<File>

  fun search(query: String): Single<List<PhotoEntity>>
}