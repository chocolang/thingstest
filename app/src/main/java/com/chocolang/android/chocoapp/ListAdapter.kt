package com.chocolang.android.chocoapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolang.android.chocoapp.databinding.ViewPerson2Binding
import com.chocolang.android.chocoapp.databinding.ViewPersonBinding
import com.chocolang.android.chocoapp.repository.model.GitRepository
import com.chocolang.android.chocoapp.repository.model.GitRepositoryList
import com.chocolang.android.chocoapp.repository.ui.activity.*

class ListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ViewPersonBinding
    private lateinit var binding2: ViewPerson2Binding
    private val items: ArrayList<GitRepositoryList> = arrayListOf()

    fun add(item: GitRepositoryList) {
        this.items.add(item)
        notifyItemInserted(this.items.size)
    }

    fun addAll(items: List<GitRepositoryList>) {
        val preSize = items.size
        this.items.addAll(items)
        notifyItemRangeChanged(preSize, items.size)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    /** default */
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        var itemPhoto: ImageView = itemView.findViewById(R.id.iv_person_photo)
//        var itemName: TextView = itemView.findViewById(R.id.tv_person_name)
//        var itemLocation: TextView = itemView.findViewById(R.id.tv_person_location)
//    }

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("jwlee", viewType.toString())
        when(viewType) {
            5 -> {
                binding2 = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.view_person2,
                    parent,
                    false
                )
                return ViewHolder2(binding2)
            }
            else -> {
                binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.view_person,
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
        /** default */
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_person, parent, false)
//        return ViewHolder()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder) {
            holder.bindUpdate(items[position])
        } else if(holder is ViewHolder2) {
            holder.bindUpdate(items[position])
        }

        /** default */
//        holder.itemName.text = items[position].id.toString()
//        holder.itemLocation.text = items[position].name
//        Glide.with(context)
//            .load(items[position].owner.avatar_url)
//            .into(holder.itemPhoto)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val binding: ViewPersonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindUpdate(item: GitRepositoryList) {
            binding.setVariable(BR.myRepository, item)
            binding.setVariable(BR.uiController, this)
//            Glide.with(context)
//                .load(item.owner.avatar_url)
//                .into(binding.ivPersonPhoto)
            binding.root.setOnClickListener {
                context.startActivity(Intent(context, RepositoryActivity::class.java).apply {
                    putExtra(ARG_REPO_NUMBER, item.number.toString())
                    putExtra(ARG_REPO_AVATAR_URL, item.user.avatar_url)
                    putExtra(ARG_REPO_LOGIN, item.user.login)
                    putExtra(ARG_REPO_BODY, item.body)
                })
            }
        }

//        fun onClickRepositoryItem() {
//            context.startActivity(Intent(context, RepositoryActivity::class.java))
//        }
    }

    inner class ViewHolder2(private val binding2: ViewPerson2Binding) : RecyclerView.ViewHolder(binding2.root) {
        fun bindUpdate(item: GitRepositoryList) {
            binding2.ivHello
//            binding2.setVariable(BR.myRepository, item)
//            binding2.setVariable(BR.uiController, this)
            Glide.with(context)
                .load("https://s3.ap-northeast-2.amazonaws.com/hellobot-kr-test/image/main_logo.png")
                .into(binding2.ivHello)
            binding2.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://thingsflow.com/ko/home"))
                context.startActivity(intent)

//                context.startActivity(Intent(context, RepositoryActivity::class.java).apply {
//                    putExtra(ARG_REPO_NUMBER, item.number.toString())
//                    putExtra(ARG_REPO_AVATAR_URL, item.user.avatar_url)
//                    putExtra(ARG_REPO_LOGIN, item.user.login)
//                    putExtra(ARG_REPO_BODY, item.body)
//                })
            }
        }

//        fun onClickRepositoryItem() {
//            context.startActivity(Intent(context, RepositoryActivity::class.java))
//        }
    }
}