package com.rodcollab.cliq.utils.converters

object Converter {

    fun getValueTimeInLong(view: CharSequence): Long {
        return view.toString().split(":").mapIndexed { index, string ->
            when (index) {
                0 -> string.toLong() * 3600000L
                else -> {
                    string.toLong() * 60000L
                }
            }
        }.sumOf { it }
    }
}