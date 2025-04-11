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

    private static Stream<Arguments> provideKeysForInsert() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5),
                Arguments.of(6),
                Arguments.of(10),
                Arguments.of(20)
        );
    }

    @ParameterizedTest
    @MethodSource("provideKeysForInsert")
    void testInsert(int key) {
        SplayTree tree = new SplayTree();

        provideKeysForInsert()
                .map(args -> (int) args.get()[0])
                .forEach(tree::insert);

        assertNotNull(tree.search(key));
    }

    private static Stream<Arguments> provideKeysForSearch() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, true),
                Arguments.of(3, true),
                Arguments.of(4, false),
                Arguments.of(5, true),
                Arguments.of(6, true),
                Arguments.of(7, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideKeysForSearch")
    void testSearch(int key, boolean shouldExist) {
        SplayTree tree = new SplayTree();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(5);
        tree.insert(6);

        if (shouldExist) {
            assertNotNull(tree.search(key));
        } else {
            assertNull(tree.search(key));
        }
    }

    private static Stream<Arguments> provideKeysForDelete() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5),
                Arguments.of(6),
                Arguments.of(7),
                Arguments.of(8),
                Arguments.of(9),
                Arguments.of(10)
        );
    }

    @ParameterizedTest
    @MethodSource("provideKeysForDelete")
    void testDelete(int keyToDelete) {
        SplayTree tree = new SplayTree();

        provideKeysForDelete()
                .map(args -> (int) args.get()[0])
                .forEach(tree::insert);

        tree.delete(keyToDelete);

        assertNull(tree.search(keyToDelete));

        provideKeysForDelete()
                .map(args -> (int) args.get()[0])
                .filter(key -> key != keyToDelete)
                .forEach(key -> assertNotNull(
                        tree.search(key)
                ));
    }

    // Белый ящик

    @Test
    void testSplayOperation() {
        SplayTree tree = new SplayTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        // проверяю, что элемент, который искали, стал корнем
        tree.search(20);
        assertEquals(20, tree.getRoot().getKey());

        tree.search(10);
        assertEquals(10, tree.getRoot().getKey());
    }

    @Test
    void testRotateRight() {
        SplayTree tree = new SplayTree();

        Node root = new Node(10);
        root.setLeft(new Node(5));
        root.getLeft().setLeft(new Node(3));

        Node newRoot = tree.rotateRight(root);

        assertEquals(5, newRoot.getKey()); // новый корень
        assertEquals(3, newRoot.getLeft().getKey()); // левый потомок
        assertEquals(10, newRoot.getRight().getKey()); // правый потомок
    }

    @Test
    void testRotateLeft() {
        SplayTree tree = new SplayTree();

        Node root = new Node(10);
        root.setRight(new Node(15));
        root.getRight().setRight(new Node(20));

        Node newRoot = tree.rotateLeft(root);

        assertEquals(15, newRoot.getKey());
        assertEquals(10, newRoot.getLeft().getKey());
        assertEquals(20, newRoot.getRight().getKey());
    }


    // Проверяем, что метод splay корректно выполняет операцию splay в случае zig-zig
    // (когда целевой узел является левым потомком левого потомка прародителя)
    @Test
    void testSplayZigZigLeft() {
        SplayTree tree = new SplayTree();

        Node root = new Node(30);
        root.setLeft(new Node(20));
        root.getLeft().setLeft(new Node(10));

        Node newRoot = tree.splay(root, 10);

        assertEquals(10, newRoot.getKey()); // Новый корень
        assertEquals(20, newRoot.getRight().getKey()); // Правый потомок
        assertEquals(30, newRoot.getRight().getRight().getKey()); // Правый потомок правого потомка
    }

    @Test
    void testSplayZigZigRight() {
        SplayTree tree = new SplayTree();

        Node root = new Node(10);
        root.setRight(new Node(20));
        root.getRight().setRight(new Node(30));

        Node newRoot = tree.splay(root, 30);

        assertEquals(30, newRoot.getKey());
        assertEquals(20, newRoot.getLeft().getKey());
        assertEquals(10, newRoot.getLeft().getLeft().getKey());
    }

    @Test
    void testSplayZigZagLeftRight() {
        SplayTree tree = new SplayTree();
        // целевым узлом должен стать 40
        //        50
        //       /   \
        //     30     70
        //    /  \   /  \
        //  20   40 60  80

        Node root = new Node(50);
        root.setLeft(new Node(30));
        root.getLeft().setLeft(new Node(20));
        root.getLeft().setRight(new Node(40));
        root.setRight(new Node(70));
        root.getRight().setLeft(new Node(60));
        root.getRight().setRight(new Node(80));

        // Выполняем splay для узла 40 (Zig-Zag случай: сначала вправо, потом влево)
        Node newRoot = tree.splay(root, 40);

        //       40
        //      /  \
        //    30    50
        //   /        \
        // 20         70
        //            / \
        //           60  80
        assertEquals(40, newRoot.getKey());

        // левая ветвь
        assertEquals(30, newRoot.getLeft().getKey());
        assertEquals(20, newRoot.getLeft().getLeft().getKey());

        // правая ветвь
        assertEquals(50, newRoot.getRight().getKey());
        assertEquals(70, newRoot.getRight().getRight().getKey());
        assertEquals(60, newRoot.getRight().getRight().getLeft().getKey());
        assertEquals(80, newRoot.getRight().getRight().getRight().getKey());
    }

    @Test
    void testSplayZigZagRightLeft() {
        SplayTree tree = new SplayTree();
        // целевым узлом должен стать 60
        //        50
        //       /  \
        //     30    70
        //    /  \  /  \
        //  20  40 60  80

        Node root = new Node(50);
        root.setLeft(new Node(30));
        root.getLeft().setLeft(new Node(20));
        root.getLeft().setRight(new Node(40));
        root.setRight(new Node(70));
        root.getRight().setLeft(new Node(60));
        root.getRight().setRight(new Node(80));

        // Выполняем splay(60) - Zig-Zag (правый-левый)
        Node newRoot = tree.splay(root, 60);

        // Ожидаемая структура после splay:
        //        60
        //       /  \
        //     50    70
        //    /       \
        //  30        80
        // /  \
        //20  40

        assertEquals(60, newRoot.getKey());

        // левая ветвь
        assertEquals(50, newRoot.getLeft().getKey());
        assertEquals(30, newRoot.getLeft().getLeft().getKey());
        assertEquals(20, newRoot.getLeft().getLeft().getLeft().getKey());
        assertEquals(40, newRoot.getLeft().getLeft().getRight().getKey());

        // правая ветвь
        assertEquals(70, newRoot.getRight().getKey());
        assertEquals(80, newRoot.getRight().getRight().getKey());
    }
}