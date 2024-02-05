#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/syscall.h>

int main() {
    char input[256]; 
    long a, b;
    char op;

    while (1) {
        printf("Input: ");
        if (fgets(input, sizeof(input), stdin) == NULL || input[0] == '\n') {
            break; 
        }

        int result = sscanf(input, " %ld %c %ld", &a, &op, &b);
        long int value = -1;

        if (result == 1) {
            if (a > 0) {
                int count = 0;
                long temp = a;
                while (temp > 0) {
                    temp = temp / 10;
                    count++;
                }
                value = syscall(449, a);
                printf("Output: %0*ld\n", count, value);
            }
            else {
                printf("Wrong Input!\n");
            }
        }
        else if (result == 3) {
            if (a > 0 && b > 0) {
                if (op == '+') {
                    value = syscall(450, a, b);
		    if (a < b)
		    {
			 value = -value;
			 printf("Output: %ld\n", value);
		    }

		    else
		    {
			    printf("Output: %ld\n", value);
		    }
                    
                }
                else if (op == '-') {
                    value = syscall(451, a, b);
                    printf("Output: %ld\n", value);
                }
                else {
                    printf("Wrong Input!\n");
                }
            }
            else {
                printf("Wrong Input!\n");
            }
        }
        else {
            printf("Wrong Input!\n");
        }
    }

    return 0;
}

