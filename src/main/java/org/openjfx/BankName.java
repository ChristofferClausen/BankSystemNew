package org.openjfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BankName implements Bank{
    static int accountCounter = 1100;
    private String name;
    private int id;
    private float saldo;
    private Person person;
    public List<BankName> accountList = new ArrayList<>();
    private static BankName bankName = null;


    private BankName() {
    }
    public BankName(String name) {
        this.name = name;
    }
    public static BankName getInstance(){
        if (bankName == null)
        {
            bankName = new BankName();
        }
        return bankName;
    }
    public BankName(String name, int id, float saldo, Person person) {
        this.name = name;
        this.id = id;
        this.saldo = saldo;
        this.person = person;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Person getPerson() {
        return person;
    }
    public float getSaldo() {
        return saldo;
    }
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    public String setName(String name) {
        this.name = name;
        return name;
    }

    @Override
    public void deposit(int id, float amount) {
        float amountNow = 0.0f;
        List<BankName> filterList = accountList.stream()
                .filter(e -> e.getId() ==id)
                .collect(Collectors.toList());
        if (filterList.size()>=1 ) {
            if (amount > 0) {
                int index = accountList.indexOf(filterList.get(0));
                float amountAvailable = accountList.get(index).getSaldo();
                amountNow = amountAvailable + amount;
                accountList.get(index).setSaldo(amountNow);
            }
        }
    }

    @Override
    public void withDraw(int id, float amount) {
        float amountNow = 0.0f;
        List<BankName> filterList = accountList.stream()
                .filter(e -> e.getId()==id)
                .collect(Collectors.toList());
        if (filterList.size()>=1 ) {
            int index = accountList.indexOf(filterList.get(0));
            float amountAvailable = accountList.get(index).getSaldo();
            if (amountAvailable >= amount) {
                amountNow = amountAvailable - amount;
                accountList.get(index).setSaldo(amountNow);
            }
        }
    }

    @Override
    public float account(int id, float amount) {
        List<BankName> filterList = accountList.stream()
                .filter(e -> e.getId()==id)
                .collect(Collectors.toList());
        int index = accountList.indexOf(filterList.get(0));
        double amountAvailable = accountList.get(index).getSaldo();

        return (float) amountAvailable;
    }

    @Override
    public int accountId() {
        id = accountCounter++;
        return id;
    }

    public void transfer(int accountIdFrom , int accountIdTo, float transferAmount) {
        List<BankName> filterList = accountList.stream()
                .filter(e -> e.getId()==accountIdFrom)
                .collect(Collectors.toList());
        List<BankName> filterList1 = accountList.stream()
                .filter(e -> e.getId()==accountIdTo)
                .collect(Collectors.toList());
        if (filterList.size()>=1 || filterList1.size() >= 1) {
            if (filterList.get(0).getSaldo() >= transferAmount) {
                int index = accountList.indexOf(filterList.get(0));
                float amount = accountList.get(index).getSaldo();
                accountList.get(index).setSaldo(amount - transferAmount);
                int index1 = accountList.indexOf(filterList1.get(0));
                float amount1 = accountList.get(index1).getSaldo();
                accountList.get(index1).setSaldo(amount1 + transferAmount);
            }
        }
    }

    public void dataForTest(){
        accountList.add(new BankName("SwedBank", accountId(), 20000.0f, new Person("Maroc", "Maroc", 50)));
        accountList.add(new BankName("SEBank", accountId(), 30000.0f, new Person("Safi", "Safi", 40)));
        accountList.add(new BankName("BMCE", accountId(), 10000.0f, new Person("Maroc", "Maroc", 30)));
        accountList.add(new BankName("BankPop", accountId(), 40000.0f, new Person("Safi", "Safi", 25)));
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankName='" + name + '\'' +
                ", id=" + id +
                ", saldo=" + saldo +
                ", person=" + person +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankName bankName = (BankName) o;
        return id == bankName.id &&
                Float.compare(bankName.saldo, saldo) == 0 &&
                name.equals(bankName.name) &&
                person.equals(bankName.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, saldo, person);
    }
}
