package lz.renatkaitmazov.flickrviewer.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;
import lz.renatkaitmazov.flickrviewer.photolist.PhotoListFragment;

/**
 * A dagger module for fragments.
 * If your fragment extends {@link lz.renatkaitmazov.flickrviewer.base.BaseFragment}, then you
 * have to define it here, otherwise the app will crush when launching.
 *
 * @author Renat Kaitmazov
 */
@Module
public interface FragmentModule {

  @FragmentScope
  @ContributesAndroidInjector(modules = {PresenterModule.class})
  PhotoListFragment photoListFragmentInjection();
}
