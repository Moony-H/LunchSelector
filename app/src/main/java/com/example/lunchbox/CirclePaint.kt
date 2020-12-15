package com.example.lunchbox

import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.view.DragEvent
import android.view.View
import android.widget.RelativeLayout
import net.daum.mf.map.api.MapView

class CirclePaint(val mainActivity: Activity,val mainLayout:RelativeLayout,val mapview:MapView,val view: View) {
    var LocationX=0.0
    var LocationY=0.0
    val dragListener=View.OnDragListener{v,event->
        when(event.action){
            DragEvent.ACTION_DRAG_LOCATION->{
                LocationX=v.x as Double
                LocationY=v.x as Double
                true
            }
            else -> false
        }
    }


    fun Create(){
        view.setOnClickListener(View.OnClickListener {
            // Create a new ClipData.
            // This is done in two steps to provide clarity. The convenience method
            // ClipData.newPlainText() can create a plain text ClipData in one step.

            // Create a new ClipData.Item from the ImageView object's tag
            val item = ClipData.Item(it.tag as? CharSequence)

            // Create a new ClipData using the tag as a label, the plain text MIME type, and
            // the already-created item. This will create a new ClipDescription object within the
            // ClipData, and set its MIME type entry to "text/plain"
            val dragData = ClipData(
                it.tag as? CharSequence,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item)

            // Instantiates the drag shadow builder.
            val myShadow = MyShadowBuilder(it)

            // Starts the drag
            it.startDrag(
                dragData,   // the data to be dragged
                myShadow,   // the drag shadow builder
                null,       // no need to use local data
                0           // flags (not currently used, set to 0)
            )

        })
        view.setOnDragListener(dragListener)
    }


}
private class MyShadowBuilder(v:View):View.DragShadowBuilder(v){
    override fun onDrawShadow(canvas: Canvas?) {
        super.onDrawShadow(canvas)
    }

    override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }


}