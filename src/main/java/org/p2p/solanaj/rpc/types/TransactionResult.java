package org.p2p.solanaj.rpc.types;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.ToString;
import org.p2p.solanaj.core.AccountMeta;
import org.p2p.solanaj.core.PublicKey;

import java.util.List;

@Getter
@ToString
public class TransactionResult {

    @Json(name = "meta")
    private Meta meta;

    @Json(name = "slot")
    private long slot;

    @Json(name = "transaction")
    private Transaction transaction;

    @Getter
    @ToString
    public static class Meta {

        @Json(name = "err")
        private Object err;

        @Json(name = "fee")
        private long fee;

        @Json(name = "innerInstructions")
        private List<Object> innerInstructions;

        @Json(name = "preTokenBalances")
        private List<TokenBalance> preTokenBalances;

        @Json(name = "postTokenBalances")
        private List<TokenBalance> postTokenBalances;

        @Json(name = "postBalances")
        private List<Long> postBalances;

        @Json(name = "preBalances")
        private List<Long> preBalances;

        @Json(name = "status")
        private Status status;
    }

    @Getter
    @ToString
    public static class Transaction {

        @Json(name = "message")
        private Message message;

        @Json(name = "signatures")
        private List<String> signatures;
    }

    @Getter
    @ToString
    public static class Message {

        @Json(name = "accountKeys")
        private List<AccountKey> accountKeys;

        @Json(name = "instructions")
        private List<Instruction> instructions;

        @Json(name = "recentBlockhash")
        private String recentBlockhash;
    }

    @Getter
    @ToString
    public static class AccountKey {

        @Json(name = "pubkey")
        private String publicKey;

        @Json(name = "signer")
        private boolean signer;

        @Json(name = "writable")
        private boolean writable;

        public AccountMeta toMeta() {
            return new AccountMeta(new PublicKey(publicKey), signer, writable);
        }
    }

    @Getter
    @ToString
    public static class Instruction {

        @Json(name = "parsed")
        private Parsed parsed;

        @Json(name = "program")
        private String program;

        @Json(name = "programId")
        private String programId;
    }

    @Getter
    @ToString
    public static class Parsed {
        @Json(name = "info")
        Info info;
        @Json(name = "type")
        String type;
    }

    @Getter
    @ToString
    public static class Info {
        @Json(name = "destination")
        String destination;
        @Json(name = "lamports")
        long lamports;
        @Json(name = "source")
        String source;
    }

    @Getter
    @ToString
    public static class Status {

        @Json(name = "Ok")
        private Object ok;
    }

    @Getter
    @ToString
    public static class TokenBalance {

        @Json(name = "accountIndex")
        private Double accountIndex;

        @Json(name = "mint")
        private String mint;

        @Json(name = "uiTokenAmount")
        private TokenResultObjects.TokenAmountInfo uiTokenAmount;
    }

}
