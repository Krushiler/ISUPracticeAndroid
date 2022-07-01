package com.bravedeveloper.domain.model.user.notifications

data class ReplyItems(
    val latest: List<Reply>?,
    val readed: List<Reply>?
)