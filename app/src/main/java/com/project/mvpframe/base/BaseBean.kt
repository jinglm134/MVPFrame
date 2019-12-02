package com.project.mvpframe.base

/**
 * @CreateDate 2019/12/2 14:45
 * @Author jaylm
 */
data class BaseBean<T>(val code: Int, val message: String, val data: T)