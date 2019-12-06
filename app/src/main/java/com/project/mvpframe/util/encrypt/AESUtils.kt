package com.project.mvpframe.util.encrypt

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.IntDef
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.SecureRandom
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec

/**
 * @CreateDate 2019/12/6 9:11
 * @Author jaylm
 */
object AESUtils {

    private val SHA1PRNG = "SHA1PRNG"

    @IntDef(Cipher.ENCRYPT_MODE, Cipher.DECRYPT_MODE)
    internal annotation class AESType

    /**
     * Aes加密/解密
     *
     * @param content  字符串
     * @param password 密钥
     * @param type     加密：[Cipher.ENCRYPT_MODE]，解密：[Cipher.DECRYPT_MODE]
     * @return 加密/解密结果字符串
     */
    fun aes(content: String, password: String, @AESType type: Int): String? {
        try {
            val generator = KeyGenerator.getInstance("AES")

            val secureRandom: SecureRandom = when {
                Build.VERSION.SDK_INT >= 24 -> SecureRandom.getInstance(SHA1PRNG, CryptoProvider())
//                Build.VERSION.SDK_INT >= 17 -> SecureRandom.getInstance(SHA1PRNG, "Crypto")
                else -> SecureRandom.getInstance(SHA1PRNG)
            }
            secureRandom.setSeed(password.toByteArray())
            generator.init(128, secureRandom)
            val secretKey = generator.generateKey()
            val enCodeFormat = secretKey.encoded
            val key = SecretKeySpec(enCodeFormat, "AES")
            @SuppressLint("GetInstance") val cipher = Cipher.getInstance("AES")
            cipher.init(type, key)

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
        } catch (e: NoSuchProviderException) {
            e.printStackTrace()
        }

        return null
    }
}