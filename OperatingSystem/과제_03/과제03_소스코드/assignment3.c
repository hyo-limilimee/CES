#include <stdio.h>
#include <stdlib.h>

int main() {
    int choice;

    while (1) {
        printf("Input the Scheduling Policy to apply:\n");
        printf("1. CFS_DEFAULT\n2. CFS_NICE\n3. RT_FIFO\n4. RT_RR\n");

        if (scanf("%d", &choice) != 1) {
            while (getchar() != '\n'); // 입력 버퍼 비우기
            printf("Invalid input. Please enter an integer.\n");
            continue;
        }

        if (choice == 1) {
            int result = system("gcc -o cfsDefault cfsDefault.c");
            if (result == 0) {
                result = system("./cfsDefault");
                if (result != 0) {
                    printf("Error running cfsDefault\n");
                }
            } else {
                printf("Error compiling cfsDefault.c\n");
            }
            break;
        } else if (choice == 2) {
            int result = system("gcc -o cfsNice cfsNice.c");
            if (result == 0) {
                result = system("./cfsNice");
                if (result != 0) {
                    printf("Error running cfsNice\n");
                }
            } else {
                printf("Error compiling cfsNice.c\n");
            }
            break;
        } else if (choice == 3) {
            int result = system("gcc -o rtFifo rtFifo.c");
            if (result == 0) {
                result = system("./rtFifo");
                if (result != 0) {
                    printf("Error running rtFifo\n");
                }
            } else {
                printf("Error compiling rtFifo.c\n");
            }
            break;
        } else if (choice == 4) {
            int result = system("gcc -o rtRR rtRR.c");
            if (result == 0) {
                result = system("./rtRR");
                if (result != 0) {
                    printf("Error running rtRR\n");
                }
            } else {
                printf("Error compiling rtRR.c\n");
            }
            break;
        } else {
            printf("Wrong input: Please choose again.\n");
        }
    }

    return 0;
}


