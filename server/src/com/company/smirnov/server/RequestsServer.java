package com.company.smirnov.server;

import java.util.Arrays;

import static java.lang.System.out;

public enum RequestsServer {
    HELP("/help", "Список доступных запросов и их описание", 0) {
        final StringBuilder stringBuilder=new StringBuilder();
        @Override
        public String getFunctionality() {
            for (RequestsServer requestsServer: RequestsServer.values()){
                stringBuilder.append(requestsServer.getRequestName()).append(": ").append(requestsServer.getRequestDescription()).append("\n");
            }
            setCounterRequest(getCounterRequest()+1);
            return stringBuilder.toString();
        }
    },
    PING("/ping", "Время ответа сервера", 0) {
        @Override
        public String getFunctionality() {
            out.println("Время ответа сервера");
            setCounterRequest(getCounterRequest()+1);
            return getRequestDescription();
        }
    },
    REQUESTS("/requests", "Количество успешно обработанных запросов", 0) {
        @Override
        public String getFunctionality() {
            String text = "%s: %d".formatted(getRequestDescription(), Arrays.stream(RequestsServer.values())
                    .mapToLong(RequestsServer::getCounterRequest).sum());
            setCounterRequest(getCounterRequest()+1);
            return text;
        }
    },
    POPULAR("/popular", "Название самого популярного запроса", 0) {
        @Override
        public String getFunctionality() {
            long max = -1;
            String stringMax = "";
            for (RequestsServer requestServer : RequestsServer.values()) {
                if (requestServer.getCounterRequest() > max) {
                    max = requestServer.getCounterRequest();
                    stringMax = requestServer.getRequestName();
                }
            }
            setCounterRequest(getCounterRequest()+1);
            return "%s: %s".formatted(getRequestDescription(), stringMax);
        }
    };
    private final String requestName;
    private final String requestDescription;
    private long counterRequest;

    RequestsServer(String requestName, String requestDescription, int counterRequest) {
        this.requestName = requestName;
        this.requestDescription = requestDescription;
        this.counterRequest = counterRequest;
    }

    public String getFunctionality() {

        return null;
    }

    public long getCounterRequest() {
        return counterRequest;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    protected void setCounterRequest(long counterRequest) {
        this.counterRequest = counterRequest;
    }
}
