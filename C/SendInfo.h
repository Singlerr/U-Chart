#pragma once

#ifdef _WIN32
//define something for Windows (32-bit and 64-bit, this part is common)
#ifdef _WIN64
   //define something for Windows (64-bit only)
#define WINDOWS
#endif
#elif __APPLE__
#include "TargetConditionals.h"
#if TARGET_IPHONE_SIMULATOR
// iOS Simulator
#elif TARGET_OS_IPHONE
// iOS device
#elif TARGET_OS_MAC
// Other kinds of Mac OS
#else
#   error "Unknown Apple platform"
#endif
#elif __linux__
// linux
#elif __unix__ // all unices not caught above
// Unix
#elif defined(_POSIX_VERSION)
// POSIX
#else
#   error "Unknown compiler"
#endif



#include <stdio.h>
#include <WinSock2.h>
#include <Windows.h>
#include <string>
#include <string.h>
#pragma comment(lib,"ws2_32.lib")

#define BUFF_SIZE 1024

using namespace std;


string keys[1] = {};
string values[1] = {};

int i = 0;
void put(string key, string value) {
	keys[i] = key;
	values[i] = value;
	i++;
}

void start(char* programName) {

	WSADATA data;
	struct hostent* host;
	char res[BUFF_SIZE];

	if (WSAStartup(MAKEWORD(2, 2), &data) != 0) {
		perror("WSA Start Error");
		return;
	}

	SOCKET sock = socket(PF_INET, SOCK_STREAM, 0);
	SOCKADDR_IN addr;

	if (sock == INVALID_SOCKET) {
		perror("Socket error");
		return;
	}

	host = gethostbyname("www.u-chart.kr");

	addr.sin_family = AF_INET;
	addr.sin_port = htons(80);
	addr.sin_addr.s_addr = inet_addr(inet_ntoa(*(struct in_addr*)*host->h_addr_list));
	if (connect(sock, (SOCKADDR*)&addr, sizeof(addr)) == SOCKET_ERROR)
	{
		printf("Connection failed");
		return;
	}
	string content = "program_name=";
	content.append(programName);
	content.append("&os=");

#ifdef _WIN32
	content.append("WINDOWS");
#ifdef _WIN64
	content.append("WINDOWS");
#endif
#elif __APPLE__
#include "TargetConditionals.h"
#if TARGET_IPHONE_SIMULATOR
	content.append("IOS");
#define A
#elif TARGET_OS_IPHONE
	content.append("IOS");
#elif TARGET_OS_MAC
	content.append("MAC");
#else
#   error "Unknown Apple platform"
#endif
#elif __linux__
	content.append("LiNUX");
#elif __unix__ // all unices not caught above
	content.append("UNIX");
#   error "Unknown compiler"
#endif

	content.append("&uuid=null&key_and_value=");
	for (int k = 0; k < i; k++) {
		content.append(keys[k]).append(";").append(values[i]).append(",");
		content[content.length() - 1] = '\0';
	}
	string submit = "POST /addInfo HTTP/1.0\r\nContent-Type: text/plain\r\nContent-Length: ";
	submit.append(to_string(content.length()));
	submit.append("\r\n");
	submit.append(content);
	send(sock,submit.c_str(), content.length(), 0);
}
