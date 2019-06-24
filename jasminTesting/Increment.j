.class public Increment
.super java/lang/Object

; default constructor
.method public <init>()V
   aload_0
   invokespecial java/lang/Object/<init>()V
   return
.end method

; increments an int
.method public inc(I)I
   .limit stack 4
   .limit locals 2
   iload 1
   sipush 1
   iadd
   
   ireturn
.end method


