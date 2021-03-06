package lz.renatkaitmazov.flickrviewer.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lz.renatkaitmazov.flickrviewer.photodetail.PhotoDetailActivity;
import lz.renatkaitmazov.flickrviewer.photolist.PhotoListActivity;
import lz.renatkaitmazov.flickrviewer.di.scope.ActivityScope;
import lz.renatkaitmazov.flickrviewer.search.SearchActivity;

/**
 * A dagger module for activities.
 * If your activity extends {@link lz.renatkaitmazov.flickrviewer.base.BaseActivity}, then you
 * have to define it here, otherwise the app will crush when launching.
 *
 * @author Renat Kaitmazov
 */
@Module
public interface ActivityModule {

  @ActivityScope
  @ContributesAndroidInjector(modules = {FragmentModule.class})
  PhotoListActivity photoListActivityInjector();

  @ActivityScope
  @ContributesAndroidInjector(modules = {FragmentModule.class})
  PhotoDetailActivity photoDetailActivityInjector();

  @ActivityScope
  @ContributesAndroidInjector(modules = {FragmentModule.class})
  SearchActivity searchActivityInjector();
}
