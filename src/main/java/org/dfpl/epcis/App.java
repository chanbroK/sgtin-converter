package org.dfpl.epcis;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        SGTIN sgtin = new SGTIN("8801094013004");

        System.out.println(sgtin.getSGTIN());
    }
}
