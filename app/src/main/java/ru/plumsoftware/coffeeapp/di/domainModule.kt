package ru.plumsoftware.coffeeapp.di

import org.koin.dsl.module
import ru.plumsoftware.data.repository.CoffeeRepositoryImpl
import ru.plumsoftware.data.repository.SharedPreferencesRepositoryImpl
import ru.plumsoftware.domain.repositories.CoffeeRepository
import ru.plumsoftware.domain.repositories.SharedPreferencesRepository
import ru.plumsoftware.domain.storage.CoffeeStorage
import ru.plumsoftware.domain.storage.SharedPreferencesStorage
import ru.plumsoftware.domain.usecases.drinks.GetAllDrinksUseCase
import ru.plumsoftware.domain.usecases.drinks.GetAllIngredientsUIUseCase
import ru.plumsoftware.domain.usecases.drinks.GetAllIngredientsUseCase
import ru.plumsoftware.domain.usecases.drinks.GetRandomDrinkUseCase
import ru.plumsoftware.domain.usecases.drinks.GetTagsUseCase
import ru.plumsoftware.domain.usecases.drinks.ToMatrixUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.GetUserUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetAgreeDateUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetBirthdayUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetIsFirstUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetNameUseCase
import ru.plumsoftware.domain.usecases.sharedpreferences.SetThemeUseCase

internal val domainModule = module {
    single<CoffeeRepository> { CoffeeRepositoryImpl(context = get()) }
    single<CoffeeStorage> {
        CoffeeStorage(
            getAllIngredientsUseCase = GetAllIngredientsUseCase(coffeeRepository = get()),
            getAllDrinksUseCase = GetAllDrinksUseCase(coffeeRepository = get()),
            getTagsUseCase = GetTagsUseCase(coffeeRepository = get()),
            toMatrixUseCase = ToMatrixUseCase(coffeeRepository = get()),
            getRandomDrinkUseCase = GetRandomDrinkUseCase(coffeeRepository = get()),
            getAllIngredientsUIUseCase = GetAllIngredientsUIUseCase(coffeeRepository = get())
        )
    }

    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(context = get()) }
    single<SharedPreferencesStorage> {
        SharedPreferencesStorage(
            getUserUseCase = GetUserUseCase(sharedPreferencesRepository = get()),
            setBirthdayUseCase = SetBirthdayUseCase(sharedPreferencesRepository = get()),
            setIsFirstUseCase = SetIsFirstUseCase(sharedPreferencesRepository = get()),
            setNameUseCase = SetNameUseCase(sharedPreferencesRepository = get()),
            setThemeUseCase = SetThemeUseCase(sharedPreferencesRepository = get()),
            setAgreeDateUseCase = SetAgreeDateUseCase(sharedPreferencesRepository = get())
        )
    }
}