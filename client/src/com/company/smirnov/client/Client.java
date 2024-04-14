package com.company.smirnov.client;

import com.company.smirnov.common.Message;
import com.company.smirnov.common.ReceivingAndSendingMessage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import static java.lang.System.*;
import static java.lang.System.in;
import static java.util.Objects.requireNonNull;

public class Client {
    private final InetSocketAddress address;
    private String username;
    Scanner scanner = new Scanner(in);

    public Client(InetSocketAddress address) {
        this.address = requireNonNull(address);
    }

    public void startClient() {
        out.println("Введите имя");
        username = scanner.nextLine();
        boolean checkMessage = true;
        String exitCommand = "/exit";
        while (checkMessage) {
            out.println("Введите текст сообщения");
            String text = scanner.nextLine();
            if (!text.equals(exitCommand)) {
                try (ReceivingAndSendingMessage connectionHandler =
                             new ReceivingAndSendingMessage(new Socket(
                                     address.getHostName(),
                                     address.getPort()
                             ))) {
                    Message message = new Message(username);
                    message.setText(text);
                    try {
                        connectionHandler.send(message);
                        Message fromServer = connectionHandler.read();
                        out.println(fromServer.getText());
                        if (text.equals("/ping")){
                            out.println("%d ms".formatted(ChronoUnit.MILLIS.between(message.getTimeOfSending(), LocalDateTime.now())));
                        }
                    } catch (IOException e) {

                    }
                } catch (Exception e) {

                }
            } else {
                checkMessage = false;
            }
        }

    }
}
