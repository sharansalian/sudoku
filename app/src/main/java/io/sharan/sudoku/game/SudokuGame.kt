package io.sharan.sudoku.game

import androidx.lifecycle.MutableLiveData

class SudokuGame {

    //livedata we can subscribe to notify for
    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()

    private var selectedRow = -1
    private var selectedCol = -1

    init {
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
    }

    fun updateSelectedCell(row: Int, column: Int){
        selectedRow = row
        selectedCol = column
        selectedCellLiveData.postValue(Pair(row, column))
    }
}