package ru.plumsoftware.domain.models

interface UserModel {
    val name: String
    val birthday: Long
    val theme: Int
    val isFirst: Int
    val intolerableIngredients: List<IngredientModel>
}