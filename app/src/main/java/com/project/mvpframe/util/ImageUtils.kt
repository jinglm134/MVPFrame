package com.project.mvpframe.util


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.project.mvpframe.app.MvpApp
import com.project.mvpframe.util.encrypt.CloseUtils
import org.jetbrains.annotations.NotNull
import java.io.*
import kotlin.math.abs


/**
 * 图片处理: 压缩、保存、图片的各种转换
 * @CreateDate 2020/3/27 16:58
 * @Author jaylm
 */
object ImageUtils {

    /**
     * 计算采样大小
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private fun calculateInSampleSize(options: BitmapFactory.Options,
                                      maxWidth: Int,
                                      maxHeight: Int): Int {
        if (maxWidth == 0 || maxHeight == 0) return 1
        var height = options.outHeight
        var width = options.outWidth
        var inSampleSize = 1

        height = height shr 1
        width = width shr 1
        while (height >= maxHeight && width >= maxWidth) {
            height = height shr 1
            width = width shr 1
            inSampleSize = inSampleSize shl 1
        }

        return inSampleSize
    }

    /**
     * 从文件中获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    fun getBitmap(file: File?): Bitmap? {
        if (file == null) return null
        var inputStream: InputStream? = null
        return try {
            inputStream = BufferedInputStream(FileInputStream(file))
            BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        } finally {
            CloseUtils.closeIO(inputStream)
        }
    }

    /**
     * 从文件中获取指定大小的bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    fun getBitmap(file: File?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (file == null) return null
        var inputStream: InputStream? = null
        return try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            inputStream = BufferedInputStream(FileInputStream(file))
            BitmapFactory.decodeStream(inputStream, null, options)
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            BitmapFactory.decodeStream(inputStream, null, options)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        } finally {
            CloseUtils.closeIO(inputStream)
        }
    }


    /**
     * 从文件路径中获取指定大小的bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    fun getBitmap(filePath: String, maxWidth: Int, maxHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }


    /**
     * 缩放图片
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放后的图片
     */
    @JvmOverloads
    fun scale(@NotNull src: Bitmap, newWidth: Int,
              newHeight: Int,
              recycle: Boolean = false): Bitmap {
        val ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放后的图片
     */
    @JvmOverloads
    fun scale(@NotNull src: Bitmap, scaleWidth: Float,
              scaleHeight: Float,
              recycle: Boolean = false): Bitmap {
        val matrix = Matrix()
        matrix.setScale(scaleWidth, scaleHeight)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 裁剪图片
     *
     * @param src     源图片
     * @param x       开始坐标x
     * @param y       开始坐标y
     * @param width   裁剪宽度
     * @param height  裁剪高度
     * @param recycle 是否回收
     * @return 裁剪后的图片
     */
    @JvmOverloads
    fun clip(@NotNull src: Bitmap, x: Int,
             y: Int,
             width: Int,
             height: Int,
             recycle: Boolean = false): Bitmap {
        val ret = Bitmap.createBitmap(src, x, y, width, height)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }


    /**
     * 倾斜图片
     *
     * @param src     源图片
     * @param kx      倾斜因子x
     * @param ky      倾斜因子y
     * @param px      平移因子x
     * @param py      平移因子y
     * @param recycle 是否回收
     * @return 倾斜后的图片
     */
    @JvmOverloads
    fun skew(@NotNull src: Bitmap, kx: Float,
             ky: Float,
             px: Float = 0f,
             py: Float = 0f,
             recycle: Boolean = false): Bitmap? {
        val matrix = Matrix()
        matrix.setSkew(kx, ky, px, py)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @param recycle 是否回收
     * @return 旋转后的图片
     */
    @JvmOverloads
    fun rotate(@NotNull src: Bitmap, degrees: Int,
               px: Float,
               py: Float,
               recycle: Boolean = false): Bitmap {
        if (degrees == 0) return src
        val matrix = Matrix()
        matrix.setRotate(degrees.toFloat(), px, py)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }


    /**
     * 转为圆形图片
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return 圆形图片
     */
    @JvmOverloads
    fun toRound(@NotNull src: Bitmap, recycle: Boolean = false): Bitmap {
        val width = src.width
        val height = src.height
        val radius = width.coerceAtMost(height) shr 1
        val ret = Bitmap.createBitmap(width, height, src.config)
        val paint = Paint()
        val canvas = Canvas(ret)
        val rect = Rect(0, 0, width, height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle((width shr 1).toFloat(),
            (height shr 1).toFloat(),
            radius.toFloat(),
            paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(src, rect, rect, paint)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 转为圆角图片
     *
     * @param src     源图片
     * @param radius  圆角的度数
     * @param recycle 是否回收
     * @return 圆角图片
     */
    @JvmOverloads
    fun toRoundCorner(@NotNull src: Bitmap, radius: Float, recycle: Boolean = false): Bitmap {
        val width = src.width
        val height = src.height
        val ret = Bitmap.createBitmap(width, height, src.config)
        val paint = Paint()
        val canvas = Canvas(ret)
        val rect = Rect(0, 0, width, height)
        paint.isAntiAlias = true
        canvas.drawRoundRect(RectF(rect), radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(src, rect, rect, paint)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 快速模糊图片
     *
     * 先缩小原图，对小图进行模糊，再放大回原先尺寸
     *
     * @param context 上下文
     * @param src     源图片
     * @param scale   缩放比例(0...1)
     * @param radius  模糊半径
     * @param recycle 是否回收
     * @return 模糊后的图片
     */
    @SuppressLint("ObsoleteSdkInt")
    @JvmOverloads
    fun fastBlur(context: Context, @NotNull src: Bitmap,
                 scale: Float,
                 radius: Float,
                 recycle: Boolean = false): Bitmap {
        val width = src.width
        val height = src.height
        val scaleWidth = (width * scale + 0.5f).toInt()
        val scaleHeight = (height * scale + 0.5f).toInt()
        if (scaleWidth == 0 || scaleHeight == 0) return src
        var scaleBitmap: Bitmap = Bitmap.createScaledBitmap(src, scaleWidth, scaleHeight, true)
        val paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
        val canvas = Canvas()
        val filter = PorterDuffColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_ATOP)
        paint.colorFilter = filter
        canvas.scale(scale, scale)
        canvas.drawBitmap(scaleBitmap, 0f, 0f, paint)
        scaleBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            renderScriptBlur(context, scaleBitmap, radius)
        } else {
            stackBlur(scaleBitmap, radius.toInt(), recycle)
        }
        if (scale == 1f) return scaleBitmap
        val ret = Bitmap.createScaledBitmap(scaleBitmap, width, height, true)
        if (!scaleBitmap.isRecycled) scaleBitmap.recycle()
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * renderScript模糊图片
     *
     * API大于17
     *
     * @param context 上下文
     * @param src     源图片
     * @param radius  模糊度(1...25)
     * @return 模糊后的图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun renderScriptBlur(context: Context, src: Bitmap, radius: Float): Bitmap {
        var rs: RenderScript? = null
        var blur = radius
        try {
            rs = RenderScript.create(context)
            rs?.messageHandler = RenderScript.RSMessageHandler()
            val input = Allocation.createFromBitmap(rs,
                src,
                Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT)
            val output = Allocation.createTyped(rs, input.type)
            val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            if (blur > 25) {
                blur = 25.0f
            } else if (blur <= 0) {
                blur = 1.0f
            }
            blurScript.setInput(input)
            blurScript.setRadius(blur)
            blurScript.forEach(output)
            output.copyTo(src)
        } finally {
            rs?.destroy()
        }
        return src
    }

    /**
     * stack模糊图片
     *
     * @param src     源图片
     * @param blur  模糊半径
     * @param recycle 是否回收
     * @return stack模糊后的图片
     */
    private fun stackBlur(src: Bitmap, blur: Int, recycle: Boolean): Bitmap {
        val ret: Bitmap = if (recycle) {
            src
        } else {
            src.copy(src.config, true)
        }

        var radius = blur
        if (radius < 1) {
            radius = 1
        }

        val w = ret.width
        val h = ret.height

        val pix = IntArray(w * h)
        ret.getPixels(pix, 0, w, 0, 0, w, h)

        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1

        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(w.coerceAtLeast(h))

        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }

        yi = 0
        yw = yi

        val stack = Array(div) { IntArray(3) }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int

        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + wm.coerceAtMost(i.coerceAtLeast(0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius

            x = 0
            while (x < w) {

                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (y == 0) {
                    vmin[x] = (x + radius + 1).coerceAtMost(wm)
                }
                p = pix[yw + vmin[x]]

                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = 0.coerceAtLeast(yp) + x

                sir = stack[i + radius]

                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]

                rbs = r1 - abs(i)

                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs

                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }

                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] =
                    -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (x == 0) {
                    vmin[y] = (y + r1).coerceAtMost(hm) * w
                }
                p = x + vmin[y]

                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi += w
                y++
            }
            x++
        }
        ret.setPixels(pix, 0, w, 0, 0, w, h)
        return ret
    }


    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @param recycle  是否回收
     * @return 带有文字水印的图片
     */
    fun addTextWatermark(src: Bitmap,
                         content: String,
                         textSize: Float,
                         color: Int,
                         x: Float,
                         y: Float,
                         recycle: Boolean = false): Bitmap {
        val ret = src.copy(src.config, true)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val canvas = Canvas(ret)
        paint.color = color
        paint.textSize = textSize
        val bounds = Rect()
        paint.getTextBounds(content, 0, content.length, bounds)
        canvas.drawText(content, x, y + textSize, paint)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 添加图片水印
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @param recycle   是否回收
     * @return 带有图片水印的图片
     */
    @JvmOverloads
    fun addImageWatermark(src: Bitmap,
                          watermark: Bitmap,
                          x: Int,
                          y: Int,
                          alpha: Int,
                          recycle: Boolean = false): Bitmap {
        val ret = src.copy(src.config, true)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val canvas = Canvas(ret)
        paint.alpha = alpha
        canvas.drawBitmap(watermark, x.toFloat(), y.toFloat(), paint)
        if (recycle && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 转为alpha位图
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return alpha位图
     */
    @JvmOverloads
    fun toAlpha(src: Bitmap, recycle: Boolean? = false): Bitmap {
        val ret = src.extractAlpha()
        if (recycle!! && !src.isRecycled) src.recycle()
        return ret
    }

    /**
     * 转为灰度图片
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return 灰度图
     */
    @JvmOverloads
    fun toGray(src: Bitmap, recycle: Boolean = false): Bitmap {
        val grayBitmap = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(grayBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixColorFilter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = colorMatrixColorFilter
        canvas.drawBitmap(src, 0f, 0f, paint)
        if (recycle && !src.isRecycled) src.recycle()
        return grayBitmap
    }


    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @param recycle  是否回收
     * @return true: 成功 false: 失败
     */
    fun save(src: Bitmap,
             filePath: String,
             format: CompressFormat = CompressFormat.JPEG,
             recycle: Boolean = false): Boolean {
        return save(src, FileUtils.getFileByPath(filePath), format, recycle)
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return true: 成功  false: 失败
     */
    fun save(src: Bitmap,
             file: File?,
             format: CompressFormat = CompressFormat.JPEG,
             recycle: Boolean = false): Boolean {
        if (!FileUtils.createOrExistsFile(file)) return false
        var os: OutputStream? = null
        var ret = false
        try {
            os = BufferedOutputStream(FileOutputStream(file!!), 8 * 8192)
            ret = src.compress(format, 100, os)
            if (recycle && !src.isRecycled) src.recycle()


            /*            MediaStore.Images.Media.insertImage(MvpApp.context.contentResolver,
                            src,
                            file.absolutePath,
                            null)*/
            //通知相册刷新图片
            MvpApp.context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.absolutePath)))
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(os)
        }
        return ret
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param file 　文件
     * @return true: 是  false: 否
     */
    fun isImage(file: File?): Boolean {
        return file != null && isImage(file.path)
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param filePath 　文件路径
     * @return true: 是  false: 否
     */
    @SuppressLint("DefaultLocale")
    fun isImage(filePath: String): Boolean {
        val path = filePath.toUpperCase()
        return (path.endsWith(".PNG") || path.endsWith(".JPG") || path.endsWith(".JPEG") || path.endsWith(
            ".BMP") || path.endsWith(".GIF"))
    }

    /**
     * 获取图片类型
     *
     * @param filePath 文件路径
     * @return 图片类型
     */
    fun getImageType(filePath: String): String? {
        return getImageType(FileUtils.getFileByPath(filePath))
    }

    /**
     * 获取图片类型
     *
     * @param file 文件
     * @return 图片类型
     */
    fun getImageType(file: File?): String? {
        if (file == null) return null
        var inputStream: InputStream? = null
        return try {
            inputStream = FileInputStream(file)
            getImageType(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            CloseUtils.closeIO(inputStream)
        }
    }

    /**
     * 流获取图片类型
     *
     * @param inputStream 图片输入流
     * @return 图片类型
     */
    private fun getImageType(inputStream: InputStream?): String? {
        if (inputStream == null) return null
        return try {
            val bytes = ByteArray(8)
            if (inputStream.read(bytes, 0, 8) != -1) getImageType(bytes) else null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

    }

    /**
     * 获取图片类型
     *
     * @param bytes bitmap的前8字节
     * @return 图片类型
     */
    private fun getImageType(bytes: ByteArray): String? {
        if (isJPEG(bytes)) return "JPEG"
        if (isGIF(bytes)) return "GIF"
        if (isPNG(bytes)) return "PNG"
        return if (isBMP(bytes)) "BMP" else null
    }

    private fun isJPEG(b: ByteArray): Boolean {
        return (b.size >= 2 && b[0] == 0xFF.toByte() && b[1] == 0xD8.toByte())
    }

    private fun isGIF(b: ByteArray): Boolean {
        return (b.size >= 6 && b[0] == 'G'.toByte() && b[1] == 'I'.toByte() && b[2] == 'F'.toByte() && b[3] == '8'.toByte() && (b[4] == '7'.toByte() || b[4] == '9'.toByte()) && b[5] == 'a'.toByte())
    }

    private fun isPNG(b: ByteArray): Boolean {
        return b.size >= 8 && (b[0] == 137.toByte() && b[1] == 80.toByte() && b[2] == 78.toByte() && b[3] == 71.toByte() && b[4] == 13.toByte() && b[5] == 10.toByte() && b[6] == 26.toByte() && b[7] == 10.toByte())
    }

    private fun isBMP(b: ByteArray): Boolean {
        return (b.size >= 2 && b[0].toInt() == 0x42 && b[1].toInt() == 0x4d)
    }


    /**~~~~~~~~~~~~~~~~~~ 下方和压缩有关 ~~~~~~~~~~~~~~~~~~ */

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放压缩后的图片
     */
    fun compressByScale(src: Bitmap,
                        newWidth: Int,
                        newHeight: Int,
                        recycle: Boolean = false): Bitmap {
        return scale(src, newWidth, newHeight, recycle)
    }


    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放压缩后的图片
     */
    fun compressByScale(src: Bitmap,
                        scaleWidth: Float,
                        scaleHeight: Float,
                        recycle: Boolean = false): Bitmap {
        return scale(src, scaleWidth, scaleHeight, recycle)
    }

    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    @JvmOverloads
    fun compressByQuality(src: Bitmap, quality: Int, recycle: Boolean = false): Bitmap {
        val baos = ByteArrayOutputStream()
        src.compress(CompressFormat.JPEG, quality, baos)
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) src.recycle()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    @JvmOverloads
    fun compressByQuality(src: Bitmap, maxByteSize: Long, recycle: Boolean = false): Bitmap {
        var i = 0
        if (maxByteSize <= 0) return src
        val baos = ByteArrayOutputStream()
        var quality = 100
        src.compress(CompressFormat.JPEG, quality, baos)
        while (baos.toByteArray().size > maxByteSize && quality > 0) {
            quality -= 5
            baos.reset()
            src.compress(CompressFormat.JPEG, quality, baos)
            i++
        }
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) src.recycle()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @param recycle    是否回收
     * @return 按采样率压缩后的图片
     */
    @JvmOverloads
    fun compressBySampleSize(src: Bitmap, sampleSize: Int, recycle: Boolean = false): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = sampleSize
        val baos = ByteArrayOutputStream()
        src.compress(CompressFormat.JPEG, 100, baos)
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) src.recycle()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    }

    /**
     * 图片尺寸压缩
     *
     * @param path   image path
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     */
    fun ratio(path: String, pixelW: Int, pixelH: Int): Bitmap? {

        var bm: Bitmap? = null
        try {
            val f = File(path)

            val options = BitmapFactory.Options()
            // 设置为true,表示解析Bitmap对象，该对象不占内存
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            // 设置缩放比例
            options.inSampleSize = calculateInSampleSize(options, pixelW, pixelH)
            // 图片质量
            options.inPreferredConfig = Bitmap.Config.RGB_565

            // 设置为false,解析Bitmap对象加入到内存中
            options.inJustDecodeBounds = false
            options.inPurgeable = true
            options.inInputShareable = true
            // 获取资源图片
            bm = BitmapFactory.decodeStream(FileInputStream(f), null, options)
            //            bitmapToFile(bm, path, 100);
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (bm != null && !bm.isRecycled) {
                bm.recycle()
            }
        }
        return bm
    }
}