package io.github.vkb24312.BaseConverter2;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        //region Swing setup
        JFrame frame = new JFrame("Base converter");
            JPanel head = new JPanel(new GridLayout(5, 1));
                JPanel inNumP = new JPanel();
                    JLabel inNumDesc = new JLabel("Input Number");
                    JTextField inNum = new JTextField(20); //20 because Long.MAX_VALUE is 19 digits (base 10)
                    inNumP.add(inNumDesc);
                    inNumP.add(inNum);

                JPanel inBasP = new JPanel();
                    JLabel inBasDesc = new JLabel("Input's base");
                    JComboBox<Integer> inBas = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6, 7, 8, 9, 10});
                    inBas.setSelectedIndex(8);
                    inBasP.add(inBasDesc);
                    inBasP.add(inBas);

                JPanel outBasP = new JPanel();
                    JLabel outBasDesc = new JLabel("Output's base");
                    JComboBox<Integer> outBas = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6, 7, 8, 9, 10});
                    outBas.setSelectedIndex(0);
                    outBasP.add(outBasDesc);
                    outBasP.add(outBas);

                JPanel inEntP = new JPanel();
                    JButton enter = new JButton("Convert");
                    inEntP.add(enter);

                JPanel outNumP = new JPanel();
                    JLabel output = new JLabel("Output will display here when you press enter");
                    outNumP.add(output);

                head.add(inNumP);
                head.add(inBasP);
                head.add(outBasP);
                head.add(inEntP);
                head.add(outNumP);

            frame.add(head);

        frame.setVisible(true);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(3);
        //endregion

        enter.addActionListener(l -> {
            String input = inNum.getText();
            int inputBase = inBas.getSelectedIndex()+2;
            int outputBase = outBas.getSelectedIndex()+2;
            try {
                output.setText(convert(input, inputBase, outputBase));
            } catch (NumberFormatException e){
                output.setText("We don't support non-numerics");
            }
        });
    }

    private static String convert(String in, Integer inBase, Integer outBase){
        if(inBase.equals(outBase)) return in;
        if(inBase!=10){
            in = toDec(in, inBase);
        }
        if(outBase!=10) {

            System.out.println(in);

            BigInteger result = new BigInteger(in);
            ArrayList<Integer> digits = new ArrayList<>();

            BigInteger OB = new BigInteger(outBase.toString());

            do {
                digits.add(Integer.parseInt(result.mod(OB).toString())); //Basically result%10
                result = result.divide(OB);
                System.out.println(result);
                System.out.println(digits);
            } while (!result.equals(new BigInteger("0")));

            Collections.reverse(digits);

            StringBuilder s = new StringBuilder("\u0000");

            for (Integer digit : digits) {
                s.append(digit);
            }

            return s.toString();
        } else return in;
    }

    private static String toDec(String in, int inBase){
        ArrayList<Integer> digits = new ArrayList<>();
        char[] tempDigitsArray = in.toCharArray();
        Integer[] tempIntDigitsArray = new Integer[tempDigitsArray.length];

        for (int i = 0; i < tempDigitsArray.length; i++) {
            tempIntDigitsArray[i] = Integer.parseInt(new String(new char[]{tempDigitsArray[i]}));
        }

        Collections.addAll(digits, tempIntDigitsArray);

        Collections.reverse(digits);

        ArrayList<Double> numbers = new ArrayList<>();

        for (int i = 0; i < digits.size(); i++) {
            numbers.add(digits.get(i)*Math.pow(inBase, i));
        }

        BigInteger out = new BigInteger("0");

        for (Double n : numbers) {
            out = out.add(new BigInteger(Integer.toString((n.intValue()))));
        }

        return out.toString();
    }
}
