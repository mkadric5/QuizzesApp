package ba.etf.rma21.projekat.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// Za ljepsi prored i razmak medju elementima u recyclerview-u
class SpaceItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.bottom = spaceHeight
        outRect.right = spaceHeight
    }
}