package com.client.javafx.login

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.ItemViewModel

class LoginViewModel : ItemViewModel<LoginMessage>(LoginMessage()) {

    val username = bind { SimpleStringProperty(item?.username, "", config.string("user")) }
    val password = bind { SimpleStringProperty(item?.password, "", config.string("pass")) }

    val remember = SimpleBooleanProperty(config.boolean("rem") ?: false)

    override fun onCommit() {

        if(remember.get()) {

            with(config) {
                set(USER_KEY to username.value)
                set(PASS_KEY to password.value)
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