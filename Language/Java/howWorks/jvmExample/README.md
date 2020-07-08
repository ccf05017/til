# JVM 시리즈 친구들
- 코틀린도 JVM 바이트 코드를 볼 수 있다.(javap -c HelloKt)
- 코틀린은 자바랑 다르게 클래스명과 파일 이름이 일치할 필요도 없다.
```java
Compiled from "hello.kt"
public final class HelloKt {
  public static final void main(java.lang.String[]);
    Code:
       0: aload_0
       1: ldc           #9                  // String args
       3: invokestatic  #15                 // Method kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
       6: ldc           #17                 // String kotlin can work on jvm
       8: astore_1
       9: iconst_0
      10: istore_2
      11: getstatic     #23                 // Field java/lang/System.out:Ljava/io/PrintStream;
      14: aload_1
      15: invokevirtual #29                 // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
      18: return
}
```
