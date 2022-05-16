package org.p2p.solanaj.programs;

import org.p2p.solanaj.core.AccountMeta;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.TransactionInstruction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Zhilenkov on 15/05/2022.
 */
public class AssociatedTokenProgram {

    static public TransactionInstruction createAssociatedTokenAccountInstruction(
            PublicKey associatedProgramId,
            PublicKey programId,
            PublicKey mint,
            PublicKey associatedAccount,
            PublicKey owner,
            PublicKey payer
    ) {
        if (associatedProgramId == null) associatedProgramId = Program.Id.splAssociatedTokenAccount;
        if (programId == null) programId = Program.Id.token;

        List<AccountMeta> keys = new ArrayList<>();
        keys.add(new AccountMeta(payer, true, true));
        keys.add(new AccountMeta(associatedAccount, false, true));
        keys.add(new AccountMeta(owner, false, false));
        keys.add(new AccountMeta(mint, false, false));
        keys.add(new AccountMeta(Program.Id.system, false, false));
        keys.add(new AccountMeta(programId, false, false));
        keys.add(new AccountMeta(Program.Id.sysvarRent, false, false));

        byte[] data = new byte[0];

        return new TransactionInstruction(associatedProgramId, keys, data);
    }
}
