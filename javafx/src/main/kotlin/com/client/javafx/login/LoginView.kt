package com.client.javafx.login

import com.client.game.ui.gameframe.GameFrameView
import com.client.javafx.login.LoginViewModel.Companion.REMEMBER_KEY
import com.client.network.NetworkClient
import com.client.scope.GameScope
import com.client.scripting.ScriptAPI.sessionProperty
import tornadofx.*

class LoginView : View() {

    val model: LoginViewModel by inject()

    val client: NetworkClient by di()

    override val root = form {

        fieldset("Login") {
            field("Username") {
                textfield(model.username).required()
            }
            field("Password") {
                passwordfield(model.password).required()
            }
            field("Remember Me") {
                checkbox(property = model.remember) {
                    action {
                        with(model.config) {
                            set(REMEMBER_KEY to model.remember.value)
                            save()
                        }
                    }
                }
            }
            button("Login") {
                enableWhen(model.valid)
                action {
                    model.commit {
                        //connect()
                        runAsync {
                            val session = client.connect(model.username.get(), model.password.get())
                            GameScope(session)
                        } ui {
                            sessionProperty.set(it.session)
                            this@LoginView.replaceWith(find<GameFrameView>(it))
                        }
                    }
                }
            }
        }

    }

}