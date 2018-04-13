package lz.renatkaitmazov.flickrviewer.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import lz.renatkaitmazov.flickrviewer.FlickViewerApp;
import lz.renatkaitmazov.flickrviewer.di.module.ActivityModule;
import lz.renatkaitmazov.flickrviewer.di.module.DeviceModule;
import lz.renatkaitmazov.flickrviewer.di.module.NetworkModule;

/**
 * Defines the root of the dependency injection tree with {@link Context} being the root.
 *
 * @author Renat Kaitmazov
 */
@Singleton
@Component(modules = {
  AndroidSupportInjectionModule.class,
  ActivityModule.class,
  NetworkModule.class,
  DeviceModule.class
})
public interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder bindContext(Context ctx);

    @BindsInstance
    Builder bindApp(FlickViewerApp app);

    AppComponent build();
  }

  void inject(FlickViewerApp app);
}
