.class public closure0
.super java/lang/Object
.field public sl Lframe_0;
.field public yy I

; default constructor
.method public <init>()V
	aload_0
	invokespecial java/lang/Object/<init>()V
	return
.end method

; square
.method public call(I)I
	.limit stack 2
	.limit locals 3
	aload_0
	getfield closure0/sl Lframe_2;
	getfield frame_2/xx I

	iload 1
	imul

	ireturn
.end method

