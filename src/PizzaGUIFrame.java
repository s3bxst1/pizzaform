import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;



public class PizzaGUIFrame extends JFrame {
    private JPanel mainPanel, selectionPanel, displayPanel, buttonPanel;
    private JTextArea orderTextArea;
    private JLabel titleLbl;
    private JScrollPane scroller;
    private JCheckBox[] toppings;
    private JRadioButton rbThin, rbRegular, rbDD;
    private JButton orderButton, clearButton, quitButton;
    private JComboBox sizeComboBox;

    private String[] sizes = {"Small   $8.00", "Medium   $12.00", "Large   $16.00", "Super   $20.00"};




    public PizzaGUIFrame() {

        mainPanel = new JPanel();
        selectionPanel = new JPanel();
        displayPanel = new JPanel();
        buttonPanel = new JPanel();


        orderTextArea = new JTextArea(20, 20);
        scroller = new JScrollPane(orderTextArea);
        orderTextArea.setEditable(false);
        orderTextArea.setWrapStyleWord(true);
        orderTextArea.setLineWrap(true);




        toppings = new JCheckBox[6];
        for (int i = 0; i < 6; i++) {
            toppings[0] = new JCheckBox("Pepperoni");
            toppings[1] = new JCheckBox("Sausage");
            toppings[2] = new JCheckBox("Broccoli");
            toppings[3] = new JCheckBox("Chocolate");
            toppings[4] = new JCheckBox("Bluberries");
            toppings[5] = new JCheckBox("Guitar string cheese");



            rbThin = new JRadioButton("Thin");
            rbRegular = new JRadioButton("Regular");
            rbDD = new JRadioButton("Deep-Dish");
            Font radBut = new Font("Helvetica", Font.BOLD, 15);
            rbThin.setFont(radBut);
            rbRegular.setFont(radBut);
            rbDD.setFont(radBut);



            ImageIcon icon = new ImageIcon("src/pizzabanner.png");
            Image image = icon.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(image);

            titleLbl = new JLabel("", resizedIcon, JLabel.CENTER);





            orderButton = new JButton("Order");
            clearButton = new JButton("Clear");
            quitButton = new JButton("Quit");
            Font butFont = new Font("Helvetica", Font.BOLD, 20);
            orderButton.setFont(butFont);
            clearButton.setFont(butFont);
            quitButton.setFont(butFont);



            sizeComboBox = new JComboBox(sizes);



            add(mainPanel);

            mainPanel.setLayout(new BorderLayout());
            selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));

            mainPanel.add(selectionPanel, BorderLayout.NORTH);
            selectionPanel.add(titleLbl);
            for (i = 0; i < 6; i++) {
                selectionPanel.add(toppings[i]);
            }
            selectionPanel.add(rbThin);
            selectionPanel.add(rbRegular);
            selectionPanel.add(rbDD);
            selectionPanel.add(sizeComboBox);
            selectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Selections"));



            mainPanel.add(displayPanel, BorderLayout.CENTER);
            displayPanel.add(scroller);
            displayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Display"));





            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            buttonPanel.setLayout(new GridLayout(1, 3));
            buttonPanel.add(orderButton);
            orderButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    createOrder();
                }
            });
            buttonPanel.add(clearButton);
            clearButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    clearForm();
                }
            });
            buttonPanel.add(quitButton);
            quitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int response = JOptionPane.showConfirmDialog(null, "Did you mean to quit?", "Quit",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });
            buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Buttons"));










            setSize(500, 1200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);






        }






    }
    private void createOrder() {
        String selectedCrust = "";
        if (rbThin.isSelected()) {
            selectedCrust = "Thin";
        } else if (rbRegular.isSelected()) {
            selectedCrust = "Regular";
        } else if (rbDD.isSelected()) {
            selectedCrust = "Deep-Dish";
        }

        String selectedSize = (String) sizeComboBox.getSelectedItem();

        StringBuilder selectedToppings = new StringBuilder();
        double totalToppingsCost = 0.0;
        for (JCheckBox topping : toppings){
            if (topping.isSelected()){
                selectedToppings.append(topping.getText()).append(", ");
                totalToppingsCost +=1.00;
            }
        }

        if (selectedCrust.isEmpty() || selectedSize.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please select both crust and size" , "Incomplete Order" , JOptionPane.WARNING_MESSAGE);
            return;
        }

        double baseCost;
        switch (selectedSize) {
            case "Small   $8.00":
                baseCost = 8.00;
                break;
            case "Medium   $12.00":
                baseCost = 12.00;
                break;
            case "Large   $16.00":
                baseCost = 16.00;
                break;
            case "Super   $20.00":
                baseCost = 20.00;
                break;
            default:
                baseCost = 0.00;

        }

        double subTotal = baseCost + totalToppingsCost;
        double tax = 0.07 * subTotal;
        double totalCost = subTotal + tax;

        DecimalFormat df = new DecimalFormat("#.##");
        orderTextArea.setText("----- Pizza Order -----\n\n");
        orderTextArea.append("Crust: " + selectedCrust + "\n");
        orderTextArea.append("Size: " + selectedSize + "\n");
        orderTextArea.append("Toppings: " + (selectedToppings.length() > 0 ? selectedToppings.substring(0, selectedToppings.length() - 2) : "None") + "\n");
        orderTextArea.append("\nSub-total: $" + df.format(subTotal) + "\n");
        orderTextArea.append("Tax: $" + df.format(tax) + "\n");
        orderTextArea.append("------------------------\n");
        orderTextArea.append("Total: $" + df.format(totalCost) + "\n");
        orderTextArea.append("=================\n");

    }

    private void clearForm() {
        rbThin.setSelected(false);
        rbRegular.setSelected(false);
        rbDD.setSelected(false);

        sizeComboBox.setSelectedIndex(0);

        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }

        orderTextArea.setText("");
    }
}
