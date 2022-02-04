.text
.globl main
main:
		# integer constant 
		li $t0, 5
		sw $t0, var1
		# t7 is just for debugging 
		lw $t7, var1
		# integer constant 
		li $t0, 2
		sw $t0, var2
		# t7 is just for debugging 
		lw $t7, var2
		# assignment a = var2 
		la $t0, a
		la $t1, var2
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, a
		# integer constant 
		li $t0, 0
		sw $t0, var3
		# t7 is just for debugging 
		lw $t7, var3
		# Array access with name vv at 0 
		la $t0, vv
		li $t4, 4
		li $t1, 0
		mul $t1, $t1, $t4
		add $t0, $t0, $t1
		lw $t0, 0($t0)
		sw $t0, var4
		# assignment var4 = a 
		la $t0, var4
		la $t1, a
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, var4
		# assignment b = var2 
		la $t0, b
		la $t1, var2
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, b
		# Real constant 
		li.s $f0, 3.14
		s.s $f0, var5
		# t7 is just for debugging 
		lw $t7, var5
		# assignment z = var5 
		la $t0, z
		la $t1, var5
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, z
		# assignment x = var5 
		la $t0, x
		la $t1, var5
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, x
		# string constant 
		# t7 is just for debugging 
		lw $t7, var6
		# assignment b = var6 
		la $t0, b
		la $t1, var6
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, b
		# assignment b = var6 
		la $t0, b
		la $t1, var6
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, b
		# Boolean constant 
		li $t0, 1
		sw $t0, var7
		# t7 is just for debugging 
		lw $t7, var7
		# assignment s = var7 
		la $t0, s
		la $t1, var7
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, s
		# assignment s = var3 
		la $t0, s
		la $t1, var3
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, s
		# read integer 
		li $v0, 5
		syscall 
		move $t0, $v0
		la $t1, var8
		sw $t0, 0($t1)
		# t7 is just for debugging 
		lw $t7, var8
		# assignment a = var8 
		la $t0, a
		la $t1, var8
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
		sw $t0, var9
		syscall 
		# assignment b = var9 
		la $t0, b
		la $t1, var9
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
		sw $f0, var10
		# t7 is just for debugging 
		lw $t7, var10
		# assignment a = var10 
		la $t0, a
		la $t1, var10
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
		sw $f0, var11
		# t7 is just for debugging 
		lw $t7, var11
		# assignment a = var11 
		la $t0, a
		la $t1, var11
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
		sw $t0, var12
		# t7 is just for debugging 
		lw $t7, var12
		# assignment a = var12 
		la $t0, a
		la $t1, var12
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, a
		# binary Convert expression of a 
		la $t0, a
		lw $t0, 0($t0)
		mtc1 $t0, $f0
		cvt.s.w $f1, $f0
		s.s $f1, a
		# assignment b = a 
		la $t0, b
		la $t1, a
		lw $t1, 0($t1)
		sw $t1, 0($t0)
		# t7 is just for debugging 
		lw $t7, b
.data
		nl: .asciiz "\n"
		strbuffer: .space 20
		stradr: .word 0
		var1: .word 0
		null: .space 20
		var2: .word 0
		var3: .word 0
		var4: .word 0
		var5: .word 0
		var6: .asciiz "salam"
		var7: .word 0
		var8: .word 0
		var9: .space 20
		var10: .word 0
		var11: .word 0
		var12: .word 0
