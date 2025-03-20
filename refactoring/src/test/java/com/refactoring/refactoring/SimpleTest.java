package com.refactoring.refactoring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleTest {

    static class Node {
        Integer value;

        Node(Integer value) {
            this.value = value;
        }

        static void addValue(Node node) {
            node.value += 10;
        }

        static Node newNode(Node node) {
            node = new Node(node.value);
            return node;
        }
    }

    @DisplayName("함수 내부에서 참조 값을 통한 값 변경은 적용이 된다.")
    @Test
    void test2() {
        // given
        Node node = new Node(100);

        // when
        Node.addValue(node);

        // then
        Assertions.assertThat(node.value).isEqualTo(110);
    }

    private void addValue(int n_arg) {
        n_arg += 10;
    }

    @DisplayName("함수 내부의 값 변경은 외부에 적용이 되지 않는다.")
    @Test
    void test() {
        // given
        int n = 100;

        // when
        addValue(n);

        // then
        Assertions.assertThat(n).isGreaterThan(100);
    }

    @DisplayName("함수 내부에서 복사된 참조 값을 바꾼다고 기존의 참조 주소가 변경되지는 않는다.")
    @Test
    void test1() {
        // given
        Node node = new Node(100);

        // when
        Node node2 = Node.newNode(node);

        // then
        Assertions.assertThat(node == node2).isFalse();
    }
}
