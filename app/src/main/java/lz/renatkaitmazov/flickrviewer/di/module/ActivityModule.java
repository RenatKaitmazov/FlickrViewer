package lz.renatkaitmazov.flickrviewer.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lz.renatkaitmazov.flickrviewer.PhotoListActivity;
import lz.renatkaitmazov.flickrviewer.di.scope.ActivityScope;

/**
 * @author Renat Kaitmazov
 */
@Module
public interface ActivityModule {

  @ActivityScope
  @ContributesAndroidInjector()
  PhotoListActivity photoListActivityInjector();
}
