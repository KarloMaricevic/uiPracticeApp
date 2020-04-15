package my.app.uiPracticeApp.data.entities

data class Location(
    val id: Int,
    val howManyDays: Int,
    val howMuchItCosts: Int,
    val whatCity: City,
    val isBookmarked: Boolean
)
