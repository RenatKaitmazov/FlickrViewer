package lz.renatkaitmazov.data.rest

import io.reactivex.Single
import lz.renatkaitmazov.data.model.photo.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines endpoints to get, search, and if possible download
 * images from the server.
 *
 * @author Renat Kaitmazov
 */
interface PhotoRestApi {

  private companion object {
    /**
     * Defines a calling method.
     * @see <https://www.flickr.com/services/api/>
     */
    const val QUERY_KEY_METHOD = "method"

    /**
     * The application key.
     */
    const val QUERY_KEY_API_KEY = "api_key"

    /**
     * Number of photos per page.
     */
    const val QUERY_KEY_PER_PAGE = "per_page"

    /**
     * Current page.
     */
    const val QUERY_KEY_PAGE = "page"

    /**
     * The format of the server response (JSON or XML).
     */
    const val QUERY_KEY_FORMAT = "format"

    /**
     * Defines whether the raw JSON response should be wrapped into a callback function.
     * The value of 1 means that a callback is not needed.
     */
    const val QUERY_KEY_NO_JSON_CALLBACK = "nojsoncallback"

    /**
     * Additional information about the photo that can be put into the response.
     */
    const val QUERY_KEY_EXTRAS = "extras"

    /**
     * A free text search.
     * Photos who's title, description or tags contain the text will be returned.
     */
    const val QUERY_KEY_SEARCH_QUERY = "text"

    /**
     * Allows to filter unwanted content.
     * The value of 1 is used to filter adult content.
     */
    const val QUERY_KEY_SAFE_SEARCH = "safe_search"

    /**
     * Allows to specify what type of images to search for (photos, screenshots, etc).
     * The value of 1 is used for photos only.
     */
    const val QUERY_KEY_CONTENT_TYPE = "content_type"

    /**
     * Filter results by media type. Possible values are all (default), photos or videos.
     */
    const val QUERY_KEY_MEDIA_TYPE = "media"

    const val JSON = "json"
    const val PHOTOS_PER_PAGE = 100
    const val NO_JSON_CALLBACK = 1
    const val SAFE_SEARCH = 1
    const val CONTENT_TYPE_PHOTOS = 1
    const val MEDIA_TYPE_PHOTOS = "photos"
  }

  /**
   * Returns a raw JSON response that contains a list of recent photos.
   */
  @GET(".")
  fun getRecentPhotos(@Query(QUERY_KEY_METHOD) method: String,
                      @Query(QUERY_KEY_API_KEY) apiKey: String,
                      @Query(QUERY_KEY_PAGE) page: Int,
                      @Query(QUERY_KEY_EXTRAS) extras: String,
                      @Query(QUERY_KEY_FORMAT) format: String = JSON,
                      @Query(QUERY_KEY_PER_PAGE) photosPerPage: Int = PHOTOS_PER_PAGE,
                      @Query(QUERY_KEY_NO_JSON_CALLBACK) noJsonCallback: Int = NO_JSON_CALLBACK
  ): Single<PhotosResponse>

  @GET(".")
  fun search(@Query(QUERY_KEY_METHOD) method: String,
             @Query(QUERY_KEY_API_KEY) apiKey: String,
             @Query(QUERY_KEY_PAGE) page: Int,
             @Query(QUERY_KEY_EXTRAS) extras: String,
             @Query(QUERY_KEY_SEARCH_QUERY) searchQuery: String,
             @Query(QUERY_KEY_FORMAT) format: String = JSON,
             @Query(QUERY_KEY_PER_PAGE) photosPerPage: Int = PHOTOS_PER_PAGE,
             @Query(QUERY_KEY_NO_JSON_CALLBACK) noJsonCallback: Int = NO_JSON_CALLBACK,
             @Query(QUERY_KEY_SAFE_SEARCH) safeSearch: Int = SAFE_SEARCH,
             @Query(QUERY_KEY_CONTENT_TYPE) contentType: Int = CONTENT_TYPE_PHOTOS,
             @Query(QUERY_KEY_MEDIA_TYPE) mediaType: String = MEDIA_TYPE_PHOTOS
  ): Single<PhotosResponse>
}