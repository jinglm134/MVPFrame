package com.project.mvpframe.util.encrypt

import android.annotation.SuppressLint
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONException
import org.json.JSONObject
import java.security.InvalidKeyException
import java.security.Key
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.*
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

/**
 * @CreateDate 2019/12/6 16:33
 * @Author jaylm
 */
object EncryptUtils {

    // 字符编码格式
    private const val CHARSET = "UTF-8"
    private const val PUBLIC_KEY =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDn/QnUfkKkQpG++6YDUYo8qc0mrcRbRsmokp3EJZJrwIblldv6W2+yAIWkkel4GzsL+oklZReqZcG4F+ug3otc6IZa3fFMXuaDzoDzicdOABe2s8igLjMcfhujI9MqYvkVVSyFWdVOLNAnzQJgkisnHKZGPQZcxnQAHxTOUco+LwIDAQAB"
    // 定义jackson对象
    private val MAPPER = ObjectMapper()
    //非对称密钥算法
    private const val KEY_ALGORITHM = "RSA/NONE/PKCS1Padding"

    /**
     * 将对象转换成json字符串。
     *
     * @param data
     * @return
     */
    private fun objectToJson(data: Any): String {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return MAPPER.writeValueAsString(data)
    }


    /**
     * @return
     * @Object parm 对参数进行加密
     */
    @SuppressLint("NewApi", "LocalSuppress")
    fun encrypt(parm: Any): Map<String, String> {
        val jsonParm = objectToJson(parm)
        val randomKey = getRandomString(8)
        //对公钥进行RSA非对称加密
        var encryptRSA: String? = null
        try {
            encryptRSA = encryptRSA(randomKey, PUBLIC_KEY)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //对时间和参数进行DES对称加密
        val time = System.currentTimeMillis()
        val timeDES = encode(randomKey, time.toString())
        val parmDES = encode(randomKey, jsonParm)

        val jsonObject = JSONObject()
        try {
            if (parmDES != null) {
                jsonObject.put("ciphertext", parmDES.replace("\n", ""))
            }
            if (encryptRSA != null) {
                jsonObject.put("key", encryptRSA.replace("\n", ""))
            }
            if (timeDES != null) {
                jsonObject.put("time", timeDES.replace("\n", ""))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val resultMap = HashMap<String, String>(1)
        resultMap["ciphertext"] = jsonObject.toString()
        return resultMap
    }

    /**
     * @param publicKey 公钥
     * @return String
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun encryptRSA(randomKey: String, publicKey: String): String? {
        var bytes: ByteArray
        try {
            bytes = randomKey.toByteArray(charset(CHARSET))
            val decodePublicKey = publicKey.replace("\r\n", "")
            val rsaPublicKey = KeyFactory.getInstance("RSA").generatePublic(
                X509EncodedKeySpec(
                    Base64.decode(
                        decodePublicKey.toByteArray(),
                        Base64.DEFAULT
                    )
                )
            ) as RSAPublicKey
            val cipher = Cipher.getInstance(KEY_ALGORITHM)
            cipher.init(Cipher.PUBLIC_KEY, rsaPublicKey)
            bytes = cipher.doFinal(bytes)
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * @param length
     * @return 生成指定length的随机字符串（A-Z，a-z，0-9,+/）
     */
    private fun getRandomString(length: Int): String {
        val str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
        val random = Random()
        val sb = StringBuffer()
        for (i in 0 until length) {
            val number = random.nextInt(str.length)
            sb.append(str[number])
        }
        return sb.toString()
    }


    private const val TRANSFORMATION = "DES"//DES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private const val IVPARAMETERSPEC = "01020304"////初始化向量参数，AES 为16bytes. DES 为8bytes.
    private const val ALGORITHM = "DES"//DES是加密方式

    // 对密钥进行处理
    @Throws(Exception::class)
    private fun getRawKey(key: String): Key {
        val dks = DESKeySpec(key.toByteArray(charset(CHARSET)))
        val keyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        return keyFactory.generateSecret(dks)
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    private fun encode(key: String, data: String): String? {
        return encode(key, data.toByteArray())
    }


    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    private fun encode(key: String, data: ByteArray?): String? {
        if (data == null) {
            throw RuntimeException("data is null!")
        }
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val iv = IvParameterSpec(IVPARAMETERSPEC.toByteArray(charset(CHARSET)))
            cipher.init(Cipher.ENCRYPT_MODE, getRawKey(key), iv)
            val bytes = cipher.doFinal(data)
            Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            null
        }

    }
}