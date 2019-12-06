package com.project.mvpframe.util.encrypt

import android.annotation.SuppressLint
import androidx.annotation.IntDef
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.*
import javax.crypto.spec.DESKeySpec

/**
 * @CreateDate 2019/12/6 8:59
 * @Author jaylm
 */
object DESUtils {
    @IntDef(Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE)
    internal annotation class DESType

    /**
     * Des加密/解密
     *
     * @param content  字符串内容
     * @param password 密钥
     * @param type     加密：[Cipher.ENCRYPT_MODE]，解密：[Cipher.DECRYPT_MODE]
     * @return 加密/解密结果
     */
    fun des(content: String, password: String, @DESType type: Int = Cipher.ENCRYPT_MODE): String {
        try {
            val random = SecureRandom()
            val desKey = DESKeySpec(password.toByteArray())
            val keyFactory = SecretKeyFactory.getInstance("DES")
            @SuppressLint("GetInstance") val cipher = Cipher.getInstance("DES")
            cipher.init(type, keyFactory.generateSecret(desKey), random)

            return if (type == Cipher.ENCRYPT_MODE) {
                val byteContent = content.toByteArray(charset("utf-8"))
                TranscodingUtils.parseByte2HexStr(cipher.doFinal(byteContent))
            } else {
                val byteContent = TranscodingUtils.parseHexStr2Byte(content)
                String(cipher.doFinal(byteContent))
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
        }

        return ""
    }
}
