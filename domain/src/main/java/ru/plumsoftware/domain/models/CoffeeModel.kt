package ru.plumsoftware.domain.models

interface CoffeeModel {
    val id: Int
    val name: String
    val imageResId: Int
    val type: String
    val isLiked: Int
    val roastingLevel: String
    val description: String
    val ageRating: Int
    val ingredients: List<IngredientModel>
}