/*
 * Copyright (c) Siyavushkhon Kholmatov, 14/11/2020 at 23:42
 */

package uz.paymo.example_identifier

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.info_fragment.view.*
import uz.paymo.identifier.core.network.models.UserPassport

open class InfoFragment(
    private val userPassport: UserPassport?,
    private val passportBitmap: Bitmap,
    private val targetBitmap: Bitmap
) : BottomSheetDialogFragment() {

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bunle: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.info_fragment, container)
        initData()
        return mView
    }

    private fun initData() {
        userPassport?.let {
            mView.passportNum?.text = it.passportNumber
            mView.countryCode?.text = it.nationality
            mView.gender?.text = it.gender?.toUpperCase()
            mView.name?.text = it.firstName
            mView.surname?.text = it.lastName
            mView.nationality?.text = it.nationality
            mView.placeOfBirth?.text = it.placeOfBirth
            mView.dateOfBirth?.text = it.dateOfBirth
            mView.dateOfIssue?.text = it.dateOfIssue
            mView.dateOfExpiry?.text = it.dateOfExpiry
            mView.type?.text = it.documentType
            mView.inplNumber?.text = it.pin
            mView.authoriy?.text = it.issuerCentre
            mView.photo?.setImageBitmap(passportBitmap)
            mView.currentPhoto?.setImageBitmap(targetBitmap)
        }
    }
}