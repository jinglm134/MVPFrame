package com.project.mvpframe.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @CreateDate 2019/12/10 14:04
 * @Author jaylm
 */
data class LoginBean(val centerUserMain: CenterUserMain, val tokenResultBO: TokenResultBO) :
    BaseBean, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(CenterUserMain::class.java.classLoader)!!,
        parcel.readParcelable(TokenResultBO::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(centerUserMain, flags)
        parcel.writeParcelable(tokenResultBO, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginBean> {
        override fun createFromParcel(parcel: Parcel): LoginBean {
            return LoginBean(parcel)
        }

        override fun newArray(size: Int): Array<LoginBean?> {
            return arrayOfNulls(size)
        }
    }


}

data class TokenResultBO(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String,
    val jti: String,
    val refresh_token: String,
    val userId: String
) : BaseBean, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(access_token)
        parcel.writeString(token_type)
        parcel.writeString(expires_in)
        parcel.writeString(scope)
        parcel.writeString(jti)
        parcel.writeString(refresh_token)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TokenResultBO> {
        override fun createFromParcel(parcel: Parcel): TokenResultBO {
            return TokenResultBO(parcel)
        }

        override fun newArray(size: Int): Array<TokenResultBO?> {
            return arrayOfNulls(size)
        }
    }
}

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
) : BaseBean, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(memberId)
        parcel.writeString(parentId)
        parcel.writeString(mobile)
        parcel.writeString(email)
        parcel.writeString(tradePassword)
        parcel.writeString(name)
        parcel.writeString(idcard)
        parcel.writeString(idcardPicFront)
        parcel.writeString(idcardPicBack)
        parcel.writeString(idcardPicOnhand)
        parcel.writeInt(idcardPicCheckId)
        parcel.writeInt(idcardPicCheckType)
        parcel.writeString(regTime)
        parcel.writeInt(userEnabled)
        parcel.writeInt(tradePasswordType)
        parcel.writeInt(changePasswordType)
        parcel.writeInt(feeDeductionType)
        parcel.writeString(ip)
        parcel.writeInt(regType)
        parcel.writeInt(loginType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CenterUserMain> {
        override fun createFromParcel(parcel: Parcel): CenterUserMain {
            return CenterUserMain(parcel)
        }

        override fun newArray(size: Int): Array<CenterUserMain?> {
            return arrayOfNulls(size)
        }
    }
}
