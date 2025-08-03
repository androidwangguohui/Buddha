package com.example.buddha.util

import android.widget.ScrollView
import android.widget.TextView

object  TextUtil {

     fun getWindowText(textView: TextView,scrollView:ScrollView):String{
         val start = textView.layout.getLineStart(getFirstLineIndex(textView,scrollView))
         val end = textView.layout.getLineEnd(getLastLineIndex(textView,scrollView))
         return textView.text.toString().substring(start, end)
     }

    private fun getLastLineIndex(
        textView: TextView,
        scrollView: ScrollView
    ): Int {
        var height = scrollView .height
        var scrollY = scrollView .scrollY
        var layout = textView.layout
        if (layout != null) {
            return layout.getLineForVertical(scrollY + height);
        }
        return -1;
    }

    private fun getFirstLineIndex(
        textView: TextView,
        scrollView: ScrollView
    ): Int {
        var scrollY = scrollView.scrollY
        var layout = textView .layout
        if (layout != null) {
            return layout.getLineForVertical(scrollY);
        }
        return -1;
    }


}