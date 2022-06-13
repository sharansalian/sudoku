package io.sharan.sudoku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import io.sharan.sudoku.R
import io.sharan.sudoku.game.Cell
import io.sharan.sudoku.view.custom.SudokuBoardView
import io.sharan.sudoku.viewmodel.SudokuViewModel

class SudokuActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: SudokuViewModel

    private lateinit var sudokuBoardView: SudokuBoardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku)
        sudokuBoardView = findViewById(R.id.sudokuBoardView)
        val buttonOne = findViewById<Button>(R.id.oneButton)
        val buttonTwo = findViewById<Button>(R.id.twoButton)
        val buttonThree = findViewById<Button>(R.id.threeButton)
        val buttonFour = findViewById<Button>(R.id.fourButton)
        val buttonFive = findViewById<Button>(R.id.fiveButton)
        val buttonSix = findViewById<Button>(R.id.sixButton)
        val buttonSeven = findViewById<Button>(R.id.sevenButton)
        val buttonEight = findViewById<Button>(R.id.eightButton)
        val buttonNine = findViewById<Button>(R.id.nineButton)

        val buttons = listOf<Button>(
            buttonOne,
            buttonTwo,
            buttonThree,
            buttonFour,
            buttonFive,
            buttonSix,
            buttonSeven,
            buttonEight,
            buttonNine
        )

        sudokuBoardView.registerListener(this)

        viewModel = ViewModelProvider(this).get(SudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(this) {
            updateSelectedCellUi(it)
        }
        viewModel.sudokuGame.cellsLiveData.observe(this) {
            updateCells(it)
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.sudokuGame.handleInput(index + 1)
            }
        }
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        sudokuBoardView.updateCells(cells)
    }

    private fun updateSelectedCellUi(cell: Pair<Int, Int>?) = cell?.let {
        sudokuBoardView.updateSelectedCellUi(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}