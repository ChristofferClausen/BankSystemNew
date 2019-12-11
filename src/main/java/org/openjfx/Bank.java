package org.openjfx;

public interface Bank {
    void deposit(int id, float amount);
    void withDraw(int id, float amount);
    float account(int id, float amount);
    int accountId();
}
