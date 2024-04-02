package com.example.dnd.ui.screens

import org.junit.Assert.*

import org.junit.Test
import org.testng.Assert

class SignUpScreenKtTest {

    @Test
    fun `empty username returns false`() {
        val res = registerCheck("", "password", "password")

        Assert.assertFalse(res)
    }

    @Test
    fun `empty password returns false`() {
        val res = registerCheck("username", "", "")

        Assert.assertFalse(res)
    }

    @Test
    fun `different confirm returns false`() {
        val res = registerCheck("username", "password", "pass")

        Assert.assertFalse(res)
    }

    @Test
    fun `correct input returns true`() {
        val res = registerCheck("username", "password", "password")

        Assert.assertTrue(res)
    }
}