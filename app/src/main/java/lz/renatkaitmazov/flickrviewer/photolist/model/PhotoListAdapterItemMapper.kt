package lz.renatkaitmazov.flickrviewer.photolist.model

import lz.renatkaitmazov.data.model.entity.PhotoEntity
import lz.renatkaitmazov.data.model.mapper.Mapper

/**
 * Transforms data layer model into presentation layer model.
 *
 * @author Renat Kaitmazov
 */
class PhotoListAdapterItemMapper : Mapper<List<PhotoEntity>, List<PhotoListAdapterItem>> {

  override fun map(source: List<PhotoEntity>): List<PhotoListAdapterItem> {
    val adapterItems = ArrayList<PhotoListAdapterItem>(source.size)
    source.map(this::map)
      .forEach { adapterItems.add(it) }
    return adapterItems
  }

  private fun map(entity: PhotoEntity): PhotoListAdapterItem {
    return PhotoListThumbnailItem(
      entity.id,
      entity.thumbnailUrl,
      entity.mediumSizeImageUrl
    )
  }
}