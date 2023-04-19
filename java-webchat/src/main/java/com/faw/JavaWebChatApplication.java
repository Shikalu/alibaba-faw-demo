package com.faw;

import com.faw.netty.NettyWebSocketServer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class JavaWebChatApplication implements CommandLineRunner {

	private final NettyWebSocketServer nettyWebSocketServer;

	public static void main(String[] args) {
		SpringApplication.run(JavaWebChatApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new Thread(nettyWebSocketServer).start();
	}
}
