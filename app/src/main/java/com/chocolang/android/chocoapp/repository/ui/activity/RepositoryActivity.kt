package com.chocolang.android.chocoapp.repository.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chocolang.android.chocoapp.R
import com.chocolang.android.chocoapp.databinding.ActivityRepositoryBinding
import com.chocolang.android.chocoapp.repository.ApiClient
import com.chocolang.android.chocoapp.viewmodel.RepositoryViewModel

const val ARG_REPO_NUMBER = "arg_repo_number"
const val ARG_REPO_LOGIN = "arg_repo_login"
const val ARG_REPO_AVATAR_URL = "arg_repo_avatar_url"
const val ARG_REPO_BODY = "arg_repo_id_body"

class RepositoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepositoryBinding
    private lateinit var viewModel: RepositoryViewModel
    private var number: String? = null
    private var login: String? = null
    private var avatarUrl: String? = null
    private var body: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository)

        viewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)
        viewModel.repo = ApiClient(this)

        intent?.getStringExtra(ARG_REPO_NUMBER)?.run { number = this }
        intent?.getStringExtra(ARG_REPO_LOGIN)?.run { login = this }
        intent?.getStringExtra(ARG_REPO_AVATAR_URL)?.run { avatarUrl = this }
        intent?.getStringExtra(ARG_REPO_BODY)?.run { body = this }

        supportActionBar?.title = "#${number}"

        binding.tvLogin.text = login
        binding.tvBody.text = body
        Glide.with(this)
            .load(avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivAvatarUrl)
    }
}