package com.onfido.techtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FactFavorite(
    @PrimaryKey val factId: Int
)
