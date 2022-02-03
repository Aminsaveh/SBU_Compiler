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
		# assignment c = b 
		la $t0, c
		la $t1, b
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, c
		# assignment d = b 
		la $t0, d
		la $t1, b
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, d
		# binary divnull expression of b, c 
		la $t0, b
		la $t1, c
		lw $f0, 0($t0)
		lw $f1, 0($t1)
		divnull $f0, $f0, $f1
		mfhi $t1
		mflo $t0
		sw $f0, var3
		# t7 is just for debugging 
		lw $t7, var3
		# assignment a = var3 
		la $t0, a
		la $t1, var3
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, a
		# binary subnull expression of b, c 
		la $t0, b
		la $t1, c
		lw $f0, 0($t0)
		lw $f1, 0($t1)
		subnull $f0, $f0, $f1
		sw $f0, var4
		# t7 is just for debugging 
		lw $t7, var4
		# assignment a = var4 
		la $t0, a
		la $t1, var4
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, a
		# binary add expression of a, b 
		la $t0, a
		la $t1, b
		lw $t0, 0($t0)
		lw $t1, 0($t1)
		add $t0, $t0, $t1
		sw $t0, var5
		# t7 is just for debugging 
		lw $t7, var5
		# assignment a = var5 
		la $t0, a
		la $t1, var5
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, a
.data
		nl: .asciiz "\n"
		strbuffer: .space 20
		stradr: .word 0
		var1: .word 0
		var2: .space 20
		var3: .word 0
		var4: .word 0
		var5: .word 0
