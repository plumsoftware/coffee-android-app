package ru.plumsoftware.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.plumsoftware.domain.models.UserModel

@Entity
data class User(
    @PrimaryKey
    override val id: Int = 0,
    override val name: String,
    override val birthday: Long,
    override val theme: Int,
    override val isFirst: Int,
) : UserModel