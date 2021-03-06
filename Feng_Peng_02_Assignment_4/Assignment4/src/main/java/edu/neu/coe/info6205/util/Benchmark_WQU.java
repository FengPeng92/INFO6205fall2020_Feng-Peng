package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.union_find.WQU_ByDepth;
import edu.neu.coe.info6205.union_find.WQU_BySize;
import edu.neu.coe.info6205.union_find.WQU_OnePath;
import edu.neu.coe.info6205.union_find.WQU_TwoPath;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Benchmark_WQU {

    private static int bySizeSum = 0, byDepthSum = 0, onePathSum = 0, twoPathSum = 0;

    //Run Weighted Quick Union by size(without path compression)
    public static void test_WQU_BySize(int n) {
        WQU_BySize bySize = new WQU_BySize(n);
        Random random = new Random();

        while (bySize.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!bySize.connected(first, second)) {
                bySize.union(first, second);
            }
        }
        bySizeSum += bySize.getDepth();
    }

    //Run Weighted Quick Union by depth(without path compression)
    public static void test_WQU_ByDepth(int n) {
        WQU_ByDepth byDepth = new WQU_ByDepth(n);
        Random random = new Random();

        while (byDepth.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!byDepth.connected(first, second)) {
                byDepth.union(first, second);
            }
        }
        byDepthSum += byDepth.getMaxDepth();
    }

    //Run Weighted Quick Union with Path Compression(One Path)
    public static void test_WQU_OnePath(int n) {
        WQU_OnePath onePath = new WQU_OnePath(n);
        Random random = new Random();

        while (onePath.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!onePath.connected(first, second)) {
                onePath.union(first, second);
            }
        }
        onePathSum += onePath.getDepth();
    }

    //Run Weighted Quick Union with Path Compression
    public static void test_WQU_TwoPath(int n) {
        WQU_TwoPath twoPath = new WQU_TwoPath(n);
        Random random = new Random();

        while (twoPath.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!twoPath.connected(first, second)) {
                twoPath.union(first, second);
            }
        }
        twoPathSum += twoPath.getDepth();
    }

    public static void main(String[] args) {
        Benchmark_Timer<Integer> benchmark_timer_WQU_BySize;
        Benchmark_Timer<Integer> benchmark_timer_WQU_ByDepth;
        Benchmark_Timer<Integer> benchmark_timer_WQU_OnePath;
        Benchmark_Timer<Integer> benchmark_timer_WQU_TwoPath;

        Consumer<Integer> run_WQU_BySize = xs -> test_WQU_BySize(xs);
        Consumer<Integer> run_WQU_ByDepth = xs -> test_WQU_ByDepth(xs);
        Consumer<Integer> run_WQU_OnePath = xs -> test_WQU_OnePath(xs);
        Consumer<Integer> run_WQU_TwoPath = xs -> test_WQU_TwoPath(xs);

        int[] n = {1000, 2000, 4000, 8000, 16000, 32000, 64000};
        int m = 100;

        benchmark_timer_WQU_BySize = new Benchmark_Timer<>("Weighted Quick Union by Size(without path compression)"
                ,null, run_WQU_BySize, null);
        benchmark_timer_WQU_ByDepth = new Benchmark_Timer<>("Weighted Quick Union by Depth(without path compression)"
                ,null, run_WQU_ByDepth, null);
        benchmark_timer_WQU_OnePath = new Benchmark_Timer<>("Weighted Quick Union with Path Compression（One Path"
                ,null, run_WQU_OnePath, null);
        benchmark_timer_WQU_TwoPath = new Benchmark_Timer<>("Weighted Quick Union with Path Compression (Two Path)"
                ,null, run_WQU_TwoPath, null);

        for (int i = 0; i < n.length; i++) {
            int temp = n[i];
            Supplier<Integer> supplier = () -> temp;

            double bySizeTime = benchmark_timer_WQU_BySize.runFromSupplier(supplier, m);
            double byDepthTime = benchmark_timer_WQU_ByDepth.runFromSupplier(supplier, m);
            double onePathTime = benchmark_timer_WQU_OnePath.runFromSupplier(supplier, m);
            double twoPathTime = benchmark_timer_WQU_TwoPath.runFromSupplier(supplier, m);

            double averageBySizeTime = bySizeSum / (m * 1.0 + 2.0);
            double averageByDepthTime = byDepthSum / (m * 1.0 + 2.0);
            double averageOnePathSum = onePathSum / (m * 1.0 + 2.0);
            double averageTwoPathSum = twoPathSum / (m * 1.0 + 2.0);

            System.out.println("n = " + n[i]);
            System.out.println("Average time without path compression(By Size): " + bySizeTime + " Average depth without path compression(By Size): " + averageBySizeTime);
            System.out.println("Average time without path compression(By Depth): " + byDepthTime + " Average depth without path compression(By Depth): " + averageByDepthTime);
            System.out.println("Average time with path compression(one path): " + onePathTime + " Average depth with path compression(one path): " + averageOnePathSum);
            System.out.println("Average time with path compression(two path): " + twoPathTime + " Average depth with path compression(two path): " + averageTwoPathSum);
            System.out.println();
            bySizeSum = 0;
            byDepthSum = 0;
            onePathSum = 0;
            twoPathSum = 0;
        }
    }

}
