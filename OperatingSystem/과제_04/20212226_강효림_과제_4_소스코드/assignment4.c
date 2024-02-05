#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include <string.h>

#define MAX_PAGES 5000         // 가상 페이지 수
#define MAX_ADDRESS_COUNT 5000 // 가상 주소 개수

// 페이지 교체 알고리즘에 사용될 구조체 정의
typedef struct
{
    int page_number;
    int frame_number;
    int entry_time;
    bool referenced; // Second-Chance 알고리즘을 위한 참조 비트
} PageTableEntry;

// 페이지 테이블 초기화 함수
void initialize_page_table(PageTableEntry page_table[], int frames)
{
    int i;

    for (i = 0; i < frames; i++)
    {
        page_table[i].page_number = -1;
        page_table[i].frame_number = -1;
        page_table[i].entry_time = 0;
        page_table[i].referenced = false; // reference 0으로 초기화
    }
}

// 가상 주소를 생성하는 함수
unsigned long generate_virtual_address(int address_length)
{

    static int srand_called = 0;
    //가상 주소 랜덤으로 생성
    if (!srand_called)
    {
        srand((unsigned int)time(NULL));
        srand_called = 1;
    }

    unsigned long max_value = 1UL << address_length;
    return rand() % max_value;
}

// 미래에 페이지 접근 확인
int find_future_access(int page_number, int reference_string[], int current_index, int num_addresses, int pageSize)
{
    for (int i = current_index + 1; i < num_addresses; i++)
    {
        // 미래에 같은 페이지 번호가 사용되는 경우
        if (reference_string[i] / pageSize == page_number)
        {
            return i - current_index;
        }
    }

    // 해당 페이지에 대한 참조가 없다면, 큰 숫자 return
    return 99999;
}

void optimal(PageTableEntry page_table[], int cnt_frames, int reference_string[], int num_addresses, int pageSize, FILE *output_file)
{
    int page_faults = 0;

    for (int i = 0; i < num_addresses; i++)
    {
        int virtual_address = reference_string[i];
        int page_number = virtual_address / pageSize;
        int frame_number = -1;
        bool page_fault = true;

        // 페이지가 메모리에 있는지 확인
        for (int j = 0; j < cnt_frames; j++)
        {
            if (page_table[j].page_number == page_number)
            {
                frame_number = page_table[j].frame_number;
                page_fault = false;
                break;
            }
        }

        // 페이지가 메모리에 없는 경우
        if (page_fault)
        {
            page_faults++;

            int empty_frame = -1;

            // 빈 프레임 찾기
            for (int j = 0; j < cnt_frames; j++)
            {
                if (page_table[j].page_number == -1)
                {
                    page_table[j].page_number = page_number;
                    page_table[j].frame_number = j;
                    frame_number = j;
                    empty_frame = j;
                    break;
                }
            }

            // Optimal page replacement 적용
            if (empty_frame == -1)
            {
                // 가장 먼 미래에 참조되는 페이지 찾기
                int max_future_index = -1;
                int max_future_access = -1;

                // 동일한 미래 액세스를 가진 페이지 중에서 가장 낮은 인덱스 선택
                int selected_index = -1;

                for (int j = 0; j < cnt_frames; j++)
                {
                    int future_access = find_future_access(page_table[j].page_number, reference_string, i, num_addresses, pageSize);

                    if (future_access > max_future_access || (future_access == max_future_access && j < selected_index))
                    {
                        max_future_access = future_access;
                        max_future_index = j;
                        selected_index = j;
                    }
                }

                // 가장 먼 미래에 참조되는 페이지 교체
                frame_number = max_future_index;
                page_table[max_future_index].page_number = page_number;
            }
        }

        // 물리 주소 계산
        int physical_address = frame_number * pageSize + virtual_address % pageSize;

        // 결과 출력
        fprintf(output_file, "%d\t%d\t%d\t%d\t%d\t%s\n", i + 1, virtual_address, page_number, frame_number, physical_address, page_fault ? "F" : "H");
    }

    // 페이지 폴트 수 출력
    fprintf(output_file, "Page Faults: %d\n", page_faults);
}

void fifo(PageTableEntry page_table[], int cnt_frames, int reference_string[], int num_addresses, int pageSize, FILE *output_file)
{
    int page_faults = 0;
    int next_frame_to_replace = 0;
    int entry_time = 0;

    for (int i = 0; i < num_addresses; i++)
    {
        int virtual_address = reference_string[i];
        int page_number = virtual_address / pageSize;
        int frame_number = -1;
        bool page_fault = true;

        // 가상 페이지가 현재 메모리에 있는지 확인 - 히트
        for (int j = 0; j < cnt_frames; j++)
        {
            if (page_table[j].page_number == page_number)
            {
                frame_number = page_table[j].frame_number;
                page_fault = false;
                break;
            }
        }

        // 페이지가 메모리에 없는 경우
        if (page_fault == true)
        {
            page_faults++;

            int empty_frame = -1;

            // 빈 프레임 찾기
            for (int j = 0; j < cnt_frames; j++)
            {
                // 빈 프레임을 찾은 경우
                if (page_table[j].page_number == -1)
                {
                    page_table[j].page_number = page_number;
                    page_table[j].frame_number = j;
                    frame_number = j;
                    empty_frame = j;
		    page_table[j].entry_time = i; 
                    break;
                }
            }
            // FIFO 페이지 교체 알고리즘 사용
            if (empty_frame == -1)
            {
                // 가장 먼저 메모리에 들어온 페이지의 인덱스를 찾기
                int oldest_frame_index = 0;
                int oldest_entry_time = page_table[0].entry_time;

                for (int j = 1; j < cnt_frames; j++)
                {
                    if (page_table[j].entry_time < oldest_entry_time)
                    {
                        oldest_entry_time = page_table[j].entry_time;
                        oldest_frame_index = j;
                    }
                }

                // 가장 오래된 페이지를 교체
                frame_number = oldest_frame_index;
                page_table[oldest_frame_index].page_number = page_number;
                page_table[oldest_frame_index].entry_time = i; // 현재 시점으로 갱신
            }
        }

        // 물리주소 계산
        int physical_address = frame_number * pageSize + virtual_address % pageSize;

        // 결과 파일에 출력
        fprintf(output_file, "%d\t%d\t%d\t%d\t%d\t%s\n", i + 1, virtual_address, page_number, frame_number, physical_address, page_fault ? "F" : "H");
    }

    // 마지막에 페이지 폴트 발생 횟수 출력
    fprintf(output_file, "Page Faults: %d\n", page_faults);
}

void lru(PageTableEntry page_table[], int cnt_frames, int reference_string[], int num_addresses, int pageSize, FILE *output_file)
{
    int page_faults = 0;

    for (int i = 0; i < num_addresses; i++)
    {
        int virtual_address = reference_string[i];
        int page_number = virtual_address / pageSize;
        int frame_number = -1;
        bool page_fault = true;

        // 가상 페이지가 현재 메모리에 있는지 확인 - hit
        for (int j = 0; j < cnt_frames; j++)
        {
            if (page_table[j].page_number == page_number)
            {
                frame_number = page_table[j].frame_number;
                page_table[j].entry_time = i;
                page_fault = false;
                break;
            }
        }

        // 페이지가 메모리에 없는 경우
        if (page_fault)
        {
            page_faults++;

            int empty_frame = -1;

            // 빈 프레임 찾기
            for (int j = 0; j < cnt_frames; j++)
            {
                // 빈 프레임을 찾은 경우
                if (page_table[j].page_number == -1)
                {
                    page_table[j].page_number = page_number;
                    page_table[j].frame_number = j;
                    frame_number = j;
                    empty_frame = j;
                    page_table[j].entry_time = i;
                    break;
                }
            }

            // LRU page replacement 사용
            if (empty_frame == -1)
            {
                int oldest_frame_index = 0;
                int oldest_entry_time = page_table[0].entry_time;

                // 가장 먼저 참조된 페이지의 인덱스를 찾기
                for (int j = 1; j < cnt_frames; j++)
                {
                    if (page_table[j].entry_time < oldest_entry_time)
                    {
                        oldest_entry_time = page_table[j].entry_time;
                        oldest_frame_index = j;
                    }
                }

                // 가장 오래 전 참조된 페이지를 교체
                frame_number = oldest_frame_index;
                page_table[oldest_frame_index].page_number = page_number;
                page_table[oldest_frame_index].entry_time = i;
            }
        }

        // 물리주소 계산
        int physical_address = frame_number * pageSize + virtual_address % pageSize;

        // 결과 파일에 출력
        fprintf(output_file, "%d\t%d\t%d\t%d\t%d\t%s\n", i + 1, virtual_address, page_number, frame_number, physical_address, page_fault ? "F" : "H");
    }

    // 마지막에 페이지 폴트 발생 횟수 출력
    fprintf(output_file, "Page Faults: %d\n", page_faults);
}
void second_chance(PageTableEntry page_table[], int cnt_frames, int reference_string[], int num_addresses, int pageSize, FILE *output_file)
{
    int page_faults = 0;
    int hand = 0;

    for (int i = 0; i < num_addresses; i++)
    {
        int virtual_address = reference_string[i];
        int page_number = virtual_address / pageSize;
        int frame_number = -1;
        bool page_fault = true;

        // 메모리에 페이지가 있는지 확인
        for (int j = 0; j < cnt_frames; j++)
        {
            if (page_table[j].page_number == page_number)
            {
                frame_number = page_table[j].frame_number;
                page_table[j].referenced = true; // reference 1
                page_fault = false;
                break;
            }
        }

        // 페이지가 메모리가 없는 경우
        if (page_fault)
        {
            page_faults++;

            int empty_frame = -1;

            // 빈 프레임이 있는 경우
            for (int j = 0; j < cnt_frames; j++)
            {
                if (page_table[j].page_number == -1)
                {
                    page_table[j].page_number = page_number;
                    page_table[j].frame_number = j;
                    page_table[j].referenced = false;
                    frame_number = j;
                    empty_frame = j;
                    break;
                }
            }

            // Second-Chance replacement 적용
            if (empty_frame == -1)
            {
                while (true)
                {
                    // reference 되었는지 확인 (reference가 1)
                    if (page_table[hand].referenced)
                    {
                        // reference 0으로 바꾸고 다음 프레임으로 이동
                        page_table[hand].referenced = false;
                        hand = (hand + 1) % cnt_frames;
                    }
                    else
                    {
                        // reference가 0인 것 찾으면 프레임 교체
                        frame_number = page_table[hand].frame_number;

                        // 현재 프레임을 새로운 페이지로 교체
                        page_table[hand].page_number = page_number;
                        page_table[hand].referenced = false; // 새로운 reference를 0으로

                        //다음 프레임으로 이동
                        hand = (hand + 1) % cnt_frames;

                        break; 
                    }
                }
            }
        }

        // 물리 주소 계산
        int physical_address = frame_number * pageSize + virtual_address % pageSize;

        // 결과 출력
        fprintf(output_file, "%d\t%d\t%d\t%d\t%d\t%s\n", i + 1, virtual_address, page_number, frame_number, physical_address, page_fault ? "F" : "H");
    }

    // page fault 수 출력
    fprintf(output_file, "Page Faults: %d\n", page_faults);
}

int main()
{
    int address_length_choice; // 가상 주소 길이 번호
    int address_length;        // 가상 주소 길이

    int num_addresses = MAX_ADDRESS_COUNT; // 가상 주소 개수

    // 사용자로부터 가상 주소 길이 선택 받기
    while (1)
    {
        printf("A. Simulation에 사용할 가상주소 길이를 선택하시오 (1. 18bits 2. 19bits 3. 20bits): ");
        if (scanf("%d", &address_length_choice) != 1)
        {
            while (getchar() != '\n')
                ; // 입력 버퍼 비우기
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
            continue;
        }

        if (address_length_choice == 1)
        {
            address_length = 18;
            break;
        }
        else if (address_length_choice == 2)
        {
            address_length = 19;
            break;
        }
        else if (address_length_choice == 3)
        {
            address_length = 20;
            break;
        }
        else
        {
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
        }
    }

    // B. Simulation에 사용할 페이지(프레임)의 크기 선택
    int pageSize_choice; // 페이지 크기 번호
    int pageSize;        // 페이지 크기

    while (1)
    {
        printf("B. Simulation에 사용할 페이지(프레임)의 크기를 선택하시오 (1. 1kb 2. 2kb 3. 4kb): ");
        if (scanf("%d", &pageSize_choice) != 1)
        {
            while (getchar() != '\n')
                ; // 입력 버퍼 비우기
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
            continue;
        }

        if (pageSize_choice == 1)
        {
            pageSize = 1 << 10;
            break;
        }
        else if (pageSize_choice == 2)
        {
            pageSize = 2 << 10;
            break;
        }
        else if (pageSize_choice == 3)
        {
            pageSize = 4 << 10;
            break;
        }
        else
        {
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
        }
    }

    // C. Simulation에 사용할 물리메모리의 크기 선택
    int physicalMemorySize_choice;
    int physicalMemorySize;

    while (1)
    {
        printf("C. Simulation에 사용할 물리메모리의 크기를 선택하시오 (1. 32kb 2. 64kb): ");
        if (scanf("%d", &physicalMemorySize_choice) != 1)
        {
            while (getchar() != '\n')
                ; // 입력 버퍼 비우기
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
            continue;
        }

        if (physicalMemorySize_choice == 1)
        {
            physicalMemorySize = 32 << 10;
            break;
        }
        else if (physicalMemorySize_choice == 2)
        {
            physicalMemorySize = 64 << 10;
            break;
        }
        else
        {
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
        }
    }

    // D. Simulation에 적용할 Page Replacement 알고리즘 선택
    int replacementAlgorithm;

    while (1)
    {
        printf("D. Simulation에 적용할 Page Replacement 알고리즘을 선택하시오\n (1. Optimal 2. FIFO 3.LRU 4. Second-Chance): ");
        if (scanf("%d", &replacementAlgorithm) != 1)
        {
            while (getchar() != '\n')
                ; // 입력 버퍼 비우기
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
            continue;
        }

        if (replacementAlgorithm == 1)
        {
            break;
        }
        else if (replacementAlgorithm == 2)
        {
            break;
        }
        if (replacementAlgorithm == 3)
        {
            break;
        }
        else if (replacementAlgorithm == 4)
        {
            break;
        }
        else
        {
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
        }
    }

    // E. 가상 주소 스트링 입력 방식 선택
    int inputMethod;
    char filename[100];

    while (1)
    {
        printf("E. 가상주소 스트링 입력방식을 선택하시오\n (1.input.in 자동 생성 2. 기존 파일 사용): ");
        if (scanf("%d", &inputMethod) != 1)
        {
            while (getchar() != '\n')
                ; // 입력 버퍼 비우기
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
            continue;
        }

        if (inputMethod == 1)
        {
            // 파일에 가상 주소 저장 (덮어쓰기)
            strcpy(filename, "input.in");
            const char *autoGeneratedFileName = filename;
            FILE *autoGeneratedFile = fopen(autoGeneratedFileName, "w");
            if (autoGeneratedFile == NULL)
            {
                fprintf(stderr, "자동 생성된 파일을 열 수 없습니다.\n");
                return 1;
            }

            // 가상 주소 생성 및 파일에 저장
            for (int i = 0; i < num_addresses; i++)
            {
                unsigned long generated_address = generate_virtual_address(address_length);
                fprintf(autoGeneratedFile, "%lu\n", generated_address);
            }

            fclose(autoGeneratedFile);
        }
        else if (inputMethod == 2)
        {
            printf("F.입력 파일 이름을 입력하시오: " );
            scanf("%s", filename);
        }
        else
        {
            printf("잘못된 입력입니다. 다시 선택하세요.\n");
            continue;
        }

        break; // 루프를 빠져나감
    }

    // 파일에서 가상 주소 읽어오기 (공통 부분)
    FILE *fileToRead = fopen(filename, "r");
    if (fileToRead == NULL)
    {
        fprintf(stderr, "파일을 열 수 없습니다.\n");
        return 1;
    }

    // 파일에서 가상 주소 읽어오기
    int reference_string[num_addresses];
    for (int i = 0; i < num_addresses; i++)
    {
        if (fscanf(fileToRead, "%d", &reference_string[i]) != 1)
        {
            fprintf(stderr, "파일에서 가상 주소를 읽어오는 도중 오류가 발생했습니다.\n");
            fclose(fileToRead);
            return 1;
        }
    }

    fclose(fileToRead);

    // 페이지 테이블 선언 및 초기화
    int cnt_frames = physicalMemorySize / pageSize; // 프레임 개수
    PageTableEntry page_table[cnt_frames];
    initialize_page_table(page_table, cnt_frames);

    // 페이지 교체 알고리즘 실행 및 결과 파일에 쓰기
    FILE *output_file;

    switch (replacementAlgorithm)
    {
    case 1:
        output_file = fopen("output.opt", "w");
        optimal(page_table, cnt_frames, reference_string, num_addresses, pageSize, output_file);
        break;
    case 2:
        output_file = fopen("output.fifo", "w");
        fifo(page_table, cnt_frames, reference_string, num_addresses, pageSize, output_file);
        break;
    case 3:
        output_file = fopen("output.lru", "w");
        lru(page_table, cnt_frames, reference_string, num_addresses, pageSize, output_file);
        break;
    case 4:
        output_file = fopen("output.sc", "w");
        second_chance(page_table, cnt_frames, reference_string, num_addresses, pageSize, output_file);
        break;
    }

    fclose(output_file);

    return 0;
}

