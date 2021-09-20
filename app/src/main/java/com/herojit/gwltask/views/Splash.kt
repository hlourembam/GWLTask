package com.herojit.gwl.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.herojit.gwltask.R
import kotlinx.android.synthetic.main.splash.*

class Splash : AppCompatActivity() {
    //    var binding: SplashBinding? = null;
    var zoomin: Animation? = null
    var zoomout: Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin)
        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout)
        txt_title.setAnimation(zoomin);

        Handler().postDelayed({ // This method will be executed once the timer is over
            val `in` = Intent(this@Splash, MainDashboard::class.java)
            startActivity(`in`)
            finish()
        }, 3000)

    }


}