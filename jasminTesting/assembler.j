.class public Assembler
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

	; 1 - the PrintStream object held in java.lang.out
	getstatic java/lang/System/out Ljava/io/PrintStream;

	; ----- END OF INTRO CODE
 
 	; let x = 2 in x end;;

	new frame_0
	dup
	invokespecial frame_0/<init>()V

	dup

	sipush 2
	putfield frame_0/x I

	astore 0
	aload 0

	getfield frame_0/x I


	; ----- START OF EPILOGUE CODE

	; convert to String;
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	; call println
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

	return
.end method
