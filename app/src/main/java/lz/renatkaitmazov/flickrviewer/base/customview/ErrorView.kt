package lz.renatkaitmazov.flickrviewer.base.customview

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.merge_error_view.view.*
import lz.renatkaitmazov.flickrviewer.R

/**
 * This is a compound view that is shown to let the user know that some error happened.
 * It consists of an image view, an error title, an error description, and a retry button.
 *
 * @author Renat Kaitmazov
 */
class ErrorView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAtr: Int = 0
) : NestedScrollView(context, attrs, defStyleAtr) {

  init {
    val inflater = LayoutInflater.from(context)
    inflater.inflate(R.layout.merge_error_view, this, true)

    if (attrs != null) {
      // The view has been created via XML.
      val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0)
      try {
        // The user might set custom attributes, check them.
        val errorImageResId = typedArray.getResourceId(R.styleable.ErrorView_errorImageSrc, 0)
        val errorTitle = typedArray.getString(R.styleable.ErrorView_errorTitleText)
        val errorDescription = typedArray.getString(R.styleable.ErrorView_errorDescriptionText)
        val errorButtonText = typedArray.getString(R.styleable.ErrorView_errorButtonText)

        // Set the attributes if user provided them.
        if (errorImageResId > 0) {
          errorImageView.setImageResource(errorImageResId)
        }

        if (!errorTitle.isNullOrBlank()) {
          errorTitleTextView.text = errorTitle
        }

        if (!errorDescription.isNullOrBlank()) {
          errorDescriptionTextView.text = errorDescription
        }

        if (!errorButtonText.isNullOrBlank()) {
          errorButton.text = errorButtonText
        }
      } finally {
        typedArray.recycle()
      }
    }
  }

  fun setOnErrorButtonClickListener(clickListener: View.OnClickListener) {
    errorButton.setOnClickListener(clickListener)
  }

  /**
   * Shows this view with an alpha animation.
   */
  fun show() {
    if (visibility != View.VISIBLE) {
      animate().withLayer()
        .withStartAction { visibility = View.VISIBLE }
        .alpha(1.0F)
        .start()
    }
  }

  /**
   * Hides this view with an alpha animation.
   */
  fun hide() {
    if (visibility == View.VISIBLE) {
      animate().withLayer()
        .alpha(0.0F)
        .withEndAction { visibility = View.GONE }
        .start()
    }
  }
}