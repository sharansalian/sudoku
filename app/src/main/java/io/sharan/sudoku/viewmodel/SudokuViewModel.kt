package io.sharan.sudoku.viewmodel

import androidx.lifecycle.ViewModel
import io.sharan.sudoku.game.SudokuGame

class SudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()

}