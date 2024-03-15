package com.example.brewvault

import java.io.Serializable

data class Beer(
    val id: Int,
    val user: String?,
    val brewery: String?,
    val name: String?,
    val style: String?,
    val abv: Double,
    val volume: Double,
    val pictureUrl: String?,
    val howMany: Int
): Serializable {
    override fun toString(): String {
        val userText = user ?: "Unknown"
        val breweryText = brewery ?: "Unknown"
        val nameText = name ?: "Unknown"
        val styleText = style ?: "Unknown"
        val pictureUrlText = pictureUrl ?: "Unknown"
        return "$nameText: $styleText: $abv%: $volume ml: $howMany"
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (user?.hashCode() ?: 0)
        result = 31 * result + (brewery?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (style?.hashCode() ?: 0)
        result = 31 * result + abv.hashCode()
        result = 31 * result + volume.hashCode()
        result = 31 * result + (pictureUrl?.hashCode() ?: 0)
        result = 31 * result + howMany
        return result
    }
}