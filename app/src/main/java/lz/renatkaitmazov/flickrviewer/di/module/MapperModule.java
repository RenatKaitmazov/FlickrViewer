package lz.renatkaitmazov.flickrviewer.di.module;

import dagger.Module;
import dagger.Provides;
import lz.renatkaitmazov.data.model.mapper.Mapper;
import lz.renatkaitmazov.data.model.mapper.RecentPhotoMapper;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;

/**
 * All mappers are provided here.
 *
 * @author Renat Kaitmazov
 */
@Module
public final class MapperModule {

  @Provides
  @FragmentScope
  static Mapper provideRecentPhotoMapper() {
    return new RecentPhotoMapper();
  }
}
