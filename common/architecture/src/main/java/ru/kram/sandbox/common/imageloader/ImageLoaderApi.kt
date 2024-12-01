package ru.kram.sandbox.common.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import kotlin.time.Duration

object ImageLoader {

    fun init(context: Context) {}

    fun addSource(source: ImageLoadSource) {}

    fun removeSource(source: ImageLoadSource) {}

    suspend fun load(request: ImageRequest, transformations: List<Transformation>): ImageResult { TODO() }

    fun loadSync(request: ImageRequest, transformations: List<Transformation>, listener: ImageLoadListener) {}

    fun cancel(key: String) {}
}

data class ImageRequest(
    val key: String,
    val loadSourceData: LoadSourceData,
    val configuration: LoadConfiguration
)

interface ImageResult {
    val drawable: Drawable?
}

interface ImageLoadListener {
    fun onSuccess(result: ImageResult)
    fun onError(e: Throwable)
}

data class LoadConfiguration(
    val cacheConfiguration: CacheConfiguration,
    val placeholder: Drawable?,
    val params: Map<String, String>,
)

data class CacheConfiguration(
    val isMemoryCacheEnabled: Boolean,
    val isDiskCacheEnabled: Boolean,
    val timeoutMemoryCache: Duration,
    val timeoutDiskCache: Duration,
)

interface MemoryImageStore {
    fun put(key: String, drawable: Drawable)
    fun get(key: String): Drawable?
}

interface DiskImageStore {
    fun put(key: String, drawable: Drawable)
    fun get(key: String): Drawable?
}

interface LoadSourceData {
    data class Url(val url: String) : LoadSourceData
    data class Resource(val resId: Int) : LoadSourceData
    data class File(val path: String) : LoadSourceData
    data class ByteArray(val data: kotlin.ByteArray) : LoadSourceData
    data class Drawable(val drawable: android.graphics.drawable.Drawable) : LoadSourceData
}

interface ImageLoadSource {
    fun getSupportedLoadData(): Class<out LoadSourceData>
    fun load(loadSourceData: LoadSourceData): ImageResult
}

interface Transformation {
    fun transform(drawable: Drawable): Drawable
}

