package com.project.mvpframe.bean

/**
 * Response返回数据,主要用于外层数据,code,message的统一处理
 * @CreateDate 2019/12/2 14:45
 * @Author jaylm
 */
data class BaseResponse<T>(val code: Int, val message: String, val data: T) : IBaseBean