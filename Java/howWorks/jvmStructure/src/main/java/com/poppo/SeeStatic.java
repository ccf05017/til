package com.poppo;

public class SeeStatic {

    public void run() {

        // 이런 짓을 할 수 있는 건 클래스 로더 덕분임
        System.out.println(App.name);
    }
}
