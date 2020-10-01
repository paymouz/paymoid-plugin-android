package uz.paymo.example_identifier

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import uz.paymo.identifier.IdentificationListener
import uz.paymo.identifier.PaymoIdentifier
import uz.paymo.identifier.core.IdentificationData

class MainActivity : AppCompatActivity(), IdentificationListener {

    lateinit var paymoIdentifier: PaymoIdentifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        paymoIdentifier = PaymoIdentifier.getInstance(this)
        paymoIdentifier.identificationListener = this
        identifyButton?.setOnClickListener {
            if (apiKeyInput?.text?.isBlank() == false && agentIdInput?.text?.isBlank() == false) {
                resultInfo?.text = ""
                imageView?.visibility = View.GONE
                paymoIdentifier.requestIdentification(
                    agentId = agentIdInput.text.toString().toInt(),
                    apiKey = apiKeyInput.text.toString()
                )
            } else
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        paymoIdentifier.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("SetTextI18n")
    override fun onIdentificationComplete(identificationResult: IdentificationData) {
        resultInfo?.text = "Success: ${identificationResult.success}"
        if (identificationResult.success) {
            imageView?.visibility = View.VISIBLE
            val data = identificationResult.userData!!
            imageView?.setImageBitmap(paymoIdentifier.base64ToBitmap(data.photoBase64))
            resultInfo?.append("\nName: ${data.firstName} ${data.lastName}")
            resultInfo?.append("\nPassport number: ${data.passportNumber}")
            resultInfo?.append("\nPIN: ${data.pin}")
            resultInfo?.append("\nDate of birth: ${data.dateOfBirth}")
            resultInfo?.append("\nDate of issue: ${data.dateOfIssue}")
            resultInfo?.append("\nDate of expiry: ${data.dateOfExpiry}")
            resultInfo?.append("\nIssuer centre: ${data.issuerCentre}")
            resultInfo?.append("\nIP Address: ${data.ipAddress}")
            resultInfo?.append("\nUserAgent: ${data.userAgent}")
            resultInfo?.append("\nDevice Info: ${data.deviceInfo}")
            resultInfo?.append("\nAuthKey: ${data.authKey}")
        }
    }

    override fun onIdentificationCancel() {
        resultInfo?.text = "Identification cancelled"
    }

    override fun onIdentificationFailed(throwable: Throwable) {
        resultInfo?.text = "Identification failed"
    }
}
