package ru.plumsoftware.data.models

import ru.plumsoftware.domain.models.UserModel

data class User(
    override val name: String,
    override val birthday: Long,
    override val theme: Int,
    override val isFirst: Int,
    override val intolerableIngredients: List<Ingredient>,
) : UserModel