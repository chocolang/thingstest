package com.chocolang.android.chocoapp.repository.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.chocolang.android.chocoapp.R

class PathDialog(
    context: Context,
    private var org: String,
    private var repo: String,
    private val onCallback: (String, String) -> Unit
) : Dialog(context) {
    private lateinit var etOrg: EditText
    private lateinit var etRepo: EditText

    fun setOrg(value: String) {
        etOrg.setText(value)
    }

    fun setRepo(value: String) {
        etRepo.setText(value)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_path)
        etOrg = findViewById<EditText>(R.id.et_org).apply {
            this.setText(org)
        }
        etRepo = findViewById<EditText>(R.id.et_repo).apply {
            this.setText(repo)
        }
        val btnConfirm = findViewById<Button>(R.id.btn_confirm)

        btnConfirm.setOnClickListener {
            onCallback(etOrg.text.toString(), etRepo.text.toString())
            dismiss()
        }
    }
}