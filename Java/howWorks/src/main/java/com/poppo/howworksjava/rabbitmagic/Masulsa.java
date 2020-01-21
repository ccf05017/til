package com.poppo.howworksjava.rabbitmagic;

public class Masulsa {

    public static void main(String[] args) {

        // 바이트 코드 조작 (무식한 방법)
        /*ClassLoader classLoader = Masulsa.class.getClassLoader();
        TypePool typePool = TypePool.Default.of(classLoader);
        try {
            new ByteBuddy().redefine(
                    typePool.describe("com.poppo.howworksjava.rabbitmagic.Moja").resolve(),
                    ClassFileLocator.ForClassLoader.of(classLoader))
                    .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
                    .make().saveIn(new File("/Users/saul/Documents/Development/til/Java/howWorks/target/classes"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println(new Moja().pullOut());
    }
}
