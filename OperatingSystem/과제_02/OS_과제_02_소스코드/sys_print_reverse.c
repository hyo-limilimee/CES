#include <linux/kernel.h>
#include <linux/syscalls.h>
#include <linux/slab.h> //kmalloc, kfree 함수 사용 위함

asmlinkage long sys_print_reverse(long num)
{
    long ori_num = num; //배열에 담아주는 과정을 위해 숫자 복사
    long* re_num = kmalloc(sizeof(long) * 20, GFP_KERNEL); // 동적 메모리 할당
    int count = 0; //자릿수 세어주는 변수

    while (ori_num != 0) //역순으로 배열에 숫자를 담아줌
    {
        re_num[count++] = ori_num % 10;
        ori_num = ori_num / 10;
    }

    // re_num 배열에 있는 숫자들을 long으로 변환
    long reverse_num = 0;
    int i;
    for (i = 0; i < count; i++)
    {
        reverse_num = reverse_num * 10 + re_num[i];
    }

    kfree(re_num); // 할당된 메모리 해제

    printk("Input: %ld\n", num);
    printk("Output: %0*ld\n", count, reverse_num);  

    return reverse_num;
}

SYSCALL_DEFINE1(print_reverse, long, num) { 
    return sys_print_reverse(num);
}

