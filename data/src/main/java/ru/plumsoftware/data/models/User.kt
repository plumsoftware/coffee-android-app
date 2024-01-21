package ru.plumsoftware.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.plumsoftware.domain.models.UserModel

@Entity
data class User(
    @PrimaryKey
    override val id: Int = 0,
    @ColumnInfo(defaultValue = "")
    override val name: String = "",
    @ColumnInfo(defaultValue = "")
    override val birthday: Long = 1,
    @ColumnInfo(defaultValue = "false")
    override val theme: Boolean = false,
    @ColumnInfo(defaultValue = "1")
    override val isFirst: Int = 1,
) : UserModel