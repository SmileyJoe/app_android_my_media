package io.smileyjoe.media.utils

fun <T> List<T>.sortCaseInsensitive(selector: (T) -> String) =
    sortedWith(
        comparator = compareBy(
            comparator = String.CASE_INSENSITIVE_ORDER,
            selector = selector
        )
    )