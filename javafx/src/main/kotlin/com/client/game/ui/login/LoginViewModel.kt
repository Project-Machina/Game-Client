package com.client.game.ui.login

import com.client.javafx.login.LoginMessage
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import kotlinx.coroutines.Job
import tornadofx.ItemViewModel

class LoginViewModel : ItemViewModel<LoginMessage>(LoginMessage()) {

    val username = bind { SimpleStringProperty(item?.username, "", config.string("user")) }
    val password = bind { SimpleStringProperty(item?.password, "", config.string("pass")) }

    val remember = SimpleBooleanProperty(config.boolean("rem") ?: false)

    val isLoggedIn = bind { SimpleBooleanProperty(this, "is_logged_in", false) }

    override fun onCommit() {

        if(remember.get()) {

            with(config) {
                set(USER_KEY to username.value)
                set(PASS_KEY to password.value)
                set(REMEMBER_KEY to remember.value)
                save()
            }

        }

    }

    companion object {
        const val USER_KEY = "user"
        const val PASS_KEY = "pass"
        const val REMEMBER_KEY = "rem"
    }
}