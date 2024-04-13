package com.company.smirnov.server;

import com.company.smirnov.common.Message;
import com.company.smirnov.common.ReceivingAnsSendingMessage;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static java.lang.System.out;


public class Server {

    private final int port;

    Map<String, RequestsServer> serverMap = new HashMap<>();

    public Server(int port) {
        this.port = port;
        Arrays.stream(RequestsServer.values()).forEach(requestServer -> serverMap.put(requestServer.getRequestName(), requestServer));
    }

    public void startServer() {
        try (ServerSocket serverSocketRocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocketRocket.accept();
                ReceivingAnsSendingMessage connectionHandler = new ReceivingAnsSendingMessage(socket);
                Message fromClient = connectionHandler.read();
                out.println(fromClient.getText());
                Message message = new Message("server");
                if (serverMap.containsKey(fromClient.getText())) {
                    message.setText(serverMap.get(fromClient.getText()).getFunctionality());
                    fromClient.getTimeOfSending();
                } else {
                    message.setText("Введенная команда отсутствует");
                }
                connectionHandler.send(message);
            }
        } catch (Exception e) {
            out.println("Ошибка запуска сервера");

        }
    }


}

