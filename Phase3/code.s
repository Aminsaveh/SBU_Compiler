.text
.globl main
main:
		# read integer 
		li $v0, 5
		syscall 
		move $t0, $v0
		la $t1, var1
		sw $t0, 0($t1)
		# t7 is just for debugging 
		lw $t7, var1
		# assignment a = var1 
		la $t0, a
		la $t1, var1
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, a
		# read string 
		li $v0, 8
		la $a0, strbuffer
		li $a1, 20
		move $t0, $a0
		sw $t0, stradr
		sw $t0, var2
		syscall 
		# assignment b = var2 
		la $t0, b
		la $t1, var2
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, b
		# print integer (a) 
		li $v0, 1
		la $t0, a
		lw $t0, 0($t0)
		move $a0, $t0
		syscall 
		# new line 
		li $v0, 4
		la $a0, nl
		syscall 
.data
		nl: .asciiz "\n"
		strbuffer: .space 20
		stradr: .word 0
		var1: .word 0
		var2: .space 20
