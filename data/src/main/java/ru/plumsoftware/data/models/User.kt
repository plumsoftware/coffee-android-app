package ru.plumsoftware.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.plumsoftware.domain.models.UserModel

@Entity
data class User(
    @PrimaryKey
    override val id: Int = 0,
    override val name: String = "",
    override val birthday: Long = 1,
    override val theme: Int = 1,
    override val isFirst: Int = 1,
) : UserModel