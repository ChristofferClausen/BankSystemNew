package org.openjfx;

import java.util.List;
import java.util.stream.Collectors;

public class TestClass {
    public static void main(String[] args) {
        Model model = new Model();
        int id = 1102;
        if (model.checkAccountId(id)) {
            List<BankName> filterList = BankName.getInstance().accountList.stream()
                    .filter(e -> e.getId() == id)
                    .collect(Collectors.toList());

            System.out.println(filterList.get(0).getPerson().getFirstName());
            System.out.println(filterList.get(0).getPerson().getLastName());
            System.out.println((filterList.get(0).getSaldo()));
        } else {
            System.out.println("Something wrong");
        }
    }
}
