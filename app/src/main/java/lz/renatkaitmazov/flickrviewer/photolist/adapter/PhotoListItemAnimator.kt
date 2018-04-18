package lz.renatkaitmazov.flickrviewer.photolist.adapter

import android.animation.Animator
import android.animation.ValueAnimator
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.view.animation.DecelerateInterpolator

/**
 *
 * @author Renat Kaitmazov
 */
class PhotoListItemAnimator : DefaultItemAnimator() {

  companion object {
    /**
     * The duration of animation.
     */
    private const val DURATION = 300L

    /**
     * Defines the rate of changes. The animation starts fast and slows down at the end.
     */
    @JvmStatic
    private val INTERPOLATOR = DecelerateInterpolator()
  }

  override fun animateAppearance(viewHolder: RecyclerView.ViewHolder,
                                 preLayoutInfo: ItemHolderInfo?,
                                 postLayoutInfo: ItemHolderInfo): Boolean {
    val startAlpha = 0.0F
    val endAlpha = 1.0F
    val alphaAnimation = ValueAnimator.ofFloat(startAlpha, endAlpha)
    alphaAnimation.interpolator = INTERPOLATOR
    alphaAnimation.duration = DURATION
    val viewToAnimate = viewHolder.itemView
    alphaAnimation.addUpdateListener {
      val animatedAlpha = it.animatedValue
      viewToAnimate.alpha = animatedAlpha as Float
    }
    alphaAnimation.addListener(object : Animator.AnimatorListener {

      override fun onAnimationRepeat(animation: Animator?) {
      }

      override fun onAnimationEnd(animation: Animator?) {
        dispatchAnimationFinished(viewHolder)
      }

      override fun onAnimationCancel(animation: Animator?) {
        viewToAnimate.alpha = endAlpha
        dispatchAnimationFinished(viewHolder)
      }

      override fun onAnimationStart(animation: Animator?) {
      }
    })
    alphaAnimation.start()
    return false // Do not run any pending animation when this animation is finished.
  }
}