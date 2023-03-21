package com.itis.example.di

interface ResourceProvider {
    fun getString(id: Int): String
    fun getString(id: Int, formatArgs: Any): String
    fun getColor(id: Int): Int
}
