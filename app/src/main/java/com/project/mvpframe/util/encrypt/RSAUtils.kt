package com.project.mvpframe.util.encrypt

import android.os.Build
import android.util.Base64
import androidx.annotation.IntDef
import java.io.ByteArrayOutputStream
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

/**
 * @CreateDate 2019/12/6 9:26
 * @Author jaylm
 */
object RSAUtils {

    /**
     * 随机获取密钥(公钥和私钥), 客户端公钥加密，服务器私钥解密
     *
     * @return 结果密钥对
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    fun getKeyPair(): Map<String, Any> {
        val keyPairGen = getKeyPairGenerator()
        keyPairGen.initialize(1024)

        val keyPair = keyPairGen.generateKeyPair()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey

        val keyMap = HashMap<String, Any>(2)
        keyMap["RSAPublicKey"] = publicKey
        keyMap["RSAPrivateKey"] = privateKey
        return keyMap
    }

    /**
     * 获取公钥/私钥
     *
     * @param keyMap      密钥对
     * @param isPublicKey true：获取公钥，false：获取私钥
     * @return 获取密钥字符串
     */
    fun getKey(keyMap: Map<String, Any>, isPublicKey: Boolean): String {
        val key = keyMap[if (isPublicKey) "RSAPublicKey" else "RSAPrivateKey"] as Key?
        return String(Base64.encode(key!!.encoded, Base64.DEFAULT))
    }

    /**
     * 获取数字签名
     *
     * @param data       二进制位
     * @param privateKey 私钥(BASE64编码)
     * @return 数字签名结果字符串
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    fun sign(data: ByteArray, privateKey: String): String {
        val keyBytes = Base64.decode(privateKey.toByteArray(), Base64.DEFAULT)
        val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = getKeyFactory()
        val privateK = keyFactory.generatePrivate(pkcs8KeySpec)

        val signature = Signature.getInstance("MD5withRSA")
        signature.initSign(privateK)
        signature.update(data)
        return String(Base64.encode(signature.sign(), Base64.DEFAULT))
    }

    /**
     * 数字签名校验
     *
     * @param data      二进位组
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名字符串
     * @return true：校验成功，false：校验失败
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    fun verify(data: ByteArray, publicKey: String, sign: String): Boolean {
        val keyBytes = Base64.decode(publicKey.toByteArray(), Base64.DEFAULT)

        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = getKeyFactory()
        val publicK = keyFactory.generatePublic(keySpec)

        val signature = Signature.getInstance("MD5withRSA")
        signature.initVerify(publicK)
        signature.update(data)
        return signature.verify(Base64.decode(sign.toByteArray(), Base64.DEFAULT))
    }

    /**
     * 获取 KeyFactory
     *
     * @throws NoSuchAlgorithmException 异常
     */
    @Throws(NoSuchAlgorithmException::class, NoSuchProviderException::class)
    private fun getKeyFactory(): KeyFactory {
        return if (Build.VERSION.SDK_INT >= 16) {
            KeyFactory.getInstance("RSA", "BC")
        } else {
            KeyFactory.getInstance("RSA")
        }
    }

    /**
     * 获取 KeyFactory
     *
     * @throws NoSuchAlgorithmException 异常
     */
    @Throws(NoSuchProviderException::class, NoSuchAlgorithmException::class)
    private fun getKeyPairGenerator(): KeyPairGenerator {
        return if (Build.VERSION.SDK_INT >= 16) {
            KeyPairGenerator.getInstance("RSA", "BC")
        } else {
            KeyPairGenerator.getInstance("RSA")
        }
    }


    /**
     * Rsa公钥加密类型
     */
    const val RSA_PUBLIC_ENCRYPT = 0

    /**
     * Rsa公钥解密类型
     */
    const val RSA_PUBLIC_DECRYPT = 1

    /**
     * Rsa私钥加密类型
     */
    const val RSA_PRIVATE_ENCRYPT = 2

    /**
     * Rsa私钥解密类型
     */
    const val RSA_PRIVATE_DECRYPT = 3

    @IntDef(RSA_PUBLIC_ENCRYPT, RSA_PUBLIC_DECRYPT, RSA_PRIVATE_ENCRYPT, RSA_PRIVATE_DECRYPT)
    internal annotation class RSAType

    /**
     * Rsa加密/解密（一般情况下，公钥加密私钥解密）
     *
     * @param data   源数据
     * @param string 密钥(BASE64编码)
     * @param type   操作类型：[.RSA_PUBLIC_ENCRYPT]，[               ][.RSA_PUBLIC_DECRYPT]，[.RSA_PRIVATE_ENCRYPT]，[.RSA_PRIVATE_DECRYPT]
     * @return 加密/解密结果字符串
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    fun rsa(data: ByteArray, string: String, @RSAType type: Int): ByteArray {
        val keyBytes = Base64.decode(string, Base64.DEFAULT)

        val keyFactory = getKeyFactory()
        val key = if (type == RSA_PUBLIC_ENCRYPT || type == RSA_PUBLIC_DECRYPT) {
            val x509KeySpec = X509EncodedKeySpec(keyBytes)
            keyFactory.generatePublic(x509KeySpec)
        } else {
            val pkcs8KeySpec = PKCS8EncodedKeySpec(keyBytes)
            keyFactory.generatePrivate(pkcs8KeySpec)
        }

        // 对数据加密
        val cipher = Cipher.getInstance(keyFactory.algorithm)
        val inputLen = data.size
        val out = ByteArrayOutputStream()
        var offSet = 0
        var cache: ByteArray
        var i = 0

        // 对数据分段加密
        if (type == RSA_PUBLIC_ENCRYPT || type == RSA_PRIVATE_ENCRYPT) {
            cipher.init(Cipher.ENCRYPT_MODE, key)

            while (inputLen - offSet > 0) {
                cache = if (inputLen - offSet > 117) {
                    cipher.doFinal(data, offSet, 117)
                } else {
                    cipher.doFinal(data, offSet, inputLen - offSet)
                }

                out.write(cache, 0, cache.size)
                out.flush()
                i++
                offSet = i * 117
            }
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key)
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(data, offSet, 128)
                    // 当最前面的数据是0，解密工具会错误的认为这是padding，因此导致长度不正确
                    if (cache.size < 117) {
                        val temp = ByteArray(117)
                        System.arraycopy(cache, 0, temp, 117 - cache.size, cache.size)
                        cache = temp
                    }
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet)
                }
                out.write(cache, 0, cache.size)
                out.flush()
                i++
                offSet = i * 128
            }
        }
        val result = out.toByteArray()
        out.close()
        return result
    }
}