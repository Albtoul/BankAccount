package com.example.bankaccount

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    lateinit var deposit: Button //initialise  variable
    lateinit var withdraw: Button
    lateinit var depositText: EditText
    lateinit var withdrawText: EditText
    lateinit var currentBalance : TextView
    lateinit var clroot : ConstraintLayout
    private lateinit var recycler : RecyclerView
    private lateinit var salary: ArrayList<String>
    private lateinit var sharedPreferences: SharedPreferences
    var balance = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clroot = findViewById(R.id.clRoot)//get the id to their variable
        recycler=findViewById(R.id.RV)
        salary= ArrayList()
        deposit=findViewById(R.id.button)
        withdraw=findViewById(R.id.button2)
        depositText=findViewById(R.id.DEPOSIT)
        withdrawText=findViewById(R.id.WITHDRAW)
        currentBalance=findViewById(R.id.CurrentBalance)

        recycler.adapter=Adapter(salary)
        recycler.layoutManager = LinearLayoutManager(this)

        deposit.setOnClickListener { DeoisitOperation() }
        withdraw.setOnClickListener { WithdrawOperation() }

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), MODE_PRIVATE
        )
        balance = sharedPreferences.getInt("myBalance", 0)
        with(sharedPreferences.edit()) {
            putInt("myMessage", balance)
            apply()
        }


    }



fun DeoisitOperation(){

    try {
        val amount = depositText.text.toString()
        balance+=amount.toInt()

        salary.add("Deposit:$amount")
        currentBalance.text="Current Balance: $balance"
        depositText.text.clear()
        deposit.clearFocus()
        recycler.adapter?.notifyDataSetChanged()
        recycler.scrollToPosition(salary.size -1)

    }catch (e:NumberFormatException){
        Toast.makeText(this," invalid!! please enter a number ",Toast.LENGTH_LONG).show()
    }
}
fun WithdrawOperation(){
    try {
        var times = 0
        val amount = withdrawText.text.toString()
        balance -= amount.toInt()

        if (balance > 0) {
            salary.add("withdraw :$amount")
            currentBalance.text = "Current Balance:$balance"
            withdrawText.text.clear()
            withdrawText.clearFocus()
            recycler.adapter?.notifyDataSetChanged()
            recycler.scrollToPosition(salary.size - 1)
        } else if (balance <= 0 && times == 0) {
            salary.add("withdraw :$amount")
            currentBalance.text = "Current Balance:$balance"

            Toast.makeText(this, "${currentBalance.text} you don't have mony ", Toast.LENGTH_LONG)
                .show()
            times++
            withdrawText.text.clear()
            withdrawText.clearFocus()
            recycler.adapter?.notifyDataSetChanged()
            recycler.scrollToPosition(salary.size - 1)
        }

    }catch (e:NumberFormatException){
        Toast.makeText(this," invalid!! please enter a number ",Toast.LENGTH_LONG).show()
    }


}


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("savedInt" , balance)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val userInt = savedInstanceState.getInt("savedInt",0)
        balance = userInt
        currentBalance.text = "Current Balance: $balance"
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clear_text -> {
                salary.clear()
                recycler?.adapter?.notifyDataSetChanged()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }




}