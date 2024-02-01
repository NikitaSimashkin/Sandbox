package ru.kram.sandlib.kotlin

import kotlin.reflect.KClass

fun main() {
	val a: String.Companion = String

	val b: KClass<String> = String::class

	val c: Class<String> = String::class.java

	val g: Class<out String> = String::class.java

	val d: Class<String.Companion> = String.javaClass

	val e: Class<String> = "".javaClass

	val f: Class<out CharSequence> = ""::class.java


}