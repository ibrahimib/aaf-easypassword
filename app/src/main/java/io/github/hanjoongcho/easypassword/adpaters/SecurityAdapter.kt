package io.github.hanjoongcho.easypassword.adpaters

import android.app.Activity
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import io.github.hanjoongcho.easypassword.R
import io.github.hanjoongcho.easypassword.databinding.ItemSecurityBinding
import io.github.hanjoongcho.easypassword.helper.EasyPasswordHelper
import io.github.hanjoongcho.easypassword.helper.database
import io.github.hanjoongcho.easypassword.models.Security
import io.github.hanjoongcho.easypassword.models.Theme

/**
 * Created by Administrator on 2017-11-17.
 */

class SecurityAdapter(
        private val activity: Activity,
        private val onItemClickListener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<SecurityAdapter.ViewHolder>() {

    private val resources = activity.resources
    private val layoutInflater = LayoutInflater.from(activity)
    private var securities = mutableListOf<Security>()

    fun selectAccounts() {

        securities.clear()
        securities.addAll(activity.database().selectSecurityAll())
    }

    fun selectAccounts(searchKey: String) {

        securities.clear()
        securities.addAll(activity.database().selectSecuritiesBy(searchKey))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.binding) {
            security = securities[position]
            executePendingBindings()
            setCategoryIcon(security, categoryIcon)
            EasyPasswordHelper.setPasswordStrengthLevel(activity, security.passwordStrengthLevel, included.level1, included.level2, included.level3, included.level4, included.level5)
            with(accountTitle) {
                setTextColor(EasyPasswordHelper.getColor(R.color.blackText, activity))
            }
            with(accountSummary) {
                setTextColor(EasyPasswordHelper.getColor(Theme.white.textPrimaryColor, activity))
                setBackgroundColor(EasyPasswordHelper.getColor(Theme.white.primaryColor, activity))
            }
        }
        with(holder.itemView) {
            setBackgroundColor(EasyPasswordHelper.getColor(Theme.white.windowBackgroundColor, activity))
            setOnClickListener {
                onItemClickListener.onItemClick(null, it, holder.adapterPosition, holder.itemId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(DataBindingUtil.inflate<ItemSecurityBinding>(layoutInflater,
                    R.layout.item_security,
                    parent,
                    false))

    override fun getItemCount() = securities.size

    fun getItem(position: Int): Security = securities[position]

    private fun setCategoryIcon(security: Security, icon: ImageView) {

        val imageRes = resources.getIdentifier("ic_${security.category?.resourceName}", "drawable", activity.packageName)
        icon.setImageResource(imageRes)
    }

    class ViewHolder(val binding: ItemSecurityBinding) : RecyclerView.ViewHolder(binding.root)
}