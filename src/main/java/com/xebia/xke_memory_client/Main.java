package com.xebia.xke_memory_client;

import com.xebia.xke_memory_client.service.MemoryClientService;

public class Main {

	private static final int GRID_SIZE = 6;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: ServerHostname ServerPort email");
			return;
		}

		final MemoryClientService service = new MemoryClientService(args[0], args[1]);
	}
}
