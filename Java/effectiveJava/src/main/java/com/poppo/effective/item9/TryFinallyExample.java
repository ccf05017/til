package com.poppo.effective.item9;

import java.io.*;

public class TryFinallyExample {

    static String firstLineOfFile(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        try {
            return bufferedReader.readLine();       // 어떤 이유로 readline 메서드에 오류가 발생하면 close()가 실패한다.
        } finally {
            bufferedReader.close();
        }
    }

    static void closeManyResources(String dst, String src) throws IOException {
        InputStream inputStream = new FileInputStream(src);
        try {
            OutputStream outputStream = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[100];
                int n;
                while ((n = inputStream.read(buf)) >= 0)
                    outputStream.write(buf, 0, n);
            } finally {
                outputStream.close();
            }
        } finally {
            inputStream.close();
        }
    }
}
