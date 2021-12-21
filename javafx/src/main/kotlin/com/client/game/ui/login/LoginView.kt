package com.client.game.ui.login

import com.client.network.NetworkClient
import com.client.scripting.Extensions
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.util.Duration
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

    val infoLabel: Label by fxid()

    val rememberMe: CheckBox by fxid()

    init {

        email.textProperty().bindBidirectional(model.username)
        password.textProperty().bindBidirectional(model.password)

        model.remember.bind(rememberMe.selectedProperty())

        loginBtn.setOnAction {

            runAsync(true) {
                val session = client.connect(email.text, password.text)
                if (session != null) {
                    Extensions.setSession(session)
                }
                session
            } ui {
                if(it != null) {
                    model.isLoggedIn.set(true)
                    model.commit()
                } else {
                    infoLabel.text = "Error Connecting to Server."
                    timeline(true) {
                        keyframe(Duration.seconds(5.0)) {
                            setOnFinished {
                                infoLabel.text = ""
                            }
                        }
                    }
                }
            }
        }
    }

    companion object NetworkCoroutine : CoroutineScope {
        val networkCoroutine = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        override val coroutineContext: CoroutineContext
            get() = networkCoroutine
    }
}