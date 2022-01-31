package org.p2p.solanaj.programs;

import org.p2p.solanaj.core.AccountMeta;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.TransactionInstruction;

import java.util.List;

/**
 * Abstract class for
 */
public abstract class Program {

    /**
     * Returns a {@link TransactionInstruction} built from the specified values.
     *
     * @param programId Solana program we are calling
     * @param keys      AccountMeta keys
     * @param data      byte array sent to Solana
     * @return {@link TransactionInstruction} object containing specified values
     */
    public static TransactionInstruction createTransactionInstruction(
            PublicKey programId,
            List<AccountMeta> keys,
            byte[] data
    ) {
        return new TransactionInstruction(programId, keys, data);
    }

    public static class Id {
        public static PublicKey system = new PublicKey("11111111111111111111111111111111");
        public static PublicKey token = new PublicKey("TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA");
        public static PublicKey splAssociatedTokenAccount = new PublicKey("ATokenGPvbdGVxr1b2hvZbsiqW5xWH25efTNsLJA8knL");
        public static PublicKey sysvarRent = new PublicKey("SysvarRent111111111111111111111111111111111");
        public static PublicKey memo = new PublicKey("Memo1UhkJRfHyvLMcVucJwxXeuD728EqVDDwQDxFMNo");
        // Testnet address of basic-0 = EkEwddr34fqnv2SJREPynyC335PE32PAfjY4LVW5bTJS (has a method called initialize)
        public static PublicKey anchorBasic = new PublicKey("EkEwddr34fqnv2SJREPynyC335PE32PAfjY4LVW5bTJS");

        public static PublicKey wrappedSOLMint = new PublicKey("So11111111111111111111111111111111111111112");
        public static PublicKey ownerValidation = new PublicKey("4MNPdKu9wFMvEeZBMt3Eipfs5ovVWTJb31pEXDJAAxX5");
        public static PublicKey swapHostFeeAddress = new PublicKey("AHLwq66Cg3CuDJTFtwjPfwjJhifiv6rFwApQNKgX57Yg");
    }

}
