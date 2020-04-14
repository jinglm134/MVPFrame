package com.project.mvpframe.ui.common.third_mvp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mvpframe.R
import com.project.mvpframe.app.MvpApp
import com.project.mvpframe.base.BaseFragment
import com.project.mvpframe.bean.HomeBiBrItemBean
import com.project.mvpframe.constant.EventConst
import io.socket.client.Socket
import kotlinx.android.synthetic.main.fragment_third.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @CreateDate 2019/12/19 11:57
 * @Author jaylm
 */
class ThirdFragment : BaseFragment<ThirdPresenter>(), IThirdView {

    private val mSocket by lazy { MvpApp.mSocket }
    override fun initMVP() {
        mPresenter.init(mActivity, this)
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_third
    }

    override fun initView() {
        mSocket.connect()
        mSocket.on(Socket.EVENT_DISCONNECT) { args ->
            val s = args[0].toString() }
        tv_btn1.setOnClickListener {
            getRecommend("CNT")
            getRecommend2("USDT")
        }
        tv_btn2.setOnClickListener {
            mSocket.off(EventConst.EVENT_RECOMMEND)
//            mSocket.off()
            mSocket.disconnect()
        }
    }


    private fun getRecommend(tradingArea: String) {
        val jsonObject = JSONObject()
        jsonObject.put("tradingArea", tradingArea)
        mSocket.emit(EventConst.EVENT_LEVER_TRADE_AREA, jsonObject)
        mSocket.on(EventConst.EVENT_LEVER_TRADE_AREA) { args ->
            if (args[0] == null) {
                return@on
            }
            if (args[0] is JSONArray) {
                val obj = args[0] as JSONArray
                val datas = Gson().fromJson<List<HomeBiBrItemBean>>(obj.toString(),
                    object : TypeToken<List<HomeBiBrItemBean>>() {}.type)

            } else if (args[0] is JSONObject) {
                val data = Gson().fromJson<HomeBiBrItemBean>(args[0].toString(),
                    HomeBiBrItemBean::class.java)
            }

        }
    }

    private fun getRecommend2(tradingArea: String) {
        val jsonObject = JSONObject()
        jsonObject.put("tradingArea", tradingArea)
        mSocket.emit(EventConst.EVENT_LEVER_TRADE_AREA, jsonObject)
        mSocket.on(EventConst.EVENT_LEVER_TRADE_AREA) { args ->
            if (args[0] == null) {
                return@on
            }
            if (args[0] is JSONArray) {
                val obj = args[0] as JSONArray
                val datas = Gson().fromJson<List<HomeBiBrItemBean>>(obj.toString(),
                    object : TypeToken<List<HomeBiBrItemBean>>() {}.type)

            } else if (args[0] is JSONObject) {
                val data = Gson().fromJson<HomeBiBrItemBean>(args[0].toString(),
                    HomeBiBrItemBean::class.java)
            }

        }
    }
}