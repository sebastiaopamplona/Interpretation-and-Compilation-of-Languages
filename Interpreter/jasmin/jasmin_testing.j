.class public Demo
.super java/lang/Object

.method public <init>()V
      aload_0
      invokenonvirtual java/lang/Object/<init>()V
      return
.end method

.method public static main([Ljava/lang/String;)V
      ; set limits used by this method
      .limit locals 10
      .limit stack 256

      ;    1 - the PrintStream object held in java.lang.out
      getstatic java/lang/System/out Ljava/io/PrintStream;

      ; START
      ; let x = new(new(1+2)) y = 2 in !x := !!x + 1; (!!x)+y

      ; ASTLet 
      new frame_4
      dup
      invokespecial frame_4/<init>()V

      ; put x = new(new(1+2)) on frame 
      ;dup 

      ; ASTNew
      ;new ref_class
      ;dup
      ;invokespecial ref_class/<init>()V
      ;dup

      ; ASTNew
      ;new ref_int
      ;dup
      ;invokespecial ref_int/<init>()V
      ;dup

      ; ASTAdd
      ;sipush 1
      ;sipush 2
      ;iadd
      ;putfield ref_int/value I
      ;putfield ref_class/value Ljava/lang/Object; 

      ; ASTLet
      ;putfield frame_4/loc_0 Ljava/lang/Object; ; (*) x = new(new(1+2))

      ; put y = 2 on frame
      dup

      ; ASTNum
      sipush 2

      ; ASTLet 
      putfield frame_4/loc_1 I

      astore_0 ; save the frame as the top of the stack

      ; get !x from frame 
      aload_0
      getfield frame_4/loc_0 Ljava/lang/Object;
      checkcast ref_class
      getfield ref_class/value Ljava/lang/Object;
      checkcast ref_int

      ; compute !!x+10
      ; get x from frame 

      ; ASTId
      aload_0
      getfield frame_4/loc_0 Ljava/lang/Object;
      checkcast ref_class
      getfield ref_class/value Ljava/lang/Object;
      checkcast ref_int

      getfield ref_int/value I

      ; ASTNum
      sipush 1
      
      ; ASTAdd
      iadd
      ; store value in x
      putfield ref_int/value I


      ; get x from frame 
      aload_0
      getfield frame_4/loc_0 Ljava/lang/Object;
      checkcast ref_class
      ; get !x
      getfield ref_class/value Ljava/lang/Object;
      ; get !!x
      checkcast ref_int
      getfield ref_int/value I

      ; get y from frame
      aload_0
      getfield frame_4/loc_1 I

      iadd

      ; convert to String;
      invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
      ; call println 
      invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

      return
.end method
