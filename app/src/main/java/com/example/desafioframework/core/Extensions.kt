package com.example.desafioframework.core

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.desafioframework.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Extension to create an AlertDialog
fun Context.createDialog(block: AlertDialog.Builder.() -> Unit = {}): AlertDialog {
    val builder = AlertDialog.Builder(this, R.style.AlertDialogTitle)
    builder.setPositiveButton(R.string.dialog_button, null)
    block(builder)
    return builder.create()
}

// Extension to provides information of internet connection
fun Context.noNetworkConnectivityError(): AppState.Error {
    return AppState.Error(Exception(this.resources.getString(R.string.no_network_connectivity)))
}