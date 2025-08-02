package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val savedMemo = sharedPref.getString("memo", "") ?: ""
        val savedTime = sharedPref.getString("memo_time", "") ?: ""

        setContent {
            MyApplicationTheme {
                var memo by remember { mutableStateOf(savedMemo) }
                var timeText by remember { mutableStateOf(savedTime) }

                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = memo,
                        onValueChange = { memo = it },
                        label = { Text("メモを入力") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        val now = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())
                        sharedPref.edit()
                            .putString("memo", memo)
                            .putString("memo_time", now)
                            .apply()
                        timeText = now
                    }) {
                        Text("保存")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (memo.isNotEmpty()) {
                        Text("保存されたメモ: $memo")
                        Text("保存日時: $timeText")
                    }
                }
            }
        }
    }
}
