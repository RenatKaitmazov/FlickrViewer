package lz.renatkaitmazov.flickrviewer.photolist.model

import lz.renatkaitmazov.data.model.entity.RecentPhotoEntity
import lz.renatkaitmazov.data.model.mapper.Mapper

/**
 * Transforms data layer model into presentation layer model.
 *
 * @author Renat Kaitmazov
 */
class PhotoListAdapterItemMapper : Mapper<List<RecentPhotoEntity>, List<PhotoListAdapterItem>> {

  override fun map(source: List<RecentPhotoEntity>): List<PhotoListAdapterItem> {
    val adapterItems = ArrayList<PhotoListAdapterItem>(source.size)
    source.map(this::map)
      .forEach { adapterItems.add(it) }
    return adapterItems
  }

  private fun map(entity: RecentPhotoEntity): PhotoListAdapterItem {
    return PhotoListThumbnailItem(
      entity.id,
      entity.thumbnailUrl,
      entity.mediumSizeImageUrl
    )
  }
}