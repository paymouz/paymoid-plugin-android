/*
 * Copyright (c) Siyavushkhon Kholmatov, 26/9/2020 at 22:56
 */

package uz.paymo.identifier

import uz.paymo.identifier.core.IdentificationData

/**
 * Callback listener for [PaymoIdentifier]
 */
interface IdentificationListener {
    /**
     * Callback for completed identification process from PAYMO.ID
     * @param identificationResult Identification data
     */
    fun onIdentificationComplete(identificationResult: IdentificationData)

    /**
     * Callback for explicitly canceled identification process from PAYMO.ID
     * (Ex. User closed PAYMO.ID without making some identification steps)
     */
    fun onIdentificationCancel()

    /**
     * Callback for failed connection with PAYMO.ID
     * Might be called when AgentLogin for AuthKey process fails
     * or error during the [IdentificationData] retrieval process
     */
    fun onIdentificationFailed(throwable: Throwable)

    /**
     * OPTIONAL: Callback for cancelled install option of PAYMO.ID app
     * Called when user cancels Install option for the required application
     */
    fun onPaymoIDInstallationCancel() {}
}