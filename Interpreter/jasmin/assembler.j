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

	aconst_null
	astore 4
	; ----- END OF INTRO CODE
 
	new f1
	dup
	invokespecial f1/<init>()V
	dup

	aload 4

	putfield f1/sl Ljava/lang/Object;

	astore 4
	aload 4

	sipush 1
	putfield f1/x I

	new f2
	dup
	invokespecial f2/<init>()V
	dup

	aload 4

	putfield f2/sl Lf1;
	astore 4
	aload 4

	sipush 2
	putfield f2/y I

	aload 4

	getfield f2/sl Lf1;

	getfield f1/x I

	aload 4

	getfield f2/y I

	iadd

	; ----- START OF EPILOGUE CODE

	; convert to String;
	invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
	; call println
	invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

	return
.end method
