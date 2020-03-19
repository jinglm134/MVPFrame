package com.project.mvpframe.util.helper

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.DigitsKeyListener

/**
 * @CreateDate 2019/12/24 14:34
 * @Author jaylm
 */
class InputListener : DigitsKeyListener(false, true) {

    private var digits = 2//默认小数点后两位
    private var maxLength = 12//默认位数

    fun setDigits(d: Int): InputListener {
        digits = d
        return this
    }

    fun setMaxLength(length: Int): InputListener {
        maxLength = length
        return this
    }


    override fun filter(
        source: CharSequence, start: Int, end: Int,
        dest: Spanned, dstart: Int, dend: Int
    ): CharSequence {
        //CharSequence source,  //输入的文字
        //int start,  //开始位置
        //int end,  //结束位置
        //Spanned dest, //当前显示的内容
        //int dstart,  //当前开始位置
        //int dend //当前结束位置
        //return CharSequence //当前改变返回的值

        if (dest.isEmpty() && ".".contentEquals(source)) {
            //直接输入小数点,前面加0
            return "0."
        }

        val dValue = dest.toString()
        val splitArray = dValue.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (dValue.length == maxLength) {//最大位数
            return ""
        }
        if (splitArray.size > 1) {
            val dotValue = splitArray[1]
            if (dotValue.length == digits) {//输入框小数的位数
                return ""
            }
        }
        return SpannableStringBuilder(source, start, end)
    }
}/*android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? Locale.CHINA : null,*/
