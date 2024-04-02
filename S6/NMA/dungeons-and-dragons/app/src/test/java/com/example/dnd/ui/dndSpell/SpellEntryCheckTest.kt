package com.example.dnd.ui.dndSpell

import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class SpellEntryCheckTest {

    @Test(expected = InvalidSpellDetailsException::class)
    fun `empty name throws InvalidSpellDetailsException`() {
        SpellEntryCheck.checkValidDetails(
            SpellUiState(name = "", description = "yes", timeUnit = "action", school = "abjuration")
        )
    }

    @Test(expected = InvalidSpellDetailsException::class)
    fun `empty description throws InvalidSpellDetailsException`() {
        SpellEntryCheck.checkValidDetails(
            SpellUiState(name = "yes", description = "", timeUnit = "action", school = "abjuration")
        )
    }

    @Test(expected = InvalidSpellDetailsException::class)
    fun `empty timeUnit throws InvalidSpellDetailsException`() {
        SpellEntryCheck.checkValidDetails(
            SpellUiState(name = "yes", description = "yes", timeUnit = "", school = "abjuration")
        )
    }

    @Test(expected = InvalidSpellDetailsException::class)
    fun `invalid level throws InvalidSpellDetailsException`() {
        SpellEntryCheck.checkValidDetails(
            SpellUiState(name = "yes", description = "yes", timeUnit = "action", level = -1, school = "abjuration")
        )
    }

    @Test(expected = InvalidSpellDetailsException::class)
    fun `invalid timeNumber throws InvalidSpellDetailsException`() {
        SpellEntryCheck.checkValidDetails(
            SpellUiState(name = "yes", description = "yes", timeUnit = "action", timeNumber = -1, school = "abjuration")
        )
    }

    @Test(expected = InvalidSpellDetailsException::class)
    fun `invalid distanceAmount throws InvalidSpellDetailsException`() {
        SpellEntryCheck.checkValidDetails(
            SpellUiState(name = "yes", description = "yes", timeUnit = "action", distanceAmount = -1, school = "abjuration")
        )
    }

    @Test
    fun `correct details do not throw any exception`() {
        var res = false;
        try {
            res = SpellEntryCheck.checkValidDetails(
                SpellUiState(name = "yes", description = "yes", timeUnit = "action", school = "abjuration")
            )
            // If no exception is thrown, the test passes
        } catch (e: InvalidSpellDetailsException) {
            // If an exception is thrown, fail the test
            assertFalse(true)
        }
        // If no exception is thrown, the test passes
        assertTrue(res)
    }
}
