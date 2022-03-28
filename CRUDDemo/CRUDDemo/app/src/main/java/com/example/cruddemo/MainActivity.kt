package com.example.cruddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var edContact: EditText
    private lateinit var edAddress: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: UserAdapter? = null
    private var user:UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addUser() }
        btnView.setOnClickListener { getUser() }
        btnUpdate.setOnClickListener { updateUser() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            edContact.setText(it.contact)
            edAddress.setText(it.address)
            user = it
        }

        adapter?.setOnClickDeleteItem {
            deleteUser(it.id)
        }

        }

    private fun deleteUser(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Bạn chắc chắn muốn xóa mục này?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog, _ ->
            sqliteHelper.deleteUserById(id)
            getUser()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun updateUser() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val contact = edContact.text.toString()
        val address = edAddress.text.toString()

        if (name == user?.name && email == user?.email && contact == user?.contact && address == user?.address){
            Toast.makeText(this, "Không có mục nào thay đổi!", Toast.LENGTH_SHORT).show()
            return
        }


        if(user == null) return

        val user = UserModel(id = user!!.id, name = name, email = email, contact = contact, address = address)
        val status = sqliteHelper.updateUser(user)
        if (status > -1){
            Toast.makeText(this, "Chỉnh sửa thành công.", Toast.LENGTH_SHORT).show()
            clearEditText()
            getUser()
        }else{
            Toast.makeText(this, "Chỉnh sửa không thành công.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addUser() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()
        val contact = edContact.text.toString()
        val address = edAddress.text.toString()

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty()){
            Toast.makeText(this, "Không được để trống một trường nào", Toast.LENGTH_SHORT).show()
        } else{
                val user = UserModel(name = name, email = email, contact = contact, address = address)
                val status = sqliteHelper.insertUser(user)

                if (status > -1){
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                    clearEditText()
                    getUser()
                }else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }


    private fun getUser() {
        val userList = sqliteHelper.getAllUser()
        Log.e("user", "${userList.size}")
        adapter?.addItems(userList)
    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edContact.setText("")
        edAddress.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        edContact = findViewById(R.id.edContact)
        edAddress = findViewById(R.id.edAddress)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}