//Jayden Wyatt
//CS 2463
//Java Final Project
//Description: We are making an app that will track habits/goals and give points based on a certain metric that you give. The reward will be a point system that you then can cash in to gain 
//rewards from a shop of some sort. 

import javafx.*
import java.util.HashMap;

public class HabitTrackerGUI {

    private JFrame frame;
    private DefaultListModel<String> habitListModel;
    private JList<String> habitList;
    private JLabel pointsLabel;

    private int points = 0;

    // Store habit → points value
    private HashMap<String, Integer> habitPoints = new HashMap<>();

    public HabitTrackerGUI() {
        frame = new JFrame("Habit Tracker");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Habit List
        habitListModel = new DefaultListModel<>();
        habitList = new JList<>(habitListModel);
        frame.add(new JScrollPane(habitList), BorderLayout.CENTER);

        // Bottom Panel (Buttons)
        JPanel panel = new JPanel();

        JButton addButton = new JButton("Add Habit");
        JButton completeButton = new JButton("Complete Habit");
        JButton shopButton = new JButton("Open Shop");

        panel.add(addButton);
        panel.add(completeButton);
        panel.add(shopButton);

        frame.add(panel, BorderLayout.SOUTH);

        // Points Label
        pointsLabel = new JLabel("Points: 0");
        frame.add(pointsLabel, BorderLayout.NORTH);

        // Button Actions
        addButton.addActionListener(e -> addHabit());
        completeButton.addActionListener(e -> completeHabit());
        shopButton.addActionListener(e -> openShop());

        frame.setVisible(true);
    }

    private void addHabit() {
        String habit = JOptionPane.showInputDialog("Enter habit name:");
        String pointStr = JOptionPane.showInputDialog("Enter points for this habit:");

        if (habit != null && pointStr != null) {
            try {
                int value = Integer.parseInt(pointStr);
                habitListModel.addElement(habit);
                habitPoints.put(habit, value);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid number!");
            }
        }
    }

    private void completeHabit() {
        String selected = habitList.getSelectedValue();

        if (selected != null) {
            int earned = habitPoints.get(selected);
            points += earned;
            pointsLabel.setText("Points: " + points);

            JOptionPane.showMessageDialog(frame,
                    "You earned " + earned + " points!");
        } else {
            JOptionPane.showMessageDialog(frame, "Select a habit first!");
        }
    }

    private void openShop() {
        String[] rewards = {"Snack (10 pts)", "Game Time (20 pts)", "Day Off (50 pts)"};

        String choice = (String) JOptionPane.showInputDialog(
                frame,
                "Choose reward:",
                "Shop",
                JOptionPane.PLAIN_MESSAGE,
                null,
                rewards,
                rewards[0]);

        if (choice != null) {
            int cost = 0;

            if (choice.contains("10")) cost = 10;
            else if (choice.contains("20")) cost = 20;
            else if (choice.contains("50")) cost = 50;

            if (points >= cost) {
                points -= cost;
                pointsLabel.setText("Points: " + points);

                JOptionPane.showMessageDialog(frame,
                        "You redeemed: " + choice);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Not enough points!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HabitTrackerGUI::new);
    }
}