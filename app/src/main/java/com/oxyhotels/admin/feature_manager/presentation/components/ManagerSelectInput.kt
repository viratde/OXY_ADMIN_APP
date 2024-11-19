package com.oxyhotels.admin.feature_manager.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.oxyhotels.admin.feature_manager.domain.models.SelectData

@Composable
fun <T> ManagerSelectInput(
    selected: List<T>,
    label: String,
    options: List<SelectData<T>>,
    onSelect: (List<T>) -> Unit,
) {

    var isDropDownOpen by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 13.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SelectInput(
            label = label,
            value = options.filter {
                selected.contains(it.value)
            }.joinToString(separator = ",") { it.title },
            isOpen = isDropDownOpen
        ) {
            isDropDownOpen = !isDropDownOpen
        }

        DropdownMenu(
            expanded = isDropDownOpen,
            onDismissRequest = { isDropDownOpen = false },
            properties = PopupProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                focusable = true,
            ),
            offset = DpOffset(0.dp, 20.dp),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(MaterialTheme.colorScheme.background)
        ) {

            options.map {

                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 14.sp
                            ),
                        )
                    },
                    leadingIcon = {
                        Checkbox(
                            checked = selected.contains(it.value),
                            onCheckedChange = { c ->
                                if (c) {
                                    selected + it.value
                                } else {
                                    selected - it.value
                                }
                            },
                            enabled = false
                        )

                    },
                    onClick = {
                        onSelect(
                            if (selected.contains(it.value)) {
                                selected - it.value
                            } else {
                                selected + it.value
                            }
                        )
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.secondary,
                    ),
                )

            }

        }
    }

}

@Composable
fun SelectInput(
    label: String,
    value: String,
    isOpen: Boolean,
    onClick: () -> Unit
) {

    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .clickable(onClick = { onClick() })
                .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
                .padding(vertical = 3.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp
                ),
            )

            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector = if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Arrow key",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

        }

        Row(
            modifier = Modifier
                .offset {
                    IntOffset(
                        0,
                        with(density) {
                            -18.dp
                                .toPx()
                                .toInt()
                        })
                }
                .padding(horizontal = 30.dp, vertical = 10.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

    }

}