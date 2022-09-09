package com.demo.spacedata.activity

import android.animation.ValueAnimator
import android.content.Intent
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import com.blankj.utilcode.util.ActivityUtils
import com.demo.spacedata.R
import com.demo.spacedata.base.AcBase0906
import kotlinx.android.synthetic.main.ac_0906_launch.*

class Ac0906Launch : AcBase0906(R.layout.ac_0906_launch) {
    private var launch0906Animator: ValueAnimator?=null

    override fun viewCreated() {
        launch0906Animator=ValueAnimator.ofInt(0, 100).apply {
            duration=2000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val progress = it.animatedValue as Int
                launch_progress.progress = progress
            }
            doOnEnd {
                toConnectAc()
            }
            start()
        }
    }

    private fun toConnectAc(){
        val activityExistsInStack = ActivityUtils.isActivityExistsInStack(Ac0906Connect::class.java)
        if (!activityExistsInStack){
            startActivity(Intent(this,Ac0906Connect::class.java))
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        launch0906Animator?.resume()
    }

    override fun onPause() {
        super.onPause()
        launch0906Animator?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAnimator()
    }

    private fun stopAnimator(){
        launch0906Animator?.removeAllUpdateListeners()
        launch0906Animator?.cancel()
        launch0906Animator=null
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            return true
        }
        return false
    }
}