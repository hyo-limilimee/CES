#include <linux/kernel.h>
#include <linux/syscalls.h>

asmlinkage long sys_print_add_to_sub(long a, long b)
{
    long c = a - b;
    printk("Input: %ld + %ld\n",a,b);
    printk("Output:  %ld\n", c);

    if(c < 0)
    {
	    c = -c;
	    return c;
    }
    else
    {
	    return c;
    }
}

asmlinkage long sys_print_sub_to_add(long a, long b)
{
    long c = a + b;
    printk("Input: %ld - %ld\n",a,b);
    printk("Output:  %ld\n", c);
    return c;
}

SYSCALL_DEFINE2(print_add_to_sub, long, a, long, b)
{
    return sys_print_add_to_sub(a, b);
}

SYSCALL_DEFINE2(print_sub_to_add, long, a, long, b)
{
    return sys_print_sub_to_add(a, b);
}

