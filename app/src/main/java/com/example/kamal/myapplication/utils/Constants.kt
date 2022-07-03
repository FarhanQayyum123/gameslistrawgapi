package com.example.kamal.myapplication.utils

class Constants {

    interface Const {
        companion object {
            const val PAGE_SIZE = "10"
        }
    }

    interface TimeOut {
        companion object {
            const val READ_TIME_OUT = 60
            const val CONNECTION_TIME_OUT = 60
        }
    }

    interface Tags {
        companion object {
            const val GAMES_LIST = "gamesList"
            const val ITEM_DETAIL = "itemDetail"
        }
    }
}