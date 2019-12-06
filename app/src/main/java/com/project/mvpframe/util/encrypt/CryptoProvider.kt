package com.project.mvpframe.util.encrypt

import java.security.Provider

/**
 * @CreateDate 2019/12/6 9:13
 * @Author jaylm
 */
class CryptoProvider :
    Provider("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)") {
    init {
        put(
            "SecureRandom.SHA1PRNG",
            "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl"
        )
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software")
    }
}