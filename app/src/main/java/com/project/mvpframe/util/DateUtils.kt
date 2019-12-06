package com.project.mvpframe.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @CreateDate 2019/12/6 14:48
 * @Author jaylm
 */
object DateUtils {

    /**
     *
     * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
     * 格式的意义如下： 日期和时间模式 <br></br>
     *
     * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     *
     * HH:mm    15:44
     * h:mm a    3:44 下午
     * HH:mm z    15:44 CST
     * HH:mm Z    15:44 +0800
     * HH:mm zzzz    15:44 中国标准时间
     * HH:mm:ss    15:44:40
     * yyyy-MM-dd    2016-08-12
     * yyyy-MM-dd HH:mm    2016-08-12 15:44
     * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     * K:mm a    3:44 下午
     * EEE, MMM d, ''yy    星期五, 八月 12, '16
     * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     * yyMMddHHmmssZ    160812154440+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     *
     * 注意SimpleDateFormat不是线程安全的
     */
    private const val DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss"


    /**
     * 将时间戳转为时间字符串
     * 格式为yyyy-MM-dd HH:mm:ss
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    fun millis2String(millis: Long): String {
        return millis2String(millis, DEFAULT_PATTERN)
    }

    /**
     * 将时间戳转为时间字符串
     * 格式为用户自定义
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    fun millis2String(millis: Long, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(millis))
    }

    /**
     * 将时间字符串转为时间戳
     * 格式为yyyy-MM-dd HH:mm:ss
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    fun string2Millis(time: String): Long {
        return string2Millis(time, DEFAULT_PATTERN)
    }

    /**
     * 将时间字符串转为时间戳
     * 格式为用户自定义
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    fun string2Millis(time: String, pattern: String): Long {
        try {
            return SimpleDateFormat(pattern, Locale.getDefault()).parse(time)!!.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return -1
    }

    /**
     * 将时间字符串转为Date类型
     * 格式为yyyy-MM-dd HH:mm:ss
     * @param time 时间字符串
     * @return Date类型
     */
    fun string2Date(time: String): Date {
        return string2Date(time, DEFAULT_PATTERN)
    }

    /**
     * 将时间字符串转为Date类型
     * 格式为用户自定义
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    fun string2Date(time: String, pattern: String): Date {
        return Date(string2Millis(time, pattern))
    }

    /**
     * 将Date类型转为时间字符串
     * 格式为yyyy-MM-dd HH:mm:ss
     * @param time Date类型时间
     * @return 时间字符串
     */
    fun date2String(time: Date): String {
        return date2String(time, DEFAULT_PATTERN)
    }

    /**
     * 将Date类型转为时间字符串
     * 格式为用户自定义
     * @param time    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    fun date2String(time: Date, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(time)
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    fun date2Millis(time: Date): Long {
        return time.time
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    fun millis2Date(millis: Long): Date {
        return Date(millis)
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     * @return unit时间戳
     */
    private fun millis2Unit(millis: Long, unit: ConstUtils.TimeUnit): Long {
        return when (unit) {
            ConstUtils.TimeUnit.MSEC -> millis / ConstUtils.MSEC
            ConstUtils.TimeUnit.SEC -> millis / ConstUtils.SEC
            ConstUtils.TimeUnit.MIN -> millis / ConstUtils.MIN
            ConstUtils.TimeUnit.HOUR -> millis / ConstUtils.HOUR
            ConstUtils.TimeUnit.DAY -> millis / ConstUtils.DAY
        }
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * time1和time2格式都为yyyy-MM-dd HH:mm:ss
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit  单位类型
     * @return unit时间戳
     */
    fun getTimeSpan(time0: String, time1: String, unit: ConstUtils.TimeUnit): Long {
        return getTimeSpan(time0, time1, unit, DEFAULT_PATTERN)
    }

    /**
     * 获取两个时间差（单位：unit）
     * time1和time2格式都为format
     *
     * @param time0   时间字符串1
     * @param time1   时间字符串2
     * @param unit    单位类型
     * @param pattern 时间格式
     * @return unit时间戳
     */
    fun getTimeSpan(
        time0: String,
        time1: String,
        unit: ConstUtils.TimeUnit,
        pattern: String
    ): Long {
        return millis2Unit(
            Math.abs(string2Millis(time0, pattern) - string2Millis(time1, pattern)),
            unit
        )
    }

    /**
     * 获取两个时间差（单位：unit）
     * time1和time2都为Date类型
     *
     * @param time0 Date类型时间1
     * @param time1 Date类型时间2
     * @param unit  单位类型
     * @return unit时间戳
     */
    fun getTimeSpan(time0: Date, time1: Date, unit: ConstUtils.TimeUnit): Long {
        return millis2Unit(Math.abs(date2Millis(time1) - date2Millis(time0)), unit)
    }

    /**
     * 获取当前时间戳
     *
     * @return 毫秒时间戳
     */
    fun getNowTimeMills(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获取当前时间字符串
     * 格式为yyyy-MM-dd HH:mm:ss
     * @return 时间字符串
     */
    fun getNowTimeString(): String {
        return date2String(Date())
    }

    /**
     * 获取当前时间字符串
     * 格式为用户自定义
     * @param pattern 时间格式
     * @return 时间字符串
     */
    fun getNowTimeString(pattern: String): String {
        return date2String(Date(), pattern)
    }

    /**
     * 获取当前时间Date
     * Date类型
     * @return Date类型时间
     */
    fun getNowTimeDate(): Date {
        return Date()
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * time格式为yyyy-MM-dd HH:mm:ss
     * @param time 时间字符串
     * @param unit 单位类型
     * @return unit时间戳
     */
    fun getTimeSpanByNow(time: String, unit: ConstUtils.TimeUnit): Long {
        return getTimeSpanByNow(time, unit, DEFAULT_PATTERN)
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * time格式为format
     *
     * @param time    时间字符串
     * @param unit    单位类型
     * @param pattern 时间格式
     * @return unit时间戳
     */
    fun getTimeSpanByNow(time: String, unit: ConstUtils.TimeUnit, pattern: String): Long {
        return getTimeSpan(getNowTimeString(), time, unit, pattern)
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * time为Date类型
     * @param time Date类型时间
     * @param unit 单位类型
     * @return unit时间戳
     */
    fun getTimeSpanByNow(time: Date, unit: ConstUtils.TimeUnit): Long {
        return getTimeSpan(getNowTimeDate(), time, unit)
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param time 时间字符串
     * @return
     *  如果小于1秒钟内，显示刚刚
     *  如果在1分钟内，显示XXX秒前
     *  如果在1小时内，显示XXX分钟前
     *  如果在1小时外的今天内，显示今天15:32
     *  如果是昨天的，显示昨天15:32
     *  其余显示，2016-10-15
     *  时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    fun getFriendlyTimeSpanByNow(time: String): String {
        return getFriendlyTimeSpanByNow(time, DEFAULT_PATTERN)
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param time 时间字符串
     * @return
     *  如果小于1秒钟内，显示刚刚
     *  如果在1分钟内，显示XXX秒前
     *  如果在1小时内，显示XXX分钟前
     *  如果在1小时外的今天内，显示今天15:32
     *  如果是昨天的，显示昨天15:32
     *  其余显示，2016-10-15
     *  时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    fun getFriendlyTimeSpanByNow(time: String, pattern: String): String {
        return getFriendlyTimeSpanByNow(string2Millis(time, pattern))
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param time Date类型时间
     * @return
     *  如果小于1秒钟内，显示刚刚
     *  如果在1分钟内，显示XXX秒前
     *  如果在1小时内，显示XXX分钟前
     *  如果在1小时外的今天内，显示今天15:32
     *  如果是昨天的，显示昨天15:32
     *  其余显示，2016-10-15
     *  时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    fun getFriendlyTimeSpanByNow(time: Date): String {
        return getFriendlyTimeSpanByNow(time.time)
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return
     *  如果小于1秒钟内，显示刚刚
     *  如果在1分钟内，显示XXX秒前
     *  如果在1小时内，显示XXX分钟前
     *  如果在1小时外的今天内，显示今天15:32
     *  如果是昨天的，显示昨天15:32
     *  其余显示，2016-10-15
     *  时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007
     *
     */
    @SuppressLint("DefaultLocale")
    fun getFriendlyTimeSpanByNow(millis: Long): String {
        val now = System.currentTimeMillis()
        val span = now - millis
        if (span < 0) return String.format("%tc", Date(millis))
        // 获取当天00:00
        val wee = now / ConstUtils.DAY * ConstUtils.DAY
        return when {
            span < 1000 -> "刚刚"
            span < ConstUtils.MIN -> String.format("%d秒前", span / ConstUtils.SEC)
            span < ConstUtils.HOUR -> String.format("%d分钟前", span / ConstUtils.MIN)
            millis >= wee -> String.format("今天%tR", Date(millis))
            millis >= wee - ConstUtils.DAY -> String.format("昨天%tR", Date(millis))
            else -> String.format("%tF", Date(millis))
        }
    }

    /**
     * 判断是否同一天
     *
     * @param millis 毫秒时间戳
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isSameDay(millis: Long): Boolean {
        val wee = System.currentTimeMillis() / ConstUtils.DAY * ConstUtils.DAY
        return millis >= wee && millis < wee + ConstUtils.DAY
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return `true`: 闰年<br></br>`false`: 平年
     */
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * 获取星期
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 星期
     */
    fun getWeek(time: String): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time))
    }

    /**
     * 获取星期
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 星期
     */
    fun getWeek(time: String, pattern: String): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(string2Date(time, pattern))
    }

    /**
     * 获取星期
     *
     * @param time Date类型时间
     * @return 星期
     */
    fun getWeek(time: Date): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(time)
    }

    /**
     * 获取星期
     * 注意：周日的Index才是1，周六为7
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 1...5
     */
    fun getWeekIndex(time: String): Int {
        val date = string2Date(time)
        return getWeekIndex(date)
    }

    /**
     * 获取星期
     * 注意：周日的Index才是1，周六为7
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...7
     */
    fun getWeekIndex(time: String, pattern: String): Int {
        val date = string2Date(time, pattern)
        return getWeekIndex(date)
    }

    /**
     * 获取星期
     * 注意：周日的Index才是1，周六为7
     *
     * @param time Date类型时间
     * @return 1...7
     */
    fun getWeekIndex(time: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = time
        return cal.get(Calendar.DAY_OF_WEEK)
    }

    /**
     * 获取月份中的第几周
     * 注意：国外周日才是新的一周的开始
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 1...5
     */
    fun getWeekOfMonth(time: String): Int {
        val date = string2Date(time)
        return getWeekOfMonth(date)
    }

    /**
     * 获取月份中的第几周
     * 注意：国外周日才是新的一周的开始
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...5
     */
    fun getWeekOfMonth(time: String, pattern: String): Int {
        val date = string2Date(time, pattern)
        return getWeekOfMonth(date)
    }

    /**
     * 获取月份中的第几周
     * 注意：国外周日才是新的一周的开始
     *
     * @param time Date类型时间
     * @return 1...5
     */
    fun getWeekOfMonth(time: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = time
        return cal.get(Calendar.WEEK_OF_MONTH)
    }

    /**
     * 获取年份中的第几周
     * 注意：国外周日才是新的一周的开始
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 1...54
     */
    fun getWeekOfYear(time: String): Int {
        val date = string2Date(time)
        return getWeekOfYear(date)
    }

    /**
     * 获取年份中的第几周
     * 注意：国外周日才是新的一周的开始
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...54
     */
    fun getWeekOfYear(time: String, pattern: String): Int {
        val date = string2Date(time, pattern)
        return getWeekOfYear(date)
    }

    /**
     * 获取年份中的第几周
     * 注意：国外周日才是新的一周的开始
     *
     * @param time Date类型时间
     * @return 1...54
     */
    fun getWeekOfYear(time: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = time
        return cal.get(Calendar.WEEK_OF_YEAR)
    }
}