/*
 * Copyright (c) Siyavushkhon Kholmatov, 29/9/2020 at 22:36
 */

package uz.paymo.identifier.core

import uz.paymo.identifier.core.network.models.UserData

data class IdentificationData(
    /**
     * Success status of Passport Identification process
     * [true] if user has passed successfully, and [false] otherwise
     */
    val success: Boolean,

    /**
     * [UserData] for passport identification from PAYMO.ID
     * NOTE: if [success] is false, [userData] will be null since no data passed
     */
    val userData: UserData? = null
)