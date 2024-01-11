package ru.plumsoftware.domain.models

interface UserModel {
    val id: Int
    val name: String
    val birthday: Long
    val theme: Int
    val isFirst: Int
}