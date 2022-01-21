package org.p2p.solanaj.utils.bip39

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SeedWordsValidatorTest {

    @Test
    fun `Return false when empty words list given`() {
        val given = listOf<String>()

        val _when = validateSeedWords(given)

        // then
        assertFalse(_when)
    }

    @Test
    fun `Return false when too small list given`() {
        val given = listOf<String>(
            "absurd",
            "abuse",
            "access",
            "accident",
            "account",
            "accuse",
            "achieve",
            "acid",
            "acoustic",
            "acquire",
            "across"
        )

        val _when = validateSeedWords(given)

        // then
        assertFalse(_when)
    }

    @Test
    fun `Return false when too big list given`() {
        val given = listOf<String>(
            "absurd",
            "abuse",
            "access",
            "accident",
            "account",
            "accuse",
            "achieve",
            "acid",
            "acoustic",
            "acquire",
            "across",
            "act",
            "action",
            "actor",
            "actress",
            "actual",
            "adapt",
            "add",
            "addict",
            "address",
            "adjust",
            "admit",
            "adult",
            "advance",
            "advice"
        )

        val _when = validateSeedWords(given)

        // then
        assertFalse(_when)
    }

    @Test
    fun `Return false when repeated word in list given`() {
        val given = listOf<String>(
            "absurd",
            "abuse",
            "access",
            "accident",
            "account",
            "accuse",
            "achieve",
            "acid",
            "acoustic",
            "acquire",
            "act",
            "act",
        )

        val _when = validateSeedWords(given)

        // then
        assertFalse(_when)
    }

    @Test
    fun `Return true when list without duplicates given`() {
        val given = listOf<String>(
            "absurd",
            "abuse",
            "access",
            "accident",
            "account",
            "accuse",
            "achieve",
            "acid",
            "acoustic",
            "acquire",
            "act",
            "more",
        )

        val _when = validateSeedWords(given)

        // then
        assertTrue(_when)
    }

    @Test
    fun `Return false when word is not included in allowed words list`() {
        val given = listOf<String>(
            "absurd",
            "abuse",
            "access",
            "accident",
            "account",
            "accuse",
            "achieve",
            "acid",
            "acoustic",
            "acquire",
            "act",
            "punto",
        )

        val _when = validateSeedWords(given)

        // then
        assertFalse(_when)
    }
}