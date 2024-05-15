package com.example.semestdrahosvamz.ui.screens.details.UiElements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.semestdrahosvamz.R

/**
 * A composable function to display a section of control buttons.
 * This function includes buttons to open, bind, and read a book.
 *
 * @param onOpenClick Callback function to handle the open button click.
 * @param onBindClick Callback function to handle the bind button click.
 * @param onReadClick Callback function to handle the read button click.
 */
@Composable
fun ControlButtonsSection(onOpenClick: () -> Unit, onBindClick: () -> Unit, onReadClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .fillMaxHeight(0.10f)
    ) {
        Button(onClick = onOpenClick, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.openButton), style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Button(onClick = onBindClick, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.bindButton), style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Button(onClick = onReadClick, modifier = Modifier.weight(1f)) {
            Text(stringResource(R.string.readButton), style = MaterialTheme.typography.bodySmall)
        }
    }
}
