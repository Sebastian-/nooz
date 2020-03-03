package com.sebastianmurgu.nooz.network

import com.squareup.moshi.Json

data class Article (
    val title: String,
    val media: List<Media>
)

data class ArticleResponse (
    val results: List<Article>
)

data class Media (
    val type: String,
    @Json(name = "media-metadata") val mediaMetadata: List<MediaMetadata>
)

data class MediaMetadata (
    val url: String
)