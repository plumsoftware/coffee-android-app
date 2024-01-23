package ru.plumsoftware.coffeeapp.di

import org.koin.dsl.module
import ru.plumsoftware.data.repository.CoffeeRepositoryImpl
import ru.plumsoftware.domain.repositories.CoffeeRepository
import ru.plumsoftware.domain.storage.CoffeeStorage
import ru.plumsoftware.domain.usecases.GetAllDrinksUseCase
import ru.plumsoftware.domain.usecases.GetAllIngredientsUseCase

internal val domainModule = module {
    single<CoffeeRepository> { CoffeeRepositoryImpl(context = get()) }

    single<CoffeeStorage> {
        CoffeeStorage(
            getAllIngredientsUseCase = GetAllIngredientsUseCase(coffeeRepository = get()),
            getAllDrinksUseCase = GetAllDrinksUseCase(coffeeRepository = get())
        )
    }
}