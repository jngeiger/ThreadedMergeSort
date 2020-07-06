import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MergeSort<T extends Comparable<T>> {
    public static void main(String[] args) throws InterruptedException {
        MergeSort m = new MergeSort();
        var x = new ArrayList<Integer>();
        Random dice = new Random(42);

        for (int i = 0; i < 90_000_000; i++) {
            x.add(dice.nextInt(500));
        }
        var y = System.currentTimeMillis();
        m.mergeSortThreaded(x);
        System.out.println(System.currentTimeMillis()-y);


    }

    public void mergeSortThreaded(List<T> list) throws InterruptedException {
        T[] array = (T[]) new Comparable[list.size()];
        Thread t1 = new Thread() {
            @Override
            public void run() {
                List sublist = list.subList(0,list.size()/4);
                _mergeSort(sublist,0,sublist.size()-1,array);
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                List sublist = list.subList(list.size()/4,list.size()/2);
                _mergeSort(sublist,0,sublist.size()-1,array);
            }
        };

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        _merge(list,0,list.size()/4,list.size()-1,array);


    }
    public void mergeSort(List<T> list)
    {
        T[] array = (T[])new Comparable[list.size()];
        _mergeSort(list,0,list.size()-1,array);
    }
    private void _mergeSort(List<T> list, int l, int r,T[] array)
    {
        if (r-l < 1)
            return;
        else {
            int m = (l+r)/2;
            _mergeSort(list,l,m,array);
            _mergeSort(list,m+1,r,array);
            _merge(list,l,m,r,array);
        }
    }

    private void _merge(List<T> list, int l, int m, int r, T[] array)
    {
        int lidx = l; int ridx = m+1; int idx = l;
        while (lidx <= m && ridx <= r)
        {
            if (list.get(lidx).compareTo(list.get(ridx)) < 0)
            {
                array[idx++] = list.get(lidx++);
            }
            else {
                array[idx++] = list.get(ridx++);
            }
        }
        while (lidx <= m)
        {
            array[idx++] = list.get(lidx++);
        }
        while (ridx <= r)
        {
            array[idx++] = list.get(ridx++);
        }
        for (int i = l; i <= r; i++)
        {
            list.set(i,array[i]);
        }
    }
}

