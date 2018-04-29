package lz.renatkaitmazov.flickrviewer.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import lz.renatkaitmazov.data.DaggerConstantKt;
import lz.renatkaitmazov.data.model.mapper.Mapper;
import lz.renatkaitmazov.data.model.mapper.PhotoMapper;
import lz.renatkaitmazov.flickrviewer.di.scope.FragmentScope;
import lz.renatkaitmazov.flickrviewer.photolist.model.PhotoListAdapterItemMapper;

/**
 * All mappers are provided here.
 *
 * @author Renat Kaitmazov
 */
@Module
public final class MapperModule {

  @Provides
  @FragmentScope
  @Named(DaggerConstantKt.NAMED_FRG_RECENT_PHOTO_MAPPER)
  static Mapper provideRecentPhotoMapper() {
    return new PhotoMapper();
  }

  @Provides
  @FragmentScope
  @Named(DaggerConstantKt.NAMED_FRG_PHOTO_LIST_ADAPTER_ITEM)
  static Mapper providePhotoListAdapterItemMapper() {
    return new PhotoListAdapterItemMapper();
  }
}
