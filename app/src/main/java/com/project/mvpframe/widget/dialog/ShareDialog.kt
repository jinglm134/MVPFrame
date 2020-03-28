package com.project.mvpframe.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.project.mvpframe.R
import com.project.mvpframe.constant.FileConst
import com.project.mvpframe.util.*
import com.uuzuche.lib_zxing.activity.CodeUtils
import java.io.File
import kotlin.properties.Delegates

/**
 * 分享
 *
 * @CreateDate 2020/3/26 9:42
 * @Author jaylm
 */
@SuppressLint("InflateParams")
class ShareDialog(private val mContext: Context) :
    AppCompatDialog(mContext, R.style.BaseDialogStyle) {
    private val mRootView: View
    private var ivShare: ImageView by Delegates.notNull()
    private var bitmap: Bitmap by Delegates.notNull()


    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mRootView = inflater.inflate(R.layout.dialog_share, null, false)

        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setOnKeyListener { dialog, keyCode, event ->
            if (KeyEvent.KEYCODE_BACK == keyCode && event.action == KeyEvent.ACTION_DOWN) {
                dialog.dismiss()
                return@setOnKeyListener true
            }
            false
        }

        //设置dialog的宽高为屏幕的宽高
        val width = SizeUtils.getScreenWidth()
        val layoutParams = ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        window!!.setGravity(Gravity.BOTTOM)

        initView()
        setContentView(mRootView, layoutParams)
    }


    private fun initView() {
        ivShare = mRootView.findViewById(R.id.iv_share)
        bitmap = canvasBitMap()
        ivShare.setImageBitmap(bitmap)
        val tvCancel = mRootView.findViewById<TextView>(R.id.tv_cancel)
        tvCancel.setOnClickListener { dismiss() }
        val tvSave = mRootView.findViewById<TextView>(R.id.tv_save)
        tvSave.setOnClickListener {
            if (ImageUtils.save(bitmap, File(FileUtils.getDataPath(), FileConst.INVITE_QR_CODE))) {
                ToastUtils.showShortToast("二维码保存成功,请去相册查看")
            } else {
                ToastUtils.showShortToast("保存失败")
            }
        }

        val tvWx = mRootView.findViewById<TextView>(R.id.tv_wx)
        val tvWxchat = mRootView.findViewById<TextView>(R.id.tv_wxchat)
        val tvQq = mRootView.findViewById<TextView>(R.id.tv_qq)
        val tvTelegram = mRootView.findViewById<TextView>(R.id.tv_telegram)

        tvWx.setOnClickListener {
            val intent = Intent()
        }
    }


    private fun canvasBitMap(): Bitmap {
        val bgBitmap = BitmapFactory.decodeResource(mContext.resources, R.mipmap.bg_share)
        val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        //设置画布大小
        val width = SizeUtils.getScreenWidth() - ivShare.paddingRight + ivShare.paddingLeft
        val height = width / bgBitmap.width * bgBitmap.height
        val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)

        //画背景图
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawBitmap(bgBitmap, null, rectF, bitmapPaint)
        //        canvas.drawBitmap(bgBitmap, 0f, 0f, bitmapPaint);


        //画header图,水平居中,marginTop 80dp
        val headerBitmap = BitmapFactory.decodeResource(mContext.resources, R.mipmap.bg_share_title)
        var canvasHeight = SizeUtils.dp2px(80f).toFloat()
        canvas.drawBitmap(headerBitmap,
            (width - headerBitmap.width) / 2f,
            canvasHeight,
            bitmapPaint)

        //画文字《致力打造全球最便捷、安全》,水平居中,marginTop 15dp
        val textOne = "致力打造全球最便捷、安全"
        textPaint.textSize = SizeUtils.dp2px(18f).toFloat()
        textPaint.color = UShape.getColor(R.color.white)
        textPaint.style = Paint.Style.FILL
        textPaint.strokeWidth = 12f
        val rectOne = Rect()
        textPaint.getTextBounds(textOne, 0, textOne.length, rectOne)
        canvasHeight += headerBitmap.height + rectOne.height() + SizeUtils.dp2px(15f)
        textPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(textOne, width / 2f, canvasHeight, textPaint)


        //画文字《顶级数字资产交易平台》,水平居中,marginTop 10dp
        val textTwo = "顶级数字资产交易平台"
        val rectTwo = Rect()
        textPaint.getTextBounds(textTwo, 0, textTwo.length, rectTwo)
        canvasHeight += rectTwo.height() + SizeUtils.dp2px(10f)
        canvas.drawText(textTwo, width / 2f, canvasHeight, textPaint)


        //画二维码,水平居中,marginTop 80dp
        val zxBitmap = CodeUtils.createImage("我的二维码",
            SizeUtils.dp2px(120f),
            SizeUtils.dp2px(120f),
            BitmapFactory.decodeResource(mContext.resources, R.mipmap.ic_launcher))
        canvasHeight += SizeUtils.dp2px(80f)
        canvas.drawBitmap(zxBitmap, (width - zxBitmap.width) / 2f, canvasHeight, bitmapPaint)

        //画文字《扫描二维码下载币和 APP》,水平居中,marginTop 15dp
        val textThree = "扫描二维码下载币和 APP"
        textPaint.textSize = SizeUtils.dp2px(12f).toFloat()
        val rectThree = Rect()
        textPaint.getTextBounds(textThree, 0, textThree.length, rectThree)
        canvasHeight += zxBitmap.height + rectThree.height() + SizeUtils.dp2px(15f)
        canvas.drawText(textThree, width / 2f, canvasHeight, textPaint)
        return newBitmap
    }

}
