package lz.renatkaitmazov.flickrviewer.photodetail

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseFragment

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoDetailFragment : BaseFragment() {

  companion object {
    /**
     * A key associated with a photo's URL.
     */
    private const val KEY_ARGS_PHOTO_URL = "KEY_ARGS_PHOTO_URL"

    /**
     * A handy method that allows to create an instance of this class.
     *
     * @param photoUrl  a photo's URL from where the actual image will be downloaded and shown
     *                  to the user.
     *
     * @return an instance of this class.
     */
    fun newInstance(photoUrl: String): PhotoDetailFragment {
      val args = Bundle(1)
      args.putString(KEY_ARGS_PHOTO_URL, photoUrl)
      val fragment = PhotoDetailFragment()
      fragment.arguments = args
      return fragment
    }
  }

  /*------------------------------------------------------------------------*/
  /* Fragment Lifecycle Events                                              */
  /*------------------------------------------------------------------------*/

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initToolbar()
    showPhoto()
  }

  /*------------------------------------------------------------------------*/
  /* Parent Abstract methods                                                */
  /*------------------------------------------------------------------------*/

  override fun getViewResId() = R.layout.fragment_photo_detail

  /*------------------------------------------------------------------------*/
  /* Helper Methods                                                         */
  /*------------------------------------------------------------------------*/

  private fun initToolbar() {
    includedToolbar.setBackgroundColor(Color.BLACK)
    if (supportsMaterialDesign()) {
      // Make the status bar black.
      activity?.window?.statusBarColor = Color.BLACK
      // Along with the toolbar.
      includedToolbar.elevation = resources.getDimension(R.dimen.elevation_4dp)
    }
  }

  private fun showPhoto() {
    val args = arguments
    if (args != null) {
      val photoUrl = args.getString(KEY_ARGS_PHOTO_URL)
      Glide.with(app)
        .load(photoUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(photoDetailImageView)
    }
  }
}