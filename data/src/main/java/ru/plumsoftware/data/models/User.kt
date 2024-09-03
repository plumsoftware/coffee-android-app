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
    @ColumnInfo(defaultValue = "1L")
    override val birthday: Long = 1L,
    @ColumnInfo(defaultValue = "false")
    override val theme: Boolean = false,
    @ColumnInfo(defaultValue = "1")
    override val isFirst: Int = 1,
    @ColumnInfo(defaultValue = "0L")
    override val agreeDate: Long = 0L,
) : UserModel