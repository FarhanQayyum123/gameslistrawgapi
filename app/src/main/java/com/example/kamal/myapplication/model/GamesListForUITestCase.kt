package com.example.kamal.myapplication.model

object GamesListForUITestCase {

    fun getDummyGamesData(): ArrayList<GamesListModel> {
        val gamesListArray = ArrayList<GamesListModel>()
        for (i in 0..5) {
            val gameListObject = GamesListModel()
            gameListObject.id = i
            gameListObject.name = "Name: $i"
            gamesListArray.add(gameListObject)
        }
        return gamesListArray
    }

}