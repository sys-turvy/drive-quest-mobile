package com.example.drivequest.domain.mapper

import com.example.drivequest.data.remote.api.ranking.model.RankingUserDto
import com.example.drivequest.domain.model.RankingUser

fun RankingUserDto.toDomain(): RankingUser {
    return RankingUser(
        id = this.id,
        name = this.userName,
        iconUrl = this.userIconUrl
    )
}