package com.poppo.effective.item23.subtype;

abstract class Figure {
    abstract double area();
}

// 하위 타입 명시를 통해 현재 Figure가 어떤 상태인지 알 수 있다.
class Circle extends Figure {
    // 태그 달린 클래스와 다르게 꼭 필요한 필드만 사용할 수 있다.
    // 덕분에 final로 선언할 수 있다.
    final double radius;

    public Circle(final double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

class Rectangle extends Figure {
    final double length;
    final double width;

    public Rectangle(final double length, final double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}
