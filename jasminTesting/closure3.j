.class public closure3
.super java/lang/Object
.field public sl Lframe_2;
.field public yy I

; default constructor
.method public <init>()V
	aload_0
	invokespecial java/lang/Object/<init>()V
	return
.end method

; square
.method public call(I)I
	.limit stack 4
	.limit locals 2
	aload_0
	getfield closure3/sl Lframe_2;
	getfield frame_2/xx I

	iload 1
	imul

	ireturn
.end method


