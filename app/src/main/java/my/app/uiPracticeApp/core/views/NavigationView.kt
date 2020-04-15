package my.app.uiPracticeApp.core.views

import my.app.uiPracticeApp.routers.NavigationPlaces

interface NavigationView {
    fun navigate(navigateTo: NavigationPlaces)
}
