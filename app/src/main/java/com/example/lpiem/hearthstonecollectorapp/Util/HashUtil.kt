package com.example.lpiem.hearthstonecollectorapp.Util

import android.util.Log
import java.security.MessageDigest

class HashUtil {

    companion object {
        private fun byteArrayToHexString(array: Array<Byte>): String {
            var result = StringBuilder(array.size * 2)

            for (byte in array) {
                val toAppend = String.format("%2X", byte).replace(" ", "0") //Hexa
                result.append(toAppend).append("-")
            }
            result.setLength(result.length - 1) //del the last "-"
            return result.toString()
        }

        fun toMD5Hash(text: String): String {
            var result = ""

            try {
                val md5 = MessageDigest.getInstance("MD5")
                val md5HashBytes = md5.digest(text.toByteArray()).toTypedArray()
                result = byteArrayToHexString(md5HashBytes)
            }
            catch (e: Exception) {
                result = "error: ${e.message}"
            }

            return result
        }
    }


}