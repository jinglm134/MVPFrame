package com.project.mvpframe.bean

data class HomeBiBrItemBean(val conversionPrice: Double,
                            val countQuantity: String,
                            val image: String,
                            val maxPrice: String,
                            val minPrice: String,
                            val price: String,
                            val quoteChange: Double,
                            val tradePairEunit: String,
                            val tradePairMaxValue: Double,
                            val tradePairMinValue: Double,
                            val trading: Int) : IBaseBean