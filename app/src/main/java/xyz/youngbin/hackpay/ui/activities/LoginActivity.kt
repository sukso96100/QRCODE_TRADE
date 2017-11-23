package xyz.youngbin.hackpay.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import xyz.youngbin.hackpay.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import network.HPAPI
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        id_login_form.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(textViewId.windowToken, 0)
            imm.hideSoftInputFromWindow(textViewPassword.windowToken, 0)
        }
        textViewId.setOnEditorActionListener { _, actionId, _ -> run {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                textViewPassword.requestFocus()
                return@setOnEditorActionListener true
            }else return@run false
        } }
        textViewPassword.setOnEditorActionListener { _, actionId, _ -> run {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                attemptLogin()
                return@setOnEditorActionListener true
            }else return@run false
        } }

        sign_in_button.setOnClickListener { attemptLogin() }

        textViewId.setText("test")
        textViewPassword.setText("test")
    }

    private fun attemptLogin() {
        if (isLoading) return
        isLoading = true

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        textViewId.error = null
        textViewPassword.error = null

        val id = textViewId.text.toString()
        val password = textViewPassword.text.toString()

        var cancel = false
        var focusView: View? = null

        if (!TextUtils.isEmpty(password) && password.length < 4) {
            textViewPassword.error = getString(R.string.error_invalid_password)
            focusView = textViewPassword
            cancel = true
        }
        if (TextUtils.isEmpty(id)) {
            textViewId.error = getString(R.string.error_field_required)
            focusView = textViewId
            cancel = true
        } else if (id.length < 4) {
            textViewId.error = getString(R.string.error_invalid_email)
            focusView = textViewId
            cancel = true
        }

        if (cancel) {
            focusView!!.requestFocus()
            isLoading = false
        } else {
            showProgress(true)

            thread {
                val json = JSONObject(mapOf("name" to id, "password" to password))
                val r = HPAPI.post("/user", mapOf("type" to "signin"), json)

                runOnUiThread {
                    showProgress(false)
                    if (r == null) {
                        textViewPassword.error = getString(R.string.error_network)
                        textViewPassword.requestFocus()
                    }else if (r.statusCode != 200) {
                        textViewPassword.error = "로그인 정보가 잘못되었습니다."
                        textViewPassword.requestFocus()
                    }else {
                        val rj = r.jsonObject
                        HPAPI.session = rj.getString("jwt")
                        if (!rj.isNull("seller_id")) HPAPI.seller_id = rj.getInt("seller_id")

                        val pushIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        pushIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP xor Intent.FLAG_ACTIVITY_SINGLE_TOP
                        pushIntent.putExtra("balance", rj.getInt("balance"))
                        startActivity(pushIntent)
                    }
                    isLoading = false
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                login_form.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                login_progress.visibility = if (show) View.VISIBLE else View.GONE
            }
        })
    }
}

