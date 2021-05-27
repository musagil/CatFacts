package com.onfido.techtask.factlist

data class FactListItem(
    val text: String,
    val isVerified: Boolean,
    val isNew: Boolean,
    val createdAt: String,
    val isFavorite: Boolean = false
) : com.onfido.techtask.BindableItem {
    override val id: Int = text.hashCode()
}
