package org.p2p.solanaj.utils.bip39

fun generateRandomSeedWords(count: Int) = WORDLIST_ENGLISH.shuffled().take(count)