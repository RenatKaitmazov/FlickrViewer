package lz.renatkaitmazov.flickrviewer.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import lz.renatkaitmazov.data.repository.IPhotoRepository;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;
import lz.renatkaitmazov.flickrviewer.photolist.IPhotoListFragmentPresenter;
import lz.renatkaitmazov.flickrviewer.photolist.PhotoListFragment;
import lz.renatkaitmazov.flickrviewer.photolist.PhotoListFragmentPresenter;

/**
 * All implementations of {@link lz.renatkaitmazov.flickrviewer.base.Presenter} should be provided
 * here.
 *
 * @author Renat Kaitmazov
 */
@Module(includes = {
  RxModule.class,
  RepositoryModule.class
})
public final class PresenterModule {


  @Provides
  @FragmentScope
  static IPhotoListFragmentPresenter providePhotoListPresenter(PhotoListFragment fragment,
                                                               @Named(RxModule.NAMED_FOR_FRAGMENT)
                                                               CompositeDisposable disposable,
                                                               IPhotoRepository repository) {
    return new PhotoListFragmentPresenter(fragment, disposable, repository);
  }
}
