package com.oxyhotels.admin.feature_manager.presentation.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.text.input.KeyboardType
import com.oxyhotels.admin.feature_auth.presentation.composables.AuthButton
import com.oxyhotels.admin.feature_manager.domain.models.Manager
import com.oxyhotels.admin.feature_manager.domain.models.ManagerDepartment


fun LazyListScope.ManagerBasicDetailsInput(
    manager: Manager,
    isEnabled: Boolean,
    onUpdateManager: (Manager) -> Unit,
    onFocusMove: () -> Unit,
) {

    item {
        ManagersTextFieldInput(
            value = manager.name,
            label = "Name",
            keyboardType = KeyboardType.Text,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        name = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.phoneNumber,
            label = "Phone",
            keyboardType = KeyboardType.Number,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        phoneNumber = it,
                        phoneNumbers = listOf(it)
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.email,
            label = "Email",
            keyboardType = KeyboardType.Email,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        email = it,
                        emails = listOf(it)
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.username,
            label = "Username",
            keyboardType = KeyboardType.Text,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        username = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.password,
            label = "Password",
            keyboardType = KeyboardType.Password,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        password = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }


    item {
        ManagersTextFieldInput(
            value = manager.dob,
            label = "DOB(DD-MM-YYYY)",
            keyboardType = KeyboardType.Text,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        dob = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.aadhar,
            label = "Aadhar",
            keyboardType = KeyboardType.Number,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        aadhar = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.pan,
            label = "PAN ",
            keyboardType = KeyboardType.Text,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        pan = it.uppercase()
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.businessName,
            label = "Business Name",
            keyboardType = KeyboardType.Text,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        businessName = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.gstNo,
            label = "GST",
            keyboardType = KeyboardType.Text,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        gstNo = it.uppercase()
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.address,
            label = "Address",
            keyboardType = KeyboardType.Text,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        address = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    item {
        ManagersTextFieldInput(
            value = manager.did,
            label = "DID",
            keyboardType = KeyboardType.Number,
            onValueChange = {
                onUpdateManager(
                    manager.copy(
                        did = it
                    )
                )
            },
            isEnabled = isEnabled
        ) {
            onFocusMove()
        }
    }

    if (manager.department != null) {

        item {
            ManagersTextFieldInput(
                value = manager.department.name,
                label = "Department Name",
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    onUpdateManager(
                        manager.copy(
                            department = manager.department.copy(
                                name = it
                            )
                        )
                    )
                },
                isEnabled = isEnabled
            ) {
                onFocusMove()
            }
        }

        item {
            ManagersTextFieldInput(
                value = manager.department.bookingShort,
                label = "Department Short Name",
                keyboardType = KeyboardType.Text,
                onValueChange = {
                    onUpdateManager(
                        manager.copy(
                            department = manager.department.copy(
                                bookingShort = it
                            )
                        )
                    )
                },
                isEnabled = isEnabled
            ) {
                onFocusMove()
            }
        }

        item {
            AuthButton(text = "Delete Department") {
                onUpdateManager(
                    manager.copy(
                        department = null
                    )
                )
            }
        }

    } else {
        item {
            AuthButton(text = "Add Department") {
                onUpdateManager(
                    manager.copy(
                        department = ManagerDepartment()
                    )
                )
            }
        }
    }


}