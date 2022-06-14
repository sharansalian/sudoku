package io.sharan.sudoku.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import io.sharan.sudoku.R
import io.sharan.sudoku.game.Cell
import io.sharan.sudoku.view.custom.SudokuBoardView
import io.sharan.sudoku.viewmodel.SudokuViewModel

class SudokuActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: SudokuViewModel

    private lateinit var sudokuBoardView: SudokuBoardView

    private lateinit var numberButtons: List<Button>

    private lateinit var notesButton: ImageButton

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
        notesButton = findViewById<ImageButton>(R.id.notesButton)

        numberButtons = listOf<Button>(
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
        viewModel.sudokuGame.selectedCellLiveData.observe(this) { updateSelectedCellUi(it) }
        viewModel.sudokuGame.cellsLiveData.observe(this) { updateCells(it) }
        viewModel.sudokuGame.isTakingNotesLiveData.observe(this) { updateNoteTakingUi(it) }
        viewModel.sudokuGame.highLightedKeysLiveData.observe(this) { updateHighlightedKeysUi(it) }

        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener { viewModel.sudokuGame.handleInput(index + 1) }
        }

        notesButton.setOnClickListener { viewModel.sudokuGame.changeNoteTakingState() }
    }

    private fun updateHighlightedKeysUi(set: Set<Int>?) = set?.let {
        numberButtons.forEachIndexed { index, button ->
            val color = if(set.contains(index + 1)) ContextCompat.getColor(this, R.color.purple_500) else Color.LTGRAY
            button.setBackgroundColor(color)
        }
    }

    private fun updateNoteTakingUi(isNoteTaking: Boolean?) = isNoteTaking?.let {
        if (it) {
            notesButton.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
        } else {
            notesButton.setBackgroundColor(Color.LTGRAY)
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