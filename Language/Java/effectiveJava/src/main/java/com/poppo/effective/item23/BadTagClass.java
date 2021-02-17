package com.poppo.effective.item23;

// 심지어 이름까지 이렇게 지어놓으면 이게 뭔지 해당 클래스의 인스턴스를 보고 도통 알 방법이 없다.
public class BadTagClass {
    enum Shape { RECTANGLE, CIRCLE }

    final Shape shape;

    // 사각형일 때만 필요한 필드 (쓸데 없는 필드까지 갖고 있다)
    double length;
    double width;

    // 원일 때만 필요한 필드 (쓸데 없는 필드까지 갖고 있다)
    double radius;

    public BadTagClass(final double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    public BadTagClass(final double length, final double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        // 새로운 타입이 정의될 때마다 switch문을 눈알 빠지도록 봐야 한다.
        // switch문 자체가 이미 해당 메서드에서 SRP를 위반했을 확률이 높다.
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
