package com.xebia.xke_memory_client;

import com.xebia.xke_memory_client.service.MemoryClientService;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        final MemoryClientService service = new MemoryClientService(args[0], args[1]);
        service.register("toto@gmail.com");

        service.playerScore("127.0.0.1");

        service.gameScore("1");
    }
}
