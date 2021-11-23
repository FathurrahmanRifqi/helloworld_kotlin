package com.material.utils

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;


object ViewAnimation {
    fun expand(v: View, animListener: AnimListener) {
        val a = expandAction(v)
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                animListener.onFinish()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(a)
    }

    fun expand(v: View) {
        val a = expandAction(v)
        v.startAnimation(a)
    }

    private fun expandAction(v: View): Animation {
        v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val targtetHeight: Int = v.getMeasuredHeight()
        v.getLayoutParams().height = 0
        v.setVisibility(View.VISIBLE)
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                v.getLayoutParams().height =
                    if (interpolatedTime == 1f) LayoutParams.WRAP_CONTENT else (targtetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.setDuration(
            (targtetHeight / v.getContext().getResources()
                .getDisplayMetrics().density) as Long
        )
        v.startAnimation(a)
        return a
    }

    fun collapse(v: View) {
        val initialHeight: Int = v.getMeasuredHeight()
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    v.setVisibility(View.GONE)
                } else {
                    v.getLayoutParams().height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.setDuration(
            (initialHeight / v.getContext().getResources()
                .getDisplayMetrics().density) as Long
        )
        v.startAnimation(a)
    }

    fun flyInDown(v: View, animListener: AnimListener?) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(0.0f)
        v.setTranslationY(0F)
        v.setTranslationY((-v.getHeight()).toFloat())
        // Prepare the View for the animation
        v.animate()
            .setDuration(200)
            .translationY(0F)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animListener?.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1.0f)
            .start()
    }

    fun flyOutDown(v: View, animListener: AnimListener?) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(1.0f)
        v.setTranslationY(0F)
        // Prepare the View for the animation
        v.animate()
            .setDuration(200)
            .translationY(v.getHeight().toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animListener?.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(0.0f)
            .start()
    }

    fun fadeIn(v: View) {
        fadeIn(v, null)
    }

    fun fadeIn(v: View, animListener: AnimListener?) {
        v.setVisibility(View.GONE)
        v.setAlpha(0.0f)
        // Prepare the View for the animation
        v.animate()
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.setVisibility(View.VISIBLE)
                    animListener?.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1.0f)
    }

    fun fadeOut(v: View) {
        fadeOut(v, null)
    }

    fun fadeOut(v: View, animListener: AnimListener?) {
        v.setAlpha(1.0f)
        // Prepare the View for the animation
        v.animate()
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animListener?.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(0.0f)
    }

    fun showIn(v: View) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(0f)
        v.setTranslationY(v.getHeight().toFloat())
        v.animate()
            .setDuration(200)
            .translationY(0F)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1f)
            .start()
    }

    fun initShowOut(v: View) {
        v.setVisibility(View.GONE)
        v.setTranslationY(v.getHeight().toFloat())
        v.setAlpha(0f)
    }

    fun showOut(v: View) {
        v.setVisibility(View.VISIBLE)
        v.setAlpha(1f)
        v.setTranslationY(0F)
        v.animate()
            .setDuration(200)
            .translationY(v.getHeight().toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.setVisibility(View.GONE)
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }

    fun rotateFab(v: View, rotate: Boolean): Boolean {
        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .rotation(if (rotate) 135f else 0f)
        return rotate
    }

    fun fadeOutIn(view: View) {
        view.setAlpha(0f)
        val animatorSet = AnimatorSet()
        val animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 0.5f, 1f)
        ObjectAnimator.ofFloat(view, "alpha", 0f).start()
        animatorAlpha.duration = 500
        animatorSet.play(animatorAlpha)
        animatorSet.start()
    }

    fun showScale(v: View) {
        showScale(v, null)
    }

    fun showScale(v: View, animListener: AnimListener?) {
        v.animate()
            .scaleY(1F)
            .scaleX(1F)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animListener?.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .start()
    }

    fun hideScale(v: View) {
        fadeOut(v, null)
    }

    fun hideScale(v: View, animListener: AnimListener?) {
        v.animate()
            .scaleY(0F)
            .scaleX(0F)
            .setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    animListener?.onFinish()
                    super.onAnimationEnd(animation)
                }
            })
            .start()
    }

    fun hideFab(fab: View) {
        val moveY: Int = 2 * fab.getHeight()
        fab.animate()
            .translationY(moveY.toFloat())
            .setDuration(300)
            .start()
    }

    fun showFab(fab: View) {
        fab.animate()
            .translationY(0F)
            .setDuration(300)
            .start()
    }

    interface AnimListener {
        fun onFinish()
    }
}