package com.ergophile.yd_notes

import com.chibatching.kotpref.KotprefModel

object KotprefLocalStorage: KotprefModel() {
    var username: String by stringPref()
    var email: String by stringPref()
    var accessToken: String by stringPref()
    var userUid: String by stringPref()
}