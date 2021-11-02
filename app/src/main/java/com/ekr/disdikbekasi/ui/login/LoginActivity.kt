package com.ekr.disdikbekasi.ui.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ekr.disdikbekasi.R
import com.ekr.disdikbekasi.databinding.ActivityLoginBinding
import com.ekr.disdikbekasi.model.login.PostLogin
import com.ekr.disdikbekasi.model.login.ResponseLogin
import com.ekr.disdikbekasi.ui.MainActivity
import com.ekr.disdikbekasi.ui.forgetpswd.ResetPasswordActivity
import com.ekr.disdikbekasi.utils.DialogHelper
import com.ekr.disdikbekasi.utils.SessionManager

class LoginActivity : AppCompatActivity(),LoginContract.View {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var sessionManager: SessionManager
    private lateinit var dialog : Dialog
    private lateinit var postLogin: PostLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dialog = DialogHelper.globalLoading(this)
        loginPresenter = LoginPresenter(this)
        sessionManager = SessionManager(this)
        if (sessionManager.prefIsLogin){
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finishAffinity()
            finish()
        }
    }

    override fun initListener() {
        binding.btnLoginSubmit.setOnClickListener {
            when{
                binding.loginUsrname.text.toString() ==""->{
                    binding.loginUsrname.error = "NIP Tidak Boleh Kosong"
                    binding.loginUsrname.requestFocus()
                }
                binding.loginPswd.text.toString() ==""->{
                    binding.loginPswd.error = "Password"
                    binding.loginPswd.requestFocus()
                }
                else->{
                    postLogin = PostLogin(binding.loginUsrname.text.toString(),binding.loginPswd.text.toString())
                    loginPresenter.doLogin(postLogin)
                }
            }
        }
        binding.tvForget.setOnClickListener {
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }
    }

    override fun onLoading(loading: Boolean) {
        when(loading){
            true->dialog.show()
            false->dialog.dismiss()
        }
    }

    override fun onResult(responseLogin: ResponseLogin) {
        responseLogin.dataLoginLogin?.let { loginPresenter.setSessions(sessionManager, it) }
        if (responseLogin.status){
            startActivity(Intent(this,MainActivity::class.java))
            finishAffinity()
            finish()
        }

    }

    override fun showMessage(message: String) {
       Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
    }
}