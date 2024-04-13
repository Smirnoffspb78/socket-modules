package com.company.smirnov.common;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;


public class ReceivingAnsSendingMessage implements AutoCloseable {

    private Message message;
    private Socket socket;  //Для установки соединения между клиентом и сервером нужен сокет //Для установки соединения между клиентом и сервером нужен сокет

    private ObjectInputStream inputStream; //Для получения сообщения
    private ObjectOutput outputStream; //Для отправки сообщения

    public ReceivingAnsSendingMessage(Socket socket) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream()); //инпут создается после аутпута, т.к. иначе инпут заблокирует инпут, т.к. канал будет ждать получения сообщения
    }

    public void send(Message message) throws IOException {
        message.setTimeOfSending(LocalDateTime.now());
            outputStream.writeObject(message);
            outputStream.flush();

    }
    public Message read(){
        try {
            return (Message) inputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() throws Exception {
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
