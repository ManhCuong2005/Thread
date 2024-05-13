/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Thread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Clock extends JFrame {
    private JLabel timeLabel;
    private JTextField timeZoneField;

    public Clock() {
        setTitle("World Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(547, 300);
        setLocationRelativeTo(null);

        // Label hiển thị thời gian
        timeLabel = new JLabel();
        timeLabel.setBounds(153, 38, 245, 100);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        updateTime();
        getContentPane().setLayout(null);
        getContentPane().add(timeLabel);
        JLabel label = new JLabel("Nhập múi giờ:");
        label.setBounds(21, 182, 136, 43);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        getContentPane().add(label);
        timeZoneField = new JTextField(20);
        timeZoneField.setBounds(147, 186, 166, 35);
        timeZoneField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        getContentPane().add(timeZoneField);
        
                JButton openButton = new JButton("Open");
                openButton.setBounds(360, 175, 136, 50);
                openButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
                getContentPane().add(openButton);
                openButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String timeZone = timeZoneField.getText();
                        if (!timeZone.isEmpty()) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    new WorldClock(timeZone).setVisible(true);
                                }
                            }).start();
                        } else {
                            JOptionPane.showMessageDialog(Clock.this, "Please enter a time zone.");
                        }
                    }
                });

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();
    }

    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        timeLabel.setText(dateFormat.format(new Date()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Clock().setVisible(true);
            }
        });
    }
    
    class WorldClock extends JFrame {
        private JLabel timeLabel;
        private DateTimeFormatter formatter;
        
        public WorldClock(String timeZone) {
            this.setSize(547, 300);
            this.setLocationRelativeTo(null);

                // Khởi tạo formatter
            try {
                formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            } catch (NullPointerException e) {
                System.out.println("Error initializing formatter: " + e.getMessage());
            }

            initializeUI(timeZone);

            Timer timer = new Timer(1000, e -> updateTime(timeZone));
            timer.start();
        }

        private void initializeUI(String timeZone) {
            
            getContentPane().setLayout(null);

            timeLabel = new JLabel();
            timeLabel.setBounds(153, 38, 245, 100);
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 40));
            timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            updateTime(timeZone);
            getContentPane().add(timeLabel);

            JLabel label = new JLabel("Time Zone:");
            label.setBounds(21, 182, 116, 43);
            label.setFont(new Font("Tahoma", Font.PLAIN, 20));
            getContentPane().add(label);

            JTextField timeZoneField = new JTextField(20);
            timeZoneField.setBounds(147, 186, 166, 35);
            timeZoneField.setFont(new Font("Tahoma", Font.PLAIN, 20));
            getContentPane().add(timeZoneField);

            JButton openButton = new JButton("Open");
            openButton.setBounds(360, 165, 136, 77);
            openButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
            getContentPane().add(openButton);
            openButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String timeZone = timeZoneField.getText();
                    if (!timeZone.isEmpty()) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                new WorldClock(timeZone).setVisible(true);
                            }
                        }).start();
                    } else {
                        JOptionPane.showMessageDialog(WorldClock.this, "Please enter a time zone.");
                    }
                }
            });
        }

        private void updateTime(String timeZone) {
            try {
                int offset = Integer.parseInt(timeZone);
                ZoneId zoneId = ZoneOffset.ofHours(offset);
                ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
                String formattedTime = zonedDateTime.format(formatter);
                timeLabel.setText(formattedTime);
            } catch (DateTimeException | NumberFormatException e) {
                JOptionPane.showMessageDialog(WorldClock.this, "Invalid time zone format.");
            }
            }
        }
    
    private void updateTime(String timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT" + timeZone));
        timeLabel.setText(dateFormat.format(new Date()));
    }
}
