/*
 * Copyright (c) Siyavushkhon Kholmatov, 27/9/2020 at 14:4
 */

package uz.paymo.identifier.core.utils

import android.os.Build
import uz.paymo.identifier.BuildConfig

class SoftwareSupportException : Exception(
    "PAYMO.ID is not supported by the device. MIN SDK VERSION ${BuildConfig.PAYMO_ID_MIN_SDK}, but found ${Build.VERSION.SDK_INT}"
)