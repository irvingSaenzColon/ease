package com.example.ease.util
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.SearchView


class EmptySubmitSearchView : SearchView {

    private lateinit var mSearchSrcTextView: SearchAutoComplete
    private var listener: OnQueryTextListener? = null

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    )

    override fun setOnQueryTextListener(listener: OnQueryTextListener) {
        super.setOnQueryTextListener(listener)
        this.listener = listener
        mSearchSrcTextView = findViewById(androidx.appcompat.R.id.search_src_text)

        this.mSearchSrcTextView.setOnEditorActionListener(OnEditorActionListener { textView: TextView?, i: Int, keyEvent: KeyEvent? ->
            if (listener != null) {
                listener.onQueryTextSubmit(query.toString())
            }
            true
        })
    }
}