package com.example.semestdrahosvamz.ui.screens.details.UiElements

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.unit.dp
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A composable function to display the base information of a book.
 * This function adapts its layout based on the device orientation.
 *
 * @param book The book object containing information to display.
 * @param innerPadding Padding values to apply to the content.
 * @param onStatusChange Callback function to handle status changes.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun BookBaseInfoSection(book: Book, innerPadding: PaddingValues, onStatusChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        BookBaseInfoSectionPortrait(book, innerPadding, onStatusChange, modifier)
    } else {
        BookBaseInfoSectionLandscape(book, innerPadding, onStatusChange, modifier)
    }
}

/**
 * A composable function to display the base information of a book in portrait orientation.
 *
 * @param book The book object containing information to display.
 * @param innerPadding Padding values to apply to the content.
 * @param onStatusChange Callback function to handle status changes.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun BookBaseInfoSectionPortrait(
    book: Book,
    innerPadding: PaddingValues,
    onStatusChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val bitmap = remember(book.imageUri) { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val imageSize = 250.dp

    Surface(
        modifier = modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (book.imageUri.isNotEmpty()) {
                    LaunchedEffect(book.imageUri) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val source = ImageDecoder.createSource(context.contentResolver, Uri.parse(book.imageUri))
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }
                    }

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(imageSize)
                                .aspectRatio(4f / 5f)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    ReadingStatusOptions(
                        selectedOption = book.status,
                        onOptionSelected = onStatusChange
                    )
                }
            }
        }
    }
}

/**
 * A composable function to display the base information of a book in landscape orientation.
 *
 * @param book The book object containing information to display.
 * @param innerPadding Padding values to apply to the content.
 * @param onStatusChange Callback function to handle status changes.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun BookBaseInfoSectionLandscape(book: Book, innerPadding: PaddingValues, onStatusChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    val bitmap = remember(book.imageUri) { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val imageSize = 225.dp

    Surface(
        modifier = modifier
            .padding(innerPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row {
                if (book.imageUri.isNotEmpty()) {
                    LaunchedEffect(book.imageUri) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val source = ImageDecoder.createSource(context.contentResolver, Uri.parse(book.imageUri))
                            bitmap.value = ImageDecoder.decodeBitmap(source)
                        }
                    }

                    bitmap.value?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = "",
                            modifier = Modifier
                                .size(imageSize)
                        )
                    }
                }
                Column {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    ReadingStatusOptions(selectedOption = book.status, onOptionSelected = onStatusChange)
                }
            }
        }
    }
}

/**
 * A composable function to display reading status options.
 *
 * @param selectedOption The currently selected reading status option.
 * @param onOptionSelected Callback function to handle option selection.
 */
@Composable
fun ReadingStatusOptions(selectedOption: Int, onOptionSelected: (Int) -> Unit) {
    val options = listOf(
        stringResource(R.string.readingStatusFinished),
        stringResource(R.string.readingStatusReading),
        stringResource(R.string.readingStatusPlanned)
    )
    Column {
        options.forEachIndexed { index, text ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(index) }
                    .padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = text == options[selectedOption],
                    onClick = { onOptionSelected(index) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
