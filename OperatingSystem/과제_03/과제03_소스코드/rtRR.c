#define _GNU_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <sched.h>
#include <sys/resource.h>
#include <time.h> 
#include <sys/mman.h>
#include <string.h>
#include <fcntl.h>

// 현재 시간 가져오는 함수
void getCurrentTime(struct timespec* currentTime) {
    if (clock_gettime(CLOCK_REALTIME, currentTime) == -1) {
        perror("clock_gettime");
    }
}

// form에 맞게 시간 출력 함수
void printTime(const struct timespec* timeVal) {
    struct tm tm_info;
    char buffer[20];
    strftime(buffer, sizeof(buffer), "%H:%M:%S", localtime_r(&timeVal->tv_sec, &tm_info));
    printf("%s.%09ld", buffer, timeVal->tv_nsec);
}

int main() {
    int pid;
    int count = 0;

 // 파일 경로
    const char *file_path = "/proc/sys/kernel/sched_rr_timeslice_ms";

    // CPU core 1개로 제한
    cpu_set_t mask;
    if (sched_getaffinity(0, sizeof(mask), &mask) == -1) {
        perror("sched_getaffinity");
        return 1;
    }
    CPU_ZERO(&mask);
    CPU_SET(0, &mask);

    if (sched_setaffinity(0, sizeof(mask), &mask) == -1) {
        perror("sched_setaffinity");
        return 1;
    }

    
    printf("time slice(ms): ");
    long timeSlice;
    scanf("%ld", &timeSlice);

    // 파일 열기 (파일 디스크립터를 얻기 위해 open을 사용)
    int file_descriptor = open(file_path, O_WRONLY);
    if (file_descriptor == -1) {
        perror("Failed to open file");
        return 1;
    }

    // timeslice 값을 파일에 쓰기 (write 시스템 콜 사용)
    char buffer[32];
    snprintf(buffer, sizeof(buffer), "%ld", timeSlice);
    if (write(file_descriptor, buffer, strlen(buffer)) != strlen(buffer) || close(file_descriptor) == -1) {
        perror("Failed to write to file");
        return 1;
    }

    // 공유 메모리 생성
    long double *totalElapsedTime = (long double *)mmap(NULL, sizeof(long double), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);

    if (totalElapsedTime == MAP_FAILED) {
        perror("mmap");
        return 1;
    }

    *totalElapsedTime = 0.0;


    for (int i = 0; i < 21; i++) {
        pid = fork();

        if (pid == 0) {
            struct timespec startTime, endTime;
            long double elapsedTime;

    // RT_RR
    struct sched_param param;
    param.sched_priority = 99; 

    if (sched_setscheduler(0, SCHED_RR, &param) == -1) {
        perror("sched_setscheduler");
        return 1;
    }


            int result[100][100];
            int A[100][100];
            int B[100][100];

	//배열 초기화
            for (int k = 0; k < 100; k++) {
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j < 100; j++) {
                        result[k][j] = 0;  
                        A[k][i] = 0;      
                        B[i][j] = 0;      
                    }
                }
            }

            getCurrentTime(&startTime);

            while (count < 100) {

                for (int k = 0; k < 100; k++) {
                    for (int i = 0; i < 100; i++) {
                        for (int j = 0; j < 100; j++) {
                            result[k][j] += A[k][i] * B[i][j];
                        }
                    }
                }

                count++;
            }

            getCurrentTime(&endTime);
            elapsedTime = (endTime.tv_sec - startTime.tv_sec) +
                         (endTime.tv_nsec - startTime.tv_nsec) / 1000000000.0;
	

            printf("PID: %d | Start time: ", getpid());
            printTime(&startTime); 
            printf(" | End time: ");
            printTime(&endTime); 
            printf(" | Elapsed time: %.6Lf\n", elapsedTime);

            *totalElapsedTime += elapsedTime;
            exit(0);
        }
    }

    for (int i = 0; i < 21; i++) {
        int status;
        wait(&status);
    }

    long double averageElapsedTime = *totalElapsedTime / 21;
    printf("Scheduling Policy : RT_RR | Time Quantum : %ldms | Average elapsed time : %.6Lf\n", timeSlice, averageElapsedTime);

// 공유 메모리 해제
    if (munmap(totalElapsedTime, sizeof(long double)) == -1) {
    perror("munmap");
    return 1;
}

    return 0;
}


