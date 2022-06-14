package io.sharan.sudoku.game

import androidx.lifecycle.MutableLiveData

class SudokuGame {

    //livedata we can subscribe to notify for
    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    val isTakingNotesLiveData = MutableLiveData<Boolean>()
    val highLightedKeysLiveData = MutableLiveData<Set<Int>>()

    private var selectedRow = -1
    private var selectedCol = -1
    private var isTakingNotes = false

    private val board: Board

    init {
        val cells = List(9 * 9) { i -> Cell(i / 9, i % 9, i % 9) }
        board = Board(9, cells)

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
        isTakingNotesLiveData.postValue(isTakingNotes)
    }

    fun handleInput(number: Int) {
        if (selectedRow == -1 || selectedCol == -1) return
        val cell = board.getCell(selectedRow, selectedCol)
        if (cell.isStartingCell) return

        if (isTakingNotes) {
            if (cell.notes.contains(number)) {
                cell.notes.remove(number)
            } else {
                cell.notes.add(number)
            }
            highLightedKeysLiveData.postValue(cell.notes)
        } else {
            cell.value = number
        }
        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int, column: Int) {
        val cell = board.getCell(row, column)
        if (!cell.isStartingCell) {
            selectedRow = row
            selectedCol = column
            selectedCellLiveData.postValue(Pair(row, column))

            if(isTakingNotes) {
                highLightedKeysLiveData.postValue(cell.notes)
            }
        }
    }

    fun changeNoteTakingState() {
        isTakingNotes = !isTakingNotes
        isTakingNotesLiveData.postValue(isTakingNotes)

        val curNotes = if(isTakingNotes) {
            board.getCell(selectedRow, selectedCol).notes
        } else {
            emptySet<Int>()
        }
        highLightedKeysLiveData.postValue(curNotes)
    }
}