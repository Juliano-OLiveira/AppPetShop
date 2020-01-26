package com.juliano.pet_shopp.Util;

import java.util.ArrayList;
public class Proprietarios extends ArrayList<Proprietario> {
    public Proprietario getById(int id) {
        for (Proprietario proprietario : this) {
            if (proprietario.getId() == id) {
                return proprietario;
            }
        }

        return null;
    }
}
