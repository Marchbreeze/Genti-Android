package kr.genti.presentation.auth.login

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import dagger.hilt.android.AndroidEntryPoint
import kr.genti.core.base.BaseActivity
import kr.genti.core.extension.colorOf
import kr.genti.core.extension.initOnBackPressedListener
import kr.genti.core.extension.setOnSingleClickListener
import kr.genti.presentation.R
import kr.genti.presentation.auth.signup.SignupActivity
import kr.genti.presentation.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBtnListener()
        initOnBackPressedListener(binding.root)
        setStatusBarTransparent()
        setNavigationBarGreen()
    }

    private fun initLoginBtnListener() {
        binding.btnLoginKakao.setOnSingleClickListener {
            // TODO 자동 로그인 구현
            Intent(this, SignupActivity::class.java).apply {
                startActivity(this, ActivityOptions.makeCustomAnimation(this@LoginActivity, 0, 0).toBundle())
            }
            finish()
        }
    }

    private fun setStatusBarTransparent() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            v.updatePadding(bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom)
            insets
        }
    }

    private fun setNavigationBarGreen() {
        this.window.navigationBarColor = colorOf(R.color.genti_green)
    }
}