package org.p2p.solanaj.utils.bip39

private const val SEED_WORDS_MINIMUM_COUNT = 12
private const val SEED_WORDS_MAXIMUM_COUNT = 24

fun validateSeedWords(words: List<String>): Boolean {
    if (words.count() < SEED_WORDS_MINIMUM_COUNT || words.count() > SEED_WORDS_MAXIMUM_COUNT) return false
    return WORDLIST_ENGLISH.intersect(words.toSet()).count() == words.count()
}