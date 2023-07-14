package com.example.findword.extensions

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import com.google.android.material.color.MaterialColors

const val STRING_DEFAULT = ""
const val INT_DEFAULT = 0
const val FLOAT_DEFAULT = 0.0
const val BOOLEAN_DEFAULT = false

private var application: Application? = null

fun setApplication(app: Application) {
    application = app
}

fun getApplication(): Application {
    return application!!
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.setOnSafeClick(listenerBlock: (View) -> Unit) {
    setOnClickListener(DebounceOnClickListener(listenerBlock = listenerBlock))
}

fun getAppString(@StringRes stringId: Int, context: Context? = getApplication()): String {
    return context?.getString(stringId) ?: ""
}

fun getAppString(
    @StringRes resId: Int,
    vararg formatArgs: Any?,
    context: Context? = getApplication()
): String {
    return context?.getString(resId, *formatArgs) ?: ""
}

fun getAppFont(@FontRes fontId: Int, context: Context? = getApplication()): Typeface? {
    context?.let {
        return ResourcesCompat.getFont(context, fontId)
    }
    return null
}

fun getAppDrawable(@DrawableRes drawableId: Int, context: Context? = getApplication()): Drawable? {
    if (context == null) {
        return null
    }
    return ContextCompat.getDrawable(context, drawableId)
}

fun getAppDimensionPixel(@DimenRes dimenId: Int, context: Context? = getApplication()) =
    context?.resources?.getDimensionPixelSize(dimenId) ?: -1

fun getAppDimension(@DimenRes dimenId: Int, context: Context? = getApplication()) =
    context?.resources?.getDimension(dimenId) ?: -1f

fun getAppColor(@ColorRes colorRes: Int, context: Context? = getApplication()) =
    context?.let { ContextCompat.getColor(it, colorRes) } ?: Color.TRANSPARENT

@ColorInt
fun getColorFromAttr(@AttrRes attrColor: Int, context: Context? = getApplication()): Int {
    context?.let {
        return MaterialColors.getColor(it, attrColor, Color.TRANSPARENT)
    }
    return Color.TRANSPARENT
}

fun TextView.textColor(color: Int) {
    setTextColor(getApplication().resources.getColor(color))
}

fun TextView.textHintColor(color: String?) {
    setHintTextColor(Color.parseColor(color))
}

fun View.getCoordinatesX(): Int {
    val location = IntArray(2)
    this.getLocationOnScreen(location)
    return location[0]
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}

fun Float.DpConvertToInt(): Int {
    val scale: Float = getApplication().resources.displayMetrics.density
    return (this * scale + 0.5f) as Int
}

fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
    val childViewStates = SparseArray<Parcelable>()
    children.forEach { child -> child.saveHierarchyState(childViewStates) }
    return childViewStates
}

fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
    children.forEach { child -> child.restoreHierarchyState(childViewStates) }
}

fun ViewGroup.saveInstanceState(state: Parcelable?): Parcelable? {
    return Bundle().apply {
        putParcelable("SUPER_STATE_KEY", state)
        putSparseParcelableArray("SPARSE_STATE_KEY", saveChildViewStates())
    }
}

fun ViewGroup.restoreInstanceState(state: Parcelable?): Parcelable? {
    var newState = state
    if (newState is Bundle) {
        val childrenState = newState.getSparseParcelableArray<Parcelable>("SPARSE_STATE_KEY")
        childrenState?.let { restoreChildViewStates(it) }
        newState = newState.getParcelable("SUPER_STATE_KEY")
    }
    return newState
}

@SuppressLint("UseCompatLoadingForDrawables", "DiscouragedApi")
fun getImage(name: String?): Drawable? {
    return getAppDrawable(
        getApplication().resources.getIdentifier(
            name,
            "drawable",
            getApplication().packageName
        )
    )
}

