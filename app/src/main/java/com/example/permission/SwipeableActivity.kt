package com.example.permission

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import com.example.permission.util.Object
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import swipe.gestures.GestureManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.permission.adapter.SwipeAdapter
import com.example.permission.databinding.ActivitySwipeableBinding

class SwipeableActivity : AppCompatActivity() {
    private lateinit var swipeAdapter: SwipeAdapter
    private val binding by lazy { ActivitySwipeableBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)
        swipeAdapter = SwipeAdapter(Object.contactList())
        val gestureManager = GestureManager(rightCallBack, leftCallBack)

        gestureManager.setBackgroundColorLeft(ColorDrawable(Color.parseColor("#00C853")))
        gestureManager.setBackgroundColorRight(ColorDrawable(Color.parseColor("#FF6D00")))

        gestureManager.setIconLeft(ContextCompat.getDrawable(this, R.drawable.baseline_email_24))
        gestureManager.setIconRight(ContextCompat.getDrawable(this, R.drawable.baseline_call_24))


        val itemTouchHelper = ItemTouchHelper(gestureManager)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.apply {
            adapter = swipeAdapter
            layoutManager = linearLayoutManager
        }
    }

    private val leftCallBack = GestureManager.SwipeCallbackLeft { position ->
        intent(position)
    }
    private val rightCallBack = GestureManager.SwipeCallbackRight { position ->
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${Object.contactList()[position].number}")
        startActivity(intent)
        swipeAdapter.notifyDataSetChanged()
    }

    private fun intent(pos: Int) {
        val bundle = bundleOf("contact" to Object.contactList()[pos])
        val intent = Intent(this, SendSmsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        swipeAdapter.notifyDataSetChanged()

    }
}