package task2;

import com.djeno.task2.Node;
import com.djeno.task2.SplayTree;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SplayTreeTest {

    // Черный ящик
    @Test
    void testInsert() {
        SplayTree tree = new SplayTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        assertNotNull(tree.search(10));
        assertNotNull(tree.search(20));
        assertNotNull(tree.search(30));
        assertNull(tree.search(40));
    }

    @Test
    void testSearch() {
        SplayTree tree = new SplayTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        assertEquals(10, tree.search(10).getKey());
        assertEquals(20, tree.search(20).getKey());
        assertEquals(30, tree.search(30).getKey());
        assertNull(tree.search(40));
    }

    @Test
    void testDelete() {
        SplayTree tree = new SplayTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        tree.delete(20);
        assertNull(tree.search(20));
        assertNotNull(tree.search(10));
        assertNotNull(tree.search(30));
    }

    @Test
    void testSplayOperation() {
        SplayTree tree = new SplayTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        // Проверяем, что поиск перемещает элемент в корень
        tree.search(20);
        assertEquals(20, tree.getRoot().getKey()); // 20 теперь корень

        tree.search(10);
        assertEquals(10, tree.getRoot().getKey()); // 10 теперь корень
    }

    // Параметризованные тесты
    @ParameterizedTest
    @MethodSource("provideKeysForInsert")
    void testInsert(int key) {
        SplayTree tree = new SplayTree();
        tree.insert(key);

        // Проверяем, что элемент добавлен
        assertNotNull(tree.search(key));
    }

    @ParameterizedTest
    @MethodSource("provideKeysForSearch")
    void testSearch(int key, boolean shouldExist) {
        SplayTree tree = new SplayTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        // Проверяем поиск элемента
        if (shouldExist) {
            assertNotNull(tree.search(key));
        } else {
            assertNull(tree.search(key));
        }
    }

    @ParameterizedTest
    @MethodSource("provideKeysForDelete")
    void testDelete(int keyToDelete, int keyToCheck) {
        SplayTree tree = new SplayTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        // Удаляем элемент и проверяем
        tree.delete(keyToDelete);
        assertNull(tree.search(keyToDelete)); // Элемент удален
        assertNotNull(tree.search(keyToCheck)); // Другой элемент на месте
    }

    private static Stream<Arguments> provideKeysForInsert() {
        return Stream.of(
                Arguments.of(10),
                Arguments.of(20),
                Arguments.of(30)
        );
    }

    private static Stream<Arguments> provideKeysForSearch() {
        return Stream.of(
                Arguments.of(10, true),
                Arguments.of(20, true),
                Arguments.of(30, true),
                Arguments.of(40, false)
        );
    }

    private static Stream<Arguments> provideKeysForDelete() {
        return Stream.of(
                Arguments.of(20, 10),
                Arguments.of(30, 20),
                Arguments.of(10, 30)
        );
    }

    // Белый ящик

    @Test
    void testRotateRight() {
        SplayTree tree = new SplayTree();

        // Создаем дерево:
        //     10
        //    /
        //   5
        //  /
        // 3
        Node root = new Node(10);
        root.setLeft(new Node(5));
        root.getLeft().setLeft(new Node(3));

        // Выполняем правый поворот вокруг корня (10)
        Node newRoot = tree.rotateRight(root);

        // Проверяем новую структуру:
        //     5
        //    / \
        //   3   10
        assertEquals(5, newRoot.getKey()); // Новый корень
        assertEquals(3, newRoot.getLeft().getKey()); // Левый потомок
        assertEquals(10, newRoot.getRight().getKey()); // Правый потомок
    }

    @Test
    void testRotateLeft() {
        SplayTree tree = new SplayTree();

        // Создаем дерево:
        //   10
        //    \
        //     15
        //      \
        //       20
        Node root = new Node(10);
        root.setRight(new Node(15));
        root.getRight().setRight(new Node(20));

        // Выполняем левый поворот вокруг корня (10)
        Node newRoot = tree.rotateLeft(root);

        // Проверяем новую структуру:
        //     15
        //    /  \
        //   10   20
        assertEquals(15, newRoot.getKey()); // Новый корень
        assertEquals(10, newRoot.getLeft().getKey()); // Левый потомок
        assertEquals(20, newRoot.getRight().getKey()); // Правый потомок
    }

    // Проверяем, что метод splay корректно выполняет операцию splay в случае zig-zig
    // (когда целевой узел находится в левом поддереве левого поддерева).
    @Test
    void testSplayZigZig() {
        SplayTree tree = new SplayTree();

        // Создаем дерево:
        //       30
        //      /
        //     20
        //    /
        //   10
        Node root = new Node(30);
        root.setLeft(new Node(20));
        root.getLeft().setLeft(new Node(10));

        // Выполняем splay для ключа 10
        Node newRoot = tree.splay(root, 10);

        // Проверяем новую структуру:
        //     10
        //      \
        //       20
        //        \
        //         30
        assertEquals(10, newRoot.getKey()); // Новый корень
        assertEquals(20, newRoot.getRight().getKey()); // Правый потомок
        assertEquals(30, newRoot.getRight().getRight().getKey()); // Правый потомок правого потомка
    }

    // Проверяем, что метод splay корректно выполняет операцию splay в случае zig-zig
    // (когда целевой узел находится в левом поддереве левого поддерева).
    @Test
    void testSplayZigZigLeft() {
        SplayTree tree = new SplayTree();

        // Создаем дерево:
        //       30
        //      /
        //     20
        //    /
        //   10
        Node root = new Node(30);
        root.setLeft(new Node(20));
        root.getLeft().setLeft(new Node(10));

        // Выполняем splay для ключа 10
        Node newRoot = tree.splay(root, 10);

        // Проверяем новую структуру:
        //     10
        //      \
        //       20
        //        \
        //         30
        assertEquals(10, newRoot.getKey()); // Новый корень
        assertEquals(20, newRoot.getRight().getKey()); // Правый потомок
        assertEquals(30, newRoot.getRight().getRight().getKey()); // Правый потомок правого потомка
    }

    @Test
    void testSplayZigZag() {
        SplayTree tree = new SplayTree();

        // Создаем дерево:
        //     30
        //    /
        //   10
        //    \
        //     20
        Node root = new Node(30);
        root.setLeft(new Node(10));
        root.getLeft().setRight(new Node(20));

        // Выполняем splay для ключа 20
        Node newRoot = tree.splay(root, 20);

        // Проверяем новую структуру:
        //     20
        //    /  \
        //   10   30
        assertEquals(20, newRoot.getKey()); // Новый корень
        assertEquals(10, newRoot.getLeft().getKey()); // Левый потомок
        assertEquals(30, newRoot.getRight().getKey()); // Правый потомок
    }

    @Test
    void whiteTestInsert() {
        SplayTree tree = new SplayTree();

        // Вставляем элементы
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        // Проверяем, что последний вставленный элемент находится в корне
        assertEquals(30, tree.getRoot().getKey());

        // Проверяем структуру дерева:
        //     30
        //    /
        //   20
        //  /
        // 10
        assertEquals(20, tree.getRoot().getLeft().getKey());
        assertEquals(10, tree.getRoot().getLeft().getLeft().getKey());
    }

    @Test
    void whiteTestSearch() {
        SplayTree tree = new SplayTree();

        // Вставляем элементы
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        // Ищем элемент 20
        Node result = tree.search(20);

        // Проверяем, что элемент найден и находится в корне
        assertNotNull(result);
        assertEquals(20, tree.getRoot().getKey());

        // Проверяем структуру дерева:
        //     20
        //    /  \
        //   10   30
        assertEquals(10, tree.getRoot().getLeft().getKey());
        assertEquals(30, tree.getRoot().getRight().getKey());
    }

    @Test
    void whiteTestDelete() {
        SplayTree tree = new SplayTree();

        // Вставляем элементы
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        // Удаляем элемент 20
        tree.delete(20);

        // Проверяем, что элемент удален
        assertNull(tree.search(20));

        // Проверяем структуру дерева:
        //     10
        //      \
        //       30
        assertEquals(10, tree.getRoot().getKey());
        assertEquals(30, tree.getRoot().getRight().getKey());
    }
}