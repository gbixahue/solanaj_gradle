package org.p2p.solanaj.rpc.types;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.ToString;

import java.util.AbstractMap;

/**
 * Created by Anton Zhilenkov on 07/02/2022.
 */
@Getter
@ToString
public
class AddressSignatures {
    @Json(name = "blockTime")
    private double blockTime;

    @Json(name = "confirmationStatus")
    private String confirmationStatus;

    @Json(name = "err")
    private Object err;

    @Json(name = "memo")
    private Object memo;

    @Json(name = "signature")
    private String signature;

    @Json(name = "slot")
    private double slot;

    public AddressSignatures(AbstractMap am) {
        this.blockTime = (double) am.get("blockTime");
        this.confirmationStatus = (String) am.get("confirmationStatus");
        this.signature = (String) am.get("signature");
        this.slot = (double) am.get("slot");
        this.err = am.get("err");
        this.memo = am.get("memo");
    }
}
