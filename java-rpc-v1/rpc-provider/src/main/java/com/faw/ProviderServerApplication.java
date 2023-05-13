package com.faw;

import com.faw.server.RpcServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication
@RequiredArgsConstructor
public class ProviderServerApplication implements CommandLineRunner {
    private final RpcServer rpcServer;

    public static void main(String[] args) {
        SpringApplication.run(ProviderServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> rpcServer.start("127.0.0.1", 8888)).start();
    }
}
