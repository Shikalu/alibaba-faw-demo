package com.ebanma.cloud.guava;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 鹿胜宝
 * @date 2023/03/01
 */
public class ImmutableTest {

    @Test
    public void immutable() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ImmutableSet.copyOf(list);
        ImmutableSet<Integer> immutableSet = ImmutableSet.of(1, 2, 3);
        ImmutableSet.builder()
                .add(1)
                .addAll(Sets.newHashSet(2,3))
                .add(4)
                .build();
        ArrayList<Integer> arrayList = Lists.newArrayList(1, 2, 3, 4, 5);
        System.out.println(arrayList);
    }
}
