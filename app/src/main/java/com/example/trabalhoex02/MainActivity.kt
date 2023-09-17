package com.example.trabalhoex02

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var editTextMatrixA: EditText
    private lateinit var editTextMatrixB: EditText
    private lateinit var buttonReadMatrixA: Button
    private lateinit var buttonReadMatrixB: Button
    private lateinit var buttonCalculate: Button
    private lateinit var textViewMatrixA: TextView
    private lateinit var textViewMatrixB: TextView
    private lateinit var textViewMatrixS: TextView
    private lateinit var textViewMatrixD: TextView

    private var matrixA: Array<IntArray>? = null
    private var matrixB: Array<IntArray>? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextMatrixA = findViewById(R.id.editTextMatrixA)
        editTextMatrixB = findViewById(R.id.editTextMatrixB)
        buttonReadMatrixA = findViewById(R.id.buttonReadMatrixA)
        buttonReadMatrixB = findViewById(R.id.buttonReadMatrixB)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textViewMatrixA = findViewById(R.id.textViewMatrixA)
        textViewMatrixB = findViewById(R.id.textViewMatrixB)
        textViewMatrixS = findViewById(R.id.textViewMatrixS)
        textViewMatrixD = findViewById(R.id.textViewMatrixD)

        buttonReadMatrixA.setOnClickListener {
            matrixA = readMatrix(editTextMatrixA, 4, 6, textViewMatrixA)
        }
        buttonReadMatrixB.setOnClickListener {
            matrixB = readMatrix(editTextMatrixB, 4, 6, textViewMatrixB)
        }
        buttonCalculate.setOnClickListener {
            calculateAndDisplay()
        }
    }

    private fun readMatrix(
        editText: EditText,
        numRows: Int,
        numCols: Int,
        textView: TextView
    ): Array<IntArray>? {
        val input = editText.text.toString().trim()
        val rows = input.split("\n")

        // Verificar se a matriz tem o tamanho esperado
        if (rows.size != numRows) {
            textView.text = "A matriz deve ter $numRows linhas."
            return null
        }

        val matrix = Array(numRows) { IntArray(numCols) }

        for (i in 0 until numRows) {
            val columns = rows[i].split(" ")

            // Verificar se cada linha possui o número correto de elementos
            if (columns.size != numCols) {
                textView.text = "Cada linha deve conter $numCols elementos."
                return null
            }

            for (j in 0 until numCols) {
                try {
                    matrix[i][j] = columns[j].toInt()
                } catch (e: NumberFormatException) {
                    textView.text = "Certifique-se de que todos os elementos são números."
                    return null
                }
            }
        }

        return matrix
    }

    private fun calculateAndDisplay() {
        if (matrixA != null && matrixB != null) {
            val sumMatrix = calculateSum(matrixA!!, matrixB!!)
            val diffMatrix = calculateDifference(matrixA!!, matrixB!!)

            displayMatrix(sumMatrix, textViewMatrixS)
            displayMatrix(diffMatrix, textViewMatrixD)
        } else {
            textViewMatrixS.text = "Matrizes A ou B não foram lidas corretamente."
            textViewMatrixD.text = ""
        }
    }

    private fun calculateSum(matrixA: Array<IntArray>, matrixB: Array<IntArray>): Array<IntArray> {
        val numRows = matrixA.size
        val numCols = matrixA[0].size
        val sumMatrix = Array(numRows) { IntArray(numCols) }

        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                sumMatrix[i][j] = matrixA[i][j] + matrixB[i][j]
            }
        }

        return sumMatrix
    }

    private fun calculateDifference(matrixA: Array<IntArray>, matrixB: Array<IntArray>): Array<IntArray> {
        val numRows = matrixA.size
        val numCols = matrixA[0].size
        val diffMatrix = Array(numRows) { IntArray(numCols) }

        for (i in 0 until numRows) {
            for (j in 0 until numCols) {
                diffMatrix[i][j] = matrixA[i][j] - matrixB[i][j]
            }
        }

        return diffMatrix
    }

    private fun displayMatrix(matrix: Array<IntArray>, textView: TextView) {
        val builder = StringBuilder()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                builder.append(matrix[i][j])
                builder.append("\t")
            }
            builder.append("\n")
        }
        textView.text = builder.toString()
    }
}
