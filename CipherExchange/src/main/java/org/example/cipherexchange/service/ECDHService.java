package org.example.cipherexchange.service;

import org.example.cipherexchange.api.ECPoint;

public interface ECDHService {
    String GenerateKey(int APri, ECPoint BPub);
}
