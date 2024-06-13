package com.ergophile.yd_notes

sealed class RouteName(val routeName: String) {
    object SignUp: RouteName(routeName = "signup")

    object Login: RouteName(routeName = "login")

    object Home: RouteName(routeName = "home")

    object Detail: RouteName(routeName = "detail?id={id}"){
        fun createRoute(id: Int?): String{
            return "detail?id=$id"
        }
    }

    object NewNote: RouteName(routeName = "new_note")

    object Profile: RouteName(routeName = "profile")
}