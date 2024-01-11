package ru.plumsoftware.domain.models

interface CoffeeModel {
    val id: Int
    val name: String
    val history: String
    val roastingLevel: String
    val tastes: String
    val cookingMethod: String
    val description: String
    val ageRating: String
    val ingredients: List<IngredientModel>
}