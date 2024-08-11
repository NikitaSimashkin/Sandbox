package ru.kram.architecture.imageloader

interface ImageLoader {

    suspend fun load(request: ImageRequest): ImageResult

    fun loadSync(request: ImageRequest, listener: ImageLoadListener)
}

abstract class ImageRequest(
    private val configuration: LoadConfiguration
)

interface ImageResult

interface ImageLoadListener {
    fun onSuccess(result: ImageResult)
    fun onError(e: Throwable)
}

data class LoadConfiguration(
    val forceNetwork: Boolean,
    val enableCache: Boolean,
    val params: Map<String, String>
)