package lz.renatkaitmazov.flickrviewer.di.module;

import dagger.Module;
import dagger.Provides;
import lz.renatkaitmazov.data.cache.Cache;
import lz.renatkaitmazov.data.device.IConnectivityChecker;
import lz.renatkaitmazov.data.model.mapper.Mapper;
import lz.renatkaitmazov.data.repository.IPhotoRepository;
import lz.renatkaitmazov.data.repository.PhotoRepository;
import lz.renatkaitmazov.data.rest.IPhotoRestRepository;
import lz.renatkaitmazov.data.rest.PhotoRestRepository;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;
import retrofit2.Retrofit;

/**
 * Dependencies related to retrieving data are provided here.
 *
 * @author Renat Kaitmazov
 */
@Module(includes = {MapperModule.class})
public final class RepositoryModule {

  @Provides
  @FragmentScope
  static IPhotoRepository providePhotoRepository(IPhotoRestRepository restRepository) {
    return new PhotoRepository(restRepository);
  }

  @Provides
  @FragmentScope
  static IPhotoRestRepository providePhotoRestRepository(
    Retrofit retrofit,
    IConnectivityChecker checker,
    Cache cache,
    Mapper mapper
  ) {
    return new PhotoRestRepository(retrofit, checker, cache, mapper);
  }
}
