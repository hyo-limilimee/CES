#include <stdio.h>
#include <stdlib.h>
#include <dirent.h>   //directory 조작
#include <string.h>
#include <unistd.h>   //readlink하기 위함
#include <sys/stat.h>   //file status library
#include <fcntl.h>   //file control library


// total_sec를 ps처럼 시:분:초 형식으로 변환하는 함수
void formatTime(unsigned int total_sec, char *formatted_time) {
    int hours = total_sec / 3600;
    int minutes = (total_sec % 3600) / 60;
    int seconds = total_sec % 60;
    sprintf(formatted_time, "%02d:%02d:%02d", hours, minutes, seconds);
}

int main() {
    // newps에 대한 정보 출력
    printf("PID        TTY        TIME       CMD\n");

    // '/proc' 디렉토리 오픈
    DIR* proc_directory = opendir("/proc");

    // '/proc' 디렉토리가 비어 있는 경우
    if (proc_directory == NULL) {
        perror("open '/proc' directory fail");
        return 1;
    }

    // '/proc' 디렉토리에 있는 엔트리 불러오기
    struct dirent* entry;

    // directory에서 entry를 하나씩 읽어옴
    while ((entry = readdir(proc_directory))) {
        // entry의 data type이 DT_DIR인 경우
        if (entry->d_type == DT_DIR) {
            // 문자열 -> 정수 변환하여 pid 획득
            int pid = atoi(entry->d_name);

            if (pid > 0) {
                // 프로세스 상태 파일 주소 저장
                char address[128];
                snprintf(address, sizeof(address), "/proc/%d/stat", pid);
      //읽기 전용으로 상태 파일 오픈
                FILE* state_file = fopen(address, "r");

                if (state_file) {
                    int process_pid;   //불러온 프로세스 id
                    char command[256], state; //문자열, state
                    unsigned int user_time, sys_time; //time 관련 변수 선언

                    // 상태 파일 읽어오기
                    fscanf(state_file, "%d %s %c %*d %*d %*d %*d %*d %*u %*u %*u %*u %*u %u %u",
                        &process_pid, command, &state, &user_time, &sys_time);

                    // 상태 파일 닫기
                    fclose(state_file);

                    // 포르세스 tty 정보 가져오기
                    snprintf(address, sizeof(address), "/proc/%d/fd/0", pid);
                    char tty[256];
                    ssize_t tty_len = readlink(address, tty, sizeof(tty) - 1);
      //정상적으로 정보 읽어왔을 때 공백으로 문자열 종료
                    if (tty_len > 0) {
                        tty[tty_len] = '\0';

                    } else {
                        strcpy(tty, "???");
                    }

                    // filtering: TTY가 /dev/pts/0로 시작하는 것만 출력
                    if (strstr(tty, "/dev/pts/0") == tty) {
                        
         // CPU 시간 계산 및 포맷
                        unsigned int time = user_time + sys_time;
                        char formatted_time[9];
                        formatTime(time, formatted_time);

                        // TTY 정보에서 /dev/ 부분을 제거하여 출력 (ps 명령어랑 동일한 형식으로)
                        char* tty_name = strstr(tty, "/dev/pts/") + 5;

                        // 프로세스 정보 출력
                        printf("%-10d %-10s %-10s %s\n", process_pid, tty_name, formatted_time, command);
                    }
                }
            }
        }
    }

    // '/proc' 디렉토리 닫기
    closedir(proc_directory);

    return 0;
}
