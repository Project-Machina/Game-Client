package com.client.game.ui.login

import com.client.network.NetworkClient
import com.client.network.session.NetworkSession.Companion.session
import com.client.scripting.Extensions
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import tornadofx.*

class LoginView : View() {

    val model: LoginViewModel by inject()

    val client: NetworkClient by di()

    override val root: AnchorPane by fxml("login.fxml")

    val email: TextField by fxid()
    val password: PasswordField by fxid()

    val loginBtn: Button by fxid()

    init {
        loginBtn.setOnAction {

            runAsync {
                val connection = client.connect(email.text, password.text)
                connection
                    .onEach {
                        Extensions.setSession(it.channel().session)
                        it.channel().closeFuture().sync()
                    }.launchIn(CoroutineScope(Dispatchers.IO))
            } ui {
                if(it.isActive) {
                    model.isLoggedIn.set(true)
                }
            }

        }
    }

}