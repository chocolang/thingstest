package com.chocolang.android.chocoapp.repository.model

data class GitRepositoryList(
    var number: Int,
    var title: String,
    var body: String,
    var user: GitUser
)

data class GitUser(
    var login: String,
    var avatar_url: String,

)
