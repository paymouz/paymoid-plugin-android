# PAYMO.Identifier
\
[![N|Solid](https://paymo.uz/wp-content/themes/paymo/img/logo.svg)](https://paymo.uz)

[![](https://jitpack.io/v/paymouz/paymoid-plugin-android.svg)](https://jitpack.io/#paymouz/paymoid-plugin-android)

PAYMO.Identifier is an android plugin to make an easy integration with PAYMO&#46;ID identification system.

# Table of contents
- [Installation](#installation)
- [How to use the library?](#how-to-use-the-library?)
- [Methods](#methods)
- [Result IdentificationData](#result-identificationdata)
    - [UserData](#userdata)

# Installation
**Step 1.** Add the JitPack repository to root `build.gradle` at the end of repositories:
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**Step 2.** Add the dependency in your app `build.gradle`
```gradle
dependencies {
    implementation 'com.github.paymouz:paymoid-plugin-android:1.0.0-alpha01'
}
```
# How to use the library?
## Kotlin
### Setup
First get the instance of `PaymoIdentifier` in your **activity** or **fragment**
For `AppCompactActivity` call inside `onCreate`:
```kotlin
...
lateinit var paymoIdentifier: PaymoIdentifier
override fun onCreate(savedInstanceState: Bundle?) {
    ...
    paymoIdentifier = PaymoIdentifier.getInstance(this)
    ...
}
```
For `androidx.fragment.app.Fragment`  call inside `onCreateView`
```kotlin
...
lateinit var paymoIdentifier: PaymoIdentifier
override fun onCreateView(...): View? {
    ...
    paymoIdentifier = PaymoIdentifier.getInstance(this)
    ...
}
```
### Callback listener
You can implement `IdentificationListener` and define it in `PaymoIdentifier` or do it explicitly:
```kotlin
paymoIdentifier.identificationListener = object : IdentificationListener {
    override fun onIdentificationComplete(identificationResult: IdentificationData) {
        //Callback for completed identification process from PAYMO.ID
    }

    override fun onIdentificationCancel() {
        //Callback for explicitly canceled identification process from PAYMO.ID
        //(Ex. User closed PAYMO.ID without making some identification steps)
    }

    override fun onIdentificationFailed(throwable: Throwable) {
        //Callback for failed connection with PAYMO.ID
        //Might be called when AgentLogin for AuthKey process fails
        //or error during the [IdentificationData] retrieval process
    }
    //[Kotlin-OPTIONAL]
    override fun onPaymoIDInstallationCancel() {
        //Callback for cancelled install option of PAYMO.ID app
        //Called when user cancels Install option for the required application
    }
}
```
**Important!** For the listener to be called, it is important to override `onActivityResult` and pass the data to `PaymoIdentifier`
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    paymoIdentifier.onActivityResult(requestCode, resultCode, data) //Pass result data 
}
```
### Usage
Now you can easily use the plugin just with a single line of code, whenever identification process is required according to your needs:
```kotlin
paymoIdentifier.requestIdentification(agentId, apiKey)
```
**Note:** `agentId` and `apiKey` are provided by PAYMO organization    

# Methods
`PaymoIdentifier` contains several methods for its convenient usage

| Methods | Use |
| ------ | ------ |
| setIdentificationListener(`IdentificationListener`) |  Initialize listener for callbacks  |
| setRequestCode(`Int`) | Sets custom request code used for PAYMO&#46;ID in *startActivityForResult*. Default: **11273** |
| coreAppInstalled() : `Boolean` |  Checks whether PAYMO&#46;ID application is installed on the device  |
| hasSoftwareSupport() : `Boolean` | Checks whether device supports PAYMO&#46;ID its by MIN_SDK |
| hasHardwareNFCSupport(): `Boolean` | Checks whether device supports PAYMO&#46;ID's NFC feature |
| requestIdentification(`agentId`,`apiKey`) | Starts identification process in PAYMO&#46;ID |
| onActivityResult(`Int`,`Int`,`Intent`) | Process result data from PAYMO&#46;ID |
| base64ToBitmap(`String`): `Bitmap` | Returns image bitmap from encoded base64 photo in `UserData` |
| base64ToByteArray(`String`): `byte[]` | Returns image byte array from encoded base64 photo in `UserData` |

# Result IdentificationData
`IdentificationData` result passed in `onIdentificationComplete` callback. The data contains two major feilds:

| Field | Description |
| ------ | ------ |
| success : `Boolean` | Status of passport identification process |
| userData : `UserData` (*nullable*) | User passport data from identification process of PAYMO&#46;ID. if `success` is `false`, `userData` will be null since no data passed |

### UserData
Available data fields from successful identification process
| Field | Description |
| ------ | ------ |
| id: `Long` | Unique ID of identification process |
| authKey: `String` | Auth key defining Agent for identification   |
| ipAddress: `String` | IP address of device |
| userAgent: `String` | Device user agent during identification |
| deviceInfo: `String` | Device information  |
| passportNumber: `String` | User passport number |
| pin: `String` | Passport's person identification number |
| firstName: `String` | First name in passport |
| lastName: `String` | Last name in passport |
| dateOfBirth: `String` | Date of birth in passport |
| dateOfIssue: `String` | Date of issue in passport |
| dateOfExpiry: `String` | Date of expiry in passport |
| gender: `String` | Gender in passport |
| nationality: `String` | Nationality in passport |
| photoBase64: `String` | Person's photo from successful identification process |
| documentType: `String` | Document type (for passport **'P'**) |
| issuerCentre: `String` | Issuer centre where passport is given |

