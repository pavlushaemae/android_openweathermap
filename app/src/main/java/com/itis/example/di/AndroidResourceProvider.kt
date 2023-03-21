package com.itis.example.di

import android.content.Context

class AndroidResourceProvider(
    private val applicationContext: Context
) : ResourceProvider {
    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

    override fun getString(id: Int, formatArgs: Any): String {
        return applicationContext.getString(id, formatArgs)
    }

    override fun getColor(id: Int): Int {
        return applicationContext.getColor(id)
    }
}
