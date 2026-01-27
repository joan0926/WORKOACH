package com.example.workoach

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment

class AccountManagementDialog : DialogFragment() {

    private lateinit var userid: String
    private lateinit var Btn_logout: Button
    private lateinit var Btn_deluser: Button
    private lateinit var Btn_changepw: Button
    private lateinit var Btn_Close_management: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userid = arguments?.getString("USER_ID") ?: ""
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_accountmanagement, null)

        dialog.setContentView(view)
        dialog.setCancelable(true)

        initView(view)
        setListeners()

        return dialog
    }

    private fun initView(view: View) {
        Btn_logout = view.findViewById(R.id.btnlogout)
        Btn_deluser = view.findViewById(R.id.btndelaccount)
        Btn_changepw = view.findViewById(R.id.btnchange_password)
        Btn_Close_management = view.findViewById(R.id.btnclose_accountmanage)
    }

    private fun setListeners() {
        // 닫기 버튼
        Btn_Close_management.setOnClickListener {
            dismiss()
        }

        // 로그아웃
        Btn_logout.setOnClickListener {
            sendResultToFragment(RESULT_LOGOUT)
            dismiss()
        }

        // 계정 삭제
        Btn_deluser.setOnClickListener {
            sendResultToFragment(RESULT_DELETE)
            dismiss()
        }

        // 비밀번호 변경
        Btn_changepw.setOnClickListener {
            sendResultToFragment(RESULT_CHANGE_PW)
            dismiss()
        }
    }

    private fun sendResultToFragment(resultCode: Int) {
        parentFragmentManager.setFragmentResult(
            "AccountManagementResult",
            Bundle().apply { putInt("resultCode", resultCode) }
        )
    }

    companion object {
        const val RESULT_LOGOUT = 1001
        const val RESULT_DELETE = 1002
        const val RESULT_CHANGE_PW = 1003

        fun newInstance(userid: String): AccountManagementDialog {
            val dialog = AccountManagementDialog()
            val bundle = Bundle()
            bundle.putString("USER_ID", userid)
            dialog.arguments = bundle
            return dialog
        }
    }
}
