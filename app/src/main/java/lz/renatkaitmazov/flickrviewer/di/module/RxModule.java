package lz.renatkaitmazov.flickrviewer.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;

/**
 * Dependencies related to RxJava are provided here.
 *
 * @author Renat Kaitmazov
 */
@Module
public final class RxModule {

  public static final String NAMED_FOR_FRAGMENT = "CompositeDisposable for fragment";

  @Provides
  @FragmentScope
  @Named(NAMED_FOR_FRAGMENT)
  static CompositeDisposable provideCompositeDisposableForFragment() {
    return new CompositeDisposable();
  }
}
