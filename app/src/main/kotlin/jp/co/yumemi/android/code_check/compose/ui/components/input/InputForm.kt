package jp.co.yumemi.android.code_check.compose.ui.components.input

import android.R
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import jp.co.yumemi.android.code_check.compose.ui.screens.search.SearchViewModel


@Composable
fun InputForm(
    context: Context,
    searchViewModel: SearchViewModel = SearchViewModel()
) {
    val backgroundColor = colorScheme.background
    var input by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val padding = 10

    Row(
        modifier = Modifier.padding(padding.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.weight(8f)) {
            Form(context = context, searchViewModel = searchViewModel, input = input, onInputChange = { input = it })
        }
        Box(
            modifier = Modifier
                .weight(3f)
                .shadow(elevation = 4.dp)
                .clickable { expanded = !expanded }
                .background(color = backgroundColor, shape = RoundedCornerShape(8))
                .padding(all = padding.dp),
        ) {
            Menu(expanded = expanded, onDismissRequest = { expanded = false }, searchViewModel = searchViewModel)
        }
    }
}

@Composable
private fun Form(
    context: Context,
    searchViewModel: SearchViewModel,
    input: String,
    onInputChange: (String) -> Unit,
) {
    val textColor = colorScheme.onSurface
    val focusManager = LocalFocusManager.current
    val hint = "GitHub のリポジトリを検索できるよー"

    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        placeholder = { Text(hint, color = textColor, fontSize = 10.sp) },
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                searchViewModel.onEnterHandler(context, input)
            }
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_search),
                contentDescription = "Search Icon"
            )
        }
    )
}

@Composable
private fun Menu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    searchViewModel: SearchViewModel,
) {
    val spacer = 4
    val textColor = colorScheme.onSurface

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_down_float),
            contentDescription = "並べ替え",
        )
        Spacer(modifier = Modifier.padding(spacer.dp))
        Text("並べ替え", color = textColor)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            onClick = {
                searchViewModel.sortItemsByName()
                onDismissRequest()
            },
            text = { Text("昇順") },
        )
        DropdownMenuItem(
            onClick = {
                searchViewModel.sortItemsByNameDescending()
                onDismissRequest()
            },
            text = { Text("降順") },
        )
    }
}
