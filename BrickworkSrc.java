import java.util.Arrays;
import java.util.Scanner;

public class BrickworkSrc {
    public static void main(String[] args) throws IllegalAccessException {
        Scanner scan = new Scanner(System.in);

        // Read the input array with first layer's dimensions
        int[] dimensions = Arrays
                .stream(scan.nextLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        int rows = dimensions[0];
        int cols = dimensions[1];

        // Check if the input is correct
        validateRowsOrColsDoNotExceed100 (rows, cols);

        // Initialize first layer of bricks
        int[][] firstLayer = new int[rows][cols];

        // Creating method to read the first layer from the console
        readFirstLayer(scan, firstLayer, rows, cols);

        // Validate there are no bricks spanning more than three columns
        noBricksSpanningThreeColumns(rows, cols, firstLayer);

        // If there are more than two rows, validate no brick is spanning more than two
        validateRows(rows, cols, firstLayer);

        // Initialize second layer
        int[][] secondLayer = new int[rows][cols];

        // A method that returns a possible solution
        layoutOfSecondLayer (firstLayer, secondLayer, rows, cols);

        // Printing the solution
        printSecondLayer (rows, cols, secondLayer);
    }

    private static void printSecondLayer(int rows, int cols, int[][] secondLayer) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                System.out.print(secondLayer[i][j] + " ");
            System.out.println();
        }
    }

    private static void layoutOfSecondLayer(int[][] firstLayer, int[][] secondLayer,
                                            int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (firstLayer[row][col] == firstLayer[row][col + 1]) {
                    secondLayer[row][col] = firstLayer[row][col];
                    secondLayer[row + 1][col] = firstLayer[row][col + 1];
                    secondLayer[row][col + 1] = firstLayer[row][col + 2];
                    secondLayer[row][col + 2] = firstLayer[row][col + 3];
                    secondLayer[row + 1][col + 1] = firstLayer[row + 1][col + 2];
                    secondLayer[row + 1][col + 2] = firstLayer[row + 1][col + 3];
                    secondLayer[row][col + 3] = firstLayer[row + 1][col];
                    secondLayer[row + 1][col + 3] = firstLayer[row + 1][col + 1];
                } else if (firstLayer[row][col] == firstLayer[row + 1][col]) {
                    secondLayer[row][col] = firstLayer[row][col];
                    secondLayer[row][col + 1] = firstLayer[row + 1][col];
                    secondLayer[row + 1][col] = firstLayer[row + 1][col + 1];
                    secondLayer[row + 1][col + 1] = firstLayer[row + 1][col + 2];
                    secondLayer[row][col + 2] = firstLayer[row][col + 1];
                    secondLayer[row][col + 3] = firstLayer[row][col + 2];
                    secondLayer[row + 1][col + 2] = firstLayer[row][col + 3];
                    secondLayer[row + 1][col + 3] = firstLayer[row + 1][col + 3];
                }
                col = col + 3;
            }
            row = row + 1;
        }
    }

    private static void validateRows(int rows, int cols, int[][] firstLayer)
            throws IllegalAccessException {
        if (rows > 2) {
            for (int row = 0; row < rows - 2; row++) {
                for (int col = 0; col < cols; col++) {
                    int firstElement = firstLayer[row][col];
                    int secondElement = firstLayer[row + 1][col];
                    int thirdElement = firstLayer[row + 2][col];
                    if (firstElement == secondElement && secondElement == thirdElement) {
                        throw new IllegalAccessException("- 1. No solution exists.");
                    }
                }
            }
        }
    }

    private static void validateRowsOrColsDoNotExceed100(int rows, int cols) throws IllegalAccessException {
        if (rows >= 100 || cols >= 100) {
            throw new IllegalAccessException("- 1. No solution exists.");
        }
    }

    private static void noBricksSpanningThreeColumns(int rows, int cols, int[][] firstLayer) throws IllegalAccessException {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols - 2; col++) {
                int firstElement = firstLayer[row][col];
                int secondElement = firstLayer[row][col + 1];
                int thirdElement = firstLayer[row][col + 2];
                // if there are bricks spanning more than three columns
                if (firstElement == secondElement && secondElement == thirdElement) {
                    throw new IllegalAccessException("- 1. No solution exists.");
                }
            }
        }
    }

    private static void readFirstLayer(Scanner scan, int[][] firstLayer, int rows, int cols) throws IllegalAccessException {
        for (int row = 0; row < rows; row++) {
            String[] input = scan.nextLine().split(" ");
            validateNumberOfCols(input, cols);
            for (int col = 0; col < cols; col++) {
                firstLayer[row][col] = Integer.parseInt(input[col]);
            }
        }
    }

    private static void validateNumberOfCols(String[] input, int cols) throws IllegalAccessException {
        if (input.length > cols) {
            throw new IllegalAccessException("- 1. No solution exists.");
        }
    }
}