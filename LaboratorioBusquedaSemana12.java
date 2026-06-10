import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LaboratorioBusquedaSemana12 {

    static class SearchResult {
        String algorithm;
        int target;
        boolean found;
        int index;
        int comparisons;
        long nanos;
        List<String> trace = new ArrayList<>();

        SearchResult(String algorithm, int target) {
            this.algorithm = algorithm;
            this.target = target;
            this.index = -1;
        }
    }

    public static SearchResult linearSearch(int[] data, int target) {
        SearchResult result = new SearchResult("Busqueda lineal", target);
        long start = System.nanoTime();

        for (int i = 0; i < data.length; i++) {
            result.comparisons++;
            result.trace.add("i=" + i + " | valor=" + data[i] + " | comparo con " + target);

            if (data[i] == target) {
                result.found = true;
                result.index = i;
                break;
            }
        }

        result.nanos = System.nanoTime() - start;
        return result;
    }

    public static SearchResult binarySearch(int[] data, int target) {
        SearchResult result = new SearchResult("Busqueda binaria", target);
        int left = 0;
        int right = data.length - 1;

        long start = System.nanoTime();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            result.comparisons++;

            result.trace.add(
                "izq=" + left +
                " | der=" + right +
                " | medio=" + mid +
                " | valorMedio=" + data[mid] +
                " | busco=" + target
            );

            if (data[mid] == target) {
                result.found = true;
                result.index = mid;
                break;
            }

            if (data[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        result.nanos = System.nanoTime() - start;
        return result;
    }

    private static int[] buildEvenArray(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = (i + 1) * 2;
        }
        return data;
    }

    private static void runCase(String label, int[] data, int target, int maxTraceLines) {
        System.out.println("\n==================================================");
        System.out.println(label);
        System.out.println("Arreglo ordenado: " + preview(data));
        System.out.println("Codigo buscado: " + target);

        SearchResult linear = linearSearch(data, target);
        SearchResult binary = binarySearch(data, target);

        printResult(linear, maxTraceLines);
        printResult(binary, maxTraceLines);

        System.out.println("Comparacion final:");
        System.out.println("- Lineal: " + linear.comparisons + " comparaciones");
        System.out.println("- Binaria: " + binary.comparisons + " comparaciones");
    }

    private static void printResult(SearchResult result, int maxTraceLines) {
        System.out.println("\n" + result.algorithm);
        System.out.println("Encontrado: " + result.found);
        System.out.println("Indice: " + result.index);
        System.out.println("Comparaciones: " + result.comparisons);
        System.out.println("Tiempo aproximado ns: " + result.nanos);
        System.out.println("Traza:");
        int limit = Math.min(result.trace.size(), maxTraceLines);
        for (int i = 0; i < limit; i++) {
            System.out.println("  " + (i + 1) + ") " + result.trace.get(i));
        }
        if (result.trace.size() > limit) {
            System.out.println("  ... traza recortada para lectura en pantalla ...");
        }
    }

    private static String preview(int[] data) {
        if (data.length <= 20) {
            return Arrays.toString(data);
        }
        int[] start = Arrays.copyOfRange(data, 0, 10);
        int[] end = Arrays.copyOfRange(data, data.length - 5, data.length);
        return Arrays.toString(start) + " ... " + Arrays.toString(end) + " | n=" + data.length;
    }

    public static void main(String[] args) {
        int[] productCodes = {3, 7, 12, 18, 23, 27, 34, 39, 45, 52, 61, 76};

        runCase("CASO 1: codigo existente en zona central", productCodes, 23, 20);
        runCase("CASO 2: codigo existente al inicio", productCodes, 3, 20);
        runCase("CASO 3: codigo inexistente", productCodes, 41, 20);

        // Array de 1M de elementos creados con números pares a partir de lo solicitado en el laboratorio.
        int[] largeData = buildEvenArray(1000000);
        runCase("CASO 4: arreglo grande, codigo casi al final", largeData, 500009, 10);
    }
}