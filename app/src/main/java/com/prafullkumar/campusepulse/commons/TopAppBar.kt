package com.prafullkumar.campusepulse.commons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    heading: String? = null,
    navIcon: ImageVector? = null,
    navIconClicked: () -> Unit = {},
    actionIcon: ImageVector? = null,
    actionIconClicked: () -> Unit = {},
    labelRow: @Composable () -> Unit = {},
    @StringRes label: Int? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            if (label != null) {
                Text(
                    text = stringResource(id = label),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else if (heading == null) {
                labelRow()
            } else {
                Text(
                    text = heading,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        navigationIcon = {
            if (navIcon != null) {
                IconButton(onClick = { navIconClicked() }) {
                    Icon(
                        imageVector = navIcon,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = { actionIconClicked() }) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = null,
                    )
                }
            }
        }
    )
}