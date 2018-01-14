# All Targets
all: fs

# Tool invocations
# # Executable "fs" depends on the files fs.o and Main.o.
fs: bin/BaseCommand.o bin/CdCommand.o bin/CpCommand.o bin/ExecCommand.o bin/ErrorCommand.o bin/HistoryCommand.o bin/LsCommand.o bin/MkdirCommand.o bin/MkfileCommand.o bin/MvCommand.o bin/PwdCommand.o bin/RenameCommand.o bin/RmCommand.o bin/VerboseCommand.o bin/FileSystem.o bin/Environment.o bin/File.o bin/BaseFile.o bin/Directory.o  bin/Main.o bin/Utils.o
	@echo 'Building target: fs'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/fs bin/BaseCommand.o bin/CdCommand.o bin/CpCommand.o bin/ExecCommand.o bin/ErrorCommand.o bin/HistoryCommand.o bin/LsCommand.o bin/MkdirCommand.o bin/MkfileCommand.o bin/MvCommand.o bin/PwdCommand.o bin/RenameCommand.o bin/RmCommand.o bin/VerboseCommand.o bin/FileSystem.o bin/Environment.o bin/File.o bin/BaseFile.o bin/Directory.o  bin/Main.o bin/Utils.o
	@echo 'Finished building target: fs'
	@echo ' '

# Depends on the source and header files
bin/Main.o: src/Main.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/Main.o src/Main.cpp

# Depends on the source and header files
bin/BaseFile.o: src/BaseFile.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/BaseFile.o src/BaseFile.cpp
	
# Depends on the source and header files
bin/File.o: src/File.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/File.o src/File.cpp

# Depends on the source and header files
bin/Directory.o: src/Directory.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/Directory.o src/Directory.cpp
	
# Depends on the source and header files
bin/Environment.o: src/Environment.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/Environment.o src/Environment.cpp
	
# Depends on the source and header files
bin/FileSystem.o: src/FileSystem.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/FileSystem.o src/FileSystem.cpp

# Depends on the source and header files
bin/BaseCommand.o: src/BaseCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/BaseCommand.o src/BaseCommand.cpp	
	
# Depends on the source and header files
bin/CdCommand.o: src/CdCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/CdCommand.o src/CdCommand.cpp

# Depends on the source and header files
bin/CpCommand.o: src/CpCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/CpCommand.o src/CpCommand.cpp
	
# Depends on the source and header files
bin/ExecCommand.o: src/ExecCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/ExecCommand.o src/ExecCommand.cpp
	
# Depends on the source and header files
bin/ErrorCommand.o: src/ErrorCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/ErrorCommand.o src/ErrorCommand.cpp
	
# Depends on the source and header files
bin/HistoryCommand.o: src/HistoryCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/HistoryCommand.o src/HistoryCommand.cpp	
	
# Depends on the source and header files
bin/LsCommand.o: src/LsCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/LsCommand.o src/LsCommand.cpp	
	
# Depends on the source and header files
bin/MkdirCommand.o: src/MkdirCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/MkdirCommand.o src/MkdirCommand.cpp	
	
# Depends on the source and header files
bin/MkfileCommand.o: src/MkfileCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/MkfileCommand.o src/MkfileCommand.cpp	
	
# Depends on the source and header files
bin/MvCommand.o: src/MvCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/MvCommand.o src/MvCommand.cpp	
	
# Depends on the source and header files
bin/PwdCommand.o: src/PwdCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/PwdCommand.o src/PwdCommand.cpp	
	
# Depends on the source and header files
bin/RenameCommand.o: src/RenameCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/RenameCommand.o src/RenameCommand.cpp	
	
# Depends on the source and header files
bin/RmCommand.o: src/RmCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/RmCommand.o src/RmCommand.cpp	
	
# Depends on the source and header files
bin/VerboseCommand.o: src/VerboseCommand.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/VerboseCommand.o src/VerboseCommand.cpp
	
# Depends on the source and header files
bin/Utils.o: src/Utils.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/Utils.o src/Utils.cpp
		
#Clean the build directory
clean:
	rm -f bin/*
