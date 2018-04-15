package lz.renatkaitmazov.flickrviewer.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import lz.renatkaitmazov.data.DaggerConstantKt;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;

/**
 * Dependencies related to RxJava are provided here.
 *
 * @author Renat Kaitmazov
 */
@Module
public final class RxModule {

  @Provides
  @FragmentScope
  @Named(DaggerConstantKt.NAMED_FRG_COMPOSITE_DISPOSABLE)
  static CompositeDisposable provideCompositeDisposableForFragment() {
    return new CompositeDisposable();
  }
}
