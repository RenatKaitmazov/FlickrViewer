package lz.renatkaitmazov.data.rest

import io.reactivex.Single
import lz.renatkaitmazov.data.model.recentphoto.RecentPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Path

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
    const val PATH_KEY_METHOD = "method"

    /**
     * The application key.
     */
    const val PATH_KEY_API_KEY = "api_key"

    /**
     * Number of photos per page.
     */
    const val PATH_KEY_PER_PAGE = "per_page"

    /**
     * Current page.
     */
    const val PATH_KEY_PAGE = "page"

    /**
     * The format of the server response (JSON or XML).
     */
    const val PATH_KEY_FORMAT = "format"

    /**
     * Defines whether the raw JSON response should be wrapped into a callback function.
     * The value of [1] means that a callback is not needed.
     */
    const val PATH_KEY_NO_JSON_CALLBACK = "nojsoncallback"

    /**
     * Additional information about the photo that can be put into the response.
     */
    const val PATH_KEY_EXTRAS = "extras"
  }

  /**
   * Returns a raw JSON response that contains a list of recent photos.
   */
  @GET
  fun getRecentPhotos(@Path(PATH_KEY_METHOD) method: String,
                      @Path(PATH_KEY_API_KEY) apiKey: String,
                      @Path(PATH_KEY_PER_PAGE) photosPerPage: Int,
                      @Path(PATH_KEY_PAGE) page: Int,
                      @Path(PATH_KEY_FORMAT) format: String,
                      @Path(PATH_KEY_NO_JSON_CALLBACK) noJsonCallback: Int,
                      @Path(PATH_KEY_EXTRAS) extras: String): Single<RecentPhotosResponse>
}