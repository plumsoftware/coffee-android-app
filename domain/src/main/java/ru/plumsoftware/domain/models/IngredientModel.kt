package ru.plumsoftware.domain.models

interface IngredientModel {
    val id: Int
    val name: String
    val description: String
    val measure: String
}