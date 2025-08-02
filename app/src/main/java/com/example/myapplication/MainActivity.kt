package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MemoRepository(this)

        setContent {
            MyApplicationTheme {
                var memoText by remember { mutableStateOf("") }
                var memoList by remember { mutableStateOf(repository.loadMemoList()) }

                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = memoText,
                        onValueChange = { memoText = it },
                        label = { Text("メモを入力") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (memoText.isNotBlank()) {
                                val now = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())
                                val newMemo = Memo(
                                    text = memoText,
                                    timestamp = now
                                )
                                memoList = memoList + newMemo
                                repository.saveMemoList(memoList)
                                memoText = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("保存")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("保存されたメモ：", style = MaterialTheme.typography.titleMedium)

                    LazyColumn {
                        items(memoList) { memo ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(text = memo.text)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "日時: ${memo.timestamp}",
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Button(
                                        onClick = {
                                            memoList = memoList.filter { it != memo }
                                            repository.saveMemoList(memoList)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                        modifier = Modifier.align(Alignment.End)
                                    ) {
                                        Text("削除", color = MaterialTheme.colorScheme.onError)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
