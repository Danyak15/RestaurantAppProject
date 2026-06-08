package com.example.restaurantapp.di

import com.example.restaurantapp.data.repository.AccountRepositoryImpl
import com.example.restaurantapp.data.repository.CategoriesRepositoryImpl
import com.example.restaurantapp.data.repository.DishesRepositoryImpl
import com.example.restaurantapp.data.repository.FavoriteDishRepositoryImpl
import com.example.restaurantapp.data.repository.NewsRepositoryImpl
import com.example.restaurantapp.data.repository.ReservationRepositoryImpl
import com.example.restaurantapp.data.repository.RestaurantsRepositoryImpl
import com.example.restaurantapp.data.repository.SyncRepositoryImpl
import com.example.restaurantapp.domain.repository.AccountRepository
import com.example.restaurantapp.domain.repository.CategoriesRepository
import com.example.restaurantapp.domain.repository.DishesRepository
import com.example.restaurantapp.domain.repository.FavoriteDishRepository
import com.example.restaurantapp.domain.repository.NewsRepository
import com.example.restaurantapp.domain.repository.ReservationRepository
import com.example.restaurantapp.domain.repository.RestaurantsRepository
import com.example.restaurantapp.domain.repository.SyncRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAccountRepository(
        impl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    abstract fun bindRestaurantsRepository(
        impl: RestaurantsRepositoryImpl
    ): RestaurantsRepository

    @Binds
    @Singleton
    abstract fun bindCategoriesRepository(
        impl: CategoriesRepositoryImpl
    ): CategoriesRepository

    @Binds
    @Singleton
    abstract fun bindDishesRepository(
        impl: DishesRepositoryImpl
    ): DishesRepository

    @Binds
    @Singleton
    abstract fun bindSyncRepository(
        impl: SyncRepositoryImpl
    ): SyncRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteDishRepository(
        impl: FavoriteDishRepositoryImpl
    ): FavoriteDishRepository

    @Binds
    @Singleton
    abstract fun bindReservationRepository(
        impl: ReservationRepositoryImpl
    ): ReservationRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository
}