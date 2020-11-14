package uz.paymo.example_identifier

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
            val data = identificationResult.userPassport!!
            val targetBitmap =
                paymoIdentifier.base64ToBitmap(identificationResult.identifiedPhotoBase64)
            val passportBitmap = paymoIdentifier.base64ToBitmap(data.photoBase64)
            val fragment = InfoFragment(
                userPassport = data,
                passportBitmap = passportBitmap,
                targetBitmap = targetBitmap
            )
            fragment.show(supportFragmentManager, "PassportInfo")
        }
    }

    override fun onIdentificationCancel() {
        resultInfo?.text = "Identification cancelled"
    }

    override fun onIdentificationFailed(throwable: Throwable) {
        resultInfo?.text = "Identification failed"
    }
}
