package com.example.myapplication

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MemoRepository(context: Context) {
    private val prefs = context.getSharedPreferences("memo_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "memo_list"

    fun saveMemoList(memos: List<Memo>) {
        val json = gson.toJson(memos)
        prefs.edit().putString(key, json).apply()
    }

    fun loadMemoList(): List<Memo> {
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<Memo>>() {}.type
        return gson.fromJson(json, type)
    }
}
