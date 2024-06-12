package com.ergophile.yd_notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chibatching.kotpref.Kotpref
import com.ergophile.yd_notes.ui.screens.detail_notes.DetailNotesScreen
import com.ergophile.yd_notes.ui.screens.home.HomeScreen
import com.ergophile.yd_notes.ui.screens.profile.ProfileScreen
import com.ergophile.yd_notes.ui.screens.user_login.UserLoginScreen
import com.ergophile.yd_notes.ui.screens.user_signup.UserSignupScreen
import com.ergophile.yd_notes.ui.theme.YDNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Kotpref.init(this)
        enableEdgeToEdge()
        setContent {
            YDNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = RouteName.SignUp.routeName
                    ) {
                        composable(route = RouteName.SignUp.routeName) {
                            UserSignupScreen(
                                goToLogin = { navController.navigate(RouteName.Login.routeName) },
                                goToHome = { navController.navigate(RouteName.Home.routeName) }
                            )
                        }
                        composable(route = RouteName.Login.routeName) {
                            UserLoginScreen(
                                goToSignUp = { navController.navigate(RouteName.SignUp.routeName) },
                                goToHome = { navController.navigate(RouteName.Home.routeName) }
                            )
                        }
                        composable(route = RouteName.Home.routeName) {
                            HomeScreen(
                                goToDetail = { navController.navigate(RouteName.Detail.createRoute(id = it)) },
                                goToProfile = { navController.navigate(RouteName.Profile.routeName) }
                            )
                        }
                        composable(route = RouteName.Detail.routeName, arguments = listOf(
                            navArgument("id"){
                                type = NavType.IntType
                            }
                        )) {backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id")?: 0
                            DetailNotesScreen(idNote = id,goBack = {navController.navigateUp()})
                        }
                        composable(route = RouteName.Profile.routeName) {
                            ProfileScreen(goBack = {navController.navigateUp()})
                        }
                    }
                }
            }
        }
    }
}