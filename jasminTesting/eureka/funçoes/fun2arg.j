.class public fun2arg
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

      ; initialize frame pointer SL stored in local #4 to null
      aconst_null
      astore 4

      new closure0
      dup
      ;;call constructor
      invokespecial closure0/<init>()V 

      sipush 8
      sipush 7
      invokevirtual closure0/call(II)I

      ; convert to String;
      invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
      ; call println 
      invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

      return
.end method
