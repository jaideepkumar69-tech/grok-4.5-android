package com.grok45.chat

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var inputEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var adapter: ChatAdapter

    private val messages = mutableListOf<ChatMessage>()
    private var apiKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        inputEditText = findViewById(R.id.inputEditText)
        sendButton = findViewById(R.id.sendButton)

        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        recyclerView.adapter = adapter

        // Load saved API key
        val prefs = getSharedPreferences("grok45", Context.MODE_PRIVATE)
        apiKey = prefs.getString("api_key", "") ?: ""

        if (apiKey.isBlank()) {
            showApiKeyDialog()
        }

        sendButton.setOnClickListener { sendMessage() }

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else false
        }

        // Long press title area to change API key
        supportActionBar?.setDisplayShowTitleEnabled(true)
        title = "Grok 4.5"
    }

    private fun showApiKeyDialog() {
        val input = EditText(this).apply {
            hint = "xai-..."
            setText(apiKey)
        }

        AlertDialog.Builder(this)
            .setTitle("Enter xAI API Key")
            .setMessage("Get your key from https://console.x.ai")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                apiKey = input.text.toString().trim()
                getSharedPreferences("grok45", Context.MODE_PRIVATE)
                    .edit()
                    .putString("api_key", apiKey)
                    .apply()
                Toast.makeText(this, "API key saved", Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false)
            .show()
    }

    private fun sendMessage() {
        val text = inputEditText.text.toString().trim()
        if (text.isEmpty()) return

        if (apiKey.isBlank()) {
            showApiKeyDialog()
            return
        }

        // Add user message
        messages.add(ChatMessage(text, isUser = true))
        adapter.notifyItemInserted(messages.size - 1)
        recyclerView.scrollToPosition(messages.size - 1)
        inputEditText.text.clear()

        // Add empty assistant message (will be filled while streaming)
        val assistantIndex = messages.size
        messages.add(ChatMessage("", isUser = false))
        adapter.notifyItemInserted(assistantIndex)

        lifecycleScope.launch {
            try {
                val history = messages.dropLast(1).map {
                    mapOf(
                        "role" to if (it.isUser) "user" else "assistant",
                        "content" to it.text
                    )
                }

                GrokApi.streamChat(apiKey, history) { chunk ->
                    // Update UI on main thread
                    lifecycleScope.launch(Dispatchers.Main) {
                        val current = messages[assistantIndex]
                        messages[assistantIndex] = current.copy(text = current.text + chunk)
                        adapter.notifyItemChanged(assistantIndex)
                        recyclerView.scrollToPosition(assistantIndex)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    messages[assistantIndex] = ChatMessage("[Error] ${e.message}", isUser = false)
                    adapter.notifyItemChanged(assistantIndex)
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
