#include "ConnectionHandler.h"
#include <stdlib.h>
#include <string>
#include <boost/thread.hpp>


class Task {
private:
	
	ConnectionHandler* _connectionHandler;
public:
	Task(ConnectionHandler* connectionHandler) :  _connectionHandler(connectionHandler) {}

	void run() {
		while (1) {
			std::string answer;
			if (!(*_connectionHandler).getLine(answer)) {
				std::cout << "Disconnected. Exiting...\n" << std::endl;
				break;
			}
			int len = answer.length();
			answer.resize(len - 1);
			std::cout << answer << std::endl; //printing what the server has sent
			if (answer == "ACK signout succeeded") {
				std::cout << "Ready to exit. Press enter" << std::endl;
				break;
			}
		}
	}
};


class TaskKeyboard {
private:
	
	ConnectionHandler *_connectionHandler;
public:
	TaskKeyboard( ConnectionHandler* connectionHandler) : _connectionHandler(connectionHandler) {}

	void run() {  //reading from the socket
		while (1) {
			const short bufsize = 1024;
			char buf[bufsize];
			std::cin.getline(buf, bufsize);
			std::string line(buf);
			if (line == "")
				break;
			int len = line.length();
			if (!(*_connectionHandler).sendLine(line)) {
				std::cout << "Disconnected. Exiting...\n" << std::endl;
				break;
			}
		}
	}
};





/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main(int argc, char *argv[]) {
	/*if (argc < 3) {
		std::cerr << "ip: " << argv[0] << " port" << std::endl << std::endl;
		return -1;
	}
	std::string host = argv[1];
	short port = atoi(argv[2]);*/
	std::string host = "127.0.0.1";
	short port = 7777;

	ConnectionHandler connectionHandler(host, port);
	if (!connectionHandler.connect()) {
		std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
		return 1;
	}
		
		Task task1(&connectionHandler);
		TaskKeyboard task2(&connectionHandler);

		boost::thread th1(&Task::run, &task1);
		boost::thread th2(&TaskKeyboard::run, &task2);
		th1.join();
		th2.join();
		connectionHandler.close();
		return 0;
	}

