package ru.kram.sandbox.common.imageloader

/**
 * Функциональные требования:
 * 1) Авторизация
 * 2) Чат: сообщения, картинки
 * 3) Профиль: информация о пользователе, аватарка
 */

sealed interface Message {
    data class TextMessage(val text: String) : Message
    data class ImageMessage(val url: String) : Message
    data class VideoMessage(val url: String) : Message
}

interface MessageRemoteSource {
    fun getMessages(): List<Message>
    fun sendMessage(message: Message)
}

interface MessageLocalSource {
    fun getMessages(): List<Message>
    fun sendMessage(message: Message)
}