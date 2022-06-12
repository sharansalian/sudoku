package io.sharan.sudoku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import io.sharan.sudoku.R
import io.sharan.sudoku.view.custom.SudokuBoardView
import io.sharan.sudoku.viewmodel.SudokuViewModel

class SudokuActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener{

    private lateinit var viewModel: SudokuViewModel

    private lateinit var sudokuBoardView: SudokuBoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)
        sudokuBoardView = findViewById(R.id.sudokuBoardView)

        sudokuBoardView.registerListener(this)

        viewModel = ViewModelProvider(this).get(SudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(this) {
            updateSelectedCellUi(it)
        }

    }

    private fun updateSelectedCellUi(cell: Pair<Int, Int>?)  = cell?.let {
        sudokuBoardView.updateSelectedCellUi(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}