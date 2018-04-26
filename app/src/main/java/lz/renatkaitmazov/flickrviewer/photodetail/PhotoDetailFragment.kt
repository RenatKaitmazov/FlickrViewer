package lz.renatkaitmazov.flickrviewer.photodetail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import lz.renatkaitmazov.flickrviewer.R
import lz.renatkaitmazov.flickrviewer.base.BaseFragment
import lz.renatkaitmazov.flickrviewer.base.makeGone
import lz.renatkaitmazov.flickrviewer.base.makeVisible
import lz.renatkaitmazov.flickrviewer.base.showLongToast
import java.io.File
import javax.inject.Inject

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoDetailFragment
  : BaseFragment(),
    PhotoDetailFragmentView {

  companion object {
    /**
     * A key associated with a photo's URL.
     */
    private const val KEY_ARGS_PHOTO_URL = "KEY_ARGS_PHOTO_URL"

    /**
     * The type defines what type of media can be shared.
     * This fragment has the ability to share images with png extension.
     */
    private const val INTENT_TYPE_IMAGE = "image/png"

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

  @Inject
  lateinit var presenter: IPhotoDetailFragmentPresenter

  /*------------------------------------------------------------------------*/
  /* Fragment Lifecycle Events                                              */
  /*------------------------------------------------------------------------*/

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initToolbar()
    showPhoto()
  }

  /*------------------------------------------------------------------------*/
  /* Menu                                                                   */
  /*------------------------------------------------------------------------*/

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.menu_photo_detail, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      android.R.id.home -> {
        activity?.onBackPressed()
        true
      }
      R.id.menu_item_share -> {
        presenter.shareImage(getPhotoUrl(), getCacheSubDirectoryName())
        return true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  /*------------------------------------------------------------------------*/
  /* Parent Abstract methods                                                */
  /*------------------------------------------------------------------------*/

  override fun getViewResId() = R.layout.fragment_photo_detail

  /*------------------------------------------------------------------------*/
  /* PhotoDetailFragmentView implementation                                 */
  /*------------------------------------------------------------------------*/
  override fun showProgress() {
    progressBar.makeVisible()
  }

  override fun hideProgress() {
    progressBar.makeGone()
  }

  override fun onImageFileInCacheCreated(imageFile: File) {
    val authority = getString(R.string.authority_img_provider)
    val imageUri = FileProvider.getUriForFile(app, authority, imageFile)
    if (imageUri == null) {
      showImageSharingError()
      return
    }
    val shareIntent = getShareIntent(imageUri)
    if (shareIntent.resolveActivity(app.packageManager) == null) {
      // There is no app on the device that can share the given image file.
      showNoAppCanShareImageErrorAlert()
      return
    }
    val title = getString(R.string.title_share_via)
    val chooserIntent = Intent.createChooser(shareIntent, title)
    startActivity(chooserIntent)
  }

  override fun showImageSharingError() {
    showLongToast(R.string.error_when_sharing_image)
  }

  /*------------------------------------------------------------------------*/
  /* Helper Methods                                                         */
  /*------------------------------------------------------------------------*/

  private fun initToolbar() {
    val toolbar = includedToolbar as Toolbar
    includedToolbar.setBackgroundColor(Color.BLACK)
    val hostActivity = activity as? AppCompatActivity
    if (hostActivity != null) {
      hostActivity.setSupportActionBar(toolbar)
      hostActivity.supportActionBar!!.setDisplayShowHomeEnabled(true)
      hostActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      hostActivity.supportActionBar!!.title = null
      if (supportsMaterialDesign()) {
        // Make the status bar black.
        hostActivity.window?.statusBarColor = Color.BLACK
      }
    }
  }

  private fun showPhoto() {
    Glide.with(app)
      .load(getPhotoUrl())
      .transition(DrawableTransitionOptions.withCrossFade())
      .into(photoDetailImageView)
  }

  private fun getPhotoUrl(): String {
    if (arguments == null) {
      return ""
    }
    return arguments!!.getString(KEY_ARGS_PHOTO_URL, "")
  }

  private fun getCacheSubDirectoryName(): String {
    return getString(R.string.cache_subdirectory_name)
  }

  private fun getShareIntent(fileUri: Uri): Intent {
    return Intent(Intent.ACTION_SEND)
      .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      .setType(INTENT_TYPE_IMAGE)
      .putExtra(Intent.EXTRA_STREAM, fileUri)
  }

  private fun showNoAppCanShareImageErrorAlert() {
    AlertDialog.Builder(activity!!)
      .setTitle(R.string.error_general)
      .setMessage(R.string.description_no_app_to_share)
      .setPositiveButton(android.R.string.ok, null)
      .create()
      .show()
  }
}