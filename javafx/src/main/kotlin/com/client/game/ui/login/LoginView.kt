package com.client.game.ui.login

import com.client.network.NetworkClient
import com.client.scripting.Extensions
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import tornadofx.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class LoginView : View() {

    val model: LoginViewModel by inject()

    val client: NetworkClient by di()

    override val root: AnchorPane by fxml("login.fxml")

    val email: TextField by fxid()
    val password: PasswordField by fxid()

    val loginBtn: Button by fxid()

    init {
        loginBtn.setOnAction {

            runAsync(true) {
                val session = client.connect(email.text, password.text)
                Extensions.setSession(session)
            } ui {
                model.isLoggedIn.set(true)
            }

        }
    }

    companion object NetworkCoroutine : CoroutineScope {
        val networkCoroutine = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        override val coroutineContext: CoroutineContext
            get() = networkCoroutine
    }
}