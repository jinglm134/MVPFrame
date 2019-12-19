package com.project.mvpframe.bean

/**
 * @CreateDate 2019/12/10 14:04
 * @Author jaylm
 */
data class LoginBean(val centerUserMain: CenterUserMain, val tokenResultBO: TokenResultBO) : BaseBean

data class TokenResultBO(
        val access_token: String,
        val token_type: String,
        val expires_in: String,
        val scope: String,
        val jti: String,
        val refresh_token: String,
        val userId: String
) : BaseBean

data class CenterUserMain(
        val id: String,
        val memberId: String,
        val parentId: String,
        val mobile: String,
        val email: String,
        val tradePassword: String,
        val name: String,
        val idcard: String,
        val idcardPicFront: String,
        val idcardPicBack: String,
        val idcardPicOnhand: String,
        val idcardPicCheckId: Int,
        val idcardPicCheckType: Int,
        val regTime: String,
        val userEnabled: Int,
        val tradePasswordType: Int,
        val changePasswordType: Int,
        val feeDeductionType: Int,
        val ip: String,
        val regType: Int,
        val loginType: Int
) : BaseBean
