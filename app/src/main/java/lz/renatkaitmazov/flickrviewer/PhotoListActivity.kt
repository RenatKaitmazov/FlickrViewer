package lz.renatkaitmazov.flickrviewer

import android.os.Bundle
import lz.renatkaitmazov.flickrviewer.base.BaseActivity

class PhotoListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_list)
    }
}
