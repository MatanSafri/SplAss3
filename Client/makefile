LDFLAGS:=-lboost_system -lboost_thread

# All Targets
all: BBclient

# Tool invocations

BBclient: bin/connectionHandler.o bin/echoClient.o 
	@echo 'Building target: BBclient'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/BBclient bin/connectionHandler.o bin/echoClient.o $(LDFLAGS)
	@echo 'Finished building target: BBclient'
	@echo ' '

# Depends on the source and header files
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/connectionHandler.o src/connectionHandler.cpp

# Depends on the source and header files
bin/echoClient.o: src/echoClient.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/echoClient.o src/echoClient.cpp

		
#Clean the build directory
clean:
	rm -f bin/*
