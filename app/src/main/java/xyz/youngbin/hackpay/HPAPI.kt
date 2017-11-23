package xyz.youngbin.hackpay

import org.json.JSONObject

class HPAPI {
    companion object {
        const val HOST = "http://qr.youngbin.xyz:5000/api/v1"
        var session = ""

        var seller_id: Int? = null

        fun get(uri: String, params: Map<String, String>): khttp.responses.Response? {
            try {
                val headers = if (session != "") mapOf("Authorization" to session) else mapOf()
                return khttp.get("$HOST/$uri", headers=headers, params=params)
            }catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun post(uri: String, params: Map<String, String>, json: JSONObject): khttp.responses.Response? {
            try {
                val headers = if (session != "") mapOf("Authorization" to session) else mapOf()
                return khttp.post("$HOST/$uri", headers=headers, params=params, json=json)
            }catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun put(uri: String, params: Map<String, String>, json: JSONObject): khttp.responses.Response? {
            try {
                val headers = if (session != "") mapOf("Authorization" to session) else mapOf()
                return khttp.put("$HOST/$uri", headers=headers, params=params, json=json)
            }catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}