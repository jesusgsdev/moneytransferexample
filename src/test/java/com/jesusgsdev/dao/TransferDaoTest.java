package com.jesusgsdev.dao;

import com.jesusgsdev.domain.Transfer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferDaoTest {

    private TransferDao transferDao;

    TransferDaoTest() {
        this.transferDao = new TransferDao();
    }

    @Test
    @DisplayName("Save a new transfer")
    void getWhenAccountExistsTest(){
        //Given
        Transfer newTransfer = new Transfer(UUID.randomUUID(), "GB26MIDL40051512345671", "GB26MIDL40051512345672",
                100.0, LocalDateTime.now());

        //When
        Transfer transfer = transferDao.save(newTransfer);

        //Then
        assertEquals(newTransfer, transfer);
    }

}
