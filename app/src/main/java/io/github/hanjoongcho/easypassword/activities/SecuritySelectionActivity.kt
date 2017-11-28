package io.github.hanjoongcho.easypassword.activities

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.github.hanjoongcho.easypassword.R
import io.github.hanjoongcho.easypassword.databinding.ActivitySecuritySelectionBinding
import io.github.hanjoongcho.easypassword.fragment.SecuritySelectionFragment
import io.github.hanjoongcho.easypassword.helper.EasyPasswordHelper
import io.github.hanjoongcho.easypassword.helper.findFragmentById
import io.github.hanjoongcho.easypassword.helper.replaceFragment
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Created by Administrator on 2017-11-15.
 */

class SecuritySelectionActivity : CommonActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        DataBindingUtil
                .setContentView<ActivitySecuritySelectionBinding>(this,
                        R.layout.activity_security_selection)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            title = getString(R.string.security_selection_title)
        }

        attachCategoryGridFragment()
        supportPostponeEnterTransition()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.security_selection, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.setting -> {
                EasyPasswordHelper.startSettingActivityWithTransition(this@SecuritySelectionActivity, SettingActivity::class.java)
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        Log.i(IntroActivity.TAG, "SecuritySelectionActivity")
        super.onResume()
    }

    private fun attachCategoryGridFragment() {
        replaceFragment(R.id.category_container,
                findFragmentById(R.id.category_container) ?: SecuritySelectionFragment())
    }

    companion object {

        fun start(activity: Activity) {
            ActivityCompat.startActivity(activity,
                    Intent(activity, SecuritySelectionActivity::class.java),
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle())
        }
    }
}