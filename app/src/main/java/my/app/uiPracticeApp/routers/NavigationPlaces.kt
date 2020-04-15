package my.app.uiPracticeApp.routers

sealed class NavigationPlaces {
    object NavigateBack : NavigationPlaces()
    object ExitApp : NavigationPlaces()
}

// trying to find better solution but for now (all sealed classes must be in the same file)

sealed class SearchNavigationPlaces : NavigationPlaces() {
    object GoToContainerFragment : SearchNavigationPlaces()
}
