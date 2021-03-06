package lz.renatkaitmazov.flickrviewer.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import lz.renatkaitmazov.data.DaggerConstantKt;
import lz.renatkaitmazov.data.model.mapper.Mapper;
import lz.renatkaitmazov.data.repository.IPhotoRepository;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;
import lz.renatkaitmazov.flickrviewer.photodetail.IPhotoDetailFragmentPresenter;
import lz.renatkaitmazov.flickrviewer.photodetail.PhotoDetailFragment;
import lz.renatkaitmazov.flickrviewer.photodetail.PhotoDetailFragmentPresenter;
import lz.renatkaitmazov.flickrviewer.photolist.IPhotoListFragmentPresenter;
import lz.renatkaitmazov.flickrviewer.photolist.PhotoListFragment;
import lz.renatkaitmazov.flickrviewer.photolist.PhotoListFragmentPresenter;
import lz.renatkaitmazov.flickrviewer.search.ISearchFragmentPresenter;
import lz.renatkaitmazov.flickrviewer.search.SearchFragment;
import lz.renatkaitmazov.flickrviewer.search.SearchFragmentPresenter;

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
  static IPhotoListFragmentPresenter providePhotoListPresenter(
    PhotoListFragment fragment,
    @Named(DaggerConstantKt.NAMED_FRG_COMPOSITE_DISPOSABLE)
    CompositeDisposable disposable,
    IPhotoRepository repository,
    @Named(DaggerConstantKt.NAMED_FRG_PHOTO_LIST_ADAPTER_ITEM)
    Mapper mapper) {
    return new PhotoListFragmentPresenter(fragment, disposable, repository, mapper);
  }

  @Provides
  @FragmentScope
  static IPhotoDetailFragmentPresenter providePhotoDetailFragmentPresenter(
    PhotoDetailFragment fragment,
    @Named(DaggerConstantKt.NAMED_FRG_COMPOSITE_DISPOSABLE)
    CompositeDisposable disposable,
    IPhotoRepository repository
  ) {
    return new PhotoDetailFragmentPresenter(fragment, disposable, repository);
  }

  @Provides
  @FragmentScope
  static ISearchFragmentPresenter provideSearchFragmentPresenter(
    SearchFragment fragment,
    @Named(DaggerConstantKt.NAMED_FRG_COMPOSITE_DISPOSABLE)
    CompositeDisposable disposable
  ) {
    return new SearchFragmentPresenter(fragment, disposable);
  }
}
