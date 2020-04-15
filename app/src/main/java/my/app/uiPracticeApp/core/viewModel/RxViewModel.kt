package my.app.uiPracticeApp.core.viewModel

import io.reactivex.disposables.CompositeDisposable

// Use it if you don't have to talk to network but still need Rx (RoomDatabase)
abstract class RxViewModel :
    BaseViewModel() {
    protected val mCompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}
