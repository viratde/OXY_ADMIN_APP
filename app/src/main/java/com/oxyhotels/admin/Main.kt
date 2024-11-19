package com.oxyhotels.admin


class A<T>(
    val a: T & Any
)


fun main() {

    val a = A<String?>(
        a = "Hello World!"
    )

    val b = a.a



}

