.class public JasminTesting
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

      ;new closure0
      ;dup
      ;;call constructor
      ;invokespecial closure0/<init>()V 

      ;sipush 8
      ;invokevirtual closure0/call(I)I


      ;let 
      ;     x = 2 
      ;in
      ;     let 
      ;           y = 3
      ;     in  
      ;           let
      ;                 f = fun(z) => x + y + z end 
      ;           in
      ;                 f(1)
      ;           end
      ;     end
      ;end;;


      ;# let x = 2 in
      ;new frame (aka env level) 0, with only x = 2
      new frame_0 ; new frame_0 object
      dup 
      invokespecial frame_0/<init>()V ; init frame_0
      dup

      ;load and update SL
      aload 4 ; get SL
      putfield frame_0/sl Ljava/lang/Object; ; SL now points to frame_0
      astore 4 ; store updated SL

      ;set x = 2 in frame (aka env level) 0
      aload 4 ; get SL
      sipush 2 ; put 2 on top of stack
      putfield frame_0/x I ; update frame_0's x value to 2
      ;# let x = 2 in


      ;# let y = 3 in
      ;new frame (aka env level) 1, with only y = 3
      new frame_1 ; new frame_1 object
      dup
      invokespecial frame_1/<init>()V ; init frame_1
      dup

      ;load and update SL
      aload 4 ; get SL
      putfield frame_1/sl Lframe_0; ; SL now points to frame_1 && frame_1 now points to frame_0
      astore 4 ; store updated SL

      ;set y = 3 in frame (aka env level) 1
      aload 4 ; get SL
      sipush 3 ; put 3 on top of the stack
      putfield frame_1/y I ; update frame_1's y value to 3
      ;# let y = 3 in


      ;# let f = fun(z) => x + y + z
      new frame_2 ; new frame_2 object
      dup
      invokespecial frame_2/<init>()V ; init frame_2
      dup

      ;load and update SL
      aload 4 ; get SL
      putfield frame_2/sl Lframe_1; ; SL now points to frame_2 and frame_2 now points to frame_1
      astore 4 ; store updated SL

      aload 4
      sipush 18
      putfield frame_2/xx I

      ;set f = fun(z) => x + y + z in frame (aka env level) 2
      new closure0
      dup
      ;;call constructor
      invokespecial closure0/<init>()V 
      dup
      aload 4
      putfield closure0/sl Lframe_2;

      sipush 2
      invokevirtual closure0/call(I)I

      ;sipush 28

      ;aload 4
      ;getfield frame_2/xx I

      ;;# x + y
      ;;fetch x with coordinates (1,0) ((<jumps until right env level, starting from higher level>, <#var>))
      ;aload 4
      ;getfield frame_1/sl Lframe_0;
      ;getfield frame_0/x I


      ;;fetch y with coordinates (0,0) ((<jumps until right env level, starting from higher level>, <#var>))
      ;aload 4
      ;getfield frame_1/y I

      


      
      ;aload 4
      ;getfield frame_1/sl Lframe_0;
      ;astore 4

      ;aload 4
      ;getfield frame_0/sl Ljava/lang/Object;
      ;astore 4

      ; convert to String;
      invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
      ; call println 
      invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

      return
.end method
