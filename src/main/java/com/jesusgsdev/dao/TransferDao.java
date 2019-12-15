package com.jesusgsdev.dao;

import com.jesusgsdev.domain.Transfer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransferDao {

    private Map<UUID, Transfer> transfers = new HashMap<>();

    public Transfer save(final Transfer transfer){
        transfers.put(transfer.getId(), transfer);
        return transfer;
    }

}
