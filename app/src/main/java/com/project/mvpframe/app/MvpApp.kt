package com.project.mvpframe.app

import android.app.Application
import android.content.Context
import com.project.mvpframe.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.socket.client.IO
import io.socket.client.Socket
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import kotlin.properties.Delegates


/**
 * @CreateDate 2019/12/9 14:43
 * @Author jaylm
 */
class MvpApp : Application() {

    private var mIOOptions: IO.Options by Delegates.notNull()

    companion object {
        var context: Context by Delegates.notNull()
        var mSocket: Socket by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


    init {
        //        设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            ClassicsHeader(context).setTextSizeTime(10f).setTextSizeTitle(12f).setDrawableSize(22f)
            //                .setTimeFormat(DynamicTimeFormat("上次更新 %s"))
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            ClassicsFooter(context).setTextSizeTitle(12f).setDrawableSize(22f)
        }

        try {
            /*val biBrTrustManager = SocketIOClient.BiBrTrustManager()
            val secureRandom = SecureRandom()

            val sslContext: SSLContext
            sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf(biBrTrustManager), secureRandom)

            val okHttpClient =
                OkHttpClient.Builder().hostnameVerifier(SocketIOClient.BiBrHostnameVerifier())
                    .sslSocketFactory(sslContext.socketFactory, biBrTrustManager).build()

            IO.setDefaultOkHttpCallFactory(okHttpClient)
            IO.setDefaultOkHttpWebSocketFactory(okHttpClient)*/
            mIOOptions = IO.Options()
            //            mIOOptions.transports = arrayOf("websocket")
            //            mIOOptions.webSocketFactory = okHttpClient
            //            mIOOptions.callFactory = okHttpClient
            //            mIOOptions.reconnection = true
            //            mIOOptions.secure = true
            mIOOptions.forceNew = true
            mSocket = IO.socket("https://dev-api.mixcdn.co/", mIOOptions)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}