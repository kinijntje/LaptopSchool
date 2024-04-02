package com.example.dnd.ui.screens
import org.junit.Test
import org.testng.Assert

class LoginScreenKtTest {

    @Test
    fun `empty username returns false`() {
        val res = loginCheck("", "password")

        Assert.assertFalse(res)
    }

    @Test
    fun `empty password returns false`() {
        val res = loginCheck("username", "")

        Assert.assertFalse(res)
    }

    @Test
    fun `correct input returns true`() {
        val res = loginCheck("username", "password")

        Assert.assertTrue(res)
    }
}